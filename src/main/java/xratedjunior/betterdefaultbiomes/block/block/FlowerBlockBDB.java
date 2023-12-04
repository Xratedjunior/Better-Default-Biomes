package xratedjunior.betterdefaultbiomes.block.block;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class FlowerBlockBDB extends FlowerBlock implements BonemealableBlock {
	private static final VoxelShape NORMAL = makeSquareShape(2.0D, 8.0D);
	private static final VoxelShape LARGE = makeSquareShape(1.0D, 14.0D);

	public FlowerBlockBDB(Supplier<MobEffect> effect, int effectDuration, BlockBehaviour.Properties properties) {
		super(effect.get(), effectDuration, properties);
	}

	private static VoxelShape makeSquareShape(double widthPixelOffset, double yPixelHeight) {
		return Block.box(widthPixelOffset, 0.0D, widthPixelOffset, (16 - widthPixelOffset), yPixelHeight, (16 - widthPixelOffset));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
		Block block = state.getBlock();
		Vec3 vector3d = state.getOffset(worldIn, pos);

		if (block == BDBBlocks.PURPLE_VERBENA.get() || block == BDBBlocks.BLUE_POPPY.get()) {
			return LARGE.move(vector3d.x, vector3d.y, vector3d.z);
		}

		return NORMAL.move(vector3d.x, vector3d.y, vector3d.z);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		Block ground = worldIn.getBlockState(pos.below()).getBlock();

		if (this == BDBBlocks.DARK_VIOLET.get()) {
			return ground == Blocks.TERRACOTTA || ground == Blocks.RED_SAND || ground == Blocks.SAND || super.canSurvive(state, worldIn, pos);
		}

		return super.canSurvive(state, worldIn, pos);
	}

	/*
	 * Enables spawning drops
	 */
	@Override
	public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		return true;
	}

	/*
	 * Drops Items
	 */
	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		popResource(world, pos, new ItemStack(this));
	}
}
