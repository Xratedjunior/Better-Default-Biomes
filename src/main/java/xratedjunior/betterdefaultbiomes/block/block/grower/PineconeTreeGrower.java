package xratedjunior.betterdefaultbiomes.block.block.grower;

import java.util.Random;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class PineconeTreeGrower extends AbstractTreeGrower {

	/**
	 * Only create small Spruce Trees
	 */
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean hasFlowersCloseby) {
		return TreeFeatures.SPRUCE;
	}
}
