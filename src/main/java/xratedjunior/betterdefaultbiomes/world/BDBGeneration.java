package xratedjunior.betterdefaultbiomes.world;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.configuration.generation.flower.BluePoppyConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.flower.DarkVioletConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.flower.PinkCactusFlowerConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.flower.PurpleVerbenaConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.mushroom.GrayMushroomConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.mushroom.WhiteMushroomConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.mushroom.YellowMushroomConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.PineconeConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.SandCastleConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.SeaShellConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.SmallRocksConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.StarfishConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.DeadGrassConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.DuneGrassConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.FeatherReedGrassConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.ShortGrassConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.TallWaterReedsConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.tree.PalmTreeConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.tree.SwampWillowTreeConfig;
import xratedjunior.betterdefaultbiomes.util.BDBHelper;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BDBFeaturePlacements;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID)
public class BDBGeneration {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void registerFeatureGeneration(BiomeLoadingEvent event) {
		ResourceLocation biomeName = event.getName();
		Biome biome = ForgeRegistries.BIOMES.getValue(biomeName);
		if (biome != null) {
			BiomeGenerationSettingsBuilder builder = event.getGeneration();

			/*********************************************************** Plants ********************************************************/
			//	Grass Features
			addFeature(BDBFeaturePlacements.FEATHER_REED_GRASS_FEATURE, FeatherReedGrassConfig.generate.get(), FeatherReedGrassConfig.count.get(), FeatherReedGrassConfig.generation_biomes.get(), FeatherReedGrassConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.DEAD_GRASS_FEATURE, DeadGrassConfig.generate.get(), DeadGrassConfig.count.get(), DeadGrassConfig.generation_biomes.get(), DeadGrassConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.SHORT_GRASS_FEATURE, ShortGrassConfig.generate.get(), ShortGrassConfig.count.get(), ShortGrassConfig.generation_biomes.get(), ShortGrassConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.DUNE_GRASS_FEATURE, DuneGrassConfig.generate.get(), DuneGrassConfig.count.get(), DuneGrassConfig.generation_biomes.get(), DuneGrassConfig.debug.get(), biomeName, builder);

			//	Water Features
			addFeature(BDBFeaturePlacements.WATER_REEDS_FEATURE, TallWaterReedsConfig.generate.get(), TallWaterReedsConfig.count.get(), TallWaterReedsConfig.generation_biomes.get(), TallWaterReedsConfig.debug.get(), biomeName, builder);

			/*********************************************************** Flowers ********************************************************/

			addFeature(BDBFeaturePlacements.PINK_CACTUS_FLOWER_FEATURE, PinkCactusFlowerConfig.generate.get(), PinkCactusFlowerConfig.count.get(), PinkCactusFlowerConfig.generation_biomes.get(), PinkCactusFlowerConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.PURPLE_VERBENA_FEATURE, PurpleVerbenaConfig.generate.get(), PurpleVerbenaConfig.count.get(), PurpleVerbenaConfig.generation_biomes.get(), PurpleVerbenaConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.BLUE_POPPY_FEATURE, BluePoppyConfig.generate.get(), BluePoppyConfig.count.get(), BluePoppyConfig.generation_biomes.get(), BluePoppyConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.DARK_VIOLET_FEATURE, DarkVioletConfig.generate.get(), DarkVioletConfig.count.get(), DarkVioletConfig.generation_biomes.get(), DarkVioletConfig.debug.get(), biomeName, builder);

			/*********************************************************** Mushrooms ********************************************************/
			//	Mushrooms
			addFeature(BDBFeaturePlacements.WHITE_MUSHROOM_FEATURE, WhiteMushroomConfig.generate.get(), WhiteMushroomConfig.count.get(), WhiteMushroomConfig.generation_biomes.get(), WhiteMushroomConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.YELLOW_MUSHROOM_FEATURE, YellowMushroomConfig.generate.get(), YellowMushroomConfig.count.get(), YellowMushroomConfig.generation_biomes.get(), YellowMushroomConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.GRAY_MUSHROOM_FEATURE, GrayMushroomConfig.generate.get(), GrayMushroomConfig.count.get(), GrayMushroomConfig.generation_biomes.get(), GrayMushroomConfig.debug.get(), biomeName, builder);

			//	Big Mushrooms
			addFeature(BDBFeaturePlacements.BIG_WHITE_MUSHROOM_FEATURE, WhiteMushroomConfig.generate_big.get(), WhiteMushroomConfig.count_big.get(), WhiteMushroomConfig.generation_biomes_big.get(), WhiteMushroomConfig.debug_big.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.BIG_YELLOW_MUSHROOM_FEATURE, YellowMushroomConfig.generate_big.get(), YellowMushroomConfig.count_big.get(), YellowMushroomConfig.generation_biomes_big.get(), YellowMushroomConfig.debug_big.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.BIG_GRAY_MUSHROOM_FEATURE, GrayMushroomConfig.generate_big.get(), GrayMushroomConfig.count_big.get(), GrayMushroomConfig.generation_biomes_big.get(), GrayMushroomConfig.debug_big.get(), biomeName, builder);

			/*********************************************************** Small Features ********************************************************/

			addFeature(BDBFeaturePlacements.SMALL_ROCK_FEATURE, SmallRocksConfig.generate.get(), SmallRocksConfig.count.get(), SmallRocksConfig.generation_biomes.get(), SmallRocksConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.SMALL_ROCK_UNDERGROUND_FEATURE, SmallRocksConfig.generate_underground.get(), SmallRocksConfig.count_underground.get(), SmallRocksConfig.generation_biomes_underground.get(), SmallRocksConfig.debug_underground.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.PINECONE_FEATURE, PineconeConfig.generate.get(), PineconeConfig.count.get(), PineconeConfig.generation_biomes.get(), PineconeConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.SEASHELL_FEATURE, SeaShellConfig.generate.get(), SeaShellConfig.count.get(), SeaShellConfig.generation_biomes.get(), SeaShellConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.SAND_CASTLE_FEATURE, SandCastleConfig.generate.get(), SandCastleConfig.count.get(), SandCastleConfig.generation_biomes.get(), SandCastleConfig.debug.get(), biomeName, builder);

			//	Starfish
			addFeature(BDBFeaturePlacements.STARFISH_FEATURE, StarfishConfig.generate.get(), StarfishConfig.count.get(), StarfishConfig.generation_biomes.get(), StarfishConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.STARFISH_CORAL_FEATURE, StarfishConfig.generate.get(), StarfishConfig.count.get(), StarfishConfig.generation_biomes.get(), StarfishConfig.debug.get(), biomeName, builder);

			/*********************************************************** Trees ********************************************************/

			addFeature(BDBFeaturePlacements.PALM_TREE_FEATURE, PalmTreeConfig.generate.get(), PalmTreeConfig.count.get(), PalmTreeConfig.generation_biomes.get(), PalmTreeConfig.debug.get(), biomeName, builder);
			addFeature(BDBFeaturePlacements.SWAMP_WILLOW_TREE_FEATURE, SwampWillowTreeConfig.generate.get(), SwampWillowTreeConfig.count.get(), SwampWillowTreeConfig.generation_biomes.get(), SwampWillowTreeConfig.debug.get(), biomeName, builder);

			//	Return so error doesn't show for every Biome.
			return;
		}

