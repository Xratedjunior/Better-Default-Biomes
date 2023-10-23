package xratedjunior.betterdefaultbiomes.entity.client.model.passive;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.entity.client.model.util.BDBAgeableModel;
import xratedjunior.betterdefaultbiomes.entity.client.model.util.BDBModelPart;
import xratedjunior.betterdefaultbiomes.entity.passive.ZebraEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class ZebraModel<E extends ZebraEntity> extends BDBAgeableModel<E> {
	private final BDBModelPart body_rotation_point;
	private final BDBModelPart neck_rotation_point;
	private final BDBModelPart head_rotation_point;
	private final BDBModelPart mouth_bottom_rotation_point;
	private final BDBModelPart front_right_leg_rotation_point;
	private final BDBModelPart front_right_leg_knee_rotation_point;
	private final BDBModelPart front_left_leg_rotation_point;
	private final BDBModelPart front_left_leg_knee_rotation_point;
	private final BDBModelPart back_right_leg_rotation_point;
	private final BDBModelPart back_right_leg_knee_rotation_point;
	private final BDBModelPart back_left_leg_rotation_point;
	private final BDBModelPart back_left_leg_knee_rotation_point;
	private final BDBModelPart tail_rotation_point;
	private final BDBModelPart tail_bottom_rotation_point;

	public ZebraModel(ModelPart root) {
		this.body_rotation_point = this.createModelPart(this, root, "body_rotation_point");
		this.neck_rotation_point = this.createModelPart(this, this.body_rotation_point, "neck_rotation_point");
		this.head_rotation_point = this.createModelPart(this, this.neck_rotation_point, "head_rotation_point");
		this.mouth_bottom_rotation_point = this.createModelPart(this, this.head_rotation_point, "mouth_bottom_rotation_point");
		this.tail_rotation_point = this.createModelPart(this, this.body_rotation_point, "tail_rotation_point");
		this.tail_bottom_rotation_point = this.createModelPart(this, this.tail_rotation_point, "tail_bottom_rotation_point");
		this.front_right_leg_rotation_point = this.createModelPart(this, root, "front_right_leg_rotation_point");
		this.front_right_leg_knee_rotation_point = this.createModelPart(this, this.front_right_leg_rotation_point, "front_right_leg_knee_rotation_point");
		this.front_left_leg_rotation_point = this.createModelPart(this, root, "front_left_leg_rotation_point");
		this.front_left_leg_knee_rotation_point = this.createModelPart(this, this.front_left_leg_rotation_point, "front_left_leg_knee_rotation_point");
		this.back_right_leg_rotation_point = this.createModelPart(this, root, "back_right_leg_rotation_point");
		this.back_right_leg_knee_rotation_point = this.createModelPart(this, this.back_right_leg_rotation_point, "back_right_leg_knee_rotation_point");
		this.back_left_leg_rotation_point = this.createModelPart(this, root, "back_left_leg_rotation_point");
		this.back_left_leg_knee_rotation_point = this.createModelPart(this, this.back_left_leg_rotation_point, "back_left_leg_knee_rotation_point");
		//Saves the model default values
		this.setDefaultModelValues();
	}

	/**
	 * Create model for baking
	 */
	public static LayerDefinition createBodyLayer(float scaleIn) {
		float textureOverlapFix = 0.05f;

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body_rotation_point = partdefinition.addOrReplaceChild("body_rotation_point", CubeListBuilder.create().texOffs(1, 96).addBox(-5.0F, -5.0F, -11.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(textureOverlapFix + scaleIn)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition neck_rotation_point = body_rotation_point.addOrReplaceChild("neck_rotation_point", CubeListBuilder.create().texOffs(0, 96).addBox(-2.0F, -11.4837F, -2.6711F, 4.0F, 12.0F, 7.0F, new CubeDeformation(scaleIn)).texOffs(44, 96).addBox(-1.0F, -17.4837F, 2.3289F, 2.0F, 17.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offsetAndRotation(0.0F, 1.0F, -7.0F, 0.6545F, 0.0F, 0.0F));

		PartDefinition head_rotation_point = neck_rotation_point.addOrReplaceChild("head_rotation_point", CubeListBuilder.create().texOffs(16, 97).addBox(-2.5F, -9.0F, 3.99F, 2.0F, 3.0F, 1.0F, new CubeDeformation(scaleIn)).texOffs(0, 97).addBox(0.5F, -9.0F, 3.99F, 2.0F, 3.0F, 1.0F, new CubeDeformation(scaleIn)).texOffs(0, 82).addBox(-3.0F, -6.0F, -2.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(scaleIn)).texOffs(26, 78).addBox(-2.0F, -6.0F, -7.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(scaleIn)), PartPose.offset(0.0F, -10.4837F, -0.6711F));

		head_rotation_point.addOrReplaceChild("mouth_bottom_rotation_point", CubeListBuilder.create().texOffs(27, 87).addBox(-1.5F, 0.075F, -5.25F, 3.0F, 2.0F, 5.0F, new CubeDeformation(scaleIn)), PartPose.offset(0.0F, -3.325F, -1.25F));

		PartDefinition tail_rotation_point = body_rotation_point.addOrReplaceChild("tail_rotation_point", CubeListBuilder.create().texOffs(81, 106).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(scaleIn)), PartPose.offsetAndRotation(0.0F, -4.5F, 11.0F, 0.4363F, 0.0F, 0.0F));

		tail_rotation_point.addOrReplaceChild("tail_bottom_rotation_point", CubeListBuilder.create().texOffs(78, 113).addBox(-1.5F, -0.4459F, -1.7559F, 3.0F, 11.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offsetAndRotation(0.0F, 2.7926F, -0.3359F, -0.2182F, 0.0F, 0.0F));

		PartDefinition front_right_leg_rotation_point = partdefinition.addOrReplaceChild("front_right_leg_rotation_point", CubeListBuilder.create().texOffs(112, 98).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offset(-3.5F, 11.0F, -9.5F));

		front_right_leg_rotation_point.addOrReplaceChild("front_right_leg_knee_rotation_point", CubeListBuilder.create().texOffs(114, 111).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(scaleIn)).texOffs(112, 121).addBox(-2.0F, 5.5F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offset(0.0F, 4.5F, 0.0F));

		PartDefinition front_left_leg_rotation_point = partdefinition.addOrReplaceChild("front_left_leg_rotation_point", CubeListBuilder.create().texOffs(95, 98).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offset(3.5F, 11.0F, -9.5F));

		front_left_leg_rotation_point.addOrReplaceChild("front_left_leg_knee_rotation_point", CubeListBuilder.create().texOffs(97, 111).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(scaleIn)).texOffs(95, 121).addBox(-2.0F, 5.5F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offset(0.0F, 4.5F, 0.0F));

		PartDefinition back_right_leg_rotation_point = partdefinition.addOrReplaceChild("back_right_leg_rotation_point", CubeListBuilder.create().texOffs(112, 66).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offset(-3.5F, 11.0F, 9.5F));

		back_right_leg_rotation_point.addOrReplaceChild("back_right_leg_knee_rotation_point", CubeListBuilder.create().texOffs(114, 79).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(scaleIn)).texOffs(112, 89).addBox(-2.0F, 5.5F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offset(0.0F, 4.5F, 0.0F));

		PartDefinition back_left_leg_rotation_point = partdefinition.addOrReplaceChild("back_left_leg_rotation_point", CubeListBuilder.create().texOffs(95, 66).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offset(3.5F, 11.0F, 9.5F));

		back_left_leg_rotation_point.addOrReplaceChild("back_left_leg_knee_rotation_point", CubeListBuilder.create().texOffs(97, 79).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(scaleIn)).texOffs(95, 89).addBox(-2.0F, 5.5F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(scaleIn)), PartPose.offset(0.0F, 4.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.young) {
			matrixStack.pushPose();
			matrixStack.scale(0.6F, 0.6F, 0.6F);
			matrixStack.translate(0.0D, 1.0D, 0.0D);
			this.body_rotation_point.getModelPart().render(matrixStack, buffer, packedLight, packedOverlay);
			matrixStack.popPose();
			matrixStack.pushPose();
			matrixStack.scale(0.6F, 0.6F, 0.6F);
			matrixStack.translate(0.0D, 1.0D, 0.0D);
			ImmutableList.of(this.back_right_leg_rotation_point, this.back_left_leg_rotation_point, this.front_right_leg_rotation_point, this.front_left_leg_rotation_point).forEach((modelParts) -> {
				modelParts.getModelPart().render(matrixStack, buffer, packedLight, packedOverlay);
			});
			matrixStack.popPose();
		} else {
			ImmutableList.of(this.body_rotation_point, this.front_right_leg_rotation_point, this.front_left_leg_rotation_point, this.back_right_leg_rotation_point, this.back_left_leg_rotation_point).forEach((modelPart) -> {
				modelPart.getModelPart().render(matrixStack, buffer, packedLight, packedOverlay);
			});
		}
	}

	/*
	 * Animations for this model
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void prepareMobModel(E entity, float limbSwing, float limbSwingAmount, float partialTick) {
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
		this.revertToDefaultModelValues();

		float ticks = (float) entity.tickCount + partialTick;

		float yawOffset = Mth.rotlerp(entity.yBodyRotO, entity.yBodyRot, partialTick);
		float yawRotation = Mth.rotlerp(entity.yHeadRotO, entity.yHeadRot, partialTick);
		float netHeadYaw = yawRotation - yawOffset;

		float headPitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot()); //Radials
		float headPitchDegrees = headPitch * ((float) Math.PI / 180F);
		if (limbSwingAmount > 0.2F) {
			headPitchDegrees += Mth.cos(limbSwing * 0.4F) * 0.15F * limbSwingAmount;
		}

		//Makes the Head Rotate where the Entity is Looking
		this.neck_rotation_point.getModelPart().yRot = (netHeadYaw * ((float) Math.PI / 180)) / 2;
		this.neck_rotation_point.getModelPart().xRot = this.neck_rotation_point.defaultRotateAngleX + (headPitchDegrees * ((float) Math.PI / 180));
		this.head_rotation_point.getModelPart().yRot = (netHeadYaw * ((float) Math.PI / 180)) / 8;
		this.head_rotation_point.getModelPart().zRot = (netHeadYaw * ((float) Math.PI / 180)) / 8;

		//Entity is moving
		if (this.isWalking(entity)) {
			this.walkingAnimation(entity, limbSwing, limbSwingAmount, partialTick, netHeadYaw, headPitchDegrees);
		}
		//Entity is standing still
		else {
			limbSwing = entity.tickCount;
			limbSwingAmount = 1.0F;
			float globalSpeed = 0.1F;
			float globalDegree = 0.4F;
			float globalHeight = 1.0F;

			//Body
			this.bounce(this.body_rotation_point, 0.75F * globalSpeed, (0.2F * globalHeight), 0.0F, limbSwing, limbSwingAmount);
			this.rotateXPositive(this.tail_rotation_point, globalSpeed * 0.25F, 0.2F * globalDegree, false, 0.5F, limbSwing, limbSwingAmount);
			this.rotateZ(this.tail_rotation_point, globalSpeed, 0.2F * globalDegree, true, 0.5F, limbSwing, limbSwingAmount);

			//Head
			this.rotateX(this.neck_rotation_point, globalSpeed, 0.1F * globalDegree, false, 0.0F, limbSwing, limbSwingAmount);
			this.rotateXPositive(this.mouth_bottom_rotation_point, globalSpeed * 0.25F, 0.8F * globalDegree, false, 0.5F, limbSwing, limbSwingAmount);

			//Grass eating animation
			float startEating = entity.getEatingAmount(partialTick);
			if (startEating > 0) {
				float isEating = entity.getMouthOpennessAngle(partialTick);
				float grassEatingAngle = 2.1F;
				float setDefaultRotation = (1.0F - startEating) * ((this.neck_rotation_point.defaultRotateAngleX) + headPitchDegrees + isEating * this.doNeckWobble(ticks));

				this.neck_rotation_point.getModelPart().xRot = startEating * (grassEatingAngle + this.doNeckWobble(ticks)) + setDefaultRotation;
				if (startEating == 1) {
					this.rotateXPositive(this.mouth_bottom_rotation_point, 5 * globalSpeed, 0.5F * globalDegree, false, 0.0F, limbSwing, limbSwingAmount);
				}
			}

			//Swing tail animation
			boolean swingTail = entity.tailCounter != 0;
			if (swingTail) {
				this.rotateXPositive(this.tail_rotation_point, 10F * globalSpeed, 0.2F * globalDegree, false, 0.5F, limbSwing, limbSwingAmount);
				this.rotateZ(this.tail_rotation_point, 10F * globalSpeed, 0.8F * globalDegree, true, 0.5F, limbSwing, limbSwingAmount);

				this.rotateX(this.tail_bottom_rotation_point, 10F * globalSpeed, 0.2F * globalDegree, false, 0.5F, limbSwing, limbSwingAmount);
				this.rotateZ(this.tail_bottom_rotation_point, 10F * globalSpeed, 0.8F * globalDegree, true, 0.5F, limbSwing, limbSwingAmount);
			}
		}
	}

	private float doNeckWobble(float ticks) {
		return Mth.sin(ticks) * 0.05F;
	}

	/*
	 * Walking animation for this mob
	 */
	public void walkingAnimation(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float globalSpeed = 0.8F;
		float globalDegree = 0.5F;
		float globalHeight = 1;

		float halfSpeed = 0.5F;
		float pi = (float) Math.PI;

		//Body
		this.bounce(this.body_rotation_point, globalSpeed, (1.0F * globalHeight), 0.0F, limbSwing, limbSwingAmount);
		this.rotateXPositive(this.tail_rotation_point, globalSpeed * 0.25F, 0.8F * globalDegree, false, 0.5F, limbSwing, limbSwingAmount);
		this.rotateZ(this.tail_rotation_point, globalSpeed, 0.8F * globalDegree, true, 0.5F, limbSwing, limbSwingAmount);

		//Head
		this.rotateX(this.neck_rotation_point, globalSpeed, 0.2F * globalDegree, false, 0.0F, limbSwing, limbSwingAmount);
		this.rotateXPositive(this.mouth_bottom_rotation_point, globalSpeed * 0.25F, 0.8F * globalDegree, false, 0.5F, limbSwing, limbSwingAmount);
		//		rotateX(this.ear, globalSpeed, 0.2F * globalDegree, false, 0.0F, limbSwing, limbSwingAmount);

		//Legs
		this.rotateX(this.front_left_leg_rotation_point, globalSpeed, 1.4F * globalDegree, false, 0.0F, limbSwing, limbSwingAmount);
		this.rotateX(this.back_right_leg_rotation_point, globalSpeed, 1.4F * globalDegree, false, -0.5F * pi, limbSwing, limbSwingAmount);
		this.rotateX(this.front_right_leg_rotation_point, globalSpeed, 1.4F * globalDegree, false, -1.0F * pi, limbSwing, limbSwingAmount);
		this.rotateX(this.back_left_leg_rotation_point, globalSpeed, 1.4F * globalDegree, false, -1.5F * pi, limbSwing, limbSwingAmount);

		float kneeRotation = 1.4F + 0;
		this.rotateXPositive(this.front_left_leg_knee_rotation_point, globalSpeed * halfSpeed, kneeRotation * globalDegree, true, -0.1F * pi, limbSwing, limbSwingAmount);
		this.rotateXPositive(this.back_right_leg_knee_rotation_point, globalSpeed * halfSpeed, kneeRotation * globalDegree, true, -0.35F * pi, limbSwing, limbSwingAmount);
		this.rotateXPositive(this.front_right_leg_knee_rotation_point, globalSpeed * halfSpeed, kneeRotation * globalDegree, true, -0.6F * pi, limbSwing, limbSwingAmount);
		this.rotateXPositive(this.back_left_leg_knee_rotation_point, globalSpeed * halfSpeed, kneeRotation * globalDegree, true, -0.85F * pi, limbSwing, limbSwingAmount);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		// TODO Auto-generated method stub
		return null;
	}
}