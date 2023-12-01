package xratedjunior.betterdefaultbiomes.world.generation;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.world.generation.feature.configurations.SmallRockConfiguration;
import xratedjunior.betterdefaultbiomes.world.generation.feature.configurations.StarfishConfiguration;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBConfiguredFeatures {
	// public static final ResourceKey<ConfiguredFeature<?, ?>> TEST = createKey("configured_test");

	/*********************************************************** Configured Plants ********************************************************/

	//	Grass Features
	public static final ResourceKey<ConfiguredFeature<?, ?>> FEATHER_REED_GRASS = createKey("patch_feather_reed_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_GRASS = createKey("patch_dead_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_GRASS = createKey("patch_short_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DUNE_GRASS = createKey("patch_dune_grass");

	//	Water Features
	public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_REEDS = createKey("patch_water_reeds");

	/*********************************************************** Flowers ********************************************************/

	public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_CACTUS_FLOWER = createKey("patch_pink_cactus_flower");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_VERBENA = createKey("patch_purple_verbena");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_POPPY = createKey("patch_blue_poppy");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_VIOLET = createKey("patch_dark_violet");

	/*********************************************************** Configured Mushrooms ********************************************************/

	//	Mushrooms
	public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_MUSHROOM = createKey("patch_white_mushroom");
	public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MUSHROOM = createKey("patch_yellow_mushroom");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GRAY_MUSHROOM = createKey("patch_gray_mushroom");

	//	Big Mushrooms
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_WHITE_MUSHROOM = createKey("big_white_mushroom");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_YELLOW_MUSHROOM = createKey("big_yellow_mushroom");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_GRAY_MUSHROOM = createKey("big_gray_mushroom");

	/*********************************************************** Configured Small Features ********************************************************/

	public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_ROCK = createKey("small_rock");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PINECONE = createKey("pinecone");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAND_CASTLE = createKey("sand_castle");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SEASHELL = createKey("seashell");

	//	Starfish
	public static final ResourceKey<ConfiguredFeature<?, ?>> STARFISH = createKey("starfish");
	public static final ResourceKey<ConfiguredFeature<?, ?>> STARFISH_CORAL = createKey("starfish_coral");

	/*********************************************************** Configured Trees ********************************************************/

	public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_TREE = createKey("palm_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_TREE_BIG = createKey("palm_tree_big");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_TREE_RANDOM = createKey("palm_tree_random");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_WILLOW_TREE = createKey("swamp_willow_tree");

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		BetterDefaultBiomes.LOGGER.debug("Registering Configured Features");
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		// register(context, TEST, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.GLOWSTONE)));

		/*********************************************************** Configured Plants ********************************************************/

		//	Grass Features
		register(context, FEATHER_REED_GRASS, Feature.RANDOM_PATCH, patchConfig(24, 6, 2, BDBBlocks.FEATHER_REED_GRASS));
		register(context, DEAD_GRASS, Feature.RANDOM_PATCH, commonPatchConfig(BDBBlocks.DEAD_GRASS));
		register(context, SHORT_GRASS, Feature.RANDOM_PATCH, commonPatchConfig(BDBBlocks.SHORT_GRASS));
		register(context, DUNE_GRASS, Feature.RANDOM_PATCH, commonPatchConfig(BDBBlocks.DUNE_GRASS));

		//	Water Features
		register(context, WATER_REEDS, Feature.RANDOM_PATCH, new RandomPatchConfiguration(20, 6, 2, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BDBBlocks.TALL_WATER_REEDS.get())), BlockPredicate.allOf(BlockPredicate.matchesBlocks(BlockPos.ZERO, Blocks.WATER), BlockPredicate.matchesBlocks(BlockPos.ZERO.above(), Blocks.AIR)))));

		/*********************************************************** Flowers ********************************************************/

		register(context, PINK_CACTUS_FLOWER, Feature.RANDOM_PATCH, flowerPatchConfig(BDBBlocks.PINK_CACTUS_FLOWER));
		register(context, PURPLE_VERBENA, Feature.RANDOM_PATCH, flowerPatchConfig(BDBBlocks.PURPLE_VERBENA));
		register(context, BLUE_POPPY, Feature.RANDOM_PATCH, flowerPatchConfig(BDBBlocks.BLUE_POPPY));
		register(context, DARK_VIOLET, Feature.RANDOM_PATCH, flowerPatchConfig(BDBBlocks.DARK_VIOLET));

		/*********************************************************** Configured Mushrooms ********************************************************/

		//	Mushrooms
		register(context, WHITE_MUSHROOM, Feature.RANDOM_PATCH, patchConfig(10, 6, 2, BDBBlocks.WHITE_MUSHROOM));
		register(context, YELLOW_MUSHROOM, Feature.RANDOM_PATCH, patchConfig(10, 6, 2, BDBBlocks.YELLOW_MUSHROOM));
		register(context, GRAY_MUSHROOM, Feature.RANDOM_PATCH, patchConfig(10, 6, 2, BDBBlocks.GRAY_MUSHROOM));

		//	Big Mushrooms
		register(context, BIG_WHITE_MUSHROOM, BDBFeatures.BIG_WHITE_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(States.WHITE_MUSHROOM_BLOCK), BlockStateProvider.simple(States.MUSHROOM_STEM), 2));
		register(context, BIG_YELLOW_MUSHROOM, BDBFeatures.BIG_YELLOW_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(States.YELLOW_MUSHROOM_BLOCK), BlockStateProvider.simple(States.MUSHROOM_STEM), 2));
		register(context, BIG_GRAY_MUSHROOM, BDBFeatures.BIG_GRAY_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(States.GRAY_MUSHROOM_BLOCK), BlockStateProvider.simple(States.MUSHROOM_STEM), 2));

		/*********************************************************** Configured Small Features ********************************************************/

		register(context, SMALL_ROCK, BDBFeatures.SMALL_ROCK.get(), new SmallRockConfiguration());
		register(context, PINECONE, BDBFeatures.PINECONE.get(), FeatureConfiguration.NONE);
		register(context, SAND_CASTLE, BDBFeatures.SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(BDBBlocks.SAND_CASTLE.get())));
		register(context, SEASHELL, BDBFeatures.SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(BDBBlocks.SEASHELL.get())));

		//Starfish
		register(context, STARFISH, BDBFeatures.STARFISH.get(), new StarfishConfiguration());
		register(context, STARFISH_CORAL, BDBFeatures.STARFISH_CORAL.get(), new StarfishConfiguration());

		/*********************************************************** Configured Trees ********************************************************/

		register(context, PALM_TREE, BDBFeatures.PALM_TREE.get(), FeatureConfiguration.NONE);
		register(context, PALM_TREE_BIG, BDBFeatures.PALM_TREE_BIG.get(), FeatureConfiguration.NONE);
		register(context, PALM_TREE_RANDOM, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(PALM_TREE_BIG)), 0.3F)), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(PALM_TREE))));
		register(context, SWAMP_WILLOW_TREE, BDBFeatures.SWAMP_WILLOW_TREE.get(), FeatureConfiguration.NONE);
	}

	/*********************************************************** Helper methods ********************************************************/

	public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, BetterDefaultBiomes.locate(name));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> bigWhiteMushroom2, F feature, FC config) {
		context.register(bigWhiteMushroom2, new ConfiguredFeature<>(feature, config));
	}

	/*********************************************************** Config Helper methods ********************************************************/

	/**
	 * Helper method for registering block patches
	 */
	private static RandomPatchConfiguration patchConfig(int tries, int xzSpread, int ySpread, Supplier<Block> block) {
		return new RandomPatchConfiguration(tries, xzSpread, ySpread, simpleBlockPlacement(block));
	}

	/**
	 * Helper method for registering block patches
	 */
	private static RandomPatchConfiguration flowerPatchConfig(Supplier<Block> block) {
		return patchConfig(16, 7, 3, block);
	}

	/**
	 * Helper method for registering common block patches
	 */
	private static RandomPatchConfiguration commonPatchConfig(Supplier<Block> block) {
		return patchConfig(64, 7, 3, block);
	}

	/**
	 * Helper method for registering simple block features
	 */
	private static Holder<PlacedFeature> simpleBlockPlacement(Supplier<Block> block) {
		return PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block.get())));
	}

	/*********************************************************** Block States ********************************************************/

	private static final class States {
		private static final BlockState WHITE_MUSHROOM_BLOCK = BDBBlocks.WHITE_MUSHROOM_BLOCK.get().defaultBlockState();
		private static final BlockState YELLOW_MUSHROOM_BLOCK = BDBBlocks.YELLOW_MUSHROOM_BLOCK.get().defaultBlockState();
		private static final BlockState GRAY_MUSHROOM_BLOCK = BDBBlocks.GRAY_MUSHROOM_BLOCK.get().defaultBlockState();
		private static final BlockState MUSHROOM_STEM = Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.valueOf(false)).setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false));

	}
}
