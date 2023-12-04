package xratedjunior.betterdefaultbiomes.entity.client.renderer.projectile;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.entity.projectile.SmallRockEntity;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SmallRockRenderer extends EntityRenderer<SmallRockEntity> {
	private float scale;

	public SmallRockRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager);
		this.scale = 1.0F;
	}

	@Override
	public void render(SmallRockEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		if (entityIn.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entityIn) < 12.25D)) {
			matrixStackIn.pushPose();
			matrixStackIn.scale(this.scale, this.scale, this.scale);
			matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
			matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F));
			matrixStackIn.translate(0, 0.25F, 0);

			Minecraft mc = Minecraft.getInstance();

			// Rotate during flight
			float time = entityIn.tickCount + (mc.isPaused() ? 0 : partialTicks);
			matrixStackIn.mulPose(Axis.ZP.rotationDegrees(time * 2F));
			matrixStackIn.mulPose(Axis.YP.rotationDegrees(time * 20F));

			RenderSystem.enableBlend();
			mc.getItemRenderer().renderStatic(entityIn.getItem(), ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entityIn.level(), entityIn.getId());

			matrixStackIn.popPose();
			super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(SmallRockEntity entity) {
		// No texture
		return null;
	}
}
