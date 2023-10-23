package xratedjunior.betterdefaultbiomes.block.block.grower;

import java.util.Random;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BDBConfiguredFeatures;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class SwampWillowTreeGrower extends AbstractTreeGrower {

	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean hasFlowersCloseby) {
		return BDBConfiguredFeatures.SWAMP_WILLOW_TREE.getHolder().orElseThrow();
	}
}
