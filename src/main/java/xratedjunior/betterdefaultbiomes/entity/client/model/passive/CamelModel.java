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
import xratedjunior.betterdefaultbiomes.entity.passive.CamelEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class CamelModel<E extends CamelEntity> extends BDBAgeableModel<E> {
	private final BDBModelPart neck;
	private final BDBModelPart head_rotation;
	private final BDBModelPart body;
	private final BDBModelPart legBackLeft;
	private final BDBModelPart legBackRight;
	private final BDBModelPart legFrontLeft;
	private final BDBModelPart legFrontRight;
	private final BDBModelPart chest_left;
	private final BDBModelPart chest_right;

	/**
	 * Load model
	 */
	public CamelModel(ModelPart root) {
		super(true, 21, 3.52f, 0, 0, 0);
		this.neck = new BDBModelPart(this, root.getChild("neck"));
		this.head_rotation = new BDBModelPart(this, this.neck.getModelPart().getChild("head_rotation"));
		this.body = new BDBModelPart(this, root.getChild("body"));
		this.legBackLeft = new BDBModelPart(this, root.getChild("legBackLeft"));
		this.legBackRight = new BDBModelPart(this, root.getChild("legBackRight"));
		this.legFrontLeft = new BDBModelPart(this, root.getChild("legFrontLeft"));
		this.legFrontRight = new BDBModelPart(this, root.getChild("legFrontRight"));
		this.chest_left = new BDBModelPart(this, root.getChild("chest_left"));
		this.chest_right = new BDBModelPart(this, root.getChild("chest_right"));
	}

	/**
	 * Create model for baking
	 */
	public static LayerDefinition createBodyLayer(float scaleIn) {
		CubeDeformation scale = new CubeDeformation(scaleIn);

		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(35, 1).addBox(-2.0F, -11.0F, -7.0F, 4.0F, 11.0F, 5.0F, scale), PartPose.offset(0.0F, 7.0F, -8.0F));

		neck.addOrReplaceChild("neck_bottom", CubeListBuilder.create().texOffs(58, 0).addBox(-3.5F, -1.0F, -3.0F, 7.0F, 10.0F, 6.0F, scale), PartPose.offsetAndRotation(0.0F, -1.0F, -6.0F, 1.5272F, 0.0F, 0.0F));

		neck.addOrReplaceChild("rein", CubeListBuilder.create().texOffs(30, 34).addBox(0.4F, -2.0F, 1.0F, 5.0F, 2.0F, 9.0F, scale), PartPose.offset(-2.9F, -7.0F, -4.0F));

		neck.addOrReplaceChild("head_rotation", CubeListBuilder.create().texOffs(18, 54).addBox(-4.0F, -5.0F, -1.0F, 7.0F, 5.0F, 5.0F, scale).texOffs(50, 53).addBox(-3.0F, -4.25F, -6.0F, 5.0F, 4.0F, 7.0F, scale).texOffs(0, 0).addBox(1.5F, -6.0F, 1.0F, 1.0F, 1.0F, 3.0F, scale).texOffs(0, 7).addBox(-3.5F, -6.0F, 1.0F, 1.0F, 1.0F, 3.0F, scale), PartPose.offset(0.5F, -9.0F, -5.0F));

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(64, 0).addBox(-6.0F, -3.0F, -10.0F, 12.0F, 10.0F, 20.0F, scale).texOffs(104, 33).addBox(-3.0F, -5.0F, -9.0F, 6.0F, 2.0F, 6.0F, scale).texOffs(65, 38).addBox(-6.5F, -3.5F, -3.0F, 13.0F, 2.0F, 6.0F, scale).texOffs(104, 43).addBox(-3.0F, -5.0F, 3.0F, 6.0F, 2.0F, 6.0F, scale), PartPose.offset(0.0F, 5.0F, 2.0F));

		partdefinition.addOrReplaceChild("legBackLeft", CubeListBuilder.create().texOffs(112, 0).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 14.0F, 4.0F, scale), PartPose.offset(3.5F, 10.0F, 6.0F));

		partdefinition.addOrReplaceChild("legBackRight", CubeListBuilder.create().texOffs(112, 0).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 14.0F, 4.0F, scale), PartPose.offset(-3.5F, 10.0F, 6.0F));

		partdefinition.addOrReplaceChild("legFrontLeft", CubeListBuilder.create().texOffs(112, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, scale), PartPose.offset(3.5F, 10.0F, -5.0F));

		partdefinition.addOrReplaceChild("legFrontRight", CubeListBuilder.create().texOffs(112, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, scale), PartPose.offset(-3.5F, 10.0F, -5.0F));

		partdefinition.addOrReplaceChild("chest_left", CubeListBuilder.create().texOffs(83, 53).addBox(-1.5F, -4.0F, -1.5F, 8.0F, 8.0F, 3.0F, scale), PartPose.offsetAndRotation(7.0F, 6.5F, 8.5F, 0.0F, 1.5708F, 0.0F));

		partdefinition.addOrReplaceChild("chest_right", CubeListBuilder.create().texOffs(106, 53).addBox(-6.5F, -4.5F, -1.5F, 8.0F, 8.0F, 3.0F, scale), PartPose.offsetAndRotation(-7.0F, 7.0F, 3.5F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.neck.getModelPart().xRot = (float) ((headPitch * 0.6) * ((float) Math.PI / 180F));
		this.neck.getModelPart().yRot = (float) ((netHeadYaw * 0.6) * ((float) Math.PI / 180F));
		this.head_rotation.getModelPart().xRot = (float) (headPitch * 0.4) * ((float) Math.PI / 180F);
		this.head_rotation.getModelPart().yRot = (float) (netHeadYaw * 0.4) * ((float) Math.PI / 180F);
		this.legBackRight.getModelPart().xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.legBackLeft.getModelPart().xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontRight.getModelPart().xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontLeft.getModelPart().xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		boolean renderChests = !entity.isBaby() && entity.hasChest();
		this.chest_left.getModelPart().visible = renderChests;
		this.chest_right.getModelPart().visible = renderChests;
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.young) {

			matrixStack.pushPose();
			matrixStack.scale(0.71428573F, 0.64935064F, 0.7936508F);
			matrixStack.translate(0.0D, 1.3125D, (double) 0.22F);
			this.neck.getModelPart().render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			matrixStack.popPose();

			matrixStack.pushPose();
			matrixStack.scale(0.625F, 0.45454544F, 0.45454544F);
			matrixStack.translate(0.0D, 2.0625D, 0.0D);
			this.body.getModelPart().render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			matrixStack.popPose();

			matrixStack.pushPose();
			matrixStack.scale(0.45454544F, 0.41322312F, 0.45454544F);
			matrixStack.translate(0.0D, 2.0625D, 0.0D);
			ImmutableList.of(this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.chest_left, this.chest_right).forEach((modelParts) -> {
				modelParts.getModelPart().render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			});
			matrixStack.popPose();
		} else {
			ImmutableList.of(this.neck, this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft, this.chest_left, this.chest_right).forEach((modelParts) -> {
				modelParts.getModelPart().render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			});
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