package xratedjunior.betterdefaultbiomes.configuration.entity.util;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class EntityConfigHelper {
	/*********************************************************** Common ********************************************************/

	public static String weightComment(String mobName, int spawnWeight) {
		String string = "Spawn weight for the " + mobName + ". (Default: " + spawnWeight + ")";
		return string;
	}

	public static String minGroupComment(String mobName, int minGroup) {
		String string = "Minimum amount of " + mobName + "s to spawn in a group. (Default: " + minGroup + ")";
		return string;
	}

	public static String maxGroupComment(String mobName, int maxGroup) {
		String string = "Maximum amount of " + mobName + "s to spawn in a group. (Default: " + maxGroup + ")";
		return string;
	}

	public static String[] spawnBiomesComment(String mobName) {
		String[] string = new String[] {
			"Spawn Biomes/BiomeTags where the " + mobName + " will spawn.",
			"To add Biomes or BiomeTags:",
			"[\"BiomeTag 1\", \"BiomeTag 2\", \"Biome 1\", \"Biome 2\", etc...]",
			"To check for multiple mandatory matching BiomeTags per Biome:",
			"[\"BiomeTag 1 & BiomeTag 2 & etc...\"]"
		};
		return string;
	}

	/*********************************************************** Uncommon ********************************************************/

	public static String removeVanillaMobComment(String mobName, boolean removeVanillaMobDefault) {
		String string = "Removes Vanilla " + mobName + " Spawning in these Biomes. (Default: " + removeVanillaMobDefault + ")";
		return string;
	}

	/*********************************************************** Rare ********************************************************/

	public static String jockeyComment(String mobName, boolean jockeyDefault) {
		String string = "Enables the Spawning of " + mobName + " Jockeys. (Default: " + jockeyDefault + ")";
		return string;
	}

	public static String Disabled() {
		String string = "This feature is disabled for now as it requires more testing.";
		return string;
	}
}