		BetterDefaultBiomes.LOGGER.error("Could not match \"" + biomeName + "\" to any registered Biome.");
	}

	/**
	 * Helper method for adding Features to Biomes.
	 */
	private static void addFeature(RegistryObject<PlacedFeature> feature, boolean generate, int weight, List<String> generationBiomes, boolean debugLogger, ResourceLocation biomeName, BiomeGenerationSettingsBuilder builder) {
		addFeature(feature, GenerationStep.Decoration.VEGETAL_DECORATION, generate, weight, generationBiomes, debugLogger, biomeName, builder);
	}

	/**
	 * Helper method for adding Features to Biomes.
	 */
	private static void addFeature(RegistryObject<PlacedFeature> feature, Decoration generationStep, boolean generate, int weight, List<String> generationBiomes, boolean debugLogger, ResourceLocation biomeName, BiomeGenerationSettingsBuilder builder) {
		if (generate) {
			if (BDBHelper.matchBiomeOrType(biomeName, generationBiomes) && weight > 0) {
				builder.addFeature(generationStep, feature.getHolder().orElseThrow());

				if (debugLogger) {
					BetterDefaultBiomes.LOGGER.info("The {} generates in the: {}. (Weight: {})", feature.getKey().location(), biomeName.toString(), weight);
				}
			}
		}
	}
}
