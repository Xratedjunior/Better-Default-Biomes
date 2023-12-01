package xratedjunior.betterdefaultbiomes.block.block.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import xratedjunior.betterdefaultbiomes.world.generation.BDBConfiguredFeatures;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class SwampWillowTreeGrower extends AbstractTreeGrower {

	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowersCloseby) {
		return BDBConfiguredFeatures.SWAMP_WILLOW_TREE;
	}
}
