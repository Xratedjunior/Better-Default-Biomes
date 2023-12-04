package xratedjunior.betterdefaultbiomes.entity.passive;

import java.util.List;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfigRegistry;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingItem;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.passive.DuckRenderer;
import xratedjunior.betterdefaultbiomes.item.BDBItems;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 *          TODO extend if possible {@link Chicken} or {@link BDBAnimalEntityAbstract} and adjust {@link DuckRenderer} after.
 */
public class DuckEntity extends Animal {
	private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(DuckEntity.class, EntityDataSerializers.INT);
	private static final int DUCK_TEXTURES = 7;
	protected Ingredient foodItems = Ingredient.of(BreedingConfigRegistry.getTemptationItemStacks(this.getBreedingConfig()).stream());
	protected Ingredient breedingItems = Ingredient.of(BreedingConfigRegistry.getBreedingItemStacks(this.getBreedingConfig()).stream());
	public float wingRotation;
	public float destPos;
	public float oFlapSpeed;
	public float oFlap;
	public float wingRotDelta = 1.0F;
	public int timeUntilNextEgg = this.random.nextInt(6000) + 6000;
	public boolean duckJockey;

	public DuckEntity(EntityType<? extends DuckEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.addTemptGoal(worldIn, 3, 1.0D);
	}

