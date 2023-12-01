package xratedjunior.betterdefaultbiomes.entity.hostile.hunter;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xratedjunior.betterdefaultbiomes.configuration.entity.HunterConfig;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class HeadHunterEntity extends HunterEntity {

	public HeadHunterEntity(EntityType<? extends HeadHunterEntity> headHunterEntity, Level world) {
		super(headHunterEntity, world);
		this.xpReward = 10; // Increase XP reward. (Default Monster = 5)
	}

	/*********************************************************** Goals ********************************************************/

	@Override
	public void reassessCombatGoal() {
		this.reassessWeaponGoal(HunterConfig.head_hunter_firing_speed_hard.get(), HunterConfig.head_hunter_firing_speed.get());
	}

	/*********************************************************** Attack AI ********************************************************/

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem)));
		AbstractArrow abstractarrowentity = this.getArrow(itemstack, distanceFactor);
		if (this.getMainHandItem().getItem() instanceof BowItem) {
			abstractarrowentity = ((BowItem) this.getMainHandItem().getItem()).customArrow(abstractarrowentity);
		}
		double dx = target.getX() - this.getX();
		double targety = target.getEyeY();
		double dy = targety - this.getEyeY();
		double dz = target.getZ() - this.getZ();
		float velocity = 3.0F; // Default: 1.6F
		abstractarrowentity.shoot(dx, dy, dz, velocity, (float) (14 - this.level.getDifficulty().getId() * 4));
		this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level.addFreshEntity(abstractarrowentity);
	}

	/*********************************************************** Attributes ********************************************************/

	public static AttributeSupplier.Builder createHeadHunterAttributes() {
		return HeadHunterEntity.createHunterAttributes().add(Attributes.MOVEMENT_SPEED, 0.4D);
	}

	/*********************************************************** Spawn Equipment ********************************************************/

	@Override
	protected void setDefaultEquipmentAndEnchants(DifficultyInstance difficulty) {
		super.setDefaultEquipmentAndEnchants(difficulty);
		this.getItemBySlot(EquipmentSlot.MAINHAND).setHoverName(Component.translatable("equipment.betterdefaultbiomes.head_hunter_bow").withStyle(ChatFormatting.GREEN));
	}
}