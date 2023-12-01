package xratedjunior.betterdefaultbiomes.entity.passive;

import java.util.List;

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
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfigRegistry;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingItem;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.ai.goal.CamelFollowCaravanGoal;

/**
 * Reference: {@link Llama}
 * TODO Replace Llama from WanderingTrader with Camel
 * TODO Add custom sounds
 * TODO Disconnect from Llama. Maybe put together with Vanilla Camel?
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class CamelEntity extends Llama {
	protected Ingredient foodItems = Ingredient.of(BreedingConfigRegistry.getTemptationItemStacks(this.getBreedingConfig()).stream());
	private static final EntityDataAccessor<Integer> DATA_CAMEL_STRENGTH_ID = SynchedEntityData.defineId(CamelEntity.class, EntityDataSerializers.INT);
	private int waterCooldown;
	private boolean hasEaten;

	public CamelEntity(EntityType<? extends CamelEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	/*********************************************************** Inventory ********************************************************/

	private void setCamelStrength(int strengthIn) {
		this.entityData.set(DATA_CAMEL_STRENGTH_ID, Math.max(1, Math.min(5, strengthIn)));
	}

	private void setRandomCamelStrength() {
		int i = this.random.nextFloat() < 0.2F ? 4 : 2;
		this.setCamelStrength(2 + this.random.nextInt(i));
	}

	private int getCamelStrength() {
		return this.entityData.get(DATA_CAMEL_STRENGTH_ID);
	}

	@Override
	protected int getInventorySize() {
		return this.hasChest() ? 2 + 3 * this.getInventoryColumns() : super.getInventorySize();
	}

	@Override
	public int getInventoryColumns() {
		return this.getCamelStrength();
	}

	//Armor Slot
	@Override
	public boolean canWearArmor() {
		return true;
	}

	//Saddle Slot
	@Override
	public boolean isSaddleable() {
		return true;
	}

	/*********************************************************** NBT ********************************************************/

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("CamelStrength", this.getCamelStrength());
		compound.putInt("WaterCooldown", this.waterCooldown);
		compound.putBoolean("HasEaten", this.hasEaten);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		this.setCamelStrength(compound.getInt("CamelStrength"));
		super.readAdditionalSaveData(compound);
		this.waterCooldown = compound.getInt("WaterCooldown");
		this.hasEaten = compound.getBoolean("HasEaten");
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_CAMEL_STRENGTH_ID, 0);
	}

	/*********************************************************** Spawn ********************************************************/

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		this.setRandomCamelStrength();
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	/**
	 * Static predicate for determining whether or not a camel can spawn at the provided location.
	 * 
	 * @param camel The Camel entity to be spawned
	 */
	public static boolean canCamelSpawn(EntityType<CamelEntity> camel, LevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
		BlockState blockstate = world.getBlockState(pos.below());
		return (blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(Blocks.SAND)) && world.getRawBrightness(pos, 0) > 8;
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 * Was 6 originally for horses.
	 */
	@Override
	public int getMaxSpawnClusterSize() {
		return 4;
	}

	/*********************************************************** Movement ********************************************************/

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
		this.goalSelector.addGoal(2, new CamelFollowCaravanGoal(this, (double) 2.1F));
		this.goalSelector.addGoal(3, new PanicGoal(this, 1.2D));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.addBehaviourGoals();
	}

	@Override
	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
	}

	/*********************************************************** Leash ********************************************************/

	/**
	 * Edit leash placement.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, 0.72D * (double) this.getEyeHeight(), (double) this.getBbWidth() * 0.4D);
	}

	/*********************************************************** Riding ********************************************************/

	@Override
	@Nullable
	public LivingEntity getControllingPassenger() {
		if (this.isSaddled()) {
			Entity entity = this.getFirstPassenger();
			if (entity instanceof LivingEntity) {
				return (LivingEntity) entity;
			}
		}

		return null;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
	@Override
	public double getPassengersRidingOffset() {
		return (double) this.getBbHeight() * 0.55D;
	}

	@Override
	public void positionRider(Entity passenger) {
		if (this.hasPassenger(passenger)) {
			float zOffset = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
			float xOffset = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
			passenger.setPos(this.getX() + (double) (0.3F * xOffset), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset(), this.getZ() - (double) (0.16F * zOffset));
		}
	}

	/*********************************************************** Water Bucket ********************************************************/

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void aiStep() {
		super.aiStep();
		if (this.waterCooldown > 0) {
			--this.waterCooldown;
		}
		if (this.isEating()) {
			this.setHasEaten(true);
		}
	}

	private void setWaterCooldown(int ticks) {
		this.waterCooldown = ticks;
	}

	private boolean canWater() {
		return this.waterCooldown <= 0;
	}

	private void setHasEaten(boolean bool) {
		this.hasEaten = bool;
	}

	private boolean getHasEaten() {
		return this.hasEaten;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		Item item = itemStack.getItem();

		if (this.isTamed() && !this.isBaby() && item == Items.BUCKET && this.canWater()) {
			BetterDefaultBiomes.LOGGER.info("water!!!");

			player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
			ItemStack waterBucket = ItemUtils.createFilledResult(itemStack, player, Items.WATER_BUCKET.getDefaultInstance());
			player.setItemInHand(hand, waterBucket);
			this.setWaterCooldown(6000);
			this.setHasEaten(false);
			return InteractionResult.sidedSuccess(this.level.isClientSide());
		}
		return super.mobInteract(player, hand);
	}

	/*********************************************************** Breeding ********************************************************/

	protected String getBreedingConfig() {
		return BreedingConfigRegistry.CAMEL_FOOD;
	}

	@Override
	public InteractionResult fedFood(Player player, ItemStack itemInHand) {
		boolean isEating = this.handleEating(player, itemInHand);
		if (!player.getAbilities().instabuild) {
			itemInHand.shrink(1);
		}

		if (this.level.isClientSide) {
			return InteractionResult.CONSUME;
		} else {
			return isEating ? InteractionResult.SUCCESS : InteractionResult.PASS;
		}
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
	 * the animal type)
	 */
	@Override
	public boolean isFood(ItemStack stack) {
		return this.foodItems.test(stack);
	}

	@Override
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
					int decreaseTemperAmount = (int) Math.ceil(growthAmount / 20);

					//Set in love for breeding
					if (!this.level.isClientSide() && this.getAge() == 0 && this.isTamed() && !this.isInLove() && isBreedingItem) {
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

					if (decreaseTemperAmount > 0 && !this.isTamed() && this.getTemper() < this.getMaxTemper()) {
						eat = true;
						if (!this.level.isClientSide()) {
							this.modifyTemper(decreaseTemperAmount);
						}
					}

					// Play SoundEvent
					if (eat) {
						this.gameEvent(GameEvent.EAT);
						if (!this.isSilent()) {
							SoundEvent soundevent = this.getEatingSound();
							if (soundevent != null) {
								this.level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), this.getEatingSound(), this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
							}
						}
					}
				}
			}

		}
		return eat;
	}

	/*********************************************************** Baby ********************************************************/

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMate(Animal otherAnimal) {
		return otherAnimal != this && otherAnimal instanceof CamelEntity && this.canParent() && ((CamelEntity) otherAnimal).canParent();
	}

	@Override
	public CamelEntity getBreedOffspring(ServerLevel serverWorld, AgeableMob ageableEntity) {
		CamelEntity camelEntity = this.makeBaby();
		this.setOffspringAttributes(ageableEntity, camelEntity);
		CamelEntity camelEntity1 = (CamelEntity) ageableEntity;
		int i = this.random.nextInt(Math.max(this.getCamelStrength(), camelEntity1.getCamelStrength())) + 1;
		if (this.random.nextFloat() < 0.03F) {
			++i;
		}
		camelEntity.setCamelStrength(i);
		camelEntity.setVariant(this.random.nextBoolean() ? this.getVariant() : camelEntity1.getVariant());
		return camelEntity;
	}

	protected CamelEntity makeBaby() {
		return BDBEntityTypes.CAMEL.get().create(this.level);
	}
}
