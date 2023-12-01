package xratedjunior.betterdefaultbiomes.block.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class MushroomBlockBDB extends MushroomBlock {

	public MushroomBlockBDB(Block.Properties properties, ResourceKey<ConfiguredFeature<?, ?>> bigMushroom) {
		super(properties, bigMushroom);
	}

	/**
	 * Are easier to place than Vanilla Mushrooms
	 */
	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockState BlockState = worldIn.getBlockState(pos.below());
		return BlockState.canSustainPlant(worldIn, pos.below(), Direction.UP, this);
	}
}
