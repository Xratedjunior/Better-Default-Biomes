package xratedjunior.betterdefaultbiomes.world.generation.feature.feature.tree;

import java.util.Random;
import java.util.function.BiConsumer;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.util.BDBMathUtil;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class PalmTreeFeature extends TreeFeatureBDB {
	protected BlockState log;
	protected BlockState leaves;
	protected int minTreeHeight;
	protected int maxTreeHeight;

	public PalmTreeFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
		this.log = BDBBlocks.PALM_LOG.get().defaultBlockState();
		this.leaves = BDBBlocks.PALM_LEAVES.get().defaultBlockState();
		this.minTreeHeight = 8;
		this.maxTreeHeight = 12;
	}

	@Override
	protected boolean createTree(WorldGenLevel world, Random random, BlockPos startPos, BiConsumer<BlockPos, BlockState> logs, BiConsumer<BlockPos, BlockState> leaves) {
		// Generation settings
		int height = BDBMathUtil.nextIntBetween(random, this.minTreeHeight, this.maxTreeHeight);
		int leavesRadius = 1;
		int heightMinusTop = height - leavesRadius - 1;
		boolean slant = true;
		Direction direction = Direction.getRandom(random); //The direction the palm tree curves towards
		if (direction == Direction.DOWN || direction == Direction.UP) {
			slant = false;
		}
		double baseSlant = random.nextInt(35) / 100D;
		double slantMultiplier = 1.3D;

		if (height < 6) {
			return false;
		} //Prevent trees from being too small 

		// Move up to space above ground
		BlockPos pos = startPos;//.above();

		if (!this.checkSpace(world, pos, height, 1)) {
			// Abandon if there isn't enough room
			return false;
		}

		double slantOffset = baseSlant;

		// Generate trunk of tree (trunk only)
		for (int step = 0; step <= heightMinusTop; step++) {
			BlockPos offsetPos = pos.above(step);

			if (slant == true) {
				offsetPos = pos.above(step).relative(direction, (int) Math.floor(slantOffset));
			}

			if (step == heightMinusTop) {
				logs.accept(offsetPos, this.log);
				// Generate top of tree
				//				this.placeLog(world, offsetPos, changedLogs, boundingBox);
				this.generateLeavesTop(leaves, offsetPos);
				//				generateLeavesTop(world, offsetPos, leavesRadius, changedLeaves, boundingBox);
				break;
			}

			logs.accept(offsetPos, this.log);
			//			this.placeLog(world, offsetPos, changedLogs, boundingBox);

			//As the height increases, slant more drastically
			slantOffset *= slantMultiplier;
		}

		return true;
	}

	/**
	 * Generate Palm leaves
	 */
	private void generateLeavesTop(BiConsumer<BlockPos, BlockState> leaves, BlockPos pos) {
		leaves.accept(pos.offset(0, 1, 0), this.leaves);
		leaves.accept(pos.offset(1, 1, 0), this.leaves);
		leaves.accept(pos.offset(-1, 1, 0), this.leaves);
		leaves.accept(pos.offset(0, 1, 1), this.leaves);
		leaves.accept(pos.offset(0, 1, -1), this.leaves);

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

		leaves.accept(pos.offset(4, -1, 0), this.leaves);
		leaves.accept(pos.offset(-4, -1, 0), this.leaves);
		leaves.accept(pos.offset(0, -1, 4), this.leaves);
		leaves.accept(pos.offset(0, -1, -4), this.leaves);

		leaves.accept(pos.offset(2, 0, 2), this.leaves);
		leaves.accept(pos.offset(2, 0, -2), this.leaves);
		leaves.accept(pos.offset(-2, 0, 2), this.leaves);
		leaves.accept(pos.offset(-2, 0, -2), this.leaves);

		leaves.accept(pos.offset(3, -1, 3), this.leaves);
		leaves.accept(pos.offset(3, -1, -3), this.leaves);
		leaves.accept(pos.offset(-3, -1, 3), this.leaves);
		leaves.accept(pos.offset(-3, -1, -3), this.leaves);
	}
}