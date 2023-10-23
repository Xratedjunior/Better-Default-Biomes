package xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.hostile.JungleCreeperEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class JungleCreeperRenderer extends CreeperRenderer {
	private static final ResourceLocation JUNGLE_CREEPER_TEXTURES = BetterDefaultBiomes.locate("textures/entity/hostile/jungle_creeper.png");

	public JungleCreeperRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Nullable
	@Override
	protected RenderType getRenderType(Creeper creeper, boolean isVisible, boolean isInVisibleButNotForPlayer, boolean isGlowing) {
		if (creeper instanceof JungleCreeperEntity) {
			JungleCreeperEntity jungleCreeper = (JungleCreeperEntity) creeper;
			ResourceLocation resourcelocation = this.getTextureLocation(jungleCreeper);
			Minecraft minecraft = Minecraft.getInstance();
			if (jungleCreeper.isStealth() && !jungleCreeper.isInvisible() && jungleCreeper.renderStealth(minecraft.player)) {
				return RenderType.entityTranslucentCull(resourcelocation);
			} else if (isVisible) {
				return this.model.renderType(resourcelocation);
			} else {
				return isGlowing ? RenderType.outline(resourcelocation) : null;
			}
		}
		return super.getRenderType(creeper, isVisible, isInVisibleButNotForPlayer, isGlowing);
	}

	@Override
	protected boolean isBodyVisible(Creeper creeper) {
		if (creeper instanceof JungleCreeperEntity) {
			JungleCreeperEntity jungleCreeper = (JungleCreeperEntity) creeper;
			Minecraft minecraft = Minecraft.getInstance();
			return (!jungleCreeper.isStealth() || !jungleCreeper.renderStealth(minecraft.player)) && !jungleCreeper.isInvisible();
		}
		return super.isBodyVisible(creeper);
	}

	@Override
	public ResourceLocation getTextureLocation(Creeper entity) {
		return JUNGLE_CREEPER_TEXTURES;
	}
}