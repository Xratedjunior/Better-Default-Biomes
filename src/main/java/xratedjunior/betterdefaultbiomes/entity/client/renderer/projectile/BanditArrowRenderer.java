package xratedjunior.betterdefaultbiomes.entity.client.renderer.projectile;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.projectile.BanditArrowEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class BanditArrowRenderer extends ArrowRenderer<BanditArrowEntity> {
	private static final ResourceLocation BANDIT_ARROW_TEXTURE = BetterDefaultBiomes.locate("textures/entity/projectile/bandit_arrow.png");

	public BanditArrowRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(BanditArrowEntity entity) {
		return BANDIT_ARROW_TEXTURE;
	}
}
