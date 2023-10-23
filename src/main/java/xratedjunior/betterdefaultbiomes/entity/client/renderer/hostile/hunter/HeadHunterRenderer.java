package xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.hunter;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.hostile.hunter.HunterEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class HeadHunterRenderer extends HunterRenderer {
	private static final ResourceLocation HEAD_HUNTER_TEXTURE = BetterDefaultBiomes.locate("textures/entity/hostile/hunter/head_hunter.png");

	public HeadHunterRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(HunterEntity entity) {
		return HEAD_HUNTER_TEXTURE;
	}
}