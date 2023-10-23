package xratedjunior.betterdefaultbiomes.entity.client.renderer.passive;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.client.BDBModelLayers;
import xratedjunior.betterdefaultbiomes.entity.client.model.passive.FrogModel;
import xratedjunior.betterdefaultbiomes.entity.passive.FrogEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class FrogRenderer extends MobRenderer<FrogEntity, FrogModel<FrogEntity>> {
	private static final ResourceLocation FROG_TEXTURE = BetterDefaultBiomes.locate("textures/entity/passive/frog.png");

	public FrogRenderer(EntityRendererProvider.Context context) {
		super(context, new FrogModel<>(context.bakeLayer(BDBModelLayers.FROG.getModelLayerLocation())), 0.45F);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(FrogEntity entity) {
		return FROG_TEXTURE;
	}
}