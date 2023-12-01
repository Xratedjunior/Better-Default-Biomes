package xratedjunior.betterdefaultbiomes.entity.client.model.util;

import java.util.function.Function;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AgeableMob;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public abstract class BDBAgeableModel<E extends AgeableMob> extends BDBEntityModel<E> {
	private final boolean doScaleHead;
	private final float babyYHeadOffset;
	private final float babyZHeadOffset;
	private final float babyHeadScale;
	private final float babyBodyScale;
	private final float bodyYOffset;

	protected BDBAgeableModel(boolean doScaleHead, float babyYHeadOffset, float babyZHeadOffset) {
		this(doScaleHead, babyYHeadOffset, babyZHeadOffset, 2.0F, 2.0F, 24.0F);
	}

	protected BDBAgeableModel(boolean doScaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, float bodyYOffset) {
		this(RenderType::entityCutoutNoCull, doScaleHead, babyYHeadOffset, babyZHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
	}

	protected BDBAgeableModel(Function<ResourceLocation, RenderType> renderType, boolean doScaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, float bodyYOffset) {
		super(renderType);
		this.doScaleHead = doScaleHead;
		this.babyYHeadOffset = babyYHeadOffset;
		this.babyZHeadOffset = babyZHeadOffset;
		this.babyHeadScale = babyHeadScale;
		this.babyBodyScale = babyBodyScale;
		this.bodyYOffset = bodyYOffset;
	}

	protected BDBAgeableModel() {
		this(false, 5.0F, 2.0F);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.young) {
			stack.pushPose();
			if (this.doScaleHead) {
				float f = 1.5F / this.babyHeadScale;
				stack.scale(f, f, f);
			}

			stack.translate(0.0D, (double) (this.babyYHeadOffset / 16.0F), (double) (this.babyZHeadOffset / 16.0F));
			this.headParts().forEach((headPart) -> {
				headPart.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			});
			stack.popPose();
			stack.pushPose();
			float f1 = 1.0F / this.babyBodyScale;
			stack.scale(f1, f1, f1);
			stack.translate(0.0D, (double) (this.bodyYOffset / 16.0F), 0.0D);
			this.bodyParts().forEach((bodyPart) -> {
				bodyPart.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			});
			stack.popPose();
		} else {
			this.headParts().forEach((headPart) -> {
				headPart.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			});
			this.bodyParts().forEach((bodyPart) -> {
				bodyPart.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			});
		}

	}

	protected abstract Iterable<ModelPart> headParts();

	protected abstract Iterable<ModelPart> bodyParts();
	
	
	/**
	 * Copy of old code
	 */
	public float rotlerp(float p_14202_, float p_14203_, float p_14204_) {
		float f;
		for (f = p_14203_ - p_14202_; f < -180.0F; f += 360.0F) {
		}

		while (f >= 180.0F) {
			f -= 360.0F;
		}

		return p_14202_ + p_14204_ * f;
	}
}
