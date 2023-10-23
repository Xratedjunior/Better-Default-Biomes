package xratedjunior.betterdefaultbiomes.configuration.generation.flower;

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
public class PinkCactusFlowerConfig {
	public static ForgeConfigSpec.BooleanValue generate;
	public static ConfigValue<Integer> count;
	public static ForgeConfigSpec.ConfigValue<List<String>> generation_biomes;
	public static ForgeConfigSpec.BooleanValue debug;
	
	public static String featureName = "Pink Cactus Flower";
	private static int defaultCount = 40;
	private static List<String> generationBiomes = Lists.newArrayList("hot & sandy", "minecraft:badlands");

	public static void init(ForgeConfigSpec.Builder builder) {
		
		builder.push(featureName.replace(" ", "_"));
		
		generate = builder
			.comment(GenerationConfigHelper.generateComment(featureName+"s", true))
			.define("generate", true);
		
		count = builder
			.comment(GenerationConfigHelper.rarityComment(featureName+"s", defaultCount))
			.defineInRange("count", defaultCount, 1, GenerationConfig.maxConfigCount);
		
		generation_biomes = builder
			.comment(GenerationConfigHelper.generationBiomesComment(featureName+"s"))
			.define("generation_biomes", generationBiomes);
		
		debug = builder
			.comment(GenerationConfigHelper.debugComment())
			.define("debug", false);
		
		builder.pop();
	}
}
