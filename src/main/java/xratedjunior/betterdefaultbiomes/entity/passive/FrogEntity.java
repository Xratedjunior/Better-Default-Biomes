package xratedjunior.betterdefaultbiomes.entity.passive;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfigRegistry;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class FrogEntity extends BDBAnimalEntityAbstract {
	private static final float adultHealth = 8.0F;
	private static final float babyHealth = 4.0F;
	private static final int jumpStateID = 1;
	private static final int attackStateID = 4;
	private static final int soundStateID = 60;
	private static final double walkingSpeed = 0.6D;
	private boolean wasOnGround;
	private int currentMoveTypeDuration;
	private int jumpTicks;
	private int jumpDuration;
	private int attackDuration;
	private int attackTicks;
	private int soundDuration;
	private int soundTicks;

	public FrogEntity(EntityType<? extends FrogEntity> type, Level worldIn) {
		super(type, worldIn);
		this.jumpControl = new FrogEntity.JumpHelperController(this);
		this.moveControl = new FrogEntity.MoveHelperController(this);
		this.setMovementSpeed(0.0D);
	}

	/*********************************************************** Movement Speed ********************************************************/

	public void setMovementSpeed(double newSpeed) {
		this.getNavigation().setSpeedModifier(newSpeed);
		this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), newSpeed);
	}

	/*********************************************************** Attributes ********************************************************/

	/*
	 * Sets the attributes like Health and Movement Speed
	 */
	public static AttributeSupplier.Builder createFrogAttributes() {
		return BDBAnimalEntityAbstract.createMobAttributes().add(Attributes.MAX_HEALTH, (double) adultHealth);
	}

	@Override
	protected void babyHealth() {
		if (this.isBaby() && this.getAttributeValue(Attributes.MAX_HEALTH) != babyHealth) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(babyHealth);
			this.setHealth(babyHealth);
		} else if (!this.isBaby() && this.getAttributeValue(Attributes.MAX_HEALTH) != adultHealth) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(adultHealth);
			this.setHealth(adultHealth);
		}
	}

	/*********************************************************** Goals ********************************************************/

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new FrogEntity.FrogPanicGoal(this, 0.85D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));

		this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.8D));
		this.goalSelector.addGoal(5, new FrogEntity.FrogAttackGoal(this, 0.85D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, walkingSpeed));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Silverfish.class, true));
	}

	/*
	 * Adds the TemptGoal with the Items from the Config File.
	 */
	@Override
	protected void addTemptGoal(Level worldIn) {
		if (worldIn != null && !worldIn.isClientSide()) {
			this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, this.foodItems, false));
		}
	}

	/*********************************************************** Breeding Items ********************************************************/

	/*
	 * Returns the BreedingConfig for this entity.
	 */
	@Override
	protected String getBreedingConfig() {
		return BreedingConfigRegistry.FROG_FOOD;
	}

	/*********************************************************** Baby ********************************************************/

	@Override
	protected FrogEntity createChild(ServerLevel serverWorld) {
		return BDBEntityTypes.FROG.get().create(serverWorld);
	}

	/*********************************************************** Eye height ********************************************************/

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.5F : sizeIn.height * 0.85F;
	}

	/*********************************************************** Leash ********************************************************/

	@OnlyIn(Dist.CLIENT)
	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, (double) (0.6F * this.getEyeHeight()), (double) (this.getBbWidth() * 0.4F));
	}

	/*********************************************************** Eat Grass ********************************************************/

	@Override
	public boolean canEatGroundBlock() {
		return false;
	}

	/*********************************************************** Attack ********************************************************/

	@Override
	public boolean doHurtTarget(Entity entityIn) {
		return entityIn.hurt(this.damageSources().mobAttack(this), 8.0F);
	}

	/*********************************************************** Living Tick ********************************************************/

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void aiStep() {
		super.aiStep();
		if (this.jumpTicks != this.jumpDuration) {
			++this.jumpTicks;
		} else if (this.jumpDuration != 0) {
			this.jumpTicks = 0;
			this.jumpDuration = 0;
			this.setJumping(false);
		}
		if (this.attackTicks != this.attackDuration) {
			++this.attackTicks;
		} else if (this.attackDuration != 0) {
			this.attackTicks = 0;
			this.attackDuration = 0;
		}
		if (this.soundTicks != this.soundDuration) {
			++this.soundTicks;
		} else if (this.soundDuration != 0) {
			this.soundTicks = 0;
			this.soundDuration = 0;
		}
	}

	/*********************************************************** Update AI ********************************************************/

	@Override
	public void customServerAiStep() {
		if (this.currentMoveTypeDuration > 0) {
			--this.currentMoveTypeDuration;
		}

		if (this.onGround) {
			if (!this.wasOnGround) {
				this.setJumping(false);
				this.checkLandingDelay();
			}

			//Attack Controller
			if (this.currentMoveTypeDuration == 0) {
				LivingEntity livingentity = this.getTarget();
				if (livingentity != null && this.distanceToSqr(livingentity) < 16.0D) {
					this.calculateRotationYaw(livingentity.getX(), livingentity.getZ());
					this.moveControl.setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), this.moveControl.getSpeedModifier());
					this.startJumping();
					this.wasOnGround = true;
				}
			}

			//Jump Controller
			FrogEntity.JumpHelperController FrogEntity$jumphelpercontroller = (FrogEntity.JumpHelperController) this.jumpControl;
			if (!FrogEntity$jumphelpercontroller.getIsJumping()) {
				if (this.moveControl.hasWanted() && this.currentMoveTypeDuration == 0) {
					Path path = this.navigation.getPath();
					Vec3 vector3d = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
					if (path != null && !path.isDone()) {
						vector3d = path.getNextEntityPos(this);
					}

					this.calculateRotationYaw(vector3d.x, vector3d.z);
					this.startJumping();
				}
			} else if (!FrogEntity$jumphelpercontroller.canJump()) {
				this.enableJumpControl();
			}
		}

		this.wasOnGround = this.onGround;
	}

	private void calculateRotationYaw(double x, double z) {
		this.setYRot((float) (Mth.atan2(z - this.getZ(), x - this.getX()) * (double) (180F / (float) Math.PI)) - 90.0F);
	}

	private void checkLandingDelay() {
		this.updateMoveTypeDuration();
		this.disableJumpControl();
	}

	private void updateMoveTypeDuration() {
		if (this.moveControl.getSpeedModifier() < 2.2D) {
			this.currentMoveTypeDuration = 10;
		} else {
			this.currentMoveTypeDuration = 1;
		}
	}

	private void enableJumpControl() {
		((FrogEntity.JumpHelperController) this.jumpControl).setCanJump(true);
	}

	private void disableJumpControl() {
		((FrogEntity.JumpHelperController) this.jumpControl).setCanJump(false);
	}

	/*********************************************************** Jumping ********************************************************/

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	@Override
	protected void jumpFromGround() {
		super.jumpFromGround();
		double movementSpeed = this.moveControl.getSpeedModifier();
		if (movementSpeed > 0.0D) {
			double currentVelocity = this.getDeltaMovement().horizontalDistanceSqr();
			if (currentVelocity < 0.01D) {
				float horizontalSpeed = 0.1F;
				this.moveRelative(horizontalSpeed, new Vec3(0.0D, 0.0D, 1.0D));
			}
		}

		if (!this.level.isClientSide()) {
			this.level.broadcastEntityEvent(this, (byte) jumpStateID);
		}
	}

	@Override
	protected float getJumpPower() {
		float bigJump = 0.5F;
		if (!this.horizontalCollision && (!this.moveControl.hasWanted() || !(this.moveControl.getWantedY() > this.getY() + 0.5D))) {
			Path path = this.navigation.getPath();
			if (path != null && !path.isDone()) {
				Vec3 vector3d = path.getNextEntityPos(this);
				if (vector3d.y > this.getY() + 0.5D) {
					return bigJump;
				}
			}
			float defaultJump = 0.2F;
			float runningJump = 0.32F;
			return this.moveControl.getSpeedModifier() <= walkingSpeed ? defaultJump : runningJump;
		} else {
			return bigJump;
		}
	}

	@Override
	public void setJumping(boolean jumping) {
		super.setJumping(jumping);
		if (jumping) {
			this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
		}
	}

	public void startJumping() {
		this.setJumping(true);
		this.jumpDuration = 10;
		this.jumpTicks = 0;
	}

	@Override
	public boolean canSpawnSprintParticle() {
		return false;
	}

	/**
	 * Handler for {@link Level#broadcastEntityEvent}
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleEntityEvent(byte id) {
		if (id == jumpStateID) {
			this.spawnSprintParticle();
			this.jumpDuration = 10;
			this.jumpTicks = 0;
		}
		if (id == attackStateID) {
			if (this.attackDuration == 0) {
				this.attackDuration = 10 / 2;
				this.attackTicks = 0;
			}
		}
		if (id == soundStateID) {
			this.soundDuration = 10;
			this.soundTicks = 0;
		} else {
			super.handleEntityEvent(id);
		}
	}

	/*********************************************************** Sounds ********************************************************/

	protected SoundEvent getJumpSound() {
		return SoundEvents.RABBIT_JUMP;
	}

	@Override
	protected SoundEvent getAnimalEatingSound() {
		return super.getAnimalEatingSound();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		this.setSoundState();
		return BDBSoundEvents.ENTITY_FROG_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		this.setSoundState();
		return BDBSoundEvents.ENTITY_FROG_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		this.setSoundState();
		return BDBSoundEvents.ENTITY_FROG_DEATH.get();
	}

	private void setSoundState() {
		if (!this.level.isClientSide()) {
			this.level.broadcastEntityEvent(this, (byte) soundStateID);
		}
	}

	/*********************************************************** Animation ********************************************************/

	@OnlyIn(Dist.CLIENT)
	public float getJumpCompletion(float partialTick) {
		return this.jumpDuration == 0 ? 0.0F : ((float) this.jumpTicks + partialTick) / (float) this.jumpDuration;
	}

	@OnlyIn(Dist.CLIENT)
	public float getAttackCompletion(float partialTick) {
		return this.attackDuration == 0 ? 0.0F : ((float) this.attackTicks + partialTick) / (float) this.attackDuration;
	}

	@OnlyIn(Dist.CLIENT)
	public float getSoundCompletion(float partialTick) {
		return this.soundDuration == 0 ? 0.0F : ((float) this.soundTicks + partialTick) / (float) this.soundDuration;
	}

	/*********************************************************** JumpHelperController ********************************************************/

	/*
	 * Sets jumping motion
	 */
	public class JumpHelperController extends JumpControl {
		private final FrogEntity frog;
		private boolean canJump;

		public JumpHelperController(FrogEntity frog) {
			super(frog);
			this.frog = frog;
		}

		public boolean getIsJumping() {
			return this.jump;
		}

		public boolean canJump() {
			return this.canJump;
		}

		public void setCanJump(boolean canJumpIn) {
			this.canJump = canJumpIn;
		}

		/**
		 * Called to actually make the entity jump if isJumping is true.
		 */
		@Override
		public void tick() {
			if (this.jump) {
				this.frog.startJumping();
				this.jump = false;
			}
		}
	}

	/*********************************************************** MoveHelperController ********************************************************/

	/*
	 * Movement
	 */
	static class MoveHelperController extends MoveControl {
		private final FrogEntity frog;
		private double nextJumpSpeed;

		public MoveHelperController(FrogEntity frog) {
			super(frog);
			this.frog = frog;
		}

		@Override
		public void tick() {
			if (this.frog.onGround && !this.frog.jumping && !((FrogEntity.JumpHelperController) this.frog.jumpControl).getIsJumping()) {
				this.frog.setMovementSpeed(0.0D);
			} else if (this.hasWanted()) {
				this.frog.setMovementSpeed(this.nextJumpSpeed);
			}

			super.tick();
		}

		/**
		 * Sets the speed and location to move to
		 */
		@Override
		public void setWantedPosition(double x, double y, double z, double speedIn) {
			if (this.frog.isInWater()) {
				speedIn = 1.5D;
			}

			super.setWantedPosition(x, y, z, speedIn);
			if (speedIn > 0.0D) {
				this.nextJumpSpeed = speedIn;
			}
		}
	}

	/*********************************************************** PanicGoal ********************************************************/

	static class FrogPanicGoal extends PanicGoal {
		private final FrogEntity frog;

		public FrogPanicGoal(FrogEntity frog, double speedIn) {
			super(frog, speedIn);
			this.frog = frog;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			super.tick();
			this.frog.setMovementSpeed(this.speedModifier);
		}
	}

	/*********************************************************** AttackGoal ********************************************************/

	static class FrogAttackGoal extends MeleeAttackGoal {

		public FrogAttackGoal(FrogEntity frog, double speedIn) {
			super(frog, speedIn, true);
		}

		@Override
		protected double getAttackReachSqr(LivingEntity attackTarget) {
			return (double) (2.0F + attackTarget.getBbWidth());
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
			double d0 = this.getAttackReachSqr(enemy);
			if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
				if (!this.mob.level.isClientSide()) {
					this.mob.level.broadcastEntityEvent(this.mob, (byte) attackStateID);
				}
			}
			super.checkAndPerformAttack(enemy, distToEnemySqr);
		}
	}
}