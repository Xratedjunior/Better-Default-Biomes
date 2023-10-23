package xratedjunior.betterdefaultbiomes.configuration.generation.plant;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import xratedjunior.betterdefaultbiomes.configuration.GenerationConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.GenerationConfigHelper;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class TallWaterReedsConfig {
	public static ForgeConfigSpec.BooleanValue generate;
	public static ConfigValue<Integer> count;
	public static ForgeConfigSpec.ConfigValue<List<String>> generation_biomes;
	public static ForgeConfigSpec.BooleanValue debug;

	public static String featureName = "Tall Water Reeds";
	private static int defaultCount = 4000;
	private static List<String> generationBiomes = Lists.newArrayList("overworld");

	public static void init(ForgeConfigSpec.Builder builder) {

		builder.push(featureName.replace(" ", "_"));
		
		generate = builder
			.comment(GenerationConfigHelper.generateComment(featureName, true))
			.define("generate", true);
		
		count = builder
			.comment(GenerationConfigHelper.rarityComment(featureName, defaultCount))
			.defineInRange("count", defaultCount, 1, GenerationConfig.maxConfigCount);
		
		generation_biomes = builder
			.comment(GenerationConfigHelper.generationBiomesComment(featureName))
			.define("generation_biomes", generationBiomes);
		
		debug = builder
			.comment(GenerationConfigHelper.debugComment())
			.define("debug", false);
		
		builder.pop();
	}
}
