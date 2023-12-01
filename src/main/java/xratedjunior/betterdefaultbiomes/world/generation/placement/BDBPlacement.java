package xratedjunior.betterdefaultbiomes.world.generation.placement;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;
import xratedjunior.betterdefaultbiomes.world.generation.BDBPlacementModifierTypes;

/**
 * Config weight of 1000 equals a normal {@link CountPlacement#of(1)}
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBPlacement extends RepeatingPlacement {
	public static final Codec<BDBPlacement> CODEC = IntProvider.codec(0, Integer.MAX_VALUE).fieldOf("count").xmap(BDBPlacement::new, (config) -> {
		return config.count;
	}).codec();
	private final IntProvider count;

	public BDBPlacement(IntProvider count) {
		this.count = count;
	}

	/*********************************************************** Initialize ********************************************************/

	public static BDBPlacement of(int count) {
		return new BDBPlacement(ConstantInt.of(count));
	}

	@Override
	public PlacementModifierType<?> type() {
		return BDBPlacementModifierTypes.COUNT.get();
	}

	/*********************************************************** Workings ********************************************************/

	/**
	 * Enable range from 0 to max integer (Weight of 1000 is already frequent for surface features)
	 */
	@Override
	protected int count(RandomSource random, BlockPos pos) {
		int input = this.count.sample(random);

		if (input > 0) {
			int thousands = input / 1000;
			int singles = input % 1000;

			// Range 0.001 - 0.999
			float chance = (float) singles / 1000;
			float rand = random.nextFloat();
			int count = thousands + (rand < chance ? 1 : 0);

			// Output range 0 and above
			return count;
		}

		// Returning '0' practically disables generation
		return 0;
	}
	
	/*********************************************************** Getter ********************************************************/
	public IntProvider getCount() {
		return this.count;
	}
}
