
package xratedjunior.betterdefaultbiomes.util;

import net.minecraft.util.RandomSource;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBMathUtil {
	// get a random number between 2 values (inclusive of end points)
	public static int nextIntBetween(RandomSource rand, int minimum, int maximum) {
		if (minimum == maximum) {
			return minimum;
		}
		int min = minimum < maximum ? minimum : maximum;
		int max = minimum > maximum ? minimum : maximum;
		return min + rand.nextInt(1 + (max - min));
	}
}
