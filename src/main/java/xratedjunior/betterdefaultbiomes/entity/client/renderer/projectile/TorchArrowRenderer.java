package xratedjunior.betterdefaultbiomes.entity.client.renderer.projectile;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.projectile.TorchArrowEntity;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
@OnlyIn(Dist.CLIENT)
public class TorchArrowRenderer extends ArrowRenderer<TorchArrowEntity> {
	private static final ResourceLocation TORCH_ARROW_TEXTURE = BetterDefaultBiomes.locate("textures/entity/projectile/torch_arrow.png");
	private static final ResourceLocation TORCH_ARROW_TIP_TEXTURE = BetterDefaultBiomes.locate("textures/entity/projectile/torch_arrow_tip.png");

	public TorchArrowRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager);
	}

	@Override
	public void render(TorchArrowEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		// Render arrow tip
		matrixStackIn.pushPose();

		matrixStackIn.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
		matrixStackIn.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));

		float scale = 0.05625F;
		float f9 = (float) entityIn.shakeTime - partialTicks;
		if (f9 > 0.0F) {
			float f10 = -Mth.sin(f9 * 3.0F) * f9;
			matrixStackIn.mulPose(Axis.ZP.rotationDegrees(f10));
		}

		matrixStackIn.mulPose(Axis.XP.rotationDegrees(45.0F));
		matrixStackIn.scale(scale, scale, scale);
		matrixStackIn.translate(-4.0D, 0.0D, 0.0D);
		VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.entityCutout(TORCH_ARROW_TIP_TEXTURE));
		PoseStack.Pose posestack$pose = matrixStackIn.last();
		Matrix4f matrix4f = posestack$pose.pose();
		Matrix3f matrix3f = posestack$pose.normal();
		int glowValue = 254;
		for (int j = 0; j < 4; ++j) {
			matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F));
			this.vertex(matrix4f, matrix3f, vertexconsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, glowValue);
			this.vertex(matrix4f, matrix3f, vertexconsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, glowValue);
			this.vertex(matrix4f, matrix3f, vertexconsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, glowValue);
			this.vertex(matrix4f, matrix3f, vertexconsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, glowValue);
		}

		matrixStackIn.popPose();

		// Render arrow body
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(TorchArrowEntity entity) {
		return TORCH_ARROW_TEXTURE;
	}
}
