package xratedjunior.betterdefaultbiomes.world.generation.feature;

import java.util.List;

import com.google.common.base.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
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
import xratedjunior.betterdefaultbiomes.data.BDBTags;
import xratedjunior.betterdefaultbiomes.world.generation.feature.placement.ConfigPlacement;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBFeaturePlacements {
	public static final DeferredRegister<PlacedFeature> DEFERRED_PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, BetterDefaultBiomes.MOD_ID);

	/*********************************************************** Plant Placements ********************************************************/

	//	Grass Features
	public static final RegistryObject<PlacedFeature> FEATHER_REED_GRASS_FEATURE = register("feather_reed_grass_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.FEATHER_REED_GRASS.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> FeatherReedGrassConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> DEAD_GRASS_FEATURE = register("dead_grass_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.DEAD_GRASS.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> DeadGrassConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> SHORT_GRASS_FEATURE = register("short_grass_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.SHORT_GRASS.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> ShortGrassConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> DUNE_GRASS_FEATURE = register("dune_grass_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.DUNE_GRASS.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> DuneGrassConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	//	Water Features
	public static final RegistryObject<PlacedFeature> WATER_REEDS_FEATURE = register("water_reeds_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.WATER_REEDS.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> TallWaterReedsConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	/*********************************************************** Flower Placements ********************************************************/

	public static final RegistryObject<PlacedFeature> PINK_CACTUS_FLOWER_FEATURE = register("pink_cactus_flower_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.PINK_CACTUS_FLOWER.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> PinkCactusFlowerConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> PURPLE_VERBENA_FEATURE = register("purple_verbena_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.PURPLE_VERBENA.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> PurpleVerbenaConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> BLUE_POPPY_FEATURE = register("blue_poppy_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.BLUE_POPPY.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> BluePoppyConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> DARK_VIOLET_FEATURE = register("dark_violet_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.DARK_VIOLET.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> DarkVioletConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	/*********************************************************** Mushroom Placements ********************************************************/

	//	Mushrooms
	public static final RegistryObject<PlacedFeature> WHITE_MUSHROOM_FEATURE = register("white_mushroom_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.WHITE_MUSHROOM.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> WhiteMushroomConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> YELLOW_MUSHROOM_FEATURE = register("yellow_mushroom_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.YELLOW_MUSHROOM.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> YellowMushroomConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> GRAY_MUSHROOM_FEATURE = register("gray_mushroom_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.GRAY_MUSHROOM.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> GrayMushroomConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	//	Big Mushrooms
	public static final RegistryObject<PlacedFeature> BIG_WHITE_MUSHROOM_FEATURE = register("big_white_mushroom_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.BIG_WHITE_MUSHROOM.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> WhiteMushroomConfig.count_big), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> BIG_YELLOW_MUSHROOM_FEATURE = register("big_yellow_mushroom_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.BIG_YELLOW_MUSHROOM.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> YellowMushroomConfig.count_big), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));
	public static final RegistryObject<PlacedFeature> BIG_GRAY_MUSHROOM_FEATURE = register("big_gray_mushroom_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.BIG_GRAY_MUSHROOM.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> GrayMushroomConfig.count_big), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	/*********************************************************** Small Feature Placements ********************************************************/

	public static final RegistryObject<PlacedFeature> SMALL_ROCK_FEATURE = register("small_rock_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.SMALL_ROCK.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> SmallRocksConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	public static final RegistryObject<PlacedFeature> SMALL_ROCK_UNDERGROUND_FEATURE = register("small_rock_underground_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.SMALL_ROCK.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> SmallRocksConfig.count_underground), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, // Generation Y-level (Get random position between top and bottom of the world)
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -8), // Y-level generation constraints; (Base y-level, addition for minimum y-level, addition for maximum y-level)
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.wouldSurvive(BDBBlocks.SMALL_ROCK_STONE.get().defaultBlockState(), BlockPos.ZERO), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12), // Scan down for possible generation position
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	public static final RegistryObject<PlacedFeature> PINECONE_FEATURE = register("pinecone_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.PINECONE.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> PineconeConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	public static final RegistryObject<PlacedFeature> SEASHELL_FEATURE = register("seashell_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.SEASHELL.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> SeaShellConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BDBTags.Blocks.BEACH_SAND, BlockPos.ZERO.below())), // Verify ground block for placement
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	public static final RegistryObject<PlacedFeature> SAND_CASTLE_FEATURE = register("sand_castle_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.SAND_CASTLE.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> SandCastleConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BDBTags.Blocks.BEACH_SAND, BlockPos.ZERO.below())), // Verify ground block for placement
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	public static final RegistryObject<PlacedFeature> STARFISH_FEATURE = register("starfish_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.STARFISH.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> StarfishConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	public static final RegistryObject<PlacedFeature> STARFISH_CORAL_FEATURE = register("starfish_coral_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.STARFISH_CORAL_FEATURE.get()), List.of( // PlacementModifiers...
			CountPlacement.of(8), // Generation frequency (Count is not configurable, but can still be turned off in the config)
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID // Generation Y-level
	// Only places on Coral Blocks, so doesn't really need the BiomeFilter.
	)));

	/*********************************************************** Trees Placements ********************************************************/

	public static final RegistryObject<PlacedFeature> PALM_TREE_FEATURE = register("palm_tree_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.PALM_TREES.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> PalmTreeConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
			PlacementUtils.filteredByBlockSurvival(BDBBlocks.PALM_SAPLING.get()), // Check placement
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	public static final RegistryObject<PlacedFeature> SWAMP_WILLOW_TREE_FEATURE = register("swamp_willow_tree_feature", () -> new PlacedFeature(Holder.direct(BDBConfiguredFeatures.SWAMP_WILLOW_TREE.get()), List.of( // PlacementModifiers...
			new ConfigPlacement(() -> SwampWillowTreeConfig.count), // Gets weight from config
			InSquarePlacement.spread(), // Randomize position in chunk
			PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
			PlacementUtils.filteredByBlockSurvival(BDBBlocks.SWAMP_WILLOW_SAPLING.get()), // Check placement
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlock(Blocks.AIR, BlockPos.ZERO.above(2))), // Check placement
			BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
	)));

	/**
	 * Helper method for registering all Feature placements
	 */
	private static RegistryObject<PlacedFeature> register(String registryName, Supplier<PlacedFeature> feature) {
		return DEFERRED_PLACED_FEATURES.register(registryName, feature);
	}
}
