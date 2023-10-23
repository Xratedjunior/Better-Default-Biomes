package xratedjunior.betterdefaultbiomes.entity.client.renderer.passive;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.client.BDBModelLayers;
import xratedjunior.betterdefaultbiomes.entity.client.model.passive.ZebraModel;
import xratedjunior.betterdefaultbiomes.entity.passive.ZebraEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class ZebraRenderer extends MobRenderer<ZebraEntity, ZebraModel<ZebraEntity>> {
	private static final ResourceLocation ZEBRA_TEXTURE = BetterDefaultBiomes.locate("textures/entity/passive/zebra.png");

	public ZebraRenderer(EntityRendererProvider.Context context) {
		super(context, new ZebraModel<>(context.bakeLayer(BDBModelLayers.ZEBRA.getModelLayerLocation())), 0.8F);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(ZebraEntity entity) {
		return ZEBRA_TEXTURE;
	}
}