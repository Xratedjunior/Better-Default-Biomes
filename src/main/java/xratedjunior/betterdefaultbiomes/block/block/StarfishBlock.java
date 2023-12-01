package xratedjunior.betterdefaultbiomes.block.block;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
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
public class StarfishBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock {
	public static final EnumProperty<WallFacing> WALL_FACING = EnumProperty.create("wall_facing", WallFacing.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	private static final VoxelShape STARFISH_FLOOR = makeSquareShape(3.0D, 0.0D, 1.0D);
	private static final VoxelShape STARFISH_CEILING = makeSquareShape(3.0D, 15.0D, 16.0D);
	// Hitboxes for wall Starfish.
	private static final VoxelShape STARFISH_EAST = makeShape(3.0D, 13.0D, 0.0D, 1.0D, 3.0D, 13.0D);
	private static final VoxelShape STARFISH_WEST = makeShape(3.0D, 13.0D, 15.0D, 16.0D, 3.0D, 13.0D);
	private static final VoxelShape STARFISH_SOUTH = makeShape(0.0D, 1.0D, 3.0D, 13.0D, 3.0D, 13.0D);
	private static final VoxelShape STARFISH_NORTH = makeShape(15.0D, 16.0D, 3.0D, 13.0D, 3.0D, 13.0D);

	@Nullable
	private final DyeColor starfishColor;

	public StarfishBlock(@Nullable DyeColor colorIn, Properties properties) {
		super(properties);
		this.starfishColor = colorIn;
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.FLOOR).setValue(WALL_FACING, WallFacing.UP).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(LIT, Boolean.valueOf(false)));
	}

	/**
	 * Middle click block selector
	 */
	@Override
	public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(getBlockByColor(this.starfishColor));
	}

	/*********************************************************** Hitbox ********************************************************/

	private static VoxelShape makeSquareShape(double widthPixelOffset, double yPixelStart, double yPixelHeight) {
		return Block.box(widthPixelOffset, yPixelStart, widthPixelOffset, (16 - widthPixelOffset), yPixelHeight, (16 - widthPixelOffset));
	}

	private static VoxelShape makeShape(double northPixelOffset, double southPixelOffset, double westPixelOffset, double eastPixelOffset, double yPixelBottom, double yPixelTop) {
		return Block.box(westPixelOffset, yPixelBottom, northPixelOffset, eastPixelOffset, yPixelTop, southPixelOffset);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
		Vec3 vector3d = state.getOffset(worldIn, pos);
		Direction direction = state.getValue(FACING);
		switch ((AttachFace) state.getValue(FACE)) {
		case FLOOR:
		default:
			return STARFISH_FLOOR.move(vector3d.x, vector3d.y, vector3d.z);
		case CEILING:
			return STARFISH_CEILING.move(vector3d.x, vector3d.y, vector3d.z);
		case WALL:
			switch (direction) {
			case EAST:
				return STARFISH_EAST;
			case WEST:
				return STARFISH_WEST;
			case SOUTH:
				return STARFISH_SOUTH;
			case NORTH:
			default:
				return STARFISH_NORTH;
			}
		}
	}

	/*********************************************************** Block State ********************************************************/

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		return canAttach(worldIn, pos, getConnectedDirection(state).getOpposite());
	}

	// TODO Optimize. Many double checks and code
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		for (Direction direction : context.getNearestLookingDirections()) {
			BlockState blockstate = this.defaultBlockState();
			FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
			if (direction.getAxis() == Direction.Axis.Y) {
				if (fluidstate.getType() == Fluids.WATER) {
					blockstate = blockstate.setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, context.getHorizontalDirection().getOpposite());
				} else {
					blockstate = blockstate.setValue(FACE, AttachFace.FLOOR).setValue(FACING, context.getHorizontalDirection());
				}
			} else if (fluidstate.getType() == Fluids.WATER) {
				blockstate = getWallBlockByColor(this.starfishColor).defaultBlockState();
				blockstate = blockstate.setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite());
			} else {
				blockstate = blockstate.setValue(FACING, context.getHorizontalDirection().getOpposite());
			}

			if (this.canSurvive(blockstate, context.getLevel(), context.getClickedPos())) {
				return blockstate.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
			}
		}
		return null;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, FACE, WALL_FACING, WATERLOGGED, LIT);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	/*********************************************************** Right Click Controller ********************************************************/

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack itemStackInHand = player.getItemInHand(handIn);
		Item itemInHand = itemStackInHand.getItem();
		boolean useItem = false;
		if (itemInHand instanceof DyeItem) {
			DyeItem dyeInHand = (DyeItem) itemInHand;
			Block starfish = state.getBlock();
			Block coloredStarfish;
			if (starfish instanceof StarfishWallBlock) {
				coloredStarfish = getWallBlockByColor(dyeInHand.getDyeColor());
				if ((starfish != coloredStarfish)) {
					useItem = true;
				}
			} else {
				coloredStarfish = getBlockByColor(dyeInHand.getDyeColor());
				if ((starfish != coloredStarfish)) {
					useItem = true;
				}
			}

			if (useItem) {
				worldIn.setBlockAndUpdate(pos, coloredStarfish.defaultBlockState().setValue(FACING, state.getValue(FACING)).setValue(FACE, state.getValue(FACE)).setValue(WALL_FACING, state.getValue(WALL_FACING)).setValue(WATERLOGGED, state.getValue(WATERLOGGED)).setValue(LIT, state.getValue(LIT)));
			}
		}

		else if (itemInHand == Items.GLOWSTONE_DUST && !state.getValue(LIT)) {
			worldIn.setBlockAndUpdate(pos, state.setValue(LIT, Boolean.valueOf(true)));
			useItem = true;
		}

		if (useItem) {
			if (!player.getAbilities().instabuild) {
				itemStackInHand.shrink(1);
			}
		}

		else if (player.mayBuild()) {
			this.rotateBlock(player, state, worldIn, pos);
			useItem = true;
		}

		return useItem ? InteractionResult.SUCCESS : InteractionResult.PASS;
	}

	private void rotateBlock(Player player, BlockState state, Level worldIn, BlockPos pos) {
		SoundType sound = this.getSoundType(state, worldIn, pos, player);
		worldIn.playSound(player, pos, BDBSoundEvents.BLOCK_SMALL_ROTATE.get(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
		if (this instanceof StarfishWallBlock) {
			worldIn.setBlockAndUpdate(pos, state.setValue(WALL_FACING, WallFacing.rotateClockwise(state.getValue(WALL_FACING))));
			return;
		} else if (state.getValue(FACE).equals(AttachFace.FLOOR)) {
			// If Starfish is on the floor
			switch (state.getValue(FACING)) {
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
		} else {
			// If Starfish is on the ceiling
			switch (state.getValue(FACING)) {
			case NORTH:
			default:
				worldIn.setBlockAndUpdate(pos, state.setValue(FACING, Direction.WEST));
				break;
			case EAST:
				worldIn.setBlockAndUpdate(pos, state.setValue(FACING, Direction.NORTH));
				break;
			case SOUTH:
				worldIn.setBlockAndUpdate(pos, state.setValue(FACING, Direction.EAST));
				break;
			case WEST:
				worldIn.setBlockAndUpdate(pos, state.setValue(FACING, Direction.SOUTH));
				break;
			}
		}
	}

	/*********************************************************** Dye Controller ********************************************************/

	public static Block getBlockByColor(@Nullable DyeColor colorIn) {
		switch (colorIn) {
		case WHITE:
			return BDBBlocks.STARFISH_WHITE.get();
		case ORANGE:
			return BDBBlocks.STARFISH_ORANGE.get();
		case MAGENTA:
			return BDBBlocks.STARFISH_MAGENTA.get();
		case LIGHT_BLUE:
			return BDBBlocks.STARFISH_LIGHT_BLUE.get();
		case YELLOW:
			return BDBBlocks.STARFISH_YELLOW.get();
		case LIME:
			return BDBBlocks.STARFISH_LIME.get();
		case PINK:
		default:
			return BDBBlocks.STARFISH_PINK.get();
		case GRAY:
			return BDBBlocks.STARFISH_GRAY.get();
		case LIGHT_GRAY:
			return BDBBlocks.STARFISH_LIGHT_GRAY.get();
		case CYAN:
			return BDBBlocks.STARFISH_CYAN.get();
		case PURPLE:
			return BDBBlocks.STARFISH_PURPLE.get();
		case BLUE:
			return BDBBlocks.STARFISH_BLUE.get();
		case BROWN:
			return BDBBlocks.STARFISH_BROWN.get();
		case GREEN:
			return BDBBlocks.STARFISH_GREEN.get();
		case RED:
			return BDBBlocks.STARFISH_RED.get();
		case BLACK:
			return BDBBlocks.STARFISH_BLACK.get();
		}
	}

	public static Block getWallBlockByColor(@Nullable DyeColor colorIn) {
		switch (colorIn) {
		case WHITE:
			return BDBBlocks.STARFISH_WALL_WHITE.get();
		case ORANGE:
			return BDBBlocks.STARFISH_WALL_ORANGE.get();
		case MAGENTA:
			return BDBBlocks.STARFISH_WALL_MAGENTA.get();
		case LIGHT_BLUE:
			return BDBBlocks.STARFISH_WALL_LIGHT_BLUE.get();
		case YELLOW:
			return BDBBlocks.STARFISH_WALL_YELLOW.get();
		case LIME:
			return BDBBlocks.STARFISH_WALL_LIME.get();
		case PINK:
		default:
			return BDBBlocks.STARFISH_WALL_PINK.get();
		case GRAY:
			return BDBBlocks.STARFISH_WALL_GRAY.get();
		case LIGHT_GRAY:
			return BDBBlocks.STARFISH_WALL_LIGHT_GRAY.get();
		case CYAN:
			return BDBBlocks.STARFISH_WALL_CYAN.get();
		case PURPLE:
			return BDBBlocks.STARFISH_WALL_PURPLE.get();
		case BLUE:
			return BDBBlocks.STARFISH_WALL_BLUE.get();
		case BROWN:
			return BDBBlocks.STARFISH_WALL_BROWN.get();
		case GREEN:
			return BDBBlocks.STARFISH_WALL_GREEN.get();
		case RED:
			return BDBBlocks.STARFISH_WALL_RED.get();
		case BLACK:
			return BDBBlocks.STARFISH_WALL_BLACK.get();
		}
	}

	public enum WallFacing implements StringRepresentable {
		UP("up"),
		RIGHT("right"),
		DOWN("down"),
		LEFT("left");

		private String name;
		public static final WallFacing[] WALLFACING_DIRECTIONS = values();

		WallFacing(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}

		public static WallFacing getRandomWallFacingDirection(RandomSource rand) {
			return Util.getRandom(WALLFACING_DIRECTIONS, rand);
		}

		public static WallFacing rotateClockwise(WallFacing currentWallFacingDirection) {
			switch (currentWallFacingDirection) {
			case UP:
			default:
				return RIGHT;
			case RIGHT:
				return DOWN;
			case DOWN:
				return LEFT;
			case LEFT:
				return UP;
			}
		}
	}
}
