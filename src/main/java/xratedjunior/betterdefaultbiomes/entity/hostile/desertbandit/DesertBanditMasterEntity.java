package xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class DesertBanditMasterEntity extends DesertBanditEntity {

	public DesertBanditMasterEntity(EntityType<? extends DesertBanditMasterEntity> banditMaster, Level world) {
		super(banditMaster, world);
	}

	/*********************************************************** Attributes ********************************************************/

	public static AttributeSupplier.Builder createDesertMasterAttributes() {
		return DesertBanditMasterEntity.createDesertBanditAttributes().add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	/*********************************************************** Spawn Equipment ********************************************************/

	@Override
	public void setDefaultEquipmentAndEnchants(DifficultyInstance difficulty) {
		desertBanditMasterDefaultEquipment(this);
	}

	public static void desertBanditMasterDefaultEquipment(DesertBanditMasterEntity entity) {
		ItemStack desertMasterSword = new ItemStack(Items.GOLDEN_SWORD);
		int fireAspectChance = DesertBanditEntity.fireAspectChance;
		Map<Enchantment, Integer> map = Maps.newHashMap();
		if (entity.level.getDifficulty() == Difficulty.HARD) {
			if (entity.random.nextInt(3) == 0) {
				map.put(Enchantments.UNBREAKING, 2);
			} else {
				map.put(Enchantments.UNBREAKING, 1);
			}
			map.put(Enchantments.SHARPNESS, 3);
		} else {
			map.put(Enchantments.UNBREAKING, 1);
			map.put(Enchantments.SHARPNESS, 1);
		}
		if (entity.random.nextInt(fireAspectChance) == 0) {
			map.put(Enchantments.FIRE_ASPECT, 1);
		}
		EnchantmentHelper.setEnchantments(map, desertMasterSword);
		entity.setItemSlot(EquipmentSlot.MAINHAND, desertMasterSword);
		entity.getItemBySlot(EquipmentSlot.MAINHAND).setHoverName(Component.translatable("equipment.betterdefaultbiomes.bandit_master_sword").withStyle(ChatFormatting.BLUE));
		float armorDropChance = DesertBanditEntity.armorDropChance;
		entity.setDropChance(EquipmentSlot.MAINHAND, armorDropChance);
	}
}