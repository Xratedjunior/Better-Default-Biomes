package xratedjunior.betterdefaultbiomes.entity.hostile;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit.DesertBanditEntity;

/**
 * MINECRAFT REFERENCE: {@link Skeleton} & {@link Pillager}
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public abstract class AbstractHostileHumanoid extends Monster implements RangedAttackMob, CrossbowAttackMob {
	private static final EntityDataAccessor<Boolean> DATA_CROSSBOW_CHARGING_STATE = SynchedEntityData.defineId(AbstractHostileHumanoid.class, EntityDataSerializers.BOOLEAN);
	private static final float CROSSBOW_POWER = 1.6F; // 1.6F is default
	protected final RangedBowAttackGoal<AbstractHostileHumanoid> bowGoal = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);
	protected final RangedCrossbowAttackGoal<AbstractHostileHumanoid> crossbowGoal = new RangedCrossbowAttackGoal<>(this, 1.0D, 8.0F);
	protected final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false);

	protected AbstractHostileHumanoid(EntityType<? extends AbstractHostileHumanoid> type, Level worldIn) {
		super(type, worldIn);
		this.reassessCombatGoal();
	}

	/*********************************************************** Mob data ********************************************************/

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_CROSSBOW_CHARGING_STATE, false);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.reassessCombatGoal();
	}

	/*********************************************************** Goals ********************************************************/

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
		// CombatGoals/WeaponGoals go here.
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers(DesertBanditEntity.class)));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	/**
	 * sets this entity's combat AI.
	 */
	public void reassessWeaponGoal(int hardDifficultyAttackInterval, int attackInterval) {
		if (this.level != null && !this.level.isClientSide()) {
			this.goalSelector.removeGoal(this.meleeGoal);
			this.goalSelector.removeGoal(this.bowGoal);
			this.goalSelector.removeGoal(this.crossbowGoal);
			ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem));
			if (itemstack.is(Items.BOW)) {
				int ticksInterval = hardDifficultyAttackInterval;
				if (this.level.getDifficulty() != Difficulty.HARD) {
					ticksInterval = attackInterval;
				}
				this.bowGoal.setMinAttackInterval(ticksInterval);
				this.goalSelector.addGoal(4, this.bowGoal);
			} else if (this.isHolding(itemStack -> itemStack.getItem() instanceof CrossbowItem)) {
				this.goalSelector.addGoal(4, this.crossbowGoal);
			} else {
				this.goalSelector.addGoal(4, this.meleeGoal);
			}

		}
	}

	/**
	 * Same intervals are used by {@link AbstractSkeleton#reassessWeaponGoal()}
	 */
	public void reassessCombatGoal() {
		this.reassessWeaponGoal(20, 40);
	}

	/*********************************************************** Attack AI ********************************************************/

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		// Check for Crossbow
		if (this.isHolding(itemStack -> itemStack.getItem() instanceof CrossbowItem)) {
			this.performCrossbowAttack(this, CROSSBOW_POWER);
		} else {
			ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem)));
			AbstractArrow abstractarrowentity = this.getArrow(itemstack, distanceFactor);
			if (this.getMainHandItem().getItem() instanceof BowItem) {
				abstractarrowentity = ((BowItem) this.getMainHandItem().getItem()).customArrow(abstractarrowentity);
			}
			double d0 = target.getX() - this.getX();
			double d1 = target.getBoundingBox().minY + (double) (target.getBbHeight() / 3.0F) - abstractarrowentity.getY();
			double d2 = target.getZ() - this.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2);
			abstractarrowentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.level.getDifficulty().getId() * 4));
			this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
			this.level.addFreshEntity(abstractarrowentity);
		}
	}

	/**
	 * {@link ProjectileWeaponItem} is the parent class of {@link BowItem} and {@link CrossbowItem}
	 */
	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem itemInHand) {
		return itemInHand instanceof CrossbowItem || itemInHand instanceof BowItem;
	}

	/**
	 * Specify Arrow to shoot.
	 */
	protected abstract AbstractArrow getArrow(ItemStack arrowStack, float distanceFactor);

	/**
	 * Returns whether this Entity is on the same team as the given Entity.
	 */
	@Override
	public boolean isAlliedTo(Entity entityIn) {
		if (super.isAlliedTo(entityIn)) {
			return true;
		} else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getMobType() == this.getMobType()) {
			return this.getTeam() == null && entityIn.getTeam() == null;
		} else {
			return false;
		}
	}

	/*********************************************************** CrossbowAttackMob Implementation ********************************************************/

	@Override
	public void shootCrossbowProjectile(LivingEntity livingEntity, ItemStack itemStack, Projectile projectileEntity, float p_230284_4_) {
		this.shootCrossbowProjectile(this, livingEntity, projectileEntity, p_230284_4_, CROSSBOW_POWER);
	}

	@Override
	public void onCrossbowAttackPerformed() {
		this.noActionTime = 0;
	}

	@Override
	public void setChargingCrossbow(boolean isCharging) {
		this.entityData.set(DATA_CROSSBOW_CHARGING_STATE, isCharging);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isChargingCrossbow() {
		return this.entityData.get(DATA_CROSSBOW_CHARGING_STATE);
	}

	/*********************************************************** Ticks ********************************************************/

	/**
	 * Handles updating while riding another entity
	 */
	@Override
	public void rideTick() {
		super.rideTick();
		if (this.getVehicle() instanceof PathfinderMob) {
			PathfinderMob creatureentity = (PathfinderMob) this.getVehicle();
			this.yBodyRot = creatureentity.yBodyRot;
		}
	}

	/*********************************************************** Attributes ********************************************************/

	public static AttributeSupplier.Builder createHumanoidAttributes() {
		return AbstractHostileHumanoid.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public abstract MobType getMobType();

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.74F;
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	@Override
	public double getMyRidingOffset() {
		return -0.48D;
	}

	/*********************************************************** Spawn Rules ********************************************************/

	public static boolean checkHostileSpawnRules(EntityType<? extends AbstractHostileHumanoid> type, LevelAccessor world, MobSpawnType reason, BlockPos pos, Random random) {
		return Monster.checkAnyLightMonsterSpawnRules(type, world, reason, pos, random);
	}

	/*********************************************************** Spawning ********************************************************/

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.populateDefaultEquipmentSlots(difficultyIn);
		// Enchants Entity's current equipments based on given DifficultyInstance.
		this.populateDefaultEquipmentEnchantments(difficultyIn);
		this.reassessCombatGoal();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * difficultyIn.getSpecialMultiplier());

		// Can be used for testing:
		// this.setDropChance(EquipmentSlot.MAINHAND, 1);
		return spawnDataIn;
	}

	/*********************************************************** Spawn-Equipment ********************************************************/

	/**
	 * Set Armor/Weapons/Enchants for the Entity before random equipment is assigned.
	 */
	protected void setDefaultEquipmentAndEnchants(DifficultyInstance difficulty) {
		// Empty by default
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 * 
	 * @deprecated Use {@link giveDefaultEquipment}
	 */
	@Deprecated
	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		this.setDefaultEquipmentAndEnchants(difficulty);
		// Fill equipment in empty slots.
		super.populateDefaultEquipmentSlots(difficulty);
	}

	@Override
	public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {
		super.setItemSlot(slotIn, stack);
		if (!this.level.isClientSide()) {
			this.reassessCombatGoal();
		}
	}

	/*********************************************************** Animations ********************************************************/

	@OnlyIn(Dist.CLIENT)
	public AbstractHostileHumanoid.ArmPose getArmPose() {
		if (this.getMainHandItem().getItem() instanceof BowItem && this.isAggressive()) {
			// Set Bow and Arrow pose.
			return ArmPose.BOW_AND_ARROW;
		} else if (this.isChargingCrossbow()) {
			// Is charging the Crossbow.
			return ArmPose.CROSSBOW_CHARGE;
		} else if (this.isHolding(Items.CROSSBOW)) {
			// Is holding a Crossbow.
			return ArmPose.CROSSBOW_HOLD;
		} else {
			// Doesn't have a Crossbow or Bow.
			return this.isAggressive() ? ArmPose.ATTACKING : ArmPose.NEUTRAL;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static enum ArmPose {
		CROSSED, // Villager arms (Not used)
		NEUTRAL,
		ATTACKING,
		BOW_AND_ARROW,
		CROSSBOW_HOLD,
		CROSSBOW_CHARGE,
		SPELLCASTING, // (Not used)
		CELEBRATING; // (Not used)
	}

	/*********************************************************** Sounds ********************************************************/

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.GENERIC_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.GENERIC_DEATH;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.WOLF_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}
}