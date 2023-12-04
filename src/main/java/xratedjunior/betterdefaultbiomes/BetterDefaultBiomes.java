package xratedjunior.betterdefaultbiomes;

import java.io.File;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
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
import net.minecraftforge.registries.ForgeRegistries;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.block.property.BDBWoodTypes;
import xratedjunior.betterdefaultbiomes.compat.BDBVanillaCompat;
import xratedjunior.betterdefaultbiomes.configuration.BDBModConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfigRegistry;
import xratedjunior.betterdefaultbiomes.configuration.extra.InfoFileEventHandler;
import xratedjunior.betterdefaultbiomes.datagen.BDBDataGenerators;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;
import xratedjunior.betterdefaultbiomes.enchantment.EnchantmentEventSubscriber;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.event.GlowingEntitiesEvent;
import xratedjunior.betterdefaultbiomes.entity.projectile.dispenser.CustomDispenserBehavior;
import xratedjunior.betterdefaultbiomes.item.BDBItems;
import xratedjunior.betterdefaultbiomes.item.FuelEventHandler;
import xratedjunior.betterdefaultbiomes.loot.BDBGlobalLootModifiers;
import xratedjunior.betterdefaultbiomes.proxy.ClientProxy;
import xratedjunior.betterdefaultbiomes.proxy.CommonProxy;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;
import xratedjunior.betterdefaultbiomes.trade.BDBVillagerTradesEvent;
import xratedjunior.betterdefaultbiomes.world.BDBBiomeModifiers;
import xratedjunior.betterdefaultbiomes.world.generation.BDBFeatures;
import xratedjunior.betterdefaultbiomes.world.generation.BDBPlacementModifierTypes;

/**
 * UPDATE CHECK COMPAT FOR CREATE AND FARMERSDELIGHT!! (Not updated to 1.20.2 yet)
 * UPDATE Make DataGenerator for Farmer's Delight Tag
 * TODO Remove Biome Categories from Wiki sidebar and add 1.18 information. (Make sure older wiki info is still available)
 * TODO Initialize EventBusSubscribers from main class
 * UPDATE Make datapack available for download on discord for people to configure
 * 
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
@Mod(value = BetterDefaultBiomes.MOD_ID)
public class BetterDefaultBiomes {
	public static final String MOD_ID = "betterdefaultbiomes";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	// Used to create the configuration folder
	private static final String BDBFolderName = MOD_ID;
	private static final File BDBFolder = new File(FMLPaths.CONFIGDIR.get().toFile(), BDBFolderName);
	public static final Path BDBFolderPath = BDBFolder.toPath();

	@SuppressWarnings("deprecation")
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	public BetterDefaultBiomes() {
		/*********************************************************** Config ********************************************************/

		// Create BetterDefaultBiomes folder if it doesn't exist yet.
		if (!BDBFolder.exists()) {
			BDBFolder.mkdirs();
		}

		// Register the configuration files.
		ModLoadingContext.get().registerConfig(Type.COMMON, BDBModConfig.COMMON_SPEC, BDBFolderName + "/" + MOD_ID + ".toml");

		/*********************************************************** Deferred registers ********************************************************/

		// List is in order of registration.
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		BDBEnchantments.DEFERRED_ENCHANTMENTS.register(modEventBus);
		BDBSoundEvents.DEFERRED_SOUND_EVENTS.register(modEventBus);
		BDBBlocks.DEFERRED_BLOCKS.register(modEventBus);
		BDBItems.DEFERRED_ITEMS.register(modEventBus);
		BDBEntityTypes.DEFERRED_ENTITY_TYPES.register(modEventBus);

		BDBFeatures.DEFERRED_FEATURES.register(modEventBus);
		BDBPlacementModifierTypes.DEFERRED_PLACEMENT_MODIFIER_TYPES.register(modEventBus);
		BDBBiomeModifiers.DEFERRED_BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
		BDBGlobalLootModifiers.DEFERRED_GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

		/*********************************************************** Event Listeners ********************************************************/

		// General event listeners
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::loadComplete);

		// Data generators
		modEventBus.addListener(BDBDataGenerators::gatherData);

		// Register and fill Creative Mode Tab
		modEventBus.addListener(BDBCreativeModeTabs::registerCreativeModeTabs);
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		Sheets.addWoodType(BDBWoodTypes.PALM);
		Sheets.addWoodType(BDBWoodTypes.SWAMP_WILLOW);

		LOGGER.debug("clientSetup method completed");
	}

	/*
	 * Will run at launch (preInit)
	 */
	private void commonSetup(final FMLCommonSetupEvent event) {
		/*********************************************************** Event Subscribers ********************************************************/

		// Register event that gives Entities the Glowing effect.
		MinecraftForge.EVENT_BUS.register(new GlowingEntitiesEvent());
		// Register event for villager trades.
		MinecraftForge.EVENT_BUS.register(new BDBVillagerTradesEvent());
		// Register event for placing items in Furnace.
		MinecraftForge.EVENT_BUS.register(new FuelEventHandler());
		// Event for creating Biome Tag Info text file.
		MinecraftForge.EVENT_BUS.register(new InfoFileEventHandler());
		// Enchantment events
		MinecraftForge.EVENT_BUS.register(new EnchantmentEventSubscriber());

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
			BDBBlocks.POTTED_PLANTS.forEach((flower, pottedFlower) -> ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ForgeRegistries.BLOCKS.getKey(flower.get()), pottedFlower));

		});

		LOGGER.debug("commonSetup method completed");
	}

	/*
	 * PostRegistrationEvent
	 */
	private void loadComplete(final FMLLoadCompleteEvent event) {
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
