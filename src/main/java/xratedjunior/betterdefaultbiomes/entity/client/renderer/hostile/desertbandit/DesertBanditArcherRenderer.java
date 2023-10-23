package xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.desertbandit;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit.DesertBanditArcherEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class DesertBanditArcherRenderer extends DesertBanditRenderer<DesertBanditArcherEntity> {
	private static final ResourceLocation DESERT_BANDIT_ARCHER_TEXTURE = BetterDefaultBiomes.locate("textures/entity/hostile/desert_bandit/desert_bandit_archer.png");

	public DesertBanditArcherRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(DesertBanditArcherEntity entity) {
		return DESERT_BANDIT_ARCHER_TEXTURE;
	}
}