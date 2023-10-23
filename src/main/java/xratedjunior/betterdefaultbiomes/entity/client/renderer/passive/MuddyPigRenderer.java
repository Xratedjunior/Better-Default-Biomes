package xratedjunior.betterdefaultbiomes.entity.client.renderer.passive;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class MuddyPigRenderer extends PigRenderer {
	private static final ResourceLocation MUDDY_PIG_TEXTURES = BetterDefaultBiomes.locate("textures/entity/passive/muddy_pig.png");

	public MuddyPigRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(Pig entity) {
		return MUDDY_PIG_TEXTURES;
	}
}