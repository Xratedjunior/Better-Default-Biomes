package xratedjunior.betterdefaultbiomes.block.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class SimpleBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	/**
	 * TODO Make better hitboxes
	 * {@linkplain https://github.com/ModdingForBlockheads/Waystones/blob/1.18.x/shared/src/main/java/net/blay09/mods/waystones/block/WaystoneBlock.java}
	 */
	private static final VoxelShape SAND_CASTLE = makeSquareShape(2.5D, 9.0D);
	private static final VoxelShape SEASHELL = makeSquareShape(3.5D, 1.5D);

	public SimpleBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	/*********************************************************** Hitbox ********************************************************/

	protected static VoxelShape makeSquareShape(double widthPixelOffset, double yPixelHeight) {
		return Block.box(widthPixelOffset, 0.0D, widthPixelOffset, (16 - widthPixelOffset), yPixelHeight, (16 - widthPixelOffset));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
		Vec3 vector3d = state.getOffset(worldIn, pos);
		Block block = state.getBlock();
		if (block == BDBBlocks.SAND_CASTLE.get()) {
			return SAND_CASTLE.move(vector3d.x, vector3d.y, vector3d.z);
		} else {
			return SEASHELL.move(vector3d.x, vector3d.y, vector3d.z);
		}
	}

	/*********************************************************** Block State ********************************************************/

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos groundPos = pos.below();
		BlockState groundBlock = worldIn.getBlockState(groundPos);
		return groundBlock.isFaceSturdy(worldIn, groundPos, Direction.UP);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = this.defaultBlockState();
		blockstate = blockstate.setValue(FACING, context.getHorizontalDirection().getOpposite());

		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		if (this.canSurvive(blockstate, context.getLevel(), context.getClickedPos())) {
			return blockstate.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	/*********************************************************** Right Click Controller ********************************************************/

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		boolean swingArm = false;
		if (player.mayBuild()) {
			Direction blockDirection = state.getValue(FACING);
			this.rotateBlock(state, worldIn, pos, player, blockDirection);
			swingArm = true;
		}
		return swingArm ? InteractionResult.SUCCESS : InteractionResult.PASS;
	}

	private void rotateBlock(BlockState state, Level worldIn, BlockPos pos, Player player, Direction blockDirection) {
		SoundType sound = this.getSoundType(state, worldIn, pos, player);
		worldIn.playSound(player, pos, BDBSoundEvents.BLOCK_SMALL_ROTATE.get(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
		switch (blockDirection) {
		case NORTH:
		default:
			worldIn.setBlockAndUpdate(pos, state.setValue(FACING, Direction.EAST));
			break;
		case EAST:
			worldIn.setBlockAndUpdate(pos, state.setValue(FACING, Direction.SOUTH));
			break;
		case SOUTH:
			worldIn.setBlockAndUpdate(pos, state.setValue(FACING, Direction.WEST));
			break;
		case WEST:
			worldIn.setBlockAndUpdate(pos, state.setValue(FACING, Direction.NORTH));
			break;
		}
	}
}
