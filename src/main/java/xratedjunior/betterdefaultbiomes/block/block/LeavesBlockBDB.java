package xratedjunior.betterdefaultbiomes.block.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class LeavesBlockBDB extends LeavesBlock {

	public LeavesBlockBDB(Properties properties) {
		super(properties);
	}

	/**
	 * Performs a random tick on a block.
	 */
	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
		if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == 7 && this.despawnPalmDistance(pos, worldIn)) {
			dropResources(state, worldIn, pos);
			worldIn.removeBlock(pos, false);
		}
	}

	/**
	 * Checks the surrounding Leaves
	 */
	private boolean despawnPalmDistance(BlockPos pos, ServerLevel world) {
		int range = 3;
		for (int x = -range; x != range; x++) {
			for (int z = -range; z != range; z++) {
				for (int y = -range; y != range; y++) {
					BlockPos pos2 = pos.offset(x, y, z);
					if (world.getBlockState(pos2).getBlock() instanceof LeavesBlock) {
						if (world.getBlockState(pos2).getValue(DISTANCE) != 7) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
