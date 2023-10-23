package xratedjunior.betterdefaultbiomes.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.projectile.dispenser.CustomDispenserBehavior;
import xratedjunior.betterdefaultbiomes.item.BDBItems;
import xratedjunior.betterdefaultbiomes.item.item.HunterArrowItem;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class HunterArrowEntity extends AbstractArrow {

	public HunterArrowEntity(EntityType<? extends HunterArrowEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public HunterArrowEntity(Level worldIn, LivingEntity shooter) {
		super(BDBEntityTypes.HUNTER_ARROW.get(), shooter, worldIn);
	}

	/**
	 * Used for Dispensers in: {@link CustomDispenserBehavior}
	 */
	public HunterArrowEntity(Level worldIn, double x, double y, double z) {
		super(BDBEntityTypes.HUNTER_ARROW.get(), x, y, z, worldIn);
	}

	/*********************************************************** Data ********************************************************/

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(BDBItems.HUNTER_ARROW.get());
	}

	/*********************************************************** Shoot Arrow ********************************************************/

	/**
	 * Apply effects to target Entity
	 */
	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		// 10 seconds (1 second is 20 ticks)
		int effectDurationTicks = 200;
		MobEffectInstance poisonEffect = new MobEffectInstance(MobEffects.POISON, effectDurationTicks, 0);
		MobEffectInstance glowingEffect = new MobEffectInstance(MobEffects.GLOWING, effectDurationTicks, 0);
		living.addEffect(poisonEffect);
		living.addEffect(glowingEffect);
	}

	public static HunterArrowEntity shootHunterArrow(LivingEntity livingEntity, ItemStack itemStack, float damage) {
		HunterArrowItem arrowitem = itemStack.getItem() instanceof HunterArrowItem ? (HunterArrowItem) itemStack.getItem() : ((HunterArrowItem) BDBItems.HUNTER_ARROW.get());
		HunterArrowEntity abstractarrowentity = arrowitem.createArrow(livingEntity.level, itemStack, livingEntity);
		abstractarrowentity.setEnchantmentEffectsFromEntity(livingEntity, damage);
		return abstractarrowentity;
	}

	/*********************************************************** Networking ********************************************************/

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}