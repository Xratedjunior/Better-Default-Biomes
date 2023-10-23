package xratedjunior.betterdefaultbiomes.block.block;

import java.util.List;
import java.util.Random;

import org.apache.commons.compress.utils.Lists;

import net.minecraft.Util;
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
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.SmallRocksConfig;
import xratedjunior.betterdefaultbiomes.data.BDBTags;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class SmallRockBlock extends SimpleBlock {
	public static final int MAX_ROCK_SIZE = 2;
	public static final IntegerProperty ROCK_SIZE = IntegerProperty.create("size", 1, MAX_ROCK_SIZE);
	private static final VoxelShape ROCK_SMALL = makeSquareShape(4.8D, 2.2D);
	private static final VoxelShape ROCK_MEDIUM = makeSquareShape(3.0D, 3.2D);

	public SmallRockBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(ROCK_SIZE, 1));
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

		public static RockVariant DEFAULT_VARIANT = STONE;
		public static RockVariant[] ALL_VARIANTS = values();
		public static RockVariant[] STONE_VARIANTS = new RockVariant[] {
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

		/*********************************************************** Config Work ********************************************************/

		private boolean getConfigValue(RockVariant variantIn) {
			switch (variantIn) {
			case STONE:
			default:
				return SmallRocksConfig.generate_stone.get();
			case COBBLE:
				return SmallRocksConfig.generate_cobble.get();
			case MOSSY:
				return SmallRocksConfig.generate_mossy.get();
			case ANDESITE:
				return SmallRocksConfig.generate_andesite.get();
			case DIORITE:
				return SmallRocksConfig.generate_diorite.get();
			case GRANITE:
				return SmallRocksConfig.generate_granite.get();
			case SANDSTONE:
				return SmallRocksConfig.generate_sandstone.get();
			case RED_SANDSTONE:
				return SmallRocksConfig.generate_red_sandstone.get();
			case DEEPSLATE:
				return SmallRocksConfig.generate_deepslate.get();
			case COBBLED_DEEPSLATE:
				return SmallRocksConfig.generate_cobbled_deepslate.get();
			}
		}

		/**
		 * Set the default Small Rock variant after loading or changing the config
		 */
		public static void setDefaultVariant() {
			String configInput = SmallRocksConfig.default_rock.get().toLowerCase();

			for (RockVariant variant : values()) {
				if (variant.getSerializedName().equals(configInput)) {
					BetterDefaultBiomes.LOGGER.debug("Set default Small Rock to \"" + variant.getSerializedName() + "\".");
					DEFAULT_VARIANT = variant;
					return;
				}
			}

			// No valid config value
			BetterDefaultBiomes.LOGGER.error("Set default Small Rock to \"" + STONE.getSerializedName() + "\", because the config is invalid.");
			DEFAULT_VARIANT = STONE;
		}

		/**
		 * Checks if some variants have been turned off in the config
		 */
		public static void setPossibleStoneVariants() {
			List<RockVariant> variantList = Lists.newArrayList();
			for (RockVariant variant : STONE_VARIANTS) {
				if (variant.getConfigValue(variant)) {
					variantList.add(variant);
				}
			}

			STONE_VARIANTS = variantList.toArray(new RockVariant[variantList.size()]);
		}

		/**
		 * Checks if variants have been turned off in the config
		 */
		public static void setAllPossibleVariants() {
			List<RockVariant> variantList = Lists.newArrayList();
			for (RockVariant variant : ALL_VARIANTS) {
				if (variant.getConfigValue(variant)) {
					variantList.add(variant);
				}
			}

			ALL_VARIANTS = variantList.toArray(new RockVariant[variantList.size()]);
		}

		/*********************************************************** Placement Helpers ********************************************************/

		/**
		 * Get a random stone variant
		 */
		public static Block getRandomStoneVariant(Random rand) {
			return Util.getRandom(STONE_VARIANTS, rand).getSmallRockBlock();
		}

		/**
		 * Get a random variant from the list that matches the Block.
		 * If list is empty, return the default variant
		 */
		public static Block getMatchingRockVariant(Block groundBlock) {
			List<Block> matchingVariants = getMatchingRockVariants(groundBlock);
			// If the list is empty, return the default variant.
			return !matchingVariants.isEmpty() ? Util.getRandom(matchingVariants, new Random()) : DEFAULT_VARIANT.getSmallRockBlock();
		}

		/**
		 * Get a list of variants that match the Block
		 */
		public static List<Block> getMatchingRockVariants(Block groundBlock) {
			// Create list
			List<Block> matchingVariants = Lists.newArrayList();

			for (RockVariant variant : ALL_VARIANTS) {
				// Check if the groundBlock matches with Block Tags.
				if (groundBlock.defaultBlockState().is(variant.getPossibleFloorBlocks())) {
					// Add the variant to the final list.
					matchingVariants.add(variant.getSmallRockBlock());
				}
			}

			// Return list
			return matchingVariants;
		}
	}
}