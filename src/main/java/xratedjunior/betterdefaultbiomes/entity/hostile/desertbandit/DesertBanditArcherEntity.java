package xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class DesertBanditArcherEntity extends DesertBanditEntity {

	public DesertBanditArcherEntity(EntityType<? extends DesertBanditArcherEntity> desertArcher, Level worldIn) {
		super(desertArcher, worldIn);
	}
	
	/*********************************************************** Spawn Equipment ********************************************************/

	@Override
	protected void setDefaultEquipmentAndEnchants(DifficultyInstance difficulty) {
		desertBanditArcherDefaultEquipment(this);
	}

	public static void desertBanditArcherDefaultEquipment(DesertBanditArcherEntity entity) {
		ItemStack desertArcherBow = new ItemStack(Items.BOW);
		int flameChance = DesertBanditEntity.flameChance;
		int infinityChance = DesertBanditEntity.infinityChance;
		Map<Enchantment, Integer> map = Maps.newHashMap();
		if (entity.level.getDifficulty() == Difficulty.HARD) {
			map.put(Enchantments.POWER_ARROWS, 2);
			map.put(Enchantments.PUNCH_ARROWS, 1);
			if (entity.random.nextInt(flameChance) == 0) {
				map.put(Enchantments.FLAMING_ARROWS, 1);
			}
		} else {
			map.put(Enchantments.POWER_ARROWS, 1);
			map.put(Enchantments.PUNCH_ARROWS, 1);
		}
		if (entity.random.nextInt(infinityChance) == 0) {
			map.put(Enchantments.INFINITY_ARROWS, 1);
		}
		EnchantmentHelper.setEnchantments(map, desertArcherBow);
		entity.setItemSlot(EquipmentSlot.MAINHAND, (desertArcherBow));
		entity.getItemBySlot(EquipmentSlot.MAINHAND).setHoverName(new TranslatableComponent("equipment.betterdefaultbiomes.bandit_bow").withStyle(ChatFormatting.YELLOW));
		float armorDropChance = DesertBanditEntity.armorDropChance;
		entity.setDropChance(EquipmentSlot.MAINHAND, armorDropChance);
	}
}