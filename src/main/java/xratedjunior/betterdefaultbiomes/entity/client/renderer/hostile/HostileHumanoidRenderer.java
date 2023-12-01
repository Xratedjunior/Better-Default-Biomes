package xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.entity.client.model.hostile.HostileHumanoidModel;
import xratedjunior.betterdefaultbiomes.entity.hostile.AbstractHostileHumanoid;

/**
 * MINECRAFT REFERENCE: {@link PlayerRenderer}
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
@OnlyIn(Dist.CLIENT)
public abstract class HostileHumanoidRenderer<E extends AbstractHostileHumanoid> extends HumanoidMobRenderer<E, HostileHumanoidModel<E>> {

	public HostileHumanoidRenderer(EntityRendererProvider.Context context, float shadowSize) {
		super(context, new HostileHumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), shadowSize);
		this.addLayer(new HumanoidArmorLayer<>(this, new HostileHumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HostileHumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	public HostileHumanoidRenderer(EntityRendererProvider.Context context) {
		this(context, 0.5f);
	}

	@Override
	public abstract ResourceLocation getTextureLocation(E entity);

	/**
	 * MINECRAFT REFERENCE: {@link PlayerRenderer#render}
	 */
	public void render(E p_117788_, float p_117789_, float p_117790_, PoseStack p_117791_, MultiBufferSource p_117792_, int p_117793_) {
		this.setModelProperties(p_117788_);
		super.render(p_117788_, p_117789_, p_117790_, p_117791_, p_117792_, p_117793_);
	}

	/**
	 * MINECRAFT REFERENCE: {@link PlayerRenderer#setModelProperties}
	 */
	private void setModelProperties(E entity) {
		HostileHumanoidModel<E> entityModel = this.getModel();
		HumanoidModel.ArmPose armPoseMainHand = getArmPose(entity, InteractionHand.MAIN_HAND);
		HumanoidModel.ArmPose armPoseOffHand = getArmPose(entity, InteractionHand.OFF_HAND);

		if (armPoseMainHand.isTwoHanded()) {
			armPoseOffHand = entity.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
		}

		if (entity.getMainArm() == HumanoidArm.RIGHT) {
			entityModel.rightArmPose = armPoseMainHand;
			entityModel.leftArmPose = armPoseOffHand;
		} else {
			// Left arm is main hand
			entityModel.rightArmPose = armPoseOffHand;
			entityModel.leftArmPose = armPoseMainHand;
		}

	}

	/**
	 * MINECRAFT REFERENCE: {@link PlayerRenderer#getArmPose}
	 */
	private static HumanoidModel.ArmPose getArmPose(AbstractHostileHumanoid entity, InteractionHand interactionHand) {
		AbstractHostileHumanoid.ArmPose entityArmPose = entity.getArmPose();
		if (entityArmPose == AbstractHostileHumanoid.ArmPose.BOW_AND_ARROW) {
			// Set Bow and Arrow pose.
			return HumanoidModel.ArmPose.BOW_AND_ARROW;
		} else if (entityArmPose == AbstractHostileHumanoid.ArmPose.CROSSBOW_CHARGE) {
			// Is charging the Crossbow.
			return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
		} else if (entityArmPose == AbstractHostileHumanoid.ArmPose.CROSSBOW_HOLD) {
			// Is holding a Crossbow.
			return HumanoidModel.ArmPose.CROSSBOW_HOLD;
		}

		ItemStack itemstack = entity.getItemInHand(interactionHand);
		if (itemstack.isEmpty()) {
			return HumanoidModel.ArmPose.EMPTY;
		} else {
			if (entity.getUsedItemHand() == interactionHand && entity.getUseItemRemainingTicks() > 0) {
				UseAnim useanim = itemstack.getUseAnimation();
				if (useanim == UseAnim.BLOCK) {
					// Shield blocking
					return HumanoidModel.ArmPose.BLOCK;
				}

				if (useanim == UseAnim.SPEAR) {
					return HumanoidModel.ArmPose.THROW_SPEAR;
				}

				if (useanim == UseAnim.SPYGLASS) {
					return HumanoidModel.ArmPose.SPYGLASS;
				}
			}

			return HumanoidModel.ArmPose.ITEM;
		}
	}
}