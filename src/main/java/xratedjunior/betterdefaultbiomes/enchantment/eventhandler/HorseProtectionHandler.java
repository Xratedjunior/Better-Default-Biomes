package xratedjunior.betterdefaultbiomes.enchantment.eventhandler;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;

/**
 * UPDATE to: {@linkplain https://github.com/tinytransfem/ShinyHorsesForge}
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class HorseProtectionHandler {

	public static void attackHandler(LivingHurtEvent event) {
		LivingEntity target = (LivingEntity) event.getEntity();
		if (!(target instanceof Horse)) {
			return;
		}

		Horse horse = (Horse) target;
		ItemStack horseArmor = horse.getItemBySlot(EquipmentSlot.CHEST);
		int all = horseArmor.getEnchantmentLevel(BDBEnchantments.HORSE_PROTECTION.get());
		int fire = horseArmor.getEnchantmentLevel(BDBEnchantments.HORSE_FIRE_PROTECTION.get());
		int fall = horseArmor.getEnchantmentLevel(BDBEnchantments.HORSE_FEATHER_FALLING.get());
		int explosion = horseArmor.getEnchantmentLevel(BDBEnchantments.HORSE_BLAST_PROTECTION.get());
		int projectile = horseArmor.getEnchantmentLevel(BDBEnchantments.HORSE_PROJECTILE_PROTECTION.get());

		/**
		 * Copy from {@link ProtectionEnchantment#getDamageProtection}
		 */
		DamageSource source = event.getSource();
		int enchantmentModifier;
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			enchantmentModifier = 0;
		} else if (all > 0) {
			enchantmentModifier = all;
		} else if (fire > 0 && source.is(DamageTypeTags.IS_FIRE)) {
			enchantmentModifier = fire * 2;
		} else if (fall > 0 && source.is(DamageTypeTags.IS_FALL)) {
			enchantmentModifier = fall * 3;
		} else if (explosion > 0 && source.is(DamageTypeTags.IS_EXPLOSION)) {
			enchantmentModifier = explosion * 2;
		} else {
			enchantmentModifier = projectile > 0 && source.is(DamageTypeTags.IS_PROJECTILE) ? projectile * 2 : 0;
		}

		float damageReduction = Mth.clamp(enchantmentModifier, 0.0F, 20.0F);
		float oldDamage = event.getAmount();
		float newDamage = oldDamage * (1.0F - damageReduction / 25.0F);
		if (explosion > 0 && source.is(DamageTypeTags.IS_EXPLOSION)) {
			newDamage -= (double) Mth.floor(oldDamage * (double) ((float) explosion * 0.15F));
		}
		event.setAmount(newDamage);

		if (fire > 0 && source.is(DamageTypes.ON_FIRE) && !source.is(DamageTypes.IN_FIRE)) {
			int fireTimerOld = horse.getRemainingFireTicks();
			int fireTimerNew = fireTimerOld;
			fireTimerNew -= Mth.floor((float) fireTimerOld * (float) fire * 0.15F);
			horse.setRemainingFireTicks(fireTimerNew);
		}
	}
}
