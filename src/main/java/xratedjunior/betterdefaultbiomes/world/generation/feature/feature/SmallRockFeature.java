package xratedjunior.betterdefaultbiomes.world.generation.feature.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.block.block.SmallRockBlock;
import xratedjunior.betterdefaultbiomes.block.block.SmallRockBlock.RockVariant;

/**
 * Handle the surface generation of Small Rocks
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class SmallRockFeature extends Feature<NoneFeatureConfiguration> {

	public SmallRockFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		// Get position
		BlockPos pos = context.origin();
		WorldGenLevel worldgenlevel = context.level();

		// Check if there is space to generate
		if (!worldgenlevel.isEmptyBlock(pos) && !worldgenlevel.getBlockState(pos).is(Blocks.WATER)) {
			// No space, exit!
			return false;
		}

		return this.placeSmallRock(worldgenlevel, context.random(), pos);
	}

	/**
	 * Place a Small Rock
	 */
	protected boolean placeSmallRock(WorldGenLevel world, Random rand, BlockPos blockpos) {
		// Check if not all variants have been disabled in the config file.
		if (RockVariant.ALL_VARIANTS.length == 0) {
			return false;
		}

		Direction randomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
		int randomRockSize = rand.nextInt(SmallRockBlock.MAX_ROCK_SIZE) + 1;
		boolean waterlogged = false;

		Block floorBlock = world.getBlockState(blockpos.below()).getBlock();
		BlockState smallRockBlock = SmallRockBlock.RockVariant.getMatchingRockVariant(floorBlock).defaultBlockState();

		// Get random variant to place - 20% chance
		if (smallRockBlock.getBlock() == RockVariant.DEFAULT_VARIANT.getSmallRockBlock() && rand.nextInt(5) == 0 && RockVariant.STONE_VARIANTS.length != 0) {
			smallRockBlock = SmallRockBlock.RockVariant.getRandomStoneVariant(rand).defaultBlockState();
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
}