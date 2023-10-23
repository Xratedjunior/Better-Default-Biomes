package xratedjunior.betterdefaultbiomes;

import java.io.File;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.compat.BDBVanillaCompat;
import xratedjunior.betterdefaultbiomes.configuration.BDBModConfig;
import xratedjunior.betterdefaultbiomes.configuration.GenerationConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfigRegistry;
import xratedjunior.betterdefaultbiomes.configuration.extra.BiomeTypeInfo;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.event.GlowingEntitiesEvent;
import xratedjunior.betterdefaultbiomes.entity.projectile.dispenser.CustomDispenserBehavior;
import xratedjunior.betterdefaultbiomes.item.BDBItems;
import xratedjunior.betterdefaultbiomes.item.FuelEventHandler;
import xratedjunior.betterdefaultbiomes.item.recipeconditions.EnhancedMushroomsCondition;
import xratedjunior.betterdefaultbiomes.proxy.ClientProxy;
import xratedjunior.betterdefaultbiomes.proxy.CommonProxy;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BDBConfiguredFeatures;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BDBFeaturePlacements;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BDBFeatures;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BDBPlacementModifierTypes;

/**
 * TODO Remove Biome Categories from Wiki sidebar and add 1.18 information. (Make sure older wiki info is still available)
 * TODO Initialize EventBusSubscribers from main class
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@Mod(value = BetterDefaultBiomes.MOD_ID)
public class BetterDefaultBiomes {
	public static final String MOD_ID = "betterdefaultbiomes";
	public static final BDBItemGroup BETTERDEFAULTBIOMESTAB = new BDBItemGroup();
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	// Used to create the configuration folder
	private static final String BDBFolderName = MOD_ID;
	private static final File BDBFolder = new File(FMLPaths.CONFIGDIR.get().toFile(), BDBFolderName);
	public static final Path BDBFolderPath = BDBFolder.toPath();

	@SuppressWarnings("deprecation")
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	public BetterDefaultBiomes() {
		// Create BetterDefaultBiomes folder if it doesn't exist yet.
		if (!BDBFolder.exists()) {
			BDBFolder.mkdirs();
		}

		// Register the configuration files.
		ModLoadingContext.get().registerConfig(Type.COMMON, BDBModConfig.COMMON_SPEC, BDBFolderName + "/" + MOD_ID + ".toml");
		ModLoadingContext.get().registerConfig(Type.COMMON, GenerationConfig.COMMON_SPEC, BDBFolderName + "/" + GenerationConfig.CONFIG_FILE_NAME + ".toml");

		// List is in order of registration.
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		BDBSoundEvents.DEFERRED_SOUND_EVENTS.register(modEventBus);
		BDBBlocks.DEFERRED_BLOCKS.register(modEventBus);
		BDBItems.DEFERRED_ITEMS.register(modEventBus);
		BDBEntityTypes.DEFERRED_ENTITY_TYPES.register(modEventBus);
		BDBFeatures.DEFERRED_FEATURES.register(modEventBus);
		BDBConfiguredFeatures.DEFERRED_CONFIGURED_FEATURES.register(modEventBus);
		BDBFeaturePlacements.DEFERRED_PLACED_FEATURES.register(modEventBus);
		BDBPlacementModifierTypes.DEFERRED_PLACEMENT_MODIFIER_TYPES.register(modEventBus);

		modEventBus.addListener(this::gatherData);
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::loadComplete);

		// Register Recipe Condition
		CraftingHelper.register(new EnhancedMushroomsCondition.Serializer());
	}

	// TODO Can be after first release 
	@SuppressWarnings("unused")
	private void gatherData(final GatherDataEvent event) {
		LOGGER.debug("Gathering Data");

		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeClient()) {
			//			generator.addProvider(new BlockModelsBDB(generator, existingFileHelper));
			//			generator.addProvider(new BlockStatesAndModelsBDB(generator, existingFileHelper));
		}

		LOGGER.debug("gatherData method completed");
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		LOGGER.debug("clientSetup method completed");
	}

	/*
	 * Will run at launch (preInit)
	 */
	private void commonSetup(final FMLCommonSetupEvent event) {
		// Register event that gives Entities the Glowing effect.
		MinecraftForge.EVENT_BUS.register(new GlowingEntitiesEvent());
		MinecraftForge.EVENT_BUS.register(new FuelEventHandler());

		// Run on the main thread after the 'FMLCommonSetupEvent' is completed
		event.enqueueWork(() -> {
			LOGGER.debug("Starting common work queue");
			// Registers the breeding files.
			BreedingConfigRegistry.constructBreedingConfigs();

			// Register Blocks and Items as fuel.
			FuelEventHandler.registerFuels();

			// Register strippable, compostable and Flammable Items. 
			BDBVanillaCompat.setup();

			// Register behavior for Items like Arrows when fired from a Dispenser.
			CustomDispenserBehavior.init();

			// Add potted plants functionality.
			BetterDefaultBiomes.LOGGER.debug("Register Potted Plants");
			BDBBlocks.POTTED_PLANTS.forEach((flower, pottedFlower) -> ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(flower.get().getRegistryName(), pottedFlower));
		});

		LOGGER.debug("commonSetup method completed");
	}

	/*
	 * PostRegistrationEvent
	 */
	private void loadComplete(final FMLLoadCompleteEvent event) {
		// Generate the text file with BiomeType information.
		BiomeTypeInfo.makeInfoTextFiles();

		proxy.init();
		LOGGER.debug("loadComplete method completed");
	}

	/**
	 * Gets a resource location for Better Default Biomes.
	 */
	public static ResourceLocation locate(String name) {
		return new ResourceLocation(MOD_ID, name);
	}

	public static String find(String key) {
		return new String(MOD_ID + ":" + key);
	}
}
