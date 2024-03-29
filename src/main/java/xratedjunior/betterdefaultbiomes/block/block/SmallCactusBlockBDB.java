package xratedjunior.betterdefaultbiomes.block.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import xratedjunior.betterdefaultbiomes.data.BDBTags;

/**
 * TODO Mob will path find through Block.
 * {@link EntityType#isBlockDangerous} & {@link WalkNodeEvaluator#checkNeighbourBlocks}
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class SmallCactusBlockBDB extends BushBlock implements IPlantable {
	private static final VoxelShape SHAPE = makeSquareShape(2.0D, 15.0D);

	public SmallCactusBlockBDB(Block.Properties builder) {
		super(builder);
	}

	private static VoxelShape makeSquareShape(double widthPixelOffset, double yPixelHeight) {
		return Block.box(widthPixelOffset, 0.0D, widthPixelOffset, (16 - widthPixelOffset), yPixelHeight, (16 - widthPixelOffset));
	}

	/*
	 * Returns the HitBox of this Block
	 */
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		Vec3 vector3d = state.getOffset(worldIn, pos);
		return SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
	}

	/*
	 * Returns if the ground is a valid Block to be placed on
	 */
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return state.is(BDBTags.Blocks.PINK_CACTUS_FLOWER_SOIL);
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		// Avoid damaging bees.
		if (entityIn.getType().is(BDBTags.EntityTypes.CACTUS_IMMUNE)) {
			return;
		}

		entityIn.hurt(worldIn.damageSources().cactus(), 1.0F);
	}

	/**
	 * From: {@link CactusBlock#isPathfindable(BlockState, BlockGetter, BlockPos, PathComputationType)}
	 */
	@Override
	public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_) {
		return false;
	}

	// TODO Can maybe be removed for all plants?
	@Override
	public PlantType getPlantType(BlockGetter world, BlockPos pos) {
		return PlantType.DESERT;
	}
}