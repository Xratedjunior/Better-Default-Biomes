package xratedjunior.betterdefaultbiomes.enchantment.eventhandler;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class FloatingHandler {

	public static void livingEventHandler(LivingEvent event) {
		if (event.getEntity() instanceof Horse) {
			Horse horse = (Horse) event.getEntity();
			if (horse.getControllingPassenger() instanceof Player) {
				ItemStack horseArmor = horse.getItemBySlot(EquipmentSlot.CHEST);
				int floatEnchantmentLevel = horseArmor.getEnchantmentLevel(BDBEnchantments.FLOATING.get());
				if (floatEnchantmentLevel == 0) {
					return;
				}
				if (horse.isInWater()) {
					double fluidHeight = horse.getFluidTypeHeight(ForgeMod.WATER_TYPE.get());
					double swimPower = fluidHeight > 0.5f ? 0.025f : 0.01f;
					horse.setDeltaMovement(horse.getDeltaMovement().x(), horse.getDeltaMovement().y() + swimPower, horse.getDeltaMovement().z());
					;
				}
			}
		} else {
			return;
		}
	}
}
