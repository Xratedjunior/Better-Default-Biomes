package xratedjunior.betterdefaultbiomes.block.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class PineconeBlock extends SimpleBlock implements BonemealableBlock {
	private static final Random RANDOM = new Random();
	public static final BooleanProperty SIDEWAYS = BooleanProperty.create("sideways");
	private static final VoxelShape PINECONE = makeSquareShape(6.0D, 4.0D);
	private final AbstractTreeGrower treeGrower;

	public PineconeBlock(AbstractTreeGrower treeGrower, Properties properties) {
		super(properties);
		this.treeGrower = treeGrower;
		this.registerDefaultState(this.stateDefinition.any().setValue(SIDEWAYS, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	/*********************************************************** Hitbox ********************************************************/

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
		Vec3 vector3d = state.getOffset(worldIn, pos);
		return PINECONE.move(vector3d.x, vector3d.y, vector3d.z);
	}

	/*********************************************************** Block State ********************************************************/

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SIDEWAYS);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos groundPos = pos.below();
		BlockState groundBlock = worldIn.getBlockState(groundPos);
		return super.canSurvive(state, worldIn, pos) || groundBlock.getBlock() instanceof LeavesBlock;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = super.getStateForPlacement(context);
		if (blockstate != null) {
			if (!context.getLevel().isClientSide()) {
				int i = RANDOM.nextInt(6);
				return blockstate.setValue(SIDEWAYS, Boolean.valueOf(i == 0));
			}
		}
		return blockstate;
	}

	/*********************************************************** Right Click Controller ********************************************************/

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		boolean swingArm = false;
		ItemStack itemStackInHand = player.getItemInHand(handIn);
		Item itemInHand = itemStackInHand.getItem();

		// Rotate on side and standing up if you're holding a Pinecone
		if (itemInHand == BDBBlocks.PINECONE.get().asItem()) {
			SoundType sound = this.getSoundType(state, worldIn, pos, player);
			worldIn.playSound(player, pos, BDBSoundEvents.BLOCK_SMALL_ROTATE.get(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
			if (state.getValue(SIDEWAYS)) {
				worldIn.setBlockAndUpdate(pos, state.setValue(SIDEWAYS, Boolean.valueOf(false)));
				swingArm = true;
			} else {
				worldIn.setBlockAndUpdate(pos, state.setValue(SIDEWAYS, Boolean.valueOf(true)));
				swingArm = true;
			}
		}

		// Rotate facing if you're not holding Bonemeal
		else if (itemInHand != Items.BONE_MEAL) {
			swingArm = super.use(state, worldIn, pos, player, handIn, hit) == InteractionResult.SUCCESS;
		}
		return swingArm ? InteractionResult.SUCCESS : InteractionResult.PASS;
	}

	/*********************************************************** Grow Tree ********************************************************/

	/**
	 * Can this Block be Bonemealed?
	 */
	@Override
	public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state) {
		return true;
	}

	/**
	 * Is Bonemeal a success and grow Tree?
	 */
	@Override
	public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		BlockState blockStateBelow = worldIn.getBlockState(pos.below());

		// Copied from {@link BushBlock#mayPlaceOn}
		if (blockStateBelow.is(BlockTags.DIRT) || blockStateBelow.is(Blocks.FARMLAND)) {
			// Copied from {@link SaplingBlock#isBonemealSuccess}
			return (double) rand.nextFloat() < 0.45D;
		}

		// If Block below is not suitable, return false.
		return false;
	}

	/**
	 * Grow Tree
	 */
	@Override
	public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		this.treeGrower.growTree(worldIn, worldIn.getChunkSource().getGenerator(), pos, state, rand);
	}
}
