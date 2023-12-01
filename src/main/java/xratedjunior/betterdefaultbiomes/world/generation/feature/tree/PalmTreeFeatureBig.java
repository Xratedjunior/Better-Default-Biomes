package xratedjunior.betterdefaultbiomes.world.generation.feature.tree;

import java.util.function.BiConsumer;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.util.BDBMathUtil;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class PalmTreeFeatureBig extends TreeFeatureBDB {
	protected BlockState log;
	protected BlockState leaves;
	protected int minTreeHeight;
	protected int maxTreeHeight;

	public PalmTreeFeatureBig(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
		this.log = BDBBlocks.PALM_LOG.get().defaultBlockState();
		this.leaves = BDBBlocks.PALM_LEAVES.get().defaultBlockState();
		this.minTreeHeight = 11;
		this.maxTreeHeight = 13;
	}

	@Override
	protected boolean createTree(WorldGenLevel world, RandomSource random, BlockPos startPos, BiConsumer<BlockPos, BlockState> logs, BiConsumer<BlockPos, BlockState> leaves) {
		// Generation settings
		int height = BDBMathUtil.nextIntBetween(random, this.minTreeHeight, this.maxTreeHeight);
		int leavesRadius = 1;
		int heightMinusTop = height - leavesRadius - 1;
		boolean slant = true;
		Direction direction = Direction.getRandom(random); //The direction the palm tree curves towards
		if (direction == Direction.DOWN || direction == Direction.UP) {
			slant = false;
		}

		if (height < 8) {
			return false;
		} //Prevent trees from being too small 

		// Move up to space above ground
		BlockPos pos = startPos;//.above();

		if (!this.checkSpace(world, pos, height, 1)) {
			// Abandon if there isn't enough room
			return false;
		}

		// Generate trunk of tree (trunk only)
		for (int step = 0; step <= heightMinusTop; step++) {
			BlockPos offsetPos = pos.above(step);
			Direction directionOffset = null;
			Direction north = Direction.NORTH;
			Direction east = Direction.EAST;
			Direction south = Direction.SOUTH;
			Direction west = Direction.WEST;

			if (slant == true) {
				if (direction == west) {
					directionOffset = north;
				} else if (direction == north) {
					directionOffset = east;
				} else if (direction == east) {
					directionOffset = south;
				} else if (direction == south) {
					directionOffset = west;
				}
				if (step >= heightMinusTop) {
					BlockPos support2 = pos.above(step).relative(direction, 1).relative(directionOffset, 1).relative(direction, 1);
					logs.accept(support2, this.log);
					offsetPos = pos.above(step + 1).relative(direction, 1).relative(directionOffset, 1).relative(direction, 1);
				} else if (step >= (heightMinusTop - 3)) {
					BlockPos support1 = pos.above(step).relative(direction, 1).relative(directionOffset, 1);
					logs.accept(support1, this.log);
					offsetPos = pos.above(step + 1).relative(direction, 1).relative(directionOffset, 1);
				} else if (step >= (heightMinusTop / 2)) {
					BlockPos support = pos.above(step - 1).relative(direction, 1);
					logs.accept(support, this.log);
					offsetPos = pos.above(step + 1).relative(direction, 1);
				}
			}

			if (step == heightMinusTop) {
				// Generate top of tree
				logs.accept(offsetPos, this.log);
				generateLeavesTop(leaves, offsetPos);
				break;
			}

			logs.accept(offsetPos, this.log);
		}

		return true;
	}

	/**
	 * Generate Palm leaves
	 */
	private void generateLeavesTop(BiConsumer<BlockPos, BlockState> leaves, BlockPos pos) {
		leaves.accept(pos.offset(0, 1, 0), this.leaves);
		leaves.accept(pos.offset(0, 2, 0), this.leaves);

		leaves.accept(pos.offset(1, 1, 0), this.leaves);
		leaves.accept(pos.offset(-1, 1, 0), this.leaves);
		leaves.accept(pos.offset(0, 1, 1), this.leaves);
		leaves.accept(pos.offset(0, 1, -1), this.leaves);

		leaves.accept(pos.offset(2, 1, 0), this.leaves);
		leaves.accept(pos.offset(-2, 1, 0), this.leaves);
		leaves.accept(pos.offset(0, 1, 2), this.leaves);
		leaves.accept(pos.offset(0, 1, -2), this.leaves);

		leaves.accept(pos.offset(1, 0, 0), this.leaves);
		leaves.accept(pos.offset(-1, 0, 0), this.leaves);
		leaves.accept(pos.offset(0, 0, 1), this.leaves);
		leaves.accept(pos.offset(0, 0, -1), this.leaves);

		leaves.accept(pos.offset(1, 0, 1), this.leaves);
		leaves.accept(pos.offset(1, 0, -1), this.leaves);
		leaves.accept(pos.offset(-1, 0, 1), this.leaves);
		leaves.accept(pos.offset(-1, 0, -1), this.leaves);

		leaves.accept(pos.offset(2, 0, 0), this.leaves);
		leaves.accept(pos.offset(-2, 0, 0), this.leaves);
		leaves.accept(pos.offset(0, 0, 2), this.leaves);
		leaves.accept(pos.offset(0, 0, -2), this.leaves);
		leaves.accept(pos.offset(3, 0, 0), this.leaves);
		leaves.accept(pos.offset(-3, 0, 0), this.leaves);
		leaves.accept(pos.offset(0, 0, 3), this.leaves);
		leaves.accept(pos.offset(0, 0, -3), this.leaves);

		leaves.accept(pos.offset(3, 0, 1), this.leaves);
		leaves.accept(pos.offset(-3, 0, -1), this.leaves);
		leaves.accept(pos.offset(-1, 0, 3), this.leaves);
		leaves.accept(pos.offset(1, 0, -3), this.leaves);
		leaves.accept(pos.offset(3, -1, 1), this.leaves);
		leaves.accept(pos.offset(-3, -1, -1), this.leaves);
		leaves.accept(pos.offset(-1, -1, 3), this.leaves);
		leaves.accept(pos.offset(1, -1, -3), this.leaves);

		leaves.accept(pos.offset(4, -1, 1), this.leaves);
		leaves.accept(pos.offset(-4, -1, -1), this.leaves);
		leaves.accept(pos.offset(-1, -1, 4), this.leaves);
		leaves.accept(pos.offset(1, -1, -4), this.leaves);
		leaves.accept(pos.offset(4, -2, 1), this.leaves);
		leaves.accept(pos.offset(-4, -2, -1), this.leaves);
		leaves.accept(pos.offset(-1, -2, 4), this.leaves);
		leaves.accept(pos.offset(1, -2, -4), this.leaves);
		leaves.accept(pos.offset(4, -3, 1), this.leaves);
		leaves.accept(pos.offset(-4, -3, -1), this.leaves);
		leaves.accept(pos.offset(-1, -3, 4), this.leaves);
		leaves.accept(pos.offset(1, -3, -4), this.leaves);
	}
}
