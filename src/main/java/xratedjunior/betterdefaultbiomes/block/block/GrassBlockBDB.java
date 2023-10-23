package xratedjunior.betterdefaultbiomes.block.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.data.BDBTags;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class GrassBlockBDB extends BushBlock implements IPlantable {
	protected static final VoxelShape NORMAL = makeSquareShape(2.0D, 13.0D);
	protected static final VoxelShape SHORT = makeSquareShape(1.0D, 7.0D);

	public GrassBlockBDB(Block.Properties properties) {
		super(properties);
	}

	private static VoxelShape makeSquareShape(double widthPixelOffset, double yPixelHeight) {
		return Block.box(widthPixelOffset, 0.0D, widthPixelOffset, (16 - widthPixelOffset), yPixelHeight, (16 - widthPixelOffset));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
		Block block = state.getBlock();
		Vec3 vector3d = state.getOffset(worldIn, pos);

		if (block == BDBBlocks.SHORT_GRASS.get() || block == BDBBlocks.DEAD_GRASS.get() || block == BDBBlocks.DUNE_GRASS.get()) {
			return SHORT.move(vector3d.x, vector3d.y, vector3d.z);
		}

		return NORMAL.move(vector3d.x, vector3d.y, vector3d.z);
	}

	/*
	 * Returns if the ground is a valid Block to be placed on
	 */
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		if (this == BDBBlocks.DUNE_GRASS.get()) {
			return state.is(BDBTags.Blocks.DUNE_GRASS_SOIL);
		}

		else if (this == BDBBlocks.DEAD_GRASS.get()) {
			return state.is(BDBTags.Blocks.DEAD_GRASS_SOIL);
		}

		return super.mayPlaceOn(state, worldIn, pos);
	}

	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XYZ;
	}

	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return PlantType.PLAINS;
	}
}