	/*********************************************************** NBT ********************************************************/

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Variant", this.getVariant());
		compound.putBoolean("IsDuckJockey", this.duckJockey);
		compound.putInt("EggLayTime", this.timeUntilNextEgg);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setVariant(compound.getInt("Variant"));
		this.duckJockey = compound.getBoolean("IsDuckJockey");
		if (compound.contains("EggLayTime")) {
			this.timeUntilNextEgg = compound.getInt("EggLayTime");
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_VARIANT_ID, 0);
	}

	/*********************************************************** Variants ********************************************************/

	public int getVariant() {
		return Mth.clamp(this.entityData.get(DATA_VARIANT_ID), 0, DUCK_TEXTURES);
	}

	public void setVariant(int variantIn) {
		this.entityData.set(DATA_VARIANT_ID, variantIn);
	}

	/*********************************************************** Data Class ********************************************************/

	static class DuckData extends AgeableMob.AgeableMobGroupData {
		public final int variant;

		private DuckData(int variantIn) {
			super(true);
			this.variant = variantIn;
		}
	}

	/*********************************************************** Movement ********************************************************/

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));

		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
	}

	/*
	 * Adds the TemptGoal with the Items from the Config File.
	 */
	protected void addTemptGoal(Level worldIn, int goalSelectorNumber, double speedIn) {
		if (worldIn != null && !worldIn.isClientSide()) {
			this.goalSelector.addGoal(goalSelectorNumber, new TemptGoal(this, speedIn, this.foodItems, false));
		}
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.78F : sizeIn.height * 1.22F;
	}

	public static AttributeSupplier.Builder createDuckAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	/*********************************************************** Spawn ********************************************************/

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		int duckVariant;
		if (spawnDataIn instanceof DuckEntity.DuckData) {
			duckVariant = ((DuckEntity.DuckData) spawnDataIn).variant;
		} else {
			duckVariant = this.random.nextInt(DUCK_TEXTURES);
			spawnDataIn = new DuckEntity.DuckData(duckVariant);
		}
		this.setVariant(duckVariant);
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	/*********************************************************** Breeding Items ********************************************************/

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
	 * the animal type)
	 */
	@Override
	public boolean isFood(ItemStack stack) {
		return this.breedingItems.test(stack);
	}

	/*
	 * Returns the BreedingConfig for this entity.
	 */
	protected String getBreedingConfig() {
		return BreedingConfigRegistry.DUCK_FOOD;
	}

	/*********************************************************** Baby ********************************************************/

	/**
	 * Baby creation
	 */
	@Override
	public DuckEntity getBreedOffspring(ServerLevel serverWorld, AgeableMob ageableEntity) {
		DuckEntity duckEntity = this.createChild();
		DuckEntity duckEntity1 = (DuckEntity) ageableEntity;
		duckEntity.setVariant(this.random.nextBoolean() ? this.getVariant() : duckEntity1.getVariant());
		return duckEntity;
	}

	private DuckEntity createChild() {
		return BDBEntityTypes.DUCK.get().create(this.level());
	}

	/*********************************************************** Interact ********************************************************/

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (!itemstack.isEmpty()) {
			return this.playerInteractEating(player, itemstack);
		}
		return super.mobInteract(player, hand);
	}

	/*
	 * Interact with this entity by right-clicking
	 */
	public InteractionResult playerInteractEating(Player player, ItemStack itemStackInHand) {
		boolean flag = this.handleEating(player, itemStackInHand);

		if (this.level().isClientSide()) {
			return InteractionResult.CONSUME;
		} else {
			return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
		}
	}

	protected boolean handleEating(Player player, ItemStack itemStackInHand) {
		boolean eat = false;
		BreedingConfig breedingConfig = BreedingConfigRegistry.BREEDING_CONFIGS.get(getBreedingConfig());
		for (BreedingItem breedingItem : breedingConfig.getBreedingItems()) {
			Item itemInHand = itemStackInHand.getItem();
			List<Item> itemList = BreedingConfigRegistry.getItemList(breedingItem);
			for (Item item : itemList) {
				if (itemInHand == item) {
					boolean isBreedingItem = breedingItem.getBreeding();
					float healAmount = (float) breedingItem.getHealAmount();
					int growthAmount = breedingItem.getGrowthAmount();

					//Set in love for breeding
					if (!this.level().isClientSide() && this.getAge() == 0 && !this.isInLove() && isBreedingItem) {
						this.setInLove(player);
						eat = true;
					}

					//Heal
					if (this.getHealth() < this.getMaxHealth() && healAmount > 0.0F) {
						this.heal(healAmount);
						eat = true;
					}

					//Help child grow
					if (this.isBaby() && growthAmount > 0) {
						this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
						this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);

						if (!this.level().isClientSide()) {
							this.ageUp(growthAmount);
						}
						eat = true;
					}

					//Animate and play SoundEvent
					if (eat) {
						if (!player.getAbilities().instabuild) {
							itemStackInHand.shrink(1);
						}
					}
				}
			}

		}
		return eat;
	}

	/*********************************************************** DuckJockey ********************************************************/

	@Override
	protected void positionRider(Entity entity, Entity.MoveFunction move) {
		super.positionRider(entity, move);
		if (entity instanceof LivingEntity) {
			((LivingEntity) entity).yBodyRot = this.yBodyRot;
		}

	}

	@Override
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float scale) {
		return new Vector3f(0.0F, dimensions.height, -0.1F * scale);
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	@Override
	public int getExperienceReward() {
		return this.isDuckJockey() ? 10 : super.getExperienceReward();
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return this.isDuckJockey();
	}

	/**
	 * Determines if this Duck is a jokey with a zombie riding it.
	 */
	public boolean isDuckJockey() {
		return this.duckJockey;
	}

	/**
	 * Sets whether this Duck is a jockey or not.
	 */
	public void setDuckJockey(boolean jockey) {
		this.duckJockey = jockey;
	}

	/*********************************************************** LivingTick ********************************************************/

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void aiStep() {
		super.aiStep();
		this.oFlap = this.wingRotation;
		this.oFlapSpeed = this.destPos;
		this.destPos = (float) ((double) this.destPos + (double) (this.onGround() || (this.isInWater() && !this.wasEyeInWater) ? -1 : 4) * 0.3D);
		this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);
		if ((!this.onGround() && (!this.isInWater() && this.wasEyeInWater)) && this.wingRotDelta < 1.0F) {
			this.wingRotDelta = 1.0F;
		}

		this.wingRotDelta = (float) ((double) this.wingRotDelta * 0.9D);
		Vec3 vector3d = this.getDeltaMovement();
		if ((!this.onGround() && (!this.isInWater() && this.wasEyeInWater)) && vector3d.y < 0.0D) {
			this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
		}

		this.wingRotation += this.wingRotDelta * 2.0F;
		if (!this.level().isClientSide() && this.isAlive() && !this.isBaby() && !this.isDuckJockey() && --this.timeUntilNextEgg <= 0) {
			this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			this.spawnAtLocation(BDBItems.DUCK_EGG.get());
			this.timeUntilNextEgg = this.random.nextInt(6000) + 6000;
		}

	}

	/*********************************************************** Sounds ********************************************************/

	@Override
	protected SoundEvent getAmbientSound() {
		return BDBSoundEvents.ENTITY_DUCK_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		SoundEvent soundEvent = BDBSoundEvents.ENTITY_DUCK_HURT.get();
		if (this.isBaby()) {
			soundEvent = BDBSoundEvents.ENTITY_DUCK_HURT_BABY.get();
		}
		return soundEvent;
	}

	@Override
	protected SoundEvent getDeathSound() {
		SoundEvent soundEvent = BDBSoundEvents.ENTITY_DUCK_DEATH.get();
		if (this.isBaby()) {
			soundEvent = BDBSoundEvents.ENTITY_DUCK_DEATH_BABY.get();
		}
		return soundEvent;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.SOUL_SOIL_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.04F, 2.0F);
	}
}
