package xratedjunior.betterdefaultbiomes.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.Difficulty;
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
import xratedjunior.betterdefaultbiomes.item.item.BanditArrowItem;
import xratedjunior.betterdefaultbiomes.item.item.HunterArrowItem;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BanditArrowEntity extends AbstractArrow {

	public BanditArrowEntity(EntityType<? extends BanditArrowEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public BanditArrowEntity(Level worldIn, LivingEntity shooter) {
		super(BDBEntityTypes.BANDIT_ARROW.get(), shooter, worldIn);
	}

	/**
	 * Used for Dispensers in: {@link CustomDispenserBehavior}
	 */
	public BanditArrowEntity(Level worldIn, double x, double y, double z) {
		super(BDBEntityTypes.BANDIT_ARROW.get(), x, y, z, worldIn);
	}

	/*********************************************************** Data ********************************************************/

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(BDBItems.BANDIT_ARROW.get());
	}

	/*********************************************************** Shoot Arrow ********************************************************/

	/**
	 * Apply effects to target Entity
	 */
	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		int effectDurationTicks = 200;
		MobEffectInstance weaknessEffect = new MobEffectInstance(MobEffects.WEAKNESS, effectDurationTicks, 0);
		// Increase weakness level when difficulty is set to hard
		if (living.level.getDifficulty() == Difficulty.HARD) {
			weaknessEffect = new MobEffectInstance(MobEffects.WEAKNESS, effectDurationTicks, 1);
		}

		living.addEffect(weaknessEffect);
	}

	public static BanditArrowEntity shootBanditArrow(LivingEntity livingEntity, ItemStack itemStack, float damage) {
		BanditArrowItem arrowitem = itemStack.getItem() instanceof HunterArrowItem ? (BanditArrowItem) itemStack.getItem() : ((BanditArrowItem) BDBItems.BANDIT_ARROW.get());
		BanditArrowEntity abstractarrowentity = arrowitem.createArrow(livingEntity.level, itemStack, livingEntity);
		abstractarrowentity.setEnchantmentEffectsFromEntity(livingEntity, damage);
		return abstractarrowentity;
	}

	/*********************************************************** Networking ********************************************************/

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}