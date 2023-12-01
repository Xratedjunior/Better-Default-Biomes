package xratedjunior.betterdefaultbiomes.enchantment.eventhandler;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class HuntingHandler {

	public static void attackHandler(LivingHurtEvent event) {
		String damageType = event.getSource().getMsgId();
		if (!(damageType.contains("arrow"))) {
			return;
		}

		Entity damageSource = event.getSource().getEntity();
		if (!(damageSource instanceof LivingEntity))
			return;

		LivingEntity target = (LivingEntity) event.getEntity();
		if (!(target instanceof Animal))
			return;

		LivingEntity attacker = (LivingEntity) damageSource;
		ItemStack weapon = attacker.getItemInHand(attacker.getUsedItemHand());
		int enchantmentLevel = weapon.getEnchantmentLevel(BDBEnchantments.HUNTING.get());
		if (enchantmentLevel == 0)
			return;

		float oldDamage = event.getAmount();
		float newDamage = oldDamage + (oldDamage / 10 * enchantmentLevel);
		event.setAmount(newDamage);

		// System.out.println("Old: " + oldDamage + ", New: " + newDamage);
	}
}
