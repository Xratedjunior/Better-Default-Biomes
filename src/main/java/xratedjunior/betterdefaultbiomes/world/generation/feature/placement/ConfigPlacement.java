package xratedjunior.betterdefaultbiomes.world.generation.feature.placement;

import java.util.Random;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BDBPlacementModifierTypes;

/**
 * Config weight of 1000 equals a normal {@link CountPlacement#of(1)}
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class ConfigPlacement extends RepeatingPlacement {
	// TODO Make proper codec
	private static final ConfigPlacement INSTANCE = new ConfigPlacement(null);
	public static final Codec<ConfigPlacement> CODEC = Codec.unit(() -> INSTANCE);
	private Supplier<ConfigValue<Integer>> config = null;

	public ConfigPlacement(Supplier<ConfigValue<Integer>> configIn) {
		this.config = configIn;
	}

	/**
	 * Enable range from 0 to max integer (Weight of 1000 is already frequent for surface features)
	 */
	@Override
	protected int count(Random random, BlockPos pos) {
		if (this.config != null) {
			// Weight in Config
			int configValue = this.config.get().get();
			int input = configValue;

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

	@Override
	public PlacementModifierType<?> type() {
		return BDBPlacementModifierTypes.CONFIG.get();
	}
}
