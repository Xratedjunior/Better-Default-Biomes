package xratedjunior.betterdefaultbiomes.enchantment.eventhandler;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class FloatingHandler {

	public static void livingEventHandler(LivingEvent event) {
		if (event.getEntityLiving() instanceof Horse) {
			Horse horse = (Horse) event.getEntityLiving();
			if (horse.getControllingPassenger() instanceof Player) {
				ItemStack horseArmor = horse.getItemBySlot(EquipmentSlot.CHEST);
				int floatEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.FLOATING, horseArmor);
				if (floatEnchantmentLevel == 0) {
					return;
				}
				if (horse.isInWater()) {
					horse.setYya(0.75f);
				}
			}
			if (!horse.isInWater()) {
				horse.setYya(0);
			}
		} else {
			return;
		}
	}
}
