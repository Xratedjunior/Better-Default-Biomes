package xratedjunior.betterdefaultbiomes.entity.client.model.hostile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.entity.hostile.AbstractHostileHumanoid;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class HostileHumanoidModel<E extends AbstractHostileHumanoid> extends HumanoidModel<E> {

	public HostileHumanoidModel(ModelPart modelPart) {
		super(modelPart);
	}

	/**
	 * Most animations are already taken care off by Minecraft. Add special animations here if wanted.
	 * MINECRAFT REFERENCE: Check out {@link IllagerModel#setupAnim}
	 */
	@Override
	public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		AbstractHostileHumanoid.ArmPose entityArmPose = entity.getArmPose();
		if (entityArmPose == AbstractHostileHumanoid.ArmPose.ATTACKING) {
			// Nothing special for now, Minecraft handles arm swinging.
		}
	}

	/**
	 * Change if slim model is used again.
	 * See: {@link PlayerModel#translateToHand} & {@link SkeletonModel#translateToHand}
	 */
	@Override
	public void translateToHand(HumanoidArm handWithItem, PoseStack poseStack) {
		super.translateToHand(handWithItem, poseStack);
	}
}