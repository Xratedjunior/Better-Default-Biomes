package xratedjunior.betterdefaultbiomes.world.generation;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.data.BDBTags;
import xratedjunior.betterdefaultbiomes.world.generation.placement.BDBPlacement;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBPlacedFeatures {
	// public static final ResourceKey<PlacedFeature> TEST = registerKey("test");

	/*********************************************************** Plant Placements ********************************************************/

	//	Grass Features
	public static final ResourceKey<PlacedFeature> FEATHER_REED_GRASS_FEATURE = registerKey("feather_reed_grass_feature");
	public static final ResourceKey<PlacedFeature> DEAD_GRASS_FEATURE = registerKey("dead_grass_feature");
	public static final ResourceKey<PlacedFeature> SHORT_GRASS_FEATURE = registerKey("short_grass_feature");
	public static final ResourceKey<PlacedFeature> DUNE_GRASS_FEATURE = registerKey("dune_grass_feature");

	//	Water Features
	public static final ResourceKey<PlacedFeature> WATER_REEDS_FEATURE = registerKey("water_reeds_feature");

	/*********************************************************** Flower Placements ********************************************************/

	public static final ResourceKey<PlacedFeature> PINK_CACTUS_FLOWER_FEATURE = registerKey("pink_cactus_flower_feature");
	public static final ResourceKey<PlacedFeature> PURPLE_VERBENA_FEATURE = registerKey("purple_verbena_feature");
	public static final ResourceKey<PlacedFeature> BLUE_POPPY_FEATURE = registerKey("blue_poppy_feature");
	public static final ResourceKey<PlacedFeature> DARK_VIOLET_FEATURE = registerKey("dark_violet_feature");

	/*********************************************************** Mushroom Placements ********************************************************/

	//	Mushrooms
	public static final ResourceKey<PlacedFeature> WHITE_MUSHROOM_FEATURE = registerKey("white_mushroom_feature");
	public static final ResourceKey<PlacedFeature> YELLOW_MUSHROOM_FEATURE = registerKey("yellow_mushroom_feature");
	public static final ResourceKey<PlacedFeature> GRAY_MUSHROOM_FEATURE = registerKey("gray_mushroom_feature");

	//	Big Mushrooms
	public static final ResourceKey<PlacedFeature> BIG_WHITE_MUSHROOM_FEATURE = registerKey("big_white_mushroom_feature");
	public static final ResourceKey<PlacedFeature> BIG_YELLOW_MUSHROOM_FEATURE = registerKey("big_yellow_mushroom_feature");
	public static final ResourceKey<PlacedFeature> BIG_GRAY_MUSHROOM_FEATURE = registerKey("big_gray_mushroom_feature");

	/*********************************************************** Small Feature Placements ********************************************************/

	public static final ResourceKey<PlacedFeature> SMALL_ROCK_FEATURE = registerKey("small_rock_feature");
	public static final ResourceKey<PlacedFeature> SMALL_ROCK_UNDERGROUND_FEATURE = registerKey("small_rock_underground_feature");

	public static final ResourceKey<PlacedFeature> PINECONE_FEATURE = registerKey("pinecone_feature");
	public static final ResourceKey<PlacedFeature> SEASHELL_FEATURE = registerKey("seashell_feature");
	public static final ResourceKey<PlacedFeature> SAND_CASTLE_FEATURE = registerKey("sand_castle_feature");

	//	Starfish
	public static final ResourceKey<PlacedFeature> STARFISH_FEATURE = registerKey("starfish_feature");
	public static final ResourceKey<PlacedFeature> STARFISH_CORAL_FEATURE = registerKey("starfish_coral_feature");

	/*********************************************************** Trees Placements ********************************************************/

	public static final ResourceKey<PlacedFeature> PALM_TREE_FEATURE = registerKey("palm_tree_feature");
	public static final ResourceKey<PlacedFeature> SWAMP_WILLOW_TREE_FEATURE = registerKey("swamp_willow_tree_feature");

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		BetterDefaultBiomes.LOGGER.debug("Registering Configured Features");
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

//		register(context, configuredFeatures, TEST, BDBConfiguredFeatures.TEST, List.of( // PlacementModifiers...
//				CountPlacement.of(8), // Generation frequency (Count is not configurable, but can still be turned off in the config)
//				InSquarePlacement.spread(), // Randomize position in chunk
//				PlacementUtils.HEIGHTMAP_TOP_SOLID // Generation Y-level
//		));

		/*********************************************************** Plant Placements ********************************************************/

		//	Grass Features
		register(context, configuredFeatures, FEATHER_REED_GRASS_FEATURE, BDBConfiguredFeatures.FEATHER_REED_GRASS, List.of( // PlacementModifiers...
				BDBPlacement.of(120), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, DEAD_GRASS_FEATURE, BDBConfiguredFeatures.DEAD_GRASS, List.of( // PlacementModifiers...
				BDBPlacement.of(160), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, SHORT_GRASS_FEATURE, BDBConfiguredFeatures.SHORT_GRASS, List.of( // PlacementModifiers...
				BDBPlacement.of(200), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, DUNE_GRASS_FEATURE, BDBConfiguredFeatures.DUNE_GRASS, List.of( // PlacementModifiers...
				BDBPlacement.of(150), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));

		//	Water Features
		register(context, configuredFeatures, WATER_REEDS_FEATURE, BDBConfiguredFeatures.WATER_REEDS, List.of( // PlacementModifiers...
				BDBPlacement.of(4000), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));

		/*********************************************************** Flower Placements ********************************************************/

		register(context, configuredFeatures, PINK_CACTUS_FLOWER_FEATURE, BDBConfiguredFeatures.PINK_CACTUS_FLOWER, List.of( // PlacementModifiers...
				BDBPlacement.of(40), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));

		register(context, configuredFeatures, PURPLE_VERBENA_FEATURE, BDBConfiguredFeatures.PURPLE_VERBENA, List.of( // PlacementModifiers...
				BDBPlacement.of(20), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, BLUE_POPPY_FEATURE, BDBConfiguredFeatures.BLUE_POPPY, List.of( // PlacementModifiers...
				BDBPlacement.of(20), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, DARK_VIOLET_FEATURE, BDBConfiguredFeatures.DARK_VIOLET, List.of( // PlacementModifiers...
				BDBPlacement.of(32), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));

		/*********************************************************** Mushroom Placements ********************************************************/

		//	Mushrooms
		register(context, configuredFeatures, WHITE_MUSHROOM_FEATURE, BDBConfiguredFeatures.WHITE_MUSHROOM, List.of( // PlacementModifiers...
				BDBPlacement.of(40), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, YELLOW_MUSHROOM_FEATURE, BDBConfiguredFeatures.YELLOW_MUSHROOM, List.of( // PlacementModifiers...
				BDBPlacement.of(40), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, GRAY_MUSHROOM_FEATURE, BDBConfiguredFeatures.GRAY_MUSHROOM, List.of( // PlacementModifiers...
				BDBPlacement.of(40), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));

		//	Big Mushrooms
		register(context, configuredFeatures, BIG_WHITE_MUSHROOM_FEATURE, BDBConfiguredFeatures.BIG_WHITE_MUSHROOM, List.of( // PlacementModifiers...
				BDBPlacement.of(200), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, BIG_YELLOW_MUSHROOM_FEATURE, BDBConfiguredFeatures.BIG_YELLOW_MUSHROOM, List.of( // PlacementModifiers...
				BDBPlacement.of(200), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, BIG_GRAY_MUSHROOM_FEATURE, BDBConfiguredFeatures.BIG_GRAY_MUSHROOM, List.of( // PlacementModifiers...
				BDBPlacement.of(200), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));

		/*********************************************************** Small Feature Placements ********************************************************/

		register(context, configuredFeatures, SMALL_ROCK_FEATURE, BDBConfiguredFeatures.SMALL_ROCK, List.of( // PlacementModifiers...
				BDBPlacement.of(200), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, SMALL_ROCK_UNDERGROUND_FEATURE, BDBConfiguredFeatures.SMALL_ROCK, List.of( // PlacementModifiers...
				BDBPlacement.of(64000), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, // Generation Y-level (Get random position between top and bottom of the world)
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -8), // Y-level generation constraints; (Base y-level, addition for minimum y-level, addition for maximum y-level)
				EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.wouldSurvive(BDBBlocks.SMALL_ROCK_STONE.get().defaultBlockState(), BlockPos.ZERO), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12), // Scan down for possible generation position
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, PINECONE_FEATURE, BDBConfiguredFeatures.PINECONE, List.of( // PlacementModifiers...
				BDBPlacement.of(800), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, SEASHELL_FEATURE, BDBConfiguredFeatures.SEASHELL, List.of( // PlacementModifiers...
				BDBPlacement.of(1000), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), BDBTags.Blocks.BEACH_SAND)), // Verify ground block for placement
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, SAND_CASTLE_FEATURE, BDBConfiguredFeatures.SAND_CASTLE, List.of( // PlacementModifiers...
				BDBPlacement.of(10), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), BDBTags.Blocks.BEACH_SAND)), // Verify ground block for placement
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, STARFISH_FEATURE, BDBConfiguredFeatures.STARFISH, List.of( // PlacementModifiers...
				BDBPlacement.of(600), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, STARFISH_CORAL_FEATURE, BDBConfiguredFeatures.STARFISH_CORAL, List.of( // PlacementModifiers...
				CountPlacement.of(8), // Generation frequency (Count is not configurable, but can still be turned off in the config)
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID // Generation Y-level
		// Only places on Coral Blocks, so doesn't really need the BiomeFilter.
		));

		/*********************************************************** Trees Placements ********************************************************/

		register(context, configuredFeatures, PALM_TREE_FEATURE, BDBConfiguredFeatures.PALM_TREE_RANDOM, List.of( // PlacementModifiers...
				BDBPlacement.of(800), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // Generation Y-level
				PlacementUtils.filteredByBlockSurvival(BDBBlocks.PALM_SAPLING.get()), // Check placement
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
		register(context, configuredFeatures, SWAMP_WILLOW_TREE_FEATURE, BDBConfiguredFeatures.SWAMP_WILLOW_TREE, List.of( // PlacementModifiers...
				BDBPlacement.of(1000), // Gets weight from config
				InSquarePlacement.spread(), // Randomize position in chunk
				PlacementUtils.HEIGHTMAP_TOP_SOLID, // Generation Y-level
				PlacementUtils.filteredByBlockSurvival(BDBBlocks.SWAMP_WILLOW_SAPLING.get()), // Check placement
				BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(BlockPos.ZERO.above(2), Blocks.AIR)), // Check placement
				BiomeFilter.biome() // Verify correct Biome for Feature. Prevents placement in areas of Biome blending
		));
	}

	/*********************************************************** Helper methods ********************************************************/

	private static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, BetterDefaultBiomes.locate(name));
	}

	private static void register(BootstapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuredFeatures.getOrThrow(configuredFeature), List.copyOf(modifiers)));
	}
}
