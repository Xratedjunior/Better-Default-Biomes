package xratedjunior.betterdefaultbiomes.configuration.generation.mushroom;

import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import xratedjunior.betterdefaultbiomes.configuration.GenerationConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.GenerationConfigHelper;

/**
 * @author 	Xrated_junior
 * @version	1.18.2-Alpha 3.0.0
 */
public class MushroomConfigs {
	
	/**
	 * Initialize all the Mushroom Configs
	 */
	public static void init(ForgeConfigSpec.Builder builder) {
		builder.push("Mushrooms");
		WhiteMushroomConfig.init(builder);
		YellowMushroomConfig.init(builder);
		GrayMushroomConfig.init(builder);
		builder.pop();
	}
	
	protected static String configName(String featureName) {
		return featureName.replace(" ", "_");
	}
	
	protected static ForgeConfigSpec.BooleanValue generate(ForgeConfigSpec.Builder builder, String featureName, boolean defaultValue) {
		return builder
			.comment(GenerationConfigHelper.generateComment(featureName+"s", defaultValue))
			.define("generate", defaultValue);
	}
	
	protected static ConfigValue<Integer> count(ForgeConfigSpec.Builder builder, String featureName, int defaultCount) {
		return builder
			.comment(GenerationConfigHelper.rarityComment(featureName+"s", defaultCount))
			.defineInRange("count", defaultCount, 1, GenerationConfig.maxConfigCount);
	}
	
	protected static ForgeConfigSpec.ConfigValue<List<String>> generation_biomes(ForgeConfigSpec.Builder builder, String featureName, List<String> generationBiomes) {
		return builder
			.comment(GenerationConfigHelper.generationBiomesComment(featureName+"s"))
			.define("generation_biomes", generationBiomes);
	}
	
	protected static String pushBig(ForgeConfigSpec.Builder builder, String featureName) {
		builder.push("Big");
		return "Big "+featureName;
	}
	
	protected static ForgeConfigSpec.BooleanValue generate_big(ForgeConfigSpec.Builder builder, String featureName, boolean defaultValue) {
		return builder
			.comment(GenerationConfigHelper.generateComment(featureName+"s", defaultValue))
			.define("generate_big", defaultValue);
	}
	
	protected static ConfigValue<Integer> count_big(ForgeConfigSpec.Builder builder, String featureName, int defaultCountBig) {
		return builder
			.comment(GenerationConfigHelper.rarityComment(featureName+"s", defaultCountBig))
			.defineInRange("count_big", defaultCountBig, 0, GenerationConfig.maxConfigCount);
	}
	
	protected static ForgeConfigSpec.ConfigValue<List<String>> generation_biomes_big(ForgeConfigSpec.Builder builder, String featureName, List<String> generationBiomesBig) {
		return builder
			.comment(GenerationConfigHelper.generationBiomesComment(featureName+"s"))
			.define("generation_biomes_big", generationBiomesBig);
	}
}
