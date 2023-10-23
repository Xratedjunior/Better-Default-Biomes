package xratedjunior.betterdefaultbiomes.entity.passive;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

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
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfigRegistry;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingItem;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public abstract class BDBAnimalEntityAbstract extends Animal {
	private static final Predicate<LivingEntity> PARENT_SELECTOR = (livingEntity) -> {
		return livingEntity instanceof BDBAnimalEntityAbstract && ((BDBAnimalEntityAbstract) livingEntity).isParent();
	};
	private static final TargetingConditions PARENT_TARGETING = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().selector(PARENT_SELECTOR);
	private static final EntityDataAccessor<Byte> DATA_FLAGS = SynchedEntityData.defineId(BDBAnimalEntityAbstract.class, EntityDataSerializers.BYTE);
	private static final TargetingConditions PLAYER_PREDICATE = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight();
	protected Ingredient foodItems = Ingredient.of(BreedingConfigRegistry.getTemptationItemStacks(this.getBreedingConfig()).stream());
	protected Ingredient breedingItems = Ingredient.of(BreedingConfigRegistry.getBreedingItemStacks(this.getBreedingConfig()).stream());
	private static final int DATA_FLAG_PARENT = 8;
	private int eatingCounter;
	private int openMouthCounter;
	private float headLean;
	private float prevHeadLean;
	private float mouthOpenness;
	private float prevMouthOpenness;
	private float prevHealth;
	public int tailCounter;

	public BDBAnimalEntityAbstract(EntityType<? extends BDBAnimalEntityAbstract> type, Level worldIn) {
		super(type, worldIn);
		// Used for adding healing particles.
		this.prevHealth = this.getHealth();
		this.addTemptGoal(worldIn);

		//		for(ItemStack itemStack : getTemptationItemStacks(getBreedingConfig())) {
		//			BetterDefaultBiomes.logger.info("Temptation: " + itemStack.toString());
		//		}
		//		for(ItemStack itemStack : getBreedingItemStacks(getBreedingConfig())) {
		//			BetterDefaultBiomes.logger.info("Breeding: " + itemStack.toString());
		//		}
	}

	/*********************************************************** NBT ********************************************************/

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		// TODO Possibly make a goal like the {@link Sheep#eatBlockGoal}
		compound.putBoolean("IsEating", this.isEatingGroundBlock());
		compound.putBoolean("IsParent", this.isParent());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setEatingGroundBlock(compound.getBoolean("IsEating"));
		this.setParent(compound.getBoolean("IsParent"));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FLAGS, (byte) 0);
	}

	protected boolean getAnimalWatchableBoolean(int statusInt) {
		return (this.entityData.get(DATA_FLAGS) & statusInt) != 0;
	}

	protected void setAnimalWatchableBoolean(int flag, boolean bool) {
		byte b0 = this.entityData.get(DATA_FLAGS);
		if (bool) {
			this.entityData.set(DATA_FLAGS, (byte) (b0 | flag));
		} else {
			this.entityData.set(DATA_FLAGS, (byte) (b0 & ~flag));
		}
	}

	/*********************************************************** Spawn ********************************************************/

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 * 6 is the default value
	 */
	@Override
	public int getMaxSpawnClusterSize() {
		return 6;
	}

	/*********************************************************** Attributes ********************************************************/

	/*
	 * Sets the attributes like Health and Movement Speed
	 */
	public static AttributeSupplier.Builder createBDBAnimalAttributes() {
		return BDBAnimalEntityAbstract.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	protected void babyHealth() {
		if (this.isBaby() && this.getAttributeValue(Attributes.MAX_HEALTH) != 10.0D) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0D);
			this.setHealth(10.0F);
		} else if (!this.isBaby() && this.getAttributeValue(Attributes.MAX_HEALTH) < 20.0D) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
			this.setHealth(20.0F);
		}
	}

	/*********************************************************** Goals ********************************************************/

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));

		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}

	/*
	 * Adds the TemptGoal with the Items from the Config File.
	 */
	protected void addTemptGoal(Level worldIn) {
		if (worldIn != null && !worldIn.isClientSide()) {
			this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, this.foodItems, false));
		}
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
	protected abstract String getBreedingConfig();

	/*********************************************************** Baby ********************************************************/

	/*
	 * Return whether this entity is breeding
	 */
	public boolean isParent() {
		return this.getAnimalWatchableBoolean(DATA_FLAG_PARENT);
	}

	/*
	 * Set the status of this entity to "Breeding"
	 */
	public void setParent(boolean parent) {
		this.setAnimalWatchableBoolean(DATA_FLAG_PARENT, parent);
	}

	/**
	 * Returns true if the entity is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMate(Animal otherAnimal) {
		return otherAnimal != this && otherAnimal instanceof BDBAnimalEntityAbstract && this.canParent() && ((BDBAnimalEntityAbstract) otherAnimal).canParent();
	}

	/**
	 * Return true if this entity is ready to mate. (no child, not death...)
	 */
	protected boolean canParent() {
		return !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
	}

	/*
	 * Breed this entity
	 */
	@Nullable
	@Override
	public BDBAnimalEntityAbstract getBreedOffspring(ServerLevel serverWorld, AgeableMob otherParent) {
		// Set parents their data to true.
		if (!this.isParent()) {
			this.setParent(true);
		}
		if (otherParent instanceof BDBAnimalEntityAbstract && !((BDBAnimalEntityAbstract) otherParent).isParent()) {
			((BDBAnimalEntityAbstract) otherParent).setParent(true);
		}

		BDBAnimalEntityAbstract babyEntity = this.createChild(serverWorld);
		return babyEntity;
	}

	protected BDBAnimalEntityAbstract createChild(ServerLevel serverWorld) {
		return null;
	}

	/*********************************************************** Movement ********************************************************/

	/**
	 * Dead and sleeping entities cannot move
	 */
	@Override
	protected boolean isImmobile() {
		return super.isImmobile() || this.isEatingGroundBlock();
	}

	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource) {
		return super.causeFallDamage(distance, damageMultiplier, damageSource);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.7F : sizeIn.height * 0.95F;
	}

	private void moveTail() {
		this.tailCounter = 1;
	}

	// TODO Change/Create FollowParentGoal with adjustable range. Range is the only noticeable change added here.
	protected void followParent() {
		if (this.isParent() && this.isBaby() && !this.isEatingGroundBlock()) {
			LivingEntity livingentity = this.level.getNearestEntity(BDBAnimalEntityAbstract.class, PARENT_TARGETING, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().inflate(16.0D));
			if (livingentity != null && this.distanceToSqr(livingentity) > 4.0D) {
				this.navigation.createPath(livingentity, 0);
			}
		}
	}

	/*********************************************************** Ticks ********************************************************/

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void aiStep() {
		if (this.random.nextInt(200) == 0) {
			this.moveTail();
		}

		super.aiStep();

		if (!this.level.isClientSide() && this.isAlive()) {
			this.babyHealth();

			//Heal over time
			if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
				this.heal(1.0F);
			}

			//Start eating Grass
			if (this.canEatGroundBlock() && !this.isEatingGroundBlock() && this.random.nextInt(100) == 0 && this.level.getBlockState(this.blockPosition().below()).is(Blocks.GRASS_BLOCK)) {
				this.setEatingGroundBlock(true);
			}
			//Stop eating Grass
			if (this.isEatingGroundBlock() && ++this.eatingCounter > 50) {
				this.eatingCounter = 0;
				this.setEatingGroundBlock(false);
				if (this.random.nextInt(5) == 0) {
					this.heal(1.0F);
				}
			}
			this.followParent();
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		super.tick();

		if (this.getHealth() > this.prevHealth && this.level.isClientSide()) {
			this.level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
		}
		this.prevHealth = this.getHealth();

		//Close mouth
		if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
			this.openMouthCounter = 0;
			this.closeAnimalMouth();
		}

		//Tail movement timer
		if (this.tailCounter > 0 && ++this.tailCounter > 8) {
			this.tailCounter = 0;
		}

		//Head movement timer
		this.prevHeadLean = this.headLean;
		if (this.isEatingGroundBlock()) {
			this.headLean += (1.0F - this.headLean) * 0.4F + 0.05F;
			if (this.headLean > 1.0F) {
				this.headLean = 1.0F;
			}
		} else { //Reset
			this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;
			if (this.headLean < 0.0F) {
				this.headLean = 0.0F;
			}
		}

		//Mouth movement timer
		this.prevMouthOpenness = this.mouthOpenness;
		if (this.getAnimalWatchableBoolean(64)) {
			this.mouthOpenness += (1.0F - this.mouthOpenness) * 0.7F + 0.05F;
			if (this.mouthOpenness > 1.0F) {
				this.mouthOpenness = 1.0F;
			}
		} else { //Reset
			this.mouthOpenness += (0.0F - this.mouthOpenness) * 0.7F - 0.05F;
			if (this.mouthOpenness < 0.0F) {
				this.mouthOpenness = 0.0F;
			}
		}
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

		if (this.level.isClientSide()) {
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
					if (!this.level.isClientSide() && this.getAge() == 0 && !this.isInLove() && isBreedingItem) {
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
						this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
						this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);

						if (!this.level.isClientSide()) {
							this.ageUp(growthAmount);
						}
						eat = true;
					}

					//Animate and play SoundEvent
					if (eat) {
						this.eatingAnimal();
						if (!player.getAbilities().instabuild) {
							itemStackInHand.shrink(1);
						}
					}
				}
			}
		}
		return eat;
	}

	/*********************************************************** Eat Grass ********************************************************/

	private void eatingAnimal() {
		this.openAnimalMouth();
		if (!this.isSilent()) {
			SoundEvent soundevent = this.getAnimalEatingSound();
			if (soundevent != null) {
				this.level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), soundevent, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
			}
		}
	}

	private void openAnimalMouth() {
		if (!this.level.isClientSide()) {
			this.openMouthCounter = 1;
			this.setAnimalWatchableBoolean(64, true);
		}
	}

	private void closeAnimalMouth() {
		this.setAnimalWatchableBoolean(64, false);
	}

	@OnlyIn(Dist.CLIENT)
	public float getEatingAmount(float input) {
		return Mth.lerp(input, this.prevHeadLean, this.headLean);
	}

	@OnlyIn(Dist.CLIENT)
	public float getMouthOpennessAngle(float input) {
		return Mth.lerp(input, this.prevMouthOpenness, this.mouthOpenness);
	}

	private boolean isTempted() {
		Player player = this.level.getNearestPlayer(PLAYER_PREDICATE, this);
		if (player == null)
			return false;
		else {
			return this.foodItems.test(player.getMainHandItem()) || this.foodItems.test(player.getOffhandItem());
		}
	}

	public boolean canEatGroundBlock() {
		return !this.isLeashed() && !this.isTempted();
	}

	public void setEatingGroundBlock(boolean bool) {
		this.setAnimalWatchableBoolean(16, bool);
	}

	public boolean isEatingGroundBlock() {
		return this.getAnimalWatchableBoolean(16);
	}

	@Override
	protected void onLeashDistance(float distance) {
		if (distance > 6.0F && this.isEatingGroundBlock()) {
			this.setEatingGroundBlock(false);
		}
		super.onLeashDistance(distance);
	}

	/*********************************************************** Sounds ********************************************************/

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.8F;
	}

	/**
	 * Gets the pitch of living sounds in living entities.
	 */
	@Override
	public float getVoicePitch() {
		return super.getVoicePitch();
	}

	/**
	 * Get number of ticks, at least during which the living entity will be silent.
	 */
	@Override
	public int getAmbientSoundInterval() {
		return 400;
	}

	@Nullable
	protected SoundEvent getAnimalEatingSound() {
		return SoundEvents.GENERIC_EAT;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return super.getAmbientSound();
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return super.getHurtSound(damageSourceIn);
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return super.getDeathSound();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		super.playStepSound(pos, blockIn);
	}
}