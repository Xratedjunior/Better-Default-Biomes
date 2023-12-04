package xratedjunior.betterdefaultbiomes.enchantment.eventhandler;

import java.util.List;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class ScoutHandler {

	public static void playerTickHandler(Player player) {
		ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
		int enchantmentLevel = helmet.getEnchantmentLevel(BDBEnchantments.SCOUT.get());
		if (enchantmentLevel == 0) {
			return;
		}

		Vec3 vec3d = new Vec3((double) player.getX() + 0.5D, (double) player.getY(), (double) player.getZ() + 0.5D);
		double horizontalDistance = 2 * enchantmentLevel;
		double verticalDistance = 2 * enchantmentLevel;
		List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, new AABB(vec3d.x() - horizontalDistance, vec3d.y() - verticalDistance, vec3d.z() - horizontalDistance, vec3d.x() + horizontalDistance, vec3d.y() + verticalDistance, vec3d.z() + horizontalDistance));
		if (!mobList.isEmpty()) {
			MobEffectInstance glowing = new MobEffectInstance(MobEffects.GLOWING, 2, 0);
			mobList.forEach(mob -> mob.addEffect(glowing));
		} else
			return;
	}
}
