package xratedjunior.betterdefaultbiomes.configuration.generation.other;

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
public class SmallRocksConfig {
	public static ForgeConfigSpec.BooleanValue generate;
	public static ConfigValue<Integer> count;
	public static ForgeConfigSpec.ConfigValue<List<String>> generation_biomes;
	public static ConfigValue<String> default_rock;
	public static ForgeConfigSpec.BooleanValue debug;

	public static ForgeConfigSpec.BooleanValue generate_underground;
	public static ConfigValue<Integer> count_underground;
	public static ForgeConfigSpec.ConfigValue<List<String>> generation_biomes_underground;
	public static ForgeConfigSpec.BooleanValue debug_underground;
	
	public static ForgeConfigSpec.BooleanValue generate_stone;
	public static ForgeConfigSpec.BooleanValue generate_cobble;
	public static ForgeConfigSpec.BooleanValue generate_mossy;
	public static ForgeConfigSpec.BooleanValue generate_andesite;
	public static ForgeConfigSpec.BooleanValue generate_diorite;
	public static ForgeConfigSpec.BooleanValue generate_granite;
	public static ForgeConfigSpec.BooleanValue generate_sandstone;
	public static ForgeConfigSpec.BooleanValue generate_red_sandstone;
	public static ForgeConfigSpec.BooleanValue generate_deepslate;
	public static ForgeConfigSpec.BooleanValue generate_cobbled_deepslate;

	public static String featureName = "Small Rocks";
	public static String featureNameUnderground = "Small Rocks underground";

	private static int defaultCount = 200;
	private static List<String> generationBiomes = Lists.newArrayList("overworld");
	public static String defaultRock = "stone";

	private static int defaultCountUnderground = 64000;
	private static List<String> generationBiomesUnderground = Lists.newArrayList("overworld");

	public static void init(ForgeConfigSpec.Builder builder) {

		builder.push(featureName.replace(" ", "_"));
		
		/*********************************************************** Generation ********************************************************/

		generate = builder.comment(GenerationConfigHelper.generateComment(featureName, true)).define("generate", true);

		count = builder.comment(GenerationConfigHelper.rarityComment(featureName, defaultCount)).defineInRange("count", defaultCount, 1, GenerationConfig.maxConfigCount);

		generation_biomes = builder.comment(GenerationConfigHelper.generationBiomesComment(featureName)).define("generation_biomes", generationBiomes);

		// Updates in real time
		default_rock = builder.comment(new String[] {
			"Choose the default variant for Small Rocks to generate. (Default: \"" + defaultRock + "\")",
			"Note: This will overwrite the variants that have been turned off.",
			"Options: stone, cobble, mossy, andesite, diorite, granite, sandstone, red_sandstone, deepslate and cobbled_deepslate."
		}).define("default_rock", defaultRock);
		
		debug = builder
			.comment(GenerationConfigHelper.debugComment())
			.define("debug", false);

		/*********************************************************** Underground Rocks ********************************************************/

		builder.push("Underground");

		generate_underground = builder.comment(GenerationConfigHelper.generateComment(featureNameUnderground, true)).define("generate_underground", true);

		count_underground = builder.comment(GenerationConfigHelper.rarityComment(featureNameUnderground, defaultCountUnderground)).defineInRange("count_underground", defaultCountUnderground, 1, GenerationConfig.maxConfigCount);

		generation_biomes_underground = builder.comment(generationBiomesComment(featureName)).define("generation_biomes_underground", generationBiomesUnderground);
		
		debug_underground = builder
			.comment(GenerationConfigHelper.debugComment())
			.define("debug_underground", false);

		builder.pop();

		/*********************************************************** Rocks Variants ********************************************************/

		builder.comment("Set to false to disable types of Small Rocks that will generate. (Default: " + true + ")").push("Variants");

		generate_stone = builder.define("generate_stone", true);
		generate_cobble = builder.define("generate_cobble", true);
		generate_mossy = builder.define("generate_mossy", true);
		generate_andesite = builder.define("generate_andesite", true);
		generate_diorite = builder.define("generate_diorite", true);
		generate_granite = builder.define("generate_granite", true);
		generate_sandstone = builder.define("generate_sandstone", true);
		generate_red_sandstone = builder.define("generate_red_sandstone", true);
		generate_deepslate = builder.define("generate_deepslate", true);
		generate_cobbled_deepslate = builder.define("generate_cobbled_deepslate", true);

		builder.pop();

		builder.pop();
	}

	private static String[] generationBiomesComment(String biomeFeatureName) {
		String[] comment = GenerationConfigHelper.generationBiomesComment(biomeFeatureName);
		comment[0] = comment[0].replace("generate.", "generate underground.");
		return comment;
	}
}
