package xratedjunior.betterdefaultbiomes.enchantment.eventhandler;

import java.util.Random;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ThornsEnchantment;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;

/**
 * @author 	Xrated_junior
 * @version	1.18.2-1.0.0
 */
public class ShieldHandler {

	public static void shieldBlockHandler(LivingAttackEvent event) {
		DamageSource source = event.getSource();
		Entity trueSource = event.getSource().getEntity();
		if (!(trueSource instanceof LivingEntity))
			return;

		LivingEntity attacker = (LivingEntity) trueSource;
		LivingEntity target = event.getEntityLiving();

		ItemStack stackMainHand = target.getMainHandItem();
		ItemStack stackOffHand = target.getOffhandItem();

		int guardLevelMain = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.GUARD, stackMainHand);
		int guardLevelOff = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.GUARD, stackOffHand);
		int guardLevel = Math.max(guardLevelMain, guardLevelOff);

		int spikesLevelMain = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.SPIKES, stackMainHand);
		int spikesLevelOff = EnchantmentHelper.getItemEnchantmentLevel(BDBEnchantments.SPIKES, stackOffHand);
		int spikesLevel = Math.max(spikesLevelMain, spikesLevelOff);

		if (guardLevel == 0 && spikesLevel == 0)
			return;
		float damageAmount = event.getAmount();
		if (damageAmount > 0.0F && canBlockDamageSource(source, target)) {
			Random random = target.getRandom();
			if (spikesLevel > 0)
				attacker.hurt(DamageSource.thorns(target), (float) ThornsEnchantment.getDamage(spikesLevel, random));
			if (guardLevel > 0) {
				double xRatio = (double) Mth.sin(target.getYRot() * ((float) Math.PI / 180F));
				double zRatio = (double) (-Mth.cos(target.getYRot() * ((float) Math.PI / 180F)));
				attacker.knockback(guardLevel * 0.2F, xRatio, zRatio); //Knockback
			}
		}
	}

	/**
	 * Determines whether the entity can block the damage source based on the damage source's location, whether the
	 * damage source is blockable, and whether the entity is blocking.
	 */
	private static boolean canBlockDamageSource(DamageSource damageSourceIn, LivingEntity livingEntity) {
		Entity entity = damageSourceIn.getDirectEntity();
		boolean flag = false;
		if (entity instanceof AbstractArrow) {
			AbstractArrow abstractarrowentity = (AbstractArrow) entity;
			if (abstractarrowentity.getPierceLevel() > 0) {
				flag = true;
			}
		}

		if (!damageSourceIn.isBypassArmor() && livingEntity.isBlocking() && !flag) {
			Vec3 Vector3d2 = damageSourceIn.getSourcePosition();
			if (Vector3d2 != null) {
				Vec3 Vec3 = livingEntity.getViewVector(1.0F);
				Vec3 Vector3d1 = Vector3d2.vectorTo(livingEntity.position()).normalize();
				Vector3d1 = new Vec3(Vector3d1.x, 0.0D, Vector3d1.z);
				if (Vector3d1.dot(Vec3) < 0.0D) {
					return true;
				}
			}
		}

		return false;
	}
}
