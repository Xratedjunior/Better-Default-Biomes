package xratedjunior.betterdefaultbiomes.entity.client.renderer.passive.camel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.client.BDBModelLayers;
import xratedjunior.betterdefaultbiomes.entity.client.model.passive.CamelModel;
import xratedjunior.betterdefaultbiomes.entity.passive.CamelEntity;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
@OnlyIn(Dist.CLIENT)
public class CamelRenderer extends MobRenderer<CamelEntity, CamelModel<CamelEntity>> {
	private static final ResourceLocation SADDLE_TEXTURE = BetterDefaultBiomes.locate("textures/entity/passive/camel/camel_saddle.png");
	private static final ResourceLocation[] CAMEL_TEXTURES = new ResourceLocation[] {
		BetterDefaultBiomes.locate("textures/entity/passive/camel/camel_default.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/camel_brown.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/camel_dark.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/camel/camel_light.png")
	};

	public CamelRenderer(EntityRendererProvider.Context context) {
		super(context, new CamelModel<>(context.bakeLayer(BDBModelLayers.CAMEL.getModelLayerLocation())), 0.7F);
		this.addLayer(new SaddleLayer<>(this, new CamelModel<>(context.bakeLayer(BDBModelLayers.CAMEL_SADDLE.getModelLayerLocation())), SADDLE_TEXTURE));
		this.addLayer(new CamelDecorLayer(this, context.getModelSet()));
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(CamelEntity entity) {
		return CAMEL_TEXTURES[entity.getVariant().getId()];
	}
}