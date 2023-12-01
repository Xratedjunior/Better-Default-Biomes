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
import xratedjunior.betterdefaultbiomes.entity.passive.FrogEntity;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
@OnlyIn(Dist.CLIENT)
public class FrogModel<E extends FrogEntity> extends BDBAgeableModel<E> {
	private final BDBModelPart body_rp;
	private final BDBModelPart back_right_leg_rotation_point;
	private final BDBModelPart back_right_foot_rotation_point;
	private final BDBModelPart back_left_leg_rotation_point;
	private final BDBModelPart back_left_foot_rotation_point;
	private final BDBModelPart front_right_leg_rotation_point;
	private final BDBModelPart front_left_leg_rotation_point;
	private final BDBModelPart head_rotation_point;
	private final BDBModelPart tongue_rotation_point;
	private final BDBModelPart mouth_rotation_point;

	private float jumpRotation;

	/**
	 * Load model
	 */
	public FrogModel(ModelPart context) {
		super();
		this.body_rp = new BDBModelPart(this, context.getChild("body_rp"));
		this.back_right_leg_rotation_point = new BDBModelPart(this, context.getChild("back_right_leg_rotation_point"));
		this.back_right_foot_rotation_point = new BDBModelPart(this, this.back_right_leg_rotation_point.getModelPart().getChild("back_right_foot_rotation_point"));
		this.back_left_leg_rotation_point = new BDBModelPart(this, context.getChild("back_left_leg_rotation_point"));
		this.back_left_foot_rotation_point = new BDBModelPart(this, this.back_left_leg_rotation_point.getModelPart().getChild("back_left_foot_rotation_point"));
		this.front_right_leg_rotation_point = new BDBModelPart(this, context.getChild("front_right_leg_rotation_point"));
		this.front_left_leg_rotation_point = new BDBModelPart(this, context.getChild("front_left_leg_rotation_point"));
		this.head_rotation_point = new BDBModelPart(this, context.getChild("head_rotation_point"));
		this.tongue_rotation_point = new BDBModelPart(this, this.head_rotation_point.getModelPart().getChild("tongue_rotation_point"));
		this.mouth_rotation_point = new BDBModelPart(this, this.head_rotation_point.getModelPart().getChild("mouth_rotation_point"));
		this.setDefaultModelValues();
	}

	/**
	 * Create model for baking
	 */
	public static LayerDefinition createBodyLayer(float scaleIn) {
		CubeDeformation scale = new CubeDeformation(scaleIn);

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("body_rp", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 6.0F, 10.0F, scale), PartPose.offsetAndRotation(0.0F, 19.0094F, 0.6577F, -0.4363F, 0.0F, 0.0F));

		PartDefinition back_right_leg_rotation_point = partdefinition.addOrReplaceChild("back_right_leg_rotation_point", CubeListBuilder.create().texOffs(47, 24).addBox(-2.5F, -2.3095F, -1.8752F, 2.0F, 4.0F, 4.0F, scale), PartPose.offsetAndRotation(-1.5F, 21.7095F, 3.1752F, -0.4363F, 0.0F, 0.0F));

		back_right_leg_rotation_point.addOrReplaceChild("back_right_foot_rotation_point", CubeListBuilder.create().texOffs(47, 17).addBox(-1.0F, -0.5F, -4.0F, 2.0F, 1.0F, 5.0F, scale), PartPose.offsetAndRotation(-1.5F, 1.7197F, 0.4758F, 0.48F, 0.3491F, 0.1745F));

		PartDefinition back_left_leg_rotation_point = partdefinition.addOrReplaceChild("back_left_leg_rotation_point", CubeListBuilder.create().texOffs(34, 24).addBox(-2.5F, -2.3095F, -1.8752F, 2.0F, 4.0F, 4.0F, scale), PartPose.offsetAndRotation(4.5F, 21.7095F, 3.1752F, -0.4363F, 0.0F, 0.0F));

		back_left_leg_rotation_point.addOrReplaceChild("back_left_foot_rotation_point", CubeListBuilder.create().texOffs(32, 17).addBox(-1.0F, -0.5F, -4.0F, 2.0F, 1.0F, 5.0F, scale), PartPose.offsetAndRotation(-1.5F, 1.7197F, 0.4758F, 0.48F, -0.3491F, -0.1745F));

		PartDefinition head_rotation_point = partdefinition.addOrReplaceChild("head_rotation_point", CubeListBuilder.create().texOffs(22, 7).addBox(-3.5F, -6.0F, -2.5F, 2.0F, 2.0F, 2.0F, scale).texOffs(2, 7).addBox(1.5F, -6.0F, -2.5F, 2.0F, 2.0F, 2.0F, scale).texOffs(5, 6).addBox(-2.5F, -5.0F, -5.0F, 5.0F, 3.0F, 6.0F, scale), PartPose.offset(0.0F, 19.5F, -4.0F));

		//		head_rotation_point.addOrReplaceChild("attack_tongue_rotation_point", CubeListBuilder.create().texOffs(21, 10).addBox(-1.0F, 0.0F, -10.5F, 2.0F, 0.0F, 11.0F, scale), PartPose.offsetAndRotation(0.0F, -2.0F, -1.25F, 0.1309F, 0.0F, 0.0F));

		//		head_rotation_point.addOrReplaceChild("attack_mouth_rotation_point", CubeListBuilder.create().texOffs(37, 10).addBox(-2.0F, -0.5F, -3.5F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.1F)).texOffs(37, 10).addBox(-2.0F, -0.5F, -3.5F, 4.0F, 1.0F, 5.0F, scale), PartPose.offsetAndRotation(0.0F, -1.5F, -1.25F, 0.3054F, 0.0F, 0.0F));

