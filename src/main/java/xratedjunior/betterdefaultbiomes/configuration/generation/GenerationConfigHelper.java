package xratedjunior.betterdefaultbiomes.configuration.generation;

/**
 * One class with the most used comments in the generation configuration files.
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class GenerationConfigHelper {
	/*********************************************************** Common ********************************************************/

	public static String generateComment(String biomeFeatureName, boolean defaultValue) {
		return "Should generate " + biomeFeatureName + ". (Default: " + defaultValue + ")";
	}

	public static String rarityComment(String biomeFeatureName, int defaultCount) {
		return "Determines the rarity of " + biomeFeatureName + ". (Default: " + defaultCount + ")";
	}

	public static String rarityComment(String biomeFeatureName, double defaultCount) {
		return "Determines the rarity of " + biomeFeatureName + ". (Default: " + defaultCount + ")";
	}

	public static String[] generationBiomesComment(String biomeFeatureName) {
		String[] comment = new String[] {
			"Biomes/BiomeTypes where " + biomeFeatureName + " will generate.",
			"To add Biomes or BiomeTypes:",
			"[\"BiomeType 1\", \"BiomeType 2\", \"Biome 1\", \"Biome 2\", etc...]",
			"To check for multiple mandatory matching BiomeTypes per Biome:",
			"[\"BiomeType 1 & BiomeType 2 & etc...\"]"
		};
		return comment;
	}

	public static String debugComment() {
		return "Prints a message to the log for each Biome that has been registered for generation.";
	}
}
