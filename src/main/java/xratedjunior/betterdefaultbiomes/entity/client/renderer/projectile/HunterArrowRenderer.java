package xratedjunior.betterdefaultbiomes.entity.client.renderer.projectile;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.projectile.HunterArrowEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class HunterArrowRenderer extends ArrowRenderer<HunterArrowEntity> {
	private static final ResourceLocation HUNTER_ARROW_TEXTURE = BetterDefaultBiomes.locate("textures/entity/projectile/hunter_arrow.png");

	public HunterArrowRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(HunterArrowEntity entity) {
		return HUNTER_ARROW_TEXTURE;
	}
}
