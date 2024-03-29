package xratedjunior.betterdefaultbiomes.entity.hostile;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import xratedjunior.betterdefaultbiomes.configuration.entity.LostMinerConfig;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class LostMinerEntity extends Skeleton {

	public LostMinerEntity(EntityType<? extends LostMinerEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	/*********************************************************** Equipment ********************************************************/

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
		this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.TORCH));
		this.getItemBySlot(EquipmentSlot.MAINHAND).setHoverName(Component.translatable("equipment.betterdefaultbiomes.miner_pickaxe").withStyle(ChatFormatting.WHITE));
		this.getItemBySlot(EquipmentSlot.HEAD).setHoverName(Component.translatable("equipment.betterdefaultbiomes.miner_helmet").withStyle(ChatFormatting.WHITE));
		this.setDropChance(EquipmentSlot.MAINHAND, ((float) LostMinerConfig.lost_miner_drop_chance.get() / 100.0f));
		this.setDropChance(EquipmentSlot.HEAD, ((float) LostMinerConfig.lost_miner_drop_chance.get() / 100.0f));
		// this.setDropChance(EquipmentSlot.MAINHAND, 1);
		// this.setDropChance(EquipmentSlot.HEAD, 1);
		this.setDefaultEnchantments();
	}

	/**
	 * Enchants Entity's standard equipments
	 */
	protected void setDefaultEnchantments() {
		if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEnchanted() == false) {
			this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.UNBREAKING, 1);
			this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.BLOCK_EFFICIENCY, 1);
			this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.FIRE_ASPECT, 1);
			this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.BLOCK_FORTUNE, 1);
		}
	}

	/*********************************************************** Spawn Rules ********************************************************/

	/**
	 * TODO Remove and make spawn in the structures instead of like this.
	 * Use {@link StructureSpawnListGatherEvent} but not used in 1.18.2 and removed 1.19.4
	 */
	@SuppressWarnings("deprecation")
	public static boolean checkLostMinerSpawnRules(EntityType<? extends LostMinerEntity> type, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
		return worldIn.getDifficulty() != Difficulty.PEACEFUL && (worldIn.getBlockState(pos.north()).getBlock() == Blocks.RAIL || worldIn.getBlockState(pos.south()).getBlock() == Blocks.RAIL || worldIn.getBlockState(pos.west()).getBlock() == Blocks.RAIL || worldIn.getBlockState(pos.east()).getBlock() == Blocks.RAIL) && checkMobSpawnRules(type, worldIn, reason, pos, randomIn) && pos.getY() < worldIn.getSeaLevel() && isDarkEnoughToSpawn(worldIn, pos, randomIn);
	}
}