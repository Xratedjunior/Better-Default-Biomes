package xratedjunior.betterdefaultbiomes.entity.hostile.hunter;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import xratedjunior.betterdefaultbiomes.configuration.entity.HunterConfig;
import xratedjunior.betterdefaultbiomes.entity.ai.attribute.BDBCreatureAttribute;
import xratedjunior.betterdefaultbiomes.entity.hostile.AbstractHostileHumanoid;
import xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit.DesertBanditEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.HunterArrowEntity;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class HunterEntity extends AbstractHostileHumanoid {

	public HunterEntity(EntityType<? extends HunterEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	/*********************************************************** Goals ********************************************************/

	// TODO Drink milk if Hunter has been poisoned himself.
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, DesertBanditEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Pig.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Cow.class, true));
	}

	/*********************************************************** Attack AI ********************************************************/

	@Override
	protected AbstractArrow getArrow(ItemStack arrowStack, float distanceFactor) {
		return HunterArrowEntity.shootHunterArrow(this, arrowStack, distanceFactor);
	}

	/*********************************************************** Attributes ********************************************************/

	public static AttributeSupplier.Builder createHunterAttributes() {
		return HunterEntity.createHumanoidAttributes();
	}

	@Override
	public MobType getMobType() {
		return BDBCreatureAttribute.HUNTER;
	}

	/*********************************************************** Spawn Rules ********************************************************/

	/**
	 * Referenced from: {@link Monster#checkMonsterSpawnRules}
	 */
	@SuppressWarnings("deprecation")
	public static boolean checkHunterSpawnRules(EntityType<? extends HunterEntity> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
		return AbstractHostileHumanoid.checkHostileSpawnRules(type, worldIn, reason, pos, randomIn)
				// Prevent spawning underground in deep caves and open ravines.
				&& pos.getY() > worldIn.getSeaLevel()
				// Check sky level below 12 (Check for some darkness)
				&& worldIn.getBrightness(LightLayer.SKY, pos) < 12
				// Check for some remaining sky light. (Prevents spawning in caves and stuff)
				&& worldIn.getBrightness(LightLayer.SKY, pos) > 4
				// Check block light level of 0 (like all hostile mobs)
				&& worldIn.getBrightness(LightLayer.BLOCK, pos) <= 0;
	}

	/*********************************************************** Spawn Equipment ********************************************************/

	@Override
	protected void setDefaultEquipmentAndEnchants(DifficultyInstance difficulty) {
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		this.setDropChance(EquipmentSlot.MAINHAND, ((float) HunterConfig.hunter_bow_drop_chance.get() / 100.0f));
		this.applyDefaultEnchantments();
	}

	private void applyDefaultEnchantments() {
		if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEnchanted() == false) {
			// Set Enchantments
			this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.POWER_ARROWS, 1);
			this.getItemBySlot(EquipmentSlot.MAINHAND).enchant(Enchantments.PUNCH_ARROWS, 1);
			// Set Item name
			this.getItemBySlot(EquipmentSlot.MAINHAND).setHoverName(Component.translatable("equipment.betterdefaultbiomes.hunter_bow").withStyle(ChatFormatting.GREEN));
		}
	}
}