package xratedjunior.betterdefaultbiomes.entity.hostile;

import java.util.Collection;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.entity.ai.goal.StealthGoal;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class JungleCreeperEntity extends Creeper {
	private static final EntityDataAccessor<Boolean> STEALTH = SynchedEntityData.defineId(JungleCreeperEntity.class, EntityDataSerializers.BOOLEAN);
	private int stealthCooldown;
	private int oldFuseCount;
	private int fuseTickCount;
	private int maxFuseTickCount = 12; // 30 Default
	private int explosionRadius = 3;

	public JungleCreeperEntity(EntityType<? extends JungleCreeperEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	/*********************************************************** NBT ********************************************************/

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(STEALTH, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putShort("Fuse", (short) this.maxFuseTickCount);
		compound.putByte("ExplosionRadius", (byte) this.explosionRadius);
		compound.putBoolean("ignited", this.isIgnited());
		compound.putBoolean("stealth", this.isStealth());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Fuse", 99)) {
			this.maxFuseTickCount = compound.getShort("Fuse");
		}

		if (compound.contains("ExplosionRadius", 99)) {
			this.explosionRadius = compound.getByte("ExplosionRadius");
		}

		if (compound.getBoolean("ignited")) {
			this.ignite();
		}

		if (compound.contains("stealth", 1)) {
			setStealth(compound.getBoolean("stealth"));
		}
	}

	/*********************************************************** Movement ********************************************************/

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new SwellGoal(this));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Ocelot.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Cat.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new StealthGoal<>(this, 28.0f));
	}

	public static AttributeSupplier.Builder createJungleCreeperAttributes() {
		return JungleCreeperEntity.createMonsterAttributes().add(Attributes.MAX_HEALTH, 6.0d).add(Attributes.MOVEMENT_SPEED, 0.18D);
	}

	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource) {
		boolean flag = super.causeFallDamage(distance, damageMultiplier, damageSource);
		this.fuseTickCount = (int) ((float) this.fuseTickCount + distance * 1.5F);
		if (this.fuseTickCount > this.maxFuseTickCount - 5) {
			this.fuseTickCount = this.maxFuseTickCount - 5;
		}

		return flag;
	}

	/*********************************************************** Spawn ********************************************************/

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		this.setStealth(true);
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	@SuppressWarnings("deprecation")
	public static boolean checkJungleCreeperSpawnRules(EntityType<? extends JungleCreeperEntity> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
		return Monster.checkAnyLightMonsterSpawnRules(type, worldIn, reason, pos, randomIn)
				// Prevent spawning underground in deep caves and open ravines.
				&& pos.getY() > worldIn.getSeaLevel()
				// Check sky level below 12 (Check for some darkness)
				&& worldIn.getBrightness(LightLayer.SKY, pos) < 12
				// Check for some remaining sky light. (Prevents spawning in caves and stuff)
				&& worldIn.getBrightness(LightLayer.SKY, pos) > 4
				// Check block light level of 0 (like all hostile mobs)
				&& worldIn.getBrightness(LightLayer.BLOCK, pos) <= 0;
	}

	/*********************************************************** Stealth ********************************************************/

	public boolean isStealth() {
		return entityData.get(STEALTH);
	}

	public boolean canStealth() {
		return this.stealthCooldown <= 0 && !this.isStealth();
	}

	public void setStealth(boolean flag) {
		entityData.set(STEALTH, flag);
		if (!flag) {
			this.stealthCooldown = 30;
		}
	}

	@Override
	public float getSpeed() {
		return super.getSpeed() * (this.isStealth() ? 2.0f : 1.0f);
	}

	@Override
	protected void actuallyHurt(DamageSource damageSrc, float damageAmount) {
		super.actuallyHurt(damageSrc, damageAmount);
		this.setStealth(false);
	}

	/**
	 * Renders Jungle Creeper semi-transparent.
	 */
	@OnlyIn(Dist.CLIENT)
	public boolean renderStealth(Player player) {
		return this.isStealth() && this.getSwellDir() <= 0 && !player.isCreative() || player.isCreative() && this.distanceToSqr(player) >= 28D;
	}

	/*********************************************************** Tick ********************************************************/

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide() && this.isAlive()) {
			if (this.stealthCooldown > 0) {
				this.stealthCooldown--;
			}
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		if (this.isAlive()) {
			this.oldFuseCount = this.fuseTickCount;
			if (this.isIgnited()) {
				this.setSwellDir(1);
			}

			int i = this.getSwellDir();
			if (i > 0 && this.fuseTickCount == 0) {
				this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
				this.gameEvent(GameEvent.PRIME_FUSE);
			}

			this.fuseTickCount += i;
			if (this.fuseTickCount < 0) {
				this.fuseTickCount = 0;
			}

			if (this.fuseTickCount >= this.maxFuseTickCount) {
				this.fuseTickCount = this.maxFuseTickCount;
				this.explodeCreeper();
			}
		}
		super.tick();
	}

	/*********************************************************** Explode ********************************************************/

	/**
	 * @param  partialTicks Render tick.
	 * @return              The intensity of the creeper's flash when it is ignited.
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public float getSwelling(float partialTicks) {
		return Mth.lerp(partialTicks, (float) this.oldFuseCount, (float) this.fuseTickCount) / (float) (this.maxFuseTickCount - 2.5F);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
			this.level.playSound(player, this.getX(), this.getY(), this.getZ(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
			if (!this.level.isClientSide()) {
				this.ignite();
				itemstack.hurtAndBreak(1, player, (p_213625_1_) -> {
					p_213625_1_.broadcastBreakEvent(hand);
				});
			}

			return InteractionResult.sidedSuccess(this.level.isClientSide());
		} else {
			return super.mobInteract(player, hand);
		}
	}

	/**
	 * Creates an explosion as determined by this creeper's power and explosion
	 * radius.
	 */
	private void explodeCreeper() {
		if (!this.level.isClientSide()) {
			Explosion.BlockInteraction explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
			float f = this.isPowered() ? 2.0F : 1.0F;
			this.dead = true;
			this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float) this.explosionRadius * f, explosion$mode);
			this.discard();
			this.spawnLingeringCloud();
		}
	}

	private void spawnLingeringCloud() {
		Collection<MobEffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());

			for (MobEffectInstance effectinstance : collection) {
				areaeffectcloudentity.addEffect(new MobEffectInstance(effectinstance));
			}

			this.level.addFreshEntity(areaeffectcloudentity);
		}
	}
}