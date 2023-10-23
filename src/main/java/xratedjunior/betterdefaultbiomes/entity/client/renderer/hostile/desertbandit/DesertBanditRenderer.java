package xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.desertbandit;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.HostileHumanoidRenderer;
import xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit.DesertBanditEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class DesertBanditRenderer<E extends DesertBanditEntity> extends HostileHumanoidRenderer<E> {
	private static final ResourceLocation DESERT_BANDIT_TEXTURE = BetterDefaultBiomes.locate("textures/entity/hostile/desert_bandit/desert_bandit.png");

	public DesertBanditRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(E entity) {
		return DESERT_BANDIT_TEXTURE;
	}
}