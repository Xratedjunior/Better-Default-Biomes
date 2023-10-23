package xratedjunior.betterdefaultbiomes.entity.client.renderer.passive.camel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.client.BDBModelLayers;
import xratedjunior.betterdefaultbiomes.entity.client.model.passive.CamelModel;
import xratedjunior.betterdefaultbiomes.entity.passive.CamelEntity;

/**
 * Referenced from {@link LlamaDecorLayer}
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class CamelDecorLayer extends RenderLayer<CamelEntity, CamelModel<CamelEntity>> {
	private static final ResourceLocation[] CAMEL_DECOR_TEXTURES = new ResourceLocation[] {
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/white.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/orange.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/magenta.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/light_blue.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/yellow.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/lime.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/pink.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/gray.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/light_gray.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/cyan.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/purple.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/blue.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/brown.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/green.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/red.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/black.png")
	};
	private static final ResourceLocation NO_DECOR = BetterDefaultBiomes.locate("textures/entity/passive/camel/decor/none.png");
	private final CamelModel<CamelEntity> model;

	public CamelDecorLayer(RenderLayerParent<CamelEntity, CamelModel<CamelEntity>> renderer, EntityModelSet model) {
		super(renderer);
		this.model = new CamelModel<>(model.bakeLayer(BDBModelLayers.CAMEL_DECOR.getModelLayerLocation()));
	}

	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, CamelEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		DyeColor dyecolor = entitylivingbaseIn.getSwag();
		ResourceLocation resourcelocation = NO_DECOR;
		if (dyecolor != null) {
			resourcelocation = CAMEL_DECOR_TEXTURES[dyecolor.getId()];
		}
		this.getParentModel().copyPropertiesTo(this.model);
		this.model.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(resourcelocation));
		this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}