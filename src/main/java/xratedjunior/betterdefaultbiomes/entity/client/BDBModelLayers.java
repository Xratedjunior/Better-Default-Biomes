package xratedjunior.betterdefaultbiomes.entity.client;

import java.util.Set;
import java.util.function.Supplier;

import com.google.common.collect.Sets;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.client.model.passive.CamelModel;
import xratedjunior.betterdefaultbiomes.entity.client.model.passive.FrogModel;
import xratedjunior.betterdefaultbiomes.entity.client.model.passive.ZebraModel;
import xratedjunior.betterdefaultbiomes.entity.client.model.util.BDBModelLayer;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class BDBModelLayers {
	private static final float DEFAULT_SCALE = 0;
	public static final Set<BDBModelLayer> BDB_MODELS = Sets.newHashSet();
	public static final BDBModelLayer CAMEL = registerMainModel("camel", () -> CamelModel.createBodyLayer(DEFAULT_SCALE));
	public static final BDBModelLayer CAMEL_SADDLE = registerModel("camel", "saddle", () -> CamelModel.createBodyLayer(0.2f));
	public static final BDBModelLayer CAMEL_DECOR = registerModel("camel", "decor", () -> CamelModel.createBodyLayer(0.1f));
	public static final BDBModelLayer ZEBRA = registerMainModel("zebra", () -> ZebraModel.createBodyLayer(DEFAULT_SCALE));
	public static final BDBModelLayer FROG = registerMainModel("frog", () -> FrogModel.createBodyLayer(DEFAULT_SCALE));

	private static BDBModelLayer registerMainModel(String entityName, Supplier<LayerDefinition> supplier) {
		return registerModel(entityName, "main", supplier);
	}

	private static BDBModelLayer registerModel(String entityName, String modelName, Supplier<LayerDefinition> modelSupplier) {
		ModelLayerLocation modelLayerLocation = new ModelLayerLocation(BetterDefaultBiomes.locate(entityName), modelName);
		BDBModelLayer modelLayer = new BDBModelLayer(modelLayerLocation, modelSupplier);
		if (!BDB_MODELS.add(modelLayer)) {
			throw new IllegalStateException("Duplicate registration for " + modelLayer);
		} else {
			return modelLayer;
		}
	}
}
