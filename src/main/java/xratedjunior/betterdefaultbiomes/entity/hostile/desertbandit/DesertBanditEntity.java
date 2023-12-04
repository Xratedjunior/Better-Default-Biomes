package xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.ai.attribute.BDBCreatureAttribute;
import xratedjunior.betterdefaultbiomes.entity.hostile.AbstractHostileHumanoid;
import xratedjunior.betterdefaultbiomes.entity.projectile.BanditArrowEntity;

/**
 * TODO Ride Horses and/or Camels?
 * 
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class DesertBanditEntity extends AbstractHostileHumanoid {
	private static final Item BANDIT_SWORD = Items.IRON_SWORD;
	public static float armorDropChance = 0.08f; // Default: 0.08f
	public static int flameChance = 100; // Default: 100
	public static int infinityChance = 300; // Default: 300
	public static int multiShotChance = 200; // Default: 200
	public static int fireAspectChance = 100; // Default: 100

	public DesertBanditEntity(EntityType<? extends DesertBanditEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	/*********************************************************** Goals ********************************************************/

	/**
	 * TODO Celebrate after killing/stealing player.
	 * {@link Pillager} -> {@link AbstractIllager} -> {@link Raider}
	 * TODO Force doors open. Bang a couple times like Zombies with a chance to open every time (Chance increasing with every bang).
	 * {@link AbstractIllager.RaiderOpenDoorGoal}
	 */
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers(DesertBanditEntity.class)));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Rabbit.class, true));
	}

	/*********************************************************** Attack AI ********************************************************/

	@Override
	protected AbstractArrow getArrow(ItemStack arrowStack, float distanceFactor) {
		return BanditArrowEntity.shootBanditArrow(this, arrowStack, distanceFactor);
	}

	/*********************************************************** Attributes ********************************************************/

	public static AttributeSupplier.Builder createDesertBanditAttributes() {
		return DesertBanditEntity.createHumanoidAttributes();
	}

	@Override
	public MobType getMobType() {
		return BDBCreatureAttribute.DESERT_BANDIT;
	}

	/*********************************************************** Spawn Rules ********************************************************/

	/**
	 * Referenced from: {@link Monster#checkMonsterSpawnRules}
	 * TODO Maybe make custom spawner like {@link PhantomSpawner} and {@link WanderingTraderSpawner}?
	 */
	@SuppressWarnings("deprecation")
	public static boolean checkBanditSpawnRules(EntityType<? extends DesertBanditEntity> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
		return AbstractHostileHumanoid.checkHostileSpawnRules(type, worldIn, reason, pos, randomIn)
				// Prevent spawning underground in deep caves and open ravines.
				&& pos.getY() > worldIn.getSeaLevel()
				// Check for some remaining sky light. (Prevents spawning in caves and stuff)
				&& worldIn.getBrightness(LightLayer.SKY, pos) > 4
				// Check block light level of 0 (like all hostile mobs)
				&& worldIn.getBrightness(LightLayer.BLOCK, pos) <= 0;
	}

	/*********************************************************** Spawning ********************************************************/

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

		if (reason == MobSpawnType.NATURAL) {
			this.setSpawnGroup();
		}

		return spawnDataIn;
	}

	/**
	 * Desert Bandit Group Spawn Controller
	 * Reference: {@link Raid#spawnGroup}
	 */
	protected void setSpawnGroup() {
		int archerChance = 15; // Default: 15
		int arbalistChance = 5; // Default: 5
		int masterChance = 2; // Default: 2
		int archerChanceInput = archerChance;
		int arbalistChanceInput = archerChanceInput + arbalistChance;
		int masterChanceInput = arbalistChanceInput + masterChance;
		float chanceGenerator = Math.round(this.random.nextFloat() * 100);
		if (chanceGenerator < archerChanceInput) {
			DesertBanditArcherEntity newDesertBanditArcher = BDBEntityTypes.DESERT_BANDIT_ARCHER.get().create(this.getCommandSenderWorld());
			newDesertBanditArcher.moveTo(this.blockPosition(), this.getYRot(), this.getXRot());
			this.getCommandSenderWorld().addFreshEntity(newDesertBanditArcher);
			DesertBanditArcherEntity.desertBanditArcherDefaultEquipment(newDesertBanditArcher);
		} else if (chanceGenerator < arbalistChanceInput) {
			DesertBanditArbalistEntity newDesertBanditArbalist = BDBEntityTypes.DESERT_BANDIT_ARBALIST.get().create(this.getCommandSenderWorld());
			newDesertBanditArbalist.moveTo(this.blockPosition(), this.getYRot(), this.getXRot());
			this.getCommandSenderWorld().addFreshEntity(newDesertBanditArbalist);
			DesertBanditArbalistEntity.desertBanditArbalistDefaultEquipment(newDesertBanditArbalist);
		} else if (chanceGenerator < masterChanceInput) {
			DesertBanditMasterEntity newDesertBanditMaster = BDBEntityTypes.DESERT_BANDIT_MASTER.get().create(this.getCommandSenderWorld());
			newDesertBanditMaster.moveTo(this.blockPosition(), this.getYRot(), this.getXRot());
			this.getCommandSenderWorld().addFreshEntity(newDesertBanditMaster);
			DesertBanditMasterEntity.desertBanditMasterDefaultEquipment(newDesertBanditMaster);
		}
	}

	/*********************************************************** Spawn Equipment ********************************************************/

	@Override
	protected void setDefaultEquipmentAndEnchants(DifficultyInstance difficulty) {
		ItemStack desertBanditSword = new ItemStack(BANDIT_SWORD);
		Map<Enchantment, Integer> map = Maps.newHashMap();
		if (this.level().getDifficulty() == Difficulty.HARD) {
			map.put(Enchantments.SHARPNESS, 1);
		}
		map.put(Enchantments.UNBREAKING, 1);

		EnchantmentHelper.setEnchantments(map, desertBanditSword);
		this.setItemSlot(EquipmentSlot.MAINHAND, desertBanditSword);
		this.getItemBySlot(EquipmentSlot.MAINHAND).setHoverName(Component.translatable("equipment.betterdefaultbiomes.bandit_sword").withStyle(ChatFormatting.YELLOW));
		float armorDropChance = DesertBanditEntity.armorDropChance;
		this.setDropChance(EquipmentSlot.MAINHAND, armorDropChance);
	}
}