package xratedjunior.betterdefaultbiomes.world.generation.feature;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBConfiguredFeatures {
	public static final DeferredRegister<ConfiguredFeature<?, ?>> DEFERRED_CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, BetterDefaultBiomes.MOD_ID);

	public static final RegistryObject<ConfiguredFeature<?, ?>> TEST = register("test", () -> new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.GLOWSTONE))));
	
	/*********************************************************** Configured Plants ********************************************************/

	//	Grass Features
	public static final RegistryObject<ConfiguredFeature<?, ?>> FEATHER_REED_GRASS = register("feather_reed_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, patchConfig(24, 6, 2, BDBBlocks.FEATHER_REED_GRASS)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> DEAD_GRASS = register("dead_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, commonPatchConfig(BDBBlocks.DEAD_GRASS)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> SHORT_GRASS = register("short_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, commonPatchConfig(BDBBlocks.SHORT_GRASS)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> DUNE_GRASS = register("dune_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, commonPatchConfig(BDBBlocks.DUNE_GRASS)));

	//	Water Features
	public static final RegistryObject<ConfiguredFeature<?, ?>> WATER_REEDS = register("water_reeds", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(20, 6, 2, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BDBBlocks.TALL_WATER_REEDS.get())), BlockPredicate.allOf(BlockPredicate.matchesBlock(Blocks.WATER, BlockPos.ZERO), BlockPredicate.matchesBlock(Blocks.AIR, BlockPos.ZERO.above()))))));

	/*********************************************************** Flowers ********************************************************/

	public static final RegistryObject<ConfiguredFeature<?, ?>> PINK_CACTUS_FLOWER = register("pink_cactus_flower", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, flowerPatchConfig(BDBBlocks.PINK_CACTUS_FLOWER)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> PURPLE_VERBENA = register("purple_verbena", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, flowerPatchConfig(BDBBlocks.PURPLE_VERBENA)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BLUE_POPPY = register("blue_poppy", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, flowerPatchConfig(BDBBlocks.BLUE_POPPY)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> DARK_VIOLET = register("dark_violet", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, flowerPatchConfig(BDBBlocks.DARK_VIOLET)));

	/*********************************************************** Configured Mushrooms ********************************************************/

	//	Mushrooms
	public static final RegistryObject<ConfiguredFeature<?, ?>> WHITE_MUSHROOM = register("white_mushroom", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, patchConfig(10, 6, 2, BDBBlocks.WHITE_MUSHROOM)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> YELLOW_MUSHROOM = register("yellow_mushroom", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, patchConfig(10, 6, 2, BDBBlocks.YELLOW_MUSHROOM)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> GRAY_MUSHROOM = register("gray_mushroom", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, patchConfig(10, 6, 2, BDBBlocks.GRAY_MUSHROOM)));

	//	Big Mushrooms
	public static final RegistryObject<ConfiguredFeature<?, ?>> BIG_WHITE_MUSHROOM = register("big_white_mushroom", () -> new ConfiguredFeature<>(BDBFeatures.BIG_WHITE_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(States.WHITE_MUSHROOM_BLOCK), BlockStateProvider.simple(States.MUSHROOM_STEM), 2)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BIG_YELLOW_MUSHROOM = register("big_yellow_mushroom", () -> new ConfiguredFeature<>(BDBFeatures.BIG_YELLOW_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(States.YELLOW_MUSHROOM_BLOCK), BlockStateProvider.simple(States.MUSHROOM_STEM), 2)));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BIG_GRAY_MUSHROOM = register("big_gray_mushroom", () -> new ConfiguredFeature<>(BDBFeatures.BIG_GRAY_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(States.GRAY_MUSHROOM_BLOCK), BlockStateProvider.simple(States.MUSHROOM_STEM), 2)));

	/*********************************************************** Configured Small Features ********************************************************/

	public static final RegistryObject<ConfiguredFeature<?, ?>> SMALL_ROCK = register("small_rock", () -> new ConfiguredFeature<>(BDBFeatures.SMALL_ROCK.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> PINECONE = register("pinecone", () -> new ConfiguredFeature<>(BDBFeatures.PINECONE.get(), FeatureConfiguration.NONE));

	public static final RegistryObject<ConfiguredFeature<?, ?>> SAND_CASTLE = register("sand_castle", () -> new ConfiguredFeature<>(BDBFeatures.SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(BDBBlocks.SAND_CASTLE.get()))));
	public static final RegistryObject<ConfiguredFeature<?, ?>> SEASHELL = register("seashell", () -> new ConfiguredFeature<>(BDBFeatures.SIMPLE_BLOCK.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(BDBBlocks.SEASHELL.get()))));

	//Starfish
	public static final RegistryObject<ConfiguredFeature<?, ?>> STARFISH = register("starfish", () -> new ConfiguredFeature<>(BDBFeatures.STARFISH.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> STARFISH_CORAL_FEATURE = register("starfish_coral", () -> new ConfiguredFeature<>(BDBFeatures.STARFISH_CORAL.get(), FeatureConfiguration.NONE));

	/*********************************************************** Configured Trees ********************************************************/

	public static final RegistryObject<ConfiguredFeature<?, ?>> PALM_TREE = register("palm_tree", () -> new ConfiguredFeature<>(BDBFeatures.PALM_TREE.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> PALM_TREE_BIG = register("palm_tree_big", () -> new ConfiguredFeature<>(BDBFeatures.PALM_TREE_BIG.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<?, ?>> PALM_TREES = register("palm_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(PALM_TREE_BIG.getHolder().orElseThrow()), 0.3F)), PlacementUtils.inlinePlaced(PALM_TREE.getHolder().orElseThrow()))));
	public static final RegistryObject<ConfiguredFeature<?, ?>> SWAMP_WILLOW_TREE = register("swamp_willow_tree", () -> new ConfiguredFeature<>(BDBFeatures.SWAMP_WILLOW_TREE.get(), FeatureConfiguration.NONE));

	/*********************************************************** Helper methods ********************************************************/

	/**
	 * Helper method for registering all configured features
	 */
	private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<?, ?>> register(String registryName, Supplier<ConfiguredFeature<FC, F>> configuredFeature) {
		return DEFERRED_CONFIGURED_FEATURES.register(registryName, configuredFeature);
	}

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

	private static final class States {
		private static final BlockState WHITE_MUSHROOM_BLOCK = BDBBlocks.WHITE_MUSHROOM_BLOCK.get().defaultBlockState();
		private static final BlockState YELLOW_MUSHROOM_BLOCK = BDBBlocks.YELLOW_MUSHROOM_BLOCK.get().defaultBlockState();
		private static final BlockState GRAY_MUSHROOM_BLOCK = BDBBlocks.GRAY_MUSHROOM_BLOCK.get().defaultBlockState();
		private static final BlockState MUSHROOM_STEM = Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, Boolean.valueOf(false)).setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false));

	}
}
