package xratedjunior.betterdefaultbiomes.enchantment.eventhandler;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class HorseProtectionHandler {

	public static void attackHandler(LivingHurtEvent event) {
		LivingEntity target = (LivingEntity) event.getEntity();
		if (!(target instanceof Horse)) {
			return;
		}

		Horse horse = (Horse) target;
		ItemStack horseArmor = horse.getItemBySlot(EquipmentSlot.CHEST);
		int all = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.HORSE_PROTECTION, horseArmor);
		int fire = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.HORSE_FIRE_PROTECTION, horseArmor);
		int fall = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.HORSE_FEATHER_FALLING, horseArmor);
		int explosion = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.HORSE_BLAST_PROTECTION, horseArmor);
		int projectile = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.HORSE_PROJECTILE_PROTECTION, horseArmor);

		DamageSource source = event.getSource();
		int enchantmentModifier;
		if (source.isBypassInvul()) {
			enchantmentModifier = 0;
		} else if (all > 0) {
			enchantmentModifier = all;
		} else if (fire > 0 && source.isFire()) {
			enchantmentModifier = fire * 2;
		} else if (fall > 0 && source == DamageSource.FALL) {
			enchantmentModifier = fall * 3;
		} else if (explosion > 0 && source.isExplosion()) {
			enchantmentModifier = explosion * 2;
		} else if (projectile > 0 && source.isProjectile()) {
			enchantmentModifier = projectile * 2;
		} else {
			return;
		}

		float damageReduction = Mth.clamp(enchantmentModifier, 0.0F, 20.0F);
		float oldDamage = event.getAmount();
		float newDamage = oldDamage * (1.0F - damageReduction / 25.0F);
		if (explosion > 0 && source.isExplosion()) {
			newDamage -= (double) Mth.floor(oldDamage * (double) ((float) explosion * 0.15F));
		}
		event.setAmount(newDamage);

		if (fire > 0 && source.isFire() && source != DamageSource.IN_FIRE) {
			int fireTimerOld = horse.getRemainingFireTicks();
			int fireTimerNew = fireTimerOld;
			fireTimerNew -= Mth.floor((float) fireTimerOld * (float) fire * 0.15F);
			horse.setRemainingFireTicks(fireTimerNew);
		}
	}
}