		head_rotation_point.addOrReplaceChild("tongue_rotation_point", CubeListBuilder.create().texOffs(21, 10).addBox(-1.0F, 0.0F, -3.5F, 2.0F, 0.0F, 11.0F, scale), PartPose.offset(0.0F, -2.0F, -1.25F));

		head_rotation_point.addOrReplaceChild("mouth_rotation_point", CubeListBuilder.create().texOffs(37, 10).addBox(-2.0F, -0.5F, -3.5F, 4.0F, 1.0F, 5.0F, scale), PartPose.offset(0.0F, -1.5F, -1.25F));

		//		head_rotation_point.addOrReplaceChild("big_mouth_rotation_point", CubeListBuilder.create().texOffs(37, 10).addBox(-2.0F, -0.5F, -3.5F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -1.5F, -1.25F));

		partdefinition.addOrReplaceChild("front_right_leg_rotation_point", CubeListBuilder.create().texOffs(1, 18).addBox(-0.25F, 0.0F, 0.25F, 2.0F, 5.0F, 2.0F, scale), PartPose.offset(-3.0F, 19.0F, -5.0F));

		partdefinition.addOrReplaceChild("front_left_leg_rotation_point", CubeListBuilder.create().texOffs(23, 18).addBox(-0.25F, 0.0F, 0.25F, 2.0F, 5.0F, 2.0F, scale), PartPose.offset(1.5F, 19.0F, -5.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
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
			ImmutableList.of(this.body_rp, this.head_rotation_point, this.back_right_leg_rotation_point, this.back_left_leg_rotation_point, this.front_right_leg_rotation_point, this.front_left_leg_rotation_point).forEach((modelPart) -> {
				modelPart.getModelPart().render(matrixStack, buffer, packedLight, packedOverlay);
			});
			matrixStack.popPose();
		} else {
			ImmutableList.of(this.body_rp, this.head_rotation_point, this.back_right_leg_rotation_point, this.back_left_leg_rotation_point, this.front_right_leg_rotation_point, this.front_left_leg_rotation_point).forEach((modelPart) -> {
				modelPart.getModelPart().render(matrixStack, buffer, packedLight, packedOverlay);
			});
		}
	}

	/**
	 * Animations for this model
	 */
	@Override
	public void prepareMobModel(E entity, float limbSwing, float limbSwingAmount, float partialTick) {
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
		this.revertToDefaultModelValues();
		float pi = (float) Math.PI;
		float radian = pi / 180;

		float yawOffset = this.rotlerp(entity.yBodyRotO, entity.yBodyRot, partialTick);
		float yawRotation = this.rotlerp(entity.yHeadRotO, entity.yHeadRot, partialTick);
		float netHeadYaw = yawRotation - yawOffset;

		float headPitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot()); //Radials
		float headPitchDegrees = headPitch * ((float) Math.PI / 180F);
		if (limbSwingAmount > 0.2F) {
			headPitchDegrees += Mth.cos(limbSwing * 0.4F) * 0.15F * limbSwingAmount;
		}

		//Makes the Head Rotate where the Entity is Looking
		this.head_rotation_point.getModelPart().yRot += (netHeadYaw * radian) / 4;
		this.head_rotation_point.getModelPart().xRot += (headPitchDegrees * radian);

		//When attacking
		float attackAnimation;
		attackAnimation = Mth.sin(entity.getAttackCompletion(partialTick) * pi);
		this.tongue_rotation_point.getModelPart().z += (attackAnimation * -5.0F);
		this.tongue_rotation_point.getModelPart().xRot += (attackAnimation * 15.0F) * radian;
		this.mouth_rotation_point.getModelPart().xRot += (attackAnimation * 20.0F) * radian;

		//Entity is moving
		if (this.isWalking(entity)) {

			jumpRotation = Mth.sin(entity.getJumpCompletion(partialTick) * pi);

			//Head
			this.head_rotation_point.getModelPart().xRot += (jumpRotation * -5.0F) * radian;
			this.mouth_rotation_point.getModelPart().xRot += (jumpRotation * 5.0F) * radian;

			//Legs
			this.back_left_leg_rotation_point.getModelPart().xRot += (jumpRotation * 50.0F) * radian;
			this.back_right_leg_rotation_point.getModelPart().xRot += (jumpRotation * 50.0F) * radian;
			this.back_left_foot_rotation_point.getModelPart().xRot += jumpRotation * 50.0F * radian;
			this.back_right_foot_rotation_point.getModelPart().xRot += jumpRotation * 50.0F * radian;

			this.front_left_leg_rotation_point.getModelPart().xRot += (jumpRotation * -40.0F) * radian;
			this.front_right_leg_rotation_point.getModelPart().xRot += (jumpRotation * -40.0F) * radian;

			//			float scaling = jumpRotation*0.8F;
			////			this.body_rp.setScale(scaling + 1F, 1F, scaling + 1F);
			//			this.body_rp.scaleX = 1F;
			//			this.body_rp.scaleX += scaling;

			//Entity is standing still
		} else {
			limbSwing = entity.tickCount;
			limbSwingAmount = 1.0F;

			float globalSpeed = 0.1F;
			float globalDegree = 0.4F;
			float globalHeight = 1.0F;

			//Body
			bounce(this.body_rp, 0.75F * globalSpeed, (0.1F * globalHeight), 0.0F, limbSwing, limbSwingAmount);

			//Head
			rotateX(this.head_rotation_point, 0.75F * globalSpeed, 0.05F * globalDegree, false, 0.0F, limbSwing, limbSwingAmount);
			rotateXPositive(this.mouth_rotation_point, globalSpeed * 0.25F, 0.08F * globalDegree, false, 0.5F, limbSwing, limbSwingAmount);
		}
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
