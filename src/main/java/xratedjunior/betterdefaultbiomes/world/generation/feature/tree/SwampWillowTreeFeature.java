package xratedjunior.betterdefaultbiomes.world.generation.feature.tree;

import java.util.function.BiConsumer;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.data.BDBTags;
import xratedjunior.betterdefaultbiomes.util.BDBMathUtil;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class SwampWillowTreeFeature extends TreeFeatureBDB {
	protected BlockState log;
	protected BlockState leaves;
	protected int minTreeHeight;
	protected int maxTreeHeight;

	public SwampWillowTreeFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
		this.log = BDBBlocks.SWAMP_WILLOW_LOG.get().defaultBlockState();
		this.leaves = BDBBlocks.SWAMP_WILLOW_LEAVES.get().defaultBlockState();
		this.minTreeHeight = 8;
		this.maxTreeHeight = 12;
	}

	@Override
	protected boolean createTree(WorldGenLevel world, RandomSource random, BlockPos startPos, BiConsumer<BlockPos, BlockState> logs, BiConsumer<BlockPos, BlockState> leaves) {
		// Generation settings
		int height = BDBMathUtil.nextIntBetween(random, this.minTreeHeight, this.maxTreeHeight);
		int leavesRadius = 2;
		int heightMinusTop = height - leavesRadius - 1;

		if (height < 6) {
			return false;
		} //Prevent trees from being too small 

		// Move up to space above ground
		BlockPos pos = startPos;//.above();

		if (!this.checkSpace(world, pos, height, 1)) {
			// Abandon if there isn't enough room
			return false;
		}

		this.generateTrunk(world, logs, pos);
		this.generateLeavesTop(leaves, pos);
		this.addVines(world, random, startPos, heightMinusTop, height, 6, 10);
		this.replaceGround(world, pos);

		return true;
	}

	private void generateTrunk(WorldGenLevel world, BiConsumer<BlockPos, BlockState> logs, BlockPos pos) {
		int left = 1;
		int right = -1;
		int front = -1;
		int back = 1;

		for (int y = 0; y > -8; y--) {
			BlockPos posY = pos.offset(right, y, front);
			if (!world.getBlockState(posY).canOcclude()) {
				logs.accept(posY, this.log);
			}

			posY = pos.offset(left, y, front);
			if (!world.getBlockState(posY).canOcclude()) {
				logs.accept(posY, this.log);
			}

			posY = pos.offset(left, y + 1, 0);
			if (!world.getBlockState(posY).canOcclude()) {
				logs.accept(posY, this.log);
			}

			posY = pos.offset(0, y + 2, back);
			if (!world.getBlockState(posY).canOcclude()) {
				logs.accept(posY, this.log);
			}

			posY = pos.offset(right * 2, y, back * 2);
			if (!world.getBlockState(posY).canOcclude()) {
				logs.accept(posY, this.log);
			}

			posY = pos.offset(right, y + 1, back);
			if (!world.getBlockState(posY).canOcclude()) {
				logs.accept(posY, this.log);
			} else {
				break;
			}
		}

		for (int height = 0; height <= 3; height++) {
			BlockPos logPos = pos.above(height);
			logs.accept(logPos, this.log);
		}
		for (int height = 3; height <= 5; height++) {
			BlockPos logPos = pos.above(height).offset(right, 0, 0);
			logs.accept(logPos, this.log);
		}
		for (int height = 5; height <= 8; height++) {
			BlockPos logPos = pos.above(height).offset(right, 0, front);
			logs.accept(logPos, this.log);
		}
		int topHeight = 8;
		BlockPos topPos = pos.offset(right, topHeight, front);

		logs.accept(topPos.offset(0, 0, front), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));
		logs.accept(topPos.offset(0, 0, front * 2), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));
		logs.accept(topPos.offset(right, -1, front * 3), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));
		logs.accept(topPos.offset(right, -1, front * 4), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));
		logs.accept(topPos.offset(right, -2, front * 5), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));

		logs.accept(topPos.offset(left, 0, 0), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));
		logs.accept(topPos.offset(left * 2, 0, 0), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));
		logs.accept(topPos.offset(left * 3, 0, front), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));
		logs.accept(topPos.offset(left * 4, 0, front), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));
		logs.accept(topPos.offset(left * 5, -1, front * 2), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));

		logs.accept(topPos.offset(left, 0, back), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));
		logs.accept(topPos.offset(left, 0, back * 2), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));
		logs.accept(topPos.offset(left * 2, 0, back * 3), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));
		logs.accept(topPos.offset(left * 2, 0, back * 4), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));
		logs.accept(topPos.offset(left * 2, -1, back * 5), this.log.setValue(RotatedPillarBlock.AXIS, Axis.Z));

		logs.accept(topPos.offset(right, 0, 0), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));
		logs.accept(topPos.offset(right * 2, 0, 0), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));
		logs.accept(topPos.offset(right * 3, 0, back), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));
		logs.accept(topPos.offset(right * 4, 0, back), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));
		logs.accept(topPos.offset(right * 5, -1, back), this.log.setValue(RotatedPillarBlock.AXIS, Axis.X));
	}

	/**
	 * Generate Palm leaves
	 */
	private void generateLeavesTop(BiConsumer<BlockPos, BlockState> leaves, BlockPos pos) {
		int left = 1;
		int right = -1;
		int front = -1;
		int back = 1;

		int leavesHeight = 9;
		BlockPos topPos1 = pos.offset(right, leavesHeight, front);

		//Layer 1
		leaves.accept(topPos1.offset(0, 0, 0), this.leaves);
		leaves.accept(topPos1.offset(0, 0, front), this.leaves);
		leaves.accept(topPos1.offset(0, 0, front * 2), this.leaves);

		leaves.accept(topPos1.offset(left, 0, 0), this.leaves);
		leaves.accept(topPos1.offset(left * 2, 0, 0), this.leaves);
		leaves.accept(topPos1.offset(left * 3, 0, front), this.leaves);
		leaves.accept(topPos1.offset(left * 4, 0, front), this.leaves);

		leaves.accept(topPos1.offset(0, 0, back), this.leaves);
		leaves.accept(topPos1.offset(0, 0, back * 2), this.leaves);
		leaves.accept(topPos1.offset(0, 0, back * 3), this.leaves);
		leaves.accept(topPos1.offset(left, 0, back), this.leaves);
		leaves.accept(topPos1.offset(left, 0, back * 2), this.leaves);
		leaves.accept(topPos1.offset(left, 0, back * 3), this.leaves);
		leaves.accept(topPos1.offset(left * 2, 0, back * 3), this.leaves);
		leaves.accept(topPos1.offset(left * 2, 0, back * 4), this.leaves);

		leaves.accept(topPos1.offset(right, 0, 0), this.leaves);
		leaves.accept(topPos1.offset(right * 2, 0, 0), this.leaves);
		leaves.accept(topPos1.offset(right * 2, 0, back), this.leaves);
		leaves.accept(topPos1.offset(right * 3, 0, back), this.leaves);
		leaves.accept(topPos1.offset(right * 4, 0, back), this.leaves);
		leaves.accept(topPos1.offset(right, 0, back), this.leaves);
		leaves.accept(topPos1.offset(right, 0, back * 2), this.leaves);
		leaves.accept(topPos1.offset(right, 0, front), this.leaves);
		leaves.accept(topPos1.offset(right, 0, front * 2), this.leaves);

		//Layer 2
		BlockPos topPos2 = pos.offset(right, leavesHeight - 1, front);
		leaves.accept(topPos2.offset(left, 0, back * 3), this.leaves);
		leaves.accept(topPos2.offset(left, 0, back * 4), this.leaves);
		for (int x = 1; x <= 4; x++) {
			leaves.accept(topPos2.offset(0, 0, back * x), this.leaves);
		}
		for (int x = 1; x <= 3; x++) {
			leaves.accept(topPos2.offset(right, 0, back * x), this.leaves);
		}
		leaves.accept(topPos2.offset(right * 2, 0, back), this.leaves);
		leaves.accept(topPos2.offset(right * 2, 0, back * 2), this.leaves);
		leaves.accept(topPos2.offset(right * 3, 0, back * 2), this.leaves);
		leaves.accept(topPos2.offset(right * 4, 0, back * 2), this.leaves);
		leaves.accept(topPos2.offset(right * 3, 0, 0), this.leaves);
		leaves.accept(topPos2.offset(right * 4, 0, 0), this.leaves);
		leaves.accept(topPos2.offset(right * 5, 0, back), this.leaves);

		leaves.accept(topPos2.offset(right * 2, 0, front), this.leaves);
		leaves.accept(topPos2.offset(right * 2, 0, front * 2), this.leaves);
		for (int x = 1; x <= 4; x++) {
			leaves.accept(topPos2.offset(right, 0, front * x), this.leaves);
		}
		leaves.accept(topPos2.offset(0, 0, front * 3), this.leaves);

		leaves.accept(topPos2.offset(left, 0, front), this.leaves);
		leaves.accept(topPos2.offset(left, 0, front * 2), this.leaves);
		leaves.accept(topPos2.offset(left * 2, 0, front), this.leaves);
		leaves.accept(topPos2.offset(left * 3, 0, front * 2), this.leaves);
		leaves.accept(topPos2.offset(left * 4, 0, front * 2), this.leaves);
		leaves.accept(topPos2.offset(left * 5, 0, front * 2), this.leaves);
		leaves.accept(topPos2.offset(left * 5, 0, front), this.leaves);
		leaves.accept(topPos2.offset(left * 3, 0, 0), this.leaves);
		leaves.accept(topPos2.offset(left * 4, 0, 0), this.leaves);

		leaves.accept(topPos2.offset(left * 2, 0, back), this.leaves);
		leaves.accept(topPos2.offset(left * 2, 0, back * 2), this.leaves);
		leaves.accept(topPos2.offset(left * 3, 0, back * 2), this.leaves);
		leaves.accept(topPos2.offset(left * 3, 0, back * 3), this.leaves);
		leaves.accept(topPos2.offset(left * 3, 0, back * 4), this.leaves);
		leaves.accept(topPos2.offset(left * 2, 0, back * 5), this.leaves);

		//Layer 3
		BlockPos topPos3 = pos.offset(right, leavesHeight - 2, front);
		for (int x = 1; x <= 4; x++) {
			leaves.accept(topPos3.offset(0, 0, back * x), this.leaves);
		}
		for (int x = -2; x <= 4; x++) {
			leaves.accept(topPos3.offset(right, 0, back * x), this.leaves);
		}
		for (int x = -4; x <= 3; x++) {
			leaves.accept(topPos3.offset(right * 2, 0, back * x), this.leaves);
		}
		for (int x = -1; x <= 2; x++) {
			leaves.accept(topPos3.offset(right * 3, 0, back * x), this.leaves);
		}
		for (int x = 0; x <= 2; x++) {
			leaves.accept(topPos3.offset(right * 4, 0, back * x), this.leaves);
		}
		leaves.accept(topPos3.offset(right * 5, 0, 0), this.leaves);
		leaves.accept(topPos3.offset(right * 5, 0, back * 2), this.leaves);
		leaves.accept(topPos3.offset(right * 6, 0, back), this.leaves);

		for (int x = 1; x <= 4; x++) {
			leaves.accept(topPos3.offset(0, 0, front * x), this.leaves);
		}
		leaves.accept(topPos3.offset(right, 0, front * 5), this.leaves);
		leaves.accept(topPos3.offset(left, 0, front * 2), this.leaves);
		for (int x = 2; x <= 5; x++) {
			leaves.accept(topPos3.offset(left * x, 0, front), this.leaves);
		}
		leaves.accept(topPos3.offset(left * 3, 0, front * 2), this.leaves);
		leaves.accept(topPos3.offset(left * 4, 0, front * 2), this.leaves);
		leaves.accept(topPos3.offset(left * 6, 0, front * 2), this.leaves);
		leaves.accept(topPos3.offset(left * 5, 0, front * 3), this.leaves);

		leaves.accept(topPos3.offset(left, 0, 0), this.leaves);
		leaves.accept(topPos3.offset(left * 2, 0, 0), this.leaves);
		leaves.accept(topPos3.offset(left * 4, 0, 0), this.leaves);

		for (int x = 1; x <= 5; x++) {
			leaves.accept(topPos3.offset(left * 3, 0, back * x), this.leaves);
		}
		for (int x = 1; x <= 4; x++) {
			leaves.accept(topPos3.offset(left * 2, 0, back * x), this.leaves);
		}
		leaves.accept(topPos3.offset(left * 2, 0, back * 6), this.leaves);
		for (int x = 1; x <= 5; x++) {
			leaves.accept(topPos3.offset(left, 0, back * x), this.leaves);
		}

		//Layer Final
		BlockPos topPos4 = pos.offset(right, leavesHeight - 3, front);
		leaves.accept(topPos4.offset(right, 0, 0), this.leaves);
		for (int x = 0; x >= -3; x--) {
			leaves.accept(topPos4.offset(right, x, back * 4), this.leaves);
		}
		leaves.accept(topPos4.offset(right, 0, back * 3), this.leaves);
		leaves.accept(topPos4.offset(right * 2, 0, back * 2), this.leaves);
		leaves.accept(topPos4.offset(right * 2, 0, back * 3), this.leaves);
		leaves.accept(topPos4.offset(right * 2, -1, back * 3), this.leaves);
		for (int x = 0; x <= 3; x++) {
			leaves.accept(topPos4.offset(right * 3, 0, back * x), this.leaves);
		}
		leaves.accept(topPos4.offset(right * 3, -1, back * 2), this.leaves);
		leaves.accept(topPos4.offset(right * 3, -1, 0), this.leaves);
		leaves.accept(topPos4.offset(right * 4, -1, back), this.leaves);
		for (int x = 0; x <= 2; x++) {
			leaves.accept(topPos4.offset(right * 4, 0, back * x), this.leaves);
		}
		for (int x = 0; x >= -3; x--) {
			leaves.accept(topPos4.offset(right * 5, x, 0), this.leaves);
		}
		for (int x = 0; x >= -3; x--) {
			leaves.accept(topPos4.offset(right * 6, x, back), this.leaves);
		}
		for (int x = 0; x >= -2; x--) {
			leaves.accept(topPos4.offset(right * 5, x, back * 2), this.leaves);
		}

		for (int x = 0; x >= -2; x--) {
			leaves.accept(topPos4.offset(right * 2, x, front), this.leaves);
		}
		for (int x = 0; x >= -3; x--) {
			leaves.accept(topPos4.offset(right * 2, x, front * 3), this.leaves);
		}
		for (int x = 0; x >= -1; x--) {
			leaves.accept(topPos4.offset(right * 2, x, front * 4), this.leaves);
		}
		for (int x = 0; x >= -3; x--) {
			leaves.accept(topPos4.offset(right * 2, x, front * 5), this.leaves);
		}
		for (int x = 0; x >= -4; x--) {
			leaves.accept(topPos4.offset(right, x, front * 6), this.leaves);
		}
		for (int x = 0; x >= -2; x--) {
			leaves.accept(topPos4.offset(right, x, front * 4), this.leaves);
		}
		for (int x = 0; x >= -1; x--) {
			leaves.accept(topPos4.offset(right, x, front * 2), this.leaves);
		}

		leaves.accept(topPos4.offset(0, 0, front * 3), this.leaves);
		for (int x = 0; x >= -1; x--) {
			leaves.accept(topPos4.offset(0, x, front * 4), this.leaves);
		}
		for (int x = 0; x >= -2; x--) {
			leaves.accept(topPos4.offset(0, x, front * 5), this.leaves);
		}
		leaves.accept(topPos4.offset(left, 0, front * 2), this.leaves);
		for (int x = 0; x >= -2; x--) {
			leaves.accept(topPos4.offset(left * 2, x, front), this.leaves);
		}
		for (int x = 0; x >= -1; x--) {
			leaves.accept(topPos4.offset(left * 3, x, front * 2), this.leaves);
		}
		leaves.accept(topPos4.offset(left * 4, 0, front * 2), this.leaves);
		for (int x = 0; x >= -2; x--) {
			leaves.accept(topPos4.offset(left * 4, x, front * 3), this.leaves);
		}
		for (int x = 0; x >= -1; x--) {
			leaves.accept(topPos4.offset(left * 5, x, front * 3), this.leaves);
		}
		for (int x = 0; x >= -2; x--) {
			leaves.accept(topPos4.offset(left * 6, x, front * 2), this.leaves);
		}
		for (int x = 0; x >= -3; x--) {
			leaves.accept(topPos4.offset(left * 5, x, front), this.leaves);
		}
		leaves.accept(topPos4.offset(left * 4, 0, 0), this.leaves);

		for (int x = 0; x <= 5; x++) {
			leaves.accept(topPos4.offset(left, 0, back * x), this.leaves);
		}
		for (int x = 0; x >= -1; x--) {
			leaves.accept(topPos4.offset(left * 3, x, back), this.leaves);
		}
		leaves.accept(topPos4.offset(left * 3, 0, back * 3), this.leaves);
		for (int x = 0; x >= -1; x--) {
			leaves.accept(topPos4.offset(left * 3, x, back * 4), this.leaves);
		}
		for (int x = 0; x >= -2; x--) {
			leaves.accept(topPos4.offset(left * 3, x, back * 5), this.leaves);
		}
		for (int x = 0; x >= -3; x--) {
			leaves.accept(topPos4.offset(left * 2, x, back * 6), this.leaves);
		}
		leaves.accept(topPos4.offset(left * 2, 0, back * 4), this.leaves);

		leaves.accept(topPos4.offset(left, -1, back), this.leaves);
		leaves.accept(topPos4.offset(left, -1, back * 3), this.leaves);
		leaves.accept(topPos4.offset(left, -1, back * 5), this.leaves);

		leaves.accept(topPos4.offset(0, 0, back * 4), this.leaves);
	}

	private void addVines(WorldGenLevel world, RandomSource rand, BlockPos startPos, int baseHeight, int height, int leavesRadius, int generationAttempts) {
		for (int i = 0; i < generationAttempts; i++) {
			// choose a random direction
			Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
			Direction back = direction.getOpposite();
			Direction sideways = direction.getClockWise();

			// Choose a random starting point somewhere just outside the boundary of the tree leaves
			BlockPos pos = startPos.above(BDBMathUtil.nextIntBetween(rand, baseHeight + 1, height)).relative(direction, leavesRadius + 1).relative(sideways, BDBMathUtil.nextIntBetween(rand, -leavesRadius, leavesRadius));

			// Move back towards the center until we meet a leaf, then stick a vine on it
			for (int l = 0; l < leavesRadius; l++) {
				if (world.getBlockState(pos.relative(back, 1 + l)) == this.leaves) {
					this.setVine(world, rand, pos.relative(back, l), back, 4);
					break;
				}
			}
		}
	}

	private boolean setVine(LevelAccessor world, RandomSource rand, BlockPos pos, Direction side, int length) {
		BlockState vineBlockState = Blocks.VINE.defaultBlockState().setValue(VineBlock.NORTH, Boolean.valueOf(side == Direction.NORTH)).setValue(VineBlock.EAST, Boolean.valueOf(side == Direction.EAST)).setValue(VineBlock.SOUTH, Boolean.valueOf(side == Direction.SOUTH)).setValue(VineBlock.WEST, Boolean.valueOf(side == Direction.WEST));
		boolean generateVine = false;
		while (world.getBlockState(pos).isAir() && length > 0 && rand.nextInt(12) > 0) {
			this.setBlock(world, pos, vineBlockState);
			generateVine = true;
			length--;
			pos = pos.below();
		}
		return generateVine;
	}

	private void replaceGround(LevelAccessor world, BlockPos pos) {
		int radius = 3;
		for (int y = -radius; y <= radius; y++) {
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					BlockPos changedPosition = pos.offset(x, -1 + y, z);
					// Check if the Block is replaceable
					if (world.getBlockState(changedPosition).is(BDBTags.Blocks.SWAMP_WILLOW_TREE_REPLACEABLES)) {
						// Less chance to replace Blocks when we get further away from the sapling
						int randomChance = (Math.abs(x) + Math.abs(z)) / 2 + 1;
						// Generate Coarse Dirt
						if (world.getRandom().nextInt(randomChance) == 0) {
							world.setBlock(changedPosition, Blocks.COARSE_DIRT.defaultBlockState(), 19);
						}
						// 4x less likely to generate Podzol
						if (world.getRandom().nextInt(randomChance * 4) == 0) {
							world.setBlock(changedPosition, Blocks.PODZOL.defaultBlockState(), 19);
						}
					}
				}
			}
		}
	}
}