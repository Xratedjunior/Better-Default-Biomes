package xratedjunior.betterdefaultbiomes.block.block;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.data.BDBTags;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class SmallRockBlock extends SimpleBlock {
	public static final int MAX_ROCK_SIZE = 2;
	public static final IntegerProperty ROCK_SIZE = IntegerProperty.create("size", 1, MAX_ROCK_SIZE);
	private static final VoxelShape ROCK_SMALL = makeSquareShape(4.8D, 2.2D);
	private static final VoxelShape ROCK_MEDIUM = makeSquareShape(3.0D, 3.2D);

	public SmallRockBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(ROCK_SIZE, 1).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	/*********************************************************** Hitbox ********************************************************/

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
		Vec3 vector3d = state.getOffset(worldIn, pos);
		switch (state.getValue(ROCK_SIZE)) {
		case 1:
		default:
			return ROCK_SMALL.move(vector3d.x, vector3d.y, vector3d.z);
		case 2:
			return ROCK_MEDIUM.move(vector3d.x, vector3d.y, vector3d.z);
		}
	}

	/*********************************************************** Block State ********************************************************/

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ROCK_SIZE);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos groundPos = pos.below();
		BlockState groundBlock = worldIn.getBlockState(groundPos);
		return worldIn.getBlockState(groundPos).isFaceSturdy(worldIn, groundPos, Direction.UP) || groundBlock.getBlock() instanceof LeavesBlock;
	}

	/*********************************************************** Right Click Controller ********************************************************/

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		boolean swingArm = false;
		ItemStack itemStackInHand = player.getItemInHand(handIn);
		Item itemInHand = itemStackInHand.getItem();
		if (itemInHand == this.asItem()) {
			if (state.getValue(ROCK_SIZE) == 1) {
				this.increaseBlockSize(state, worldIn, pos, player, itemStackInHand);
				swingArm = true;
			} else {
				this.decreaseBlockSize(state, worldIn, pos, player, itemInHand);
				swingArm = true;
			}
		} else {
			swingArm = super.use(state, worldIn, pos, player, handIn, hit) == InteractionResult.SUCCESS;
		}
		return swingArm ? InteractionResult.SUCCESS : InteractionResult.PASS;
	}

	private void increaseBlockSize(BlockState state, Level worldIn, BlockPos pos, Player player, ItemStack itemStackInHand) {
		SoundType sound = this.getSoundType(state, worldIn, pos, player);
		worldIn.playSound(player, pos, BDBSoundEvents.BLOCK_SMALL_ADD.get(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
		worldIn.setBlockAndUpdate(pos, state.setValue(ROCK_SIZE, 2));
		if (!player.getAbilities().instabuild) {
			itemStackInHand.shrink(1);
		}
	}

	private void decreaseBlockSize(BlockState state, Level worldIn, BlockPos pos, Player player, Item itemInHand) {
		SoundType sound = this.getSoundType(state, worldIn, pos, player);
		worldIn.playSound(player, pos, BDBSoundEvents.BLOCK_SMALL_REMOVE.get(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
		worldIn.setBlockAndUpdate(pos, state.setValue(ROCK_SIZE, 1));
		if (!player.getAbilities().instabuild) {
			ItemEntity item = new ItemEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, new ItemStack(itemInHand));
			worldIn.addFreshEntity(item);
		}
	}

	public static enum RockVariant implements StringRepresentable {
		STONE("stone", BDBBlocks.SMALL_ROCK_STONE.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_STONE),
		COBBLE("cobble", BDBBlocks.SMALL_ROCK_COBBLE.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_COBBLE),
		MOSSY("mossy", BDBBlocks.SMALL_ROCK_MOSSY.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_MOSSY),
		ANDESITE("andesite", BDBBlocks.SMALL_ROCK_ANDESITE.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_ANDESITE),
		DIORITE("diorite", BDBBlocks.SMALL_ROCK_DIORITE.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_DIORITE),
		GRANITE("granite", BDBBlocks.SMALL_ROCK_GRANITE.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_GRANITE),
		SANDSTONE("sandstone", BDBBlocks.SMALL_ROCK_SANDSTONE.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_SANDSTONE),
		RED_SANDSTONE("red_sandstone", BDBBlocks.SMALL_ROCK_RED_SANDSTONE.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_RED_SANDSTONE),
		DEEPSLATE("deepslate", BDBBlocks.SMALL_ROCK_DEEPSLATE.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_DEEPSLATE),
		COBBLED_DEEPSLATE("cobbled_deepslate", BDBBlocks.SMALL_ROCK_COBBLED_DEEPSLATE.get(), BDBTags.Blocks.GENERATE_SMALL_ROCK_COBBLED_DEEPSLATE);

		public static final Codec<RockVariant> CODEC = StringRepresentable.fromEnum(RockVariant::values);
		public static final RockVariant DEFAULT_VARIANT = STONE;
		public static final RockVariant[] ALL_VARIANTS = values();
		public static final RockVariant[] STONE_VARIANTS = new RockVariant[] {
			STONE,
			COBBLE,
			MOSSY,
			ANDESITE,
			DIORITE,
			GRANITE
		};

		private String name;
		private Block smallRock;
		private TagKey<Block> floorBlock;

		RockVariant(String name, Block smallRock, TagKey<Block> floorBlock) {
			this.name = name;
			this.smallRock = smallRock;
			this.floorBlock = floorBlock;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}

		public Block getSmallRockBlock() {
			return this.smallRock;
		}

		public TagKey<Block> getPossibleFloorBlocks() {
			return this.floorBlock;
		}
	}
}