package xratedjunior.betterdefaultbiomes.entity.client.model.util;

import java.util.function.Supplier;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBModelLayer {
	private final ModelLayerLocation modelLayerLocation;
	private final Supplier<LayerDefinition> supplier;

	public BDBModelLayer(ModelLayerLocation modelLayerLocation, Supplier<LayerDefinition> LayerDefinition) {
		this.modelLayerLocation = modelLayerLocation;
		this.supplier = LayerDefinition;
	}

	public ModelLayerLocation getModelLayerLocation() {
		return this.modelLayerLocation;
	}

	public Supplier<LayerDefinition> getModelSupplier() {
		return this.supplier;
	}
}
