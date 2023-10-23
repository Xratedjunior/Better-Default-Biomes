package xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class FrozenZombieRenderer extends ZombieRenderer {
	private static final ResourceLocation FROZEN_ZOMBIE_TEXTURES = BetterDefaultBiomes.locate("textures/entity/hostile/frozen_zombie.png");

	public FrozenZombieRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Zombie entity) {
		return FROZEN_ZOMBIE_TEXTURES;
	}
}