package xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class LostMinerRenderer extends SkeletonRenderer {
	private static final ResourceLocation LOST_MINER_TEXTURES = BetterDefaultBiomes.locate("textures/entity/hostile/lost_miner.png");

	public LostMinerRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractSkeleton entity) {
		return LOST_MINER_TEXTURES;
	}
}