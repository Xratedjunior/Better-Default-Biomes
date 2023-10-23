package xratedjunior.betterdefaultbiomes.block.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.data.BDBTags;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class SaplingBlockBDB extends SaplingBlock {

	public SaplingBlockBDB(AbstractTreeGrower treeGrower, Properties blockProperties) {
		super(treeGrower, blockProperties);
	}

	/**
	 * Returns if the ground is a valid Block to be placed on
	 */
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		if (this == BDBBlocks.PALM_SAPLING.get()) {
			return state.is(BDBTags.Blocks.PALM_SAPLING_SOIL);
		}

		return super.mayPlaceOn(state, worldIn, pos);
	}
}
