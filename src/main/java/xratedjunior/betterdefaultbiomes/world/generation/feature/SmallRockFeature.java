package xratedjunior.betterdefaultbiomes.world.generation.feature;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.mojang.serialization.Codec;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.block.block.SmallRockBlock;
import xratedjunior.betterdefaultbiomes.block.block.SmallRockBlock.RockVariant;
import xratedjunior.betterdefaultbiomes.world.generation.feature.configurations.SmallRockConfiguration;

/**
 * Handle the surface generation of Small Rocks
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class SmallRockFeature extends Feature<SmallRockConfiguration> {

	public SmallRockFeature(Codec<SmallRockConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<SmallRockConfiguration> context) {
		// Get position
		BlockPos pos = context.origin();
		WorldGenLevel worldgenlevel = context.level();

		// Check if there is space to generate
		if (!worldgenlevel.isEmptyBlock(pos) && !worldgenlevel.getBlockState(pos).is(Blocks.WATER)) {
			// No space, exit!
			return false;
		}

		return this.placeSmallRock(worldgenlevel, context.config(), context.random(), pos);
	}

	/**
	 * Place a Small Rock
	 */
	protected boolean placeSmallRock(WorldGenLevel world, SmallRockConfiguration config, RandomSource rand, BlockPos blockpos) {
		// Check if not all variants have been disabled.
		if (config.getAllPossibleVariants().isEmpty()) {
			return false;
		}

		Direction randomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
		int randomRockSize = rand.nextInt(SmallRockBlock.MAX_ROCK_SIZE) + 1;
		boolean waterlogged = false;

		Block floorBlock = world.getBlockState(blockpos.below()).getBlock();
		BlockState smallRockBlock = getMatchingRockVariant(floorBlock, config).defaultBlockState();

		// Get random variant to place - 20% chance
		if (smallRockBlock.getBlock() == RockVariant.DEFAULT_VARIANT.getSmallRockBlock() && rand.nextInt(5) == 0 && RockVariant.STONE_VARIANTS.length != 0) {
			smallRockBlock = getRandomStoneVariant(rand, config).defaultBlockState();
		}

		if (smallRockBlock.canSurvive(world, blockpos)) {
			if (world.getBlockState(blockpos).is(Blocks.WATER)) {

				// 33% chance to place mossy rock in water
				if (rand.nextInt(3) == 0) {
					smallRockBlock = BDBBlocks.SMALL_ROCK_MOSSY.get().defaultBlockState();
				}
				waterlogged = true;

			} else if (!world.isEmptyBlock(blockpos)) {
				return false;

			}

			world.setBlock(blockpos, smallRockBlock.setValue(SmallRockBlock.WATERLOGGED, waterlogged).setValue(SmallRockBlock.FACING, randomDirection).setValue(SmallRockBlock.ROCK_SIZE, randomRockSize), 2);
			return true;
		}

		return false;
	}

	/*********************************************************** Placement Helpers ********************************************************/

	/**
	 * Get a random stone variant
	 */
	private static Block getRandomStoneVariant(RandomSource rand, SmallRockConfiguration config) {
		return Util.getRandom(config.getPossibleStoneVariants(), rand).getSmallRockBlock();
	}

	/**
	 * Get a random variant from the list that matches the Block.
	 * If list is empty, return the default variant
	 */
	private static Block getMatchingRockVariant(Block groundBlock, SmallRockConfiguration config) {
		List<Block> matchingVariants = getMatchingRockVariants(groundBlock, config);
		// If the list is empty, return the default variant.
		RandomSource random = RandomSource.create();
		return !matchingVariants.isEmpty() ? Util.getRandom(matchingVariants, random) : config.getDefaultVariant().getSmallRockBlock();
	}

	/**
	 * Get a list of variants that match the Block
	 */
	private static List<Block> getMatchingRockVariants(Block groundBlock, SmallRockConfiguration config) {
		// Create list
		List<Block> matchingVariants = Lists.newArrayList();

		for (RockVariant variant : config.getAllPossibleVariants()) {
			// Check if the groundBlock matches with Block Tags.
			if (groundBlock.defaultBlockState().is(variant.getPossibleFloorBlocks())) {
				// Add the variant to the final list.
				matchingVariants.add(variant.getSmallRockBlock());
			}
		}

		// Return list
		return matchingVariants;
	}
}