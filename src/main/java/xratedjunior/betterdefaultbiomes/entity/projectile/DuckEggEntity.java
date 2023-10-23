package xratedjunior.betterdefaultbiomes.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.passive.DuckEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.dispenser.CustomDispenserBehavior;
import xratedjunior.betterdefaultbiomes.item.BDBItems;

/**
 * Referenced from {@link ThrownEgg}
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class DuckEggEntity extends ThrowableItemProjectile {

	public DuckEggEntity(EntityType<? extends DuckEggEntity> entityType, Level world) {
		super(entityType, world);
	}

	public DuckEggEntity(Level worldIn, LivingEntity throwerIn) {
		super(BDBEntityTypes.DUCK_EGG.get(), throwerIn, worldIn);
	}

	/**
	 * Used for Dispensers in: {@link CustomDispenserBehavior}
	 */
	public DuckEggEntity(Level worldIn, double x, double y, double z) {
		super(BDBEntityTypes.DUCK_EGG.get(), x, y, z, worldIn);
	}

	/*********************************************************** Data ********************************************************/

	@Override
	protected Item getDefaultItem() {
		return BDBItems.DUCK_EGG.get();
	}

	/*********************************************************** Duck Egg ********************************************************/

	/**
	 * Called when this Entity hits another Entity
	 */
	@Override
	protected void onHitEntity(EntityHitResult entityRayTraceResult) {
		super.onHitEntity(entityRayTraceResult);
		entityRayTraceResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
	}

	/**
	 * Called when this Entity hits a Block or Entity.
	 */
	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level.isClientSide()) {
			// 12.5% chance to spawn chicken
			if (this.random.nextInt(8) == 0) {
				int i = 1;
				// 3.125% chance to spawn 4 chickens from 1 egg (Actual chance about: 0.39%)
				if (this.random.nextInt(32) == 0) {
					i = 4;
				}

				// Spawn a Ducks
				for (int j = 0; j < i; ++j) {
					DuckEntity duckEntity = BDBEntityTypes.DUCK.get().create(this.level);
					duckEntity.setAge(-24000);
					duckEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
					this.level.addFreshEntity(duckEntity);
				}
			}

			this.level.broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}

	/*********************************************************** Client ********************************************************/

	/**
	 * Handler for {@link Level#setEntityState}
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		//	Break particles when hitting the ground
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
			}
		}
	}

	/*********************************************************** Networking ********************************************************/

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
