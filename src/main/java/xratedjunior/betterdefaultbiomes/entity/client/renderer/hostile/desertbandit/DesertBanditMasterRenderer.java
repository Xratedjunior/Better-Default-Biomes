package xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.desertbandit;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit.DesertBanditMasterEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class DesertBanditMasterRenderer extends DesertBanditRenderer<DesertBanditMasterEntity> {
	private static final ResourceLocation DESERT_BANDIT_MASTER_TEXTURE = BetterDefaultBiomes.locate("textures/entity/hostile/desert_bandit/desert_bandit_master.png");

	public DesertBanditMasterRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(DesertBanditMasterEntity entity) {
		return DESERT_BANDIT_MASTER_TEXTURE;
	}
}