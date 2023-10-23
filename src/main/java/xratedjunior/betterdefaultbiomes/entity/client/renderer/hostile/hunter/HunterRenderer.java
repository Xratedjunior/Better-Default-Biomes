package xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.hunter;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.HostileHumanoidRenderer;
import xratedjunior.betterdefaultbiomes.entity.hostile.hunter.HunterEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class HunterRenderer extends HostileHumanoidRenderer<HunterEntity> {
	private static final ResourceLocation HUNTER_TEXTURE = BetterDefaultBiomes.locate("textures/entity/hostile/hunter/hunter.png");

	public HunterRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(HunterEntity entity) {
		return HUNTER_TEXTURE;
	}
}