package xratedjunior.betterdefaultbiomes.entity.client.renderer.passive;

import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.passive.DuckEntity;

/**
 * Reference {@link ChickenRenderer}
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class DuckRenderer extends MobRenderer<DuckEntity, ChickenModel<DuckEntity>> {
	private static final ResourceLocation[] DUCK_TEXTURES = new ResourceLocation[] {
		BetterDefaultBiomes.locate("textures/entity/passive/duck/duck0.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/duck/duck1.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/duck/duck2.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/duck/duck3.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/duck/duck4.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/duck/duck5.png"),
		BetterDefaultBiomes.locate("textures/entity/passive/duck/duck6.png")
	};
	private static final ResourceLocation BABY_DUCK_TEXTURE = BetterDefaultBiomes.locate("textures/entity/passive/duck/baby.png");

	public DuckRenderer(EntityRendererProvider.Context context) {
		super(context, new ChickenModel<>(context.bakeLayer(ModelLayers.CHICKEN)), 0.3F);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(DuckEntity entity) {
		ResourceLocation texture = DUCK_TEXTURES[0];
		if (entity.getVariant() < DUCK_TEXTURES.length) {
			texture = DUCK_TEXTURES[entity.getVariant()];
		}
		if (entity.isBaby()) {
			texture = BABY_DUCK_TEXTURE;
		}
		return texture;
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float getBob(DuckEntity livingBase, float partialTicks) {
		float f = Mth.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
		float f1 = Mth.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
		return (Mth.sin(f) + 1.0F) * f1;
	}
}