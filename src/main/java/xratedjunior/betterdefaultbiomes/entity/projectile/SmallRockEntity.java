package xratedjunior.betterdefaultbiomes.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.projectile.dispenser.CustomDispenserBehavior;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class SmallRockEntity extends ThrowableItemProjectile {
	private final float damage = 2.0F; // 1 Heart
	private final byte removeStateID = 3;

	public SmallRockEntity(EntityType<? extends SmallRockEntity> entityType, Level world) {
		super(entityType, world);
	}

	public SmallRockEntity(Level worldIn, LivingEntity throwerIn) {
		super(BDBEntityTypes.SMALL_ROCK.get(), throwerIn, worldIn);
	}

	/**
	 * Used for Dispensers in: {@link CustomDispenserBehavior}
	 */
	public SmallRockEntity(Level worldIn, double x, double y, double z) {
		super(BDBEntityTypes.SMALL_ROCK.get(), x, y, z, worldIn);
	}

	/*********************************************************** Data ********************************************************/

	@Override
	protected Item getDefaultItem() {
		return BDBBlocks.SMALL_ROCK_STONE.get().asItem();
	}

	@Override
	public SoundSource getSoundSource() {
		return SoundSource.BLOCKS;
	}

	/*********************************************************** Duck Egg ********************************************************/

	/**
	 * Called when this Entity hits another Entity
	 */
	@Override
	protected void onHitEntity(EntityHitResult entityRayTraceResult) {
		super.onHitEntity(entityRayTraceResult);
		Entity targetEntity = entityRayTraceResult.getEntity();
		targetEntity.hurt(this.damageSources().thrown(this, this.getOwner()), this.damage);
		this.dropOrDestroyRock(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ());
	}

	/**
	 * Called when this Entity hits a Block
	 */
	@Override
	protected void onHitBlock(BlockHitResult blockRayTraceResult) {
		super.onHitBlock(blockRayTraceResult);
		Vec3 vector3d = blockRayTraceResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
		Vec3 vector3d1 = vector3d.normalize().scale((double) 0.05F);
		this.dropOrDestroyRock(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
	}

	/**
	 * Called when this Entity hits a Block or Entity.
	 */
	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		// Remove entity
		if (!this.level().isClientSide()) {
			this.level().broadcastEntityEvent(this, this.removeStateID);
			this.discard();
		}

		// Play breaking sound
		this.playSound(BDBSoundEvents.ENTITY_SMALL_ROCK_BREAK.get(), 0.2F, 2.4F / (this.random.nextFloat() * 0.4F + 0.8F));
	}

	private void dropOrDestroyRock(double x, double y, double z) {
		// 20% chance to destroy the rock
		if (this.random.nextInt(10) <= 1) {
			return;
		}

		// Drop on the ground
		else {
			ItemEntity item = new ItemEntity(this.level(), x, y, z, this.getItem());
			this.level().addFreshEntity(item);
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
		if (id == this.removeStateID) {
			for (int i = 0; i < 8; ++i) {
				this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
			}
		}
	}
}
