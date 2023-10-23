package xratedjunior.betterdefaultbiomes.configuration.generation.mushroom;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import xratedjunior.betterdefaultbiomes.configuration.generation.GenerationConfigHelper;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class WhiteMushroomConfig extends MushroomConfigs {
	public static ForgeConfigSpec.BooleanValue generate;
	public static ConfigValue<Integer> count;
	public static ForgeConfigSpec.ConfigValue<List<String>> generation_biomes;
	public static ForgeConfigSpec.BooleanValue debug;

	public static ForgeConfigSpec.BooleanValue generate_big;
	public static ConfigValue<Integer> count_big;
	public static ForgeConfigSpec.ConfigValue<List<String>> generation_biomes_big;
	public static ForgeConfigSpec.BooleanValue debug_big;

	public static final String featureName = "White Mushroom";
	private static int defaultCount = 40;
	private static List<String> generationBiomes = Lists.newArrayList("forest", "swamp");

	private static int defaultCountBig = 200;
	private static List<String> generationBiomesBig = Lists.newArrayList("swamp");

	/**
	 * Initialize the config with the correct values
	 */
	public static void init(ForgeConfigSpec.Builder builder) {
		
		builder.push(configName(featureName));
		
		generate = generate(builder, featureName, true);
		count = count(builder, featureName, defaultCount);
		generation_biomes = generation_biomes(builder, featureName, generationBiomes);
		debug = builder
			.comment(GenerationConfigHelper.debugComment())
			.define("debug", false);
		
	    /*********************************************************** Big Mushrooms ********************************************************/

			String bigFeatureName = pushBig(builder, featureName);
						
			generate_big = generate_big(builder, bigFeatureName, false);
			count_big = count_big(builder, bigFeatureName, defaultCountBig);
			generation_biomes_big = generation_biomes_big(builder, bigFeatureName, generationBiomesBig);
			debug_big = builder
				.comment(GenerationConfigHelper.debugComment())
				.define("debug_big", false);
			
			builder.pop();
			
		builder.pop();
	}
}
