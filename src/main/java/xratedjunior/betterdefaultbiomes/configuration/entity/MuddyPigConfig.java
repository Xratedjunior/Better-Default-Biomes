package xratedjunior.betterdefaultbiomes.configuration.entity;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import xratedjunior.betterdefaultbiomes.configuration.MobSpawningConfig;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class MuddyPigConfig {
	public static ForgeConfigSpec.IntValue weight;
	public static ConfigValue<Integer> min_group;
	public static ConfigValue<Integer> max_group;
	public static ForgeConfigSpec.BooleanValue remove_vanilla_pigs;
	public static ForgeConfigSpec.ConfigValue<List<String>> spawn_biomes;

	private static String mobName = "Muddy Pig";

	private static int spawnWeight = 10;
	private static int mobSpawnWeightMin = MobSpawningConfig.mobSpawnWeightMin;
	private static int mobSpawnWeightMax = MobSpawningConfig.mobSpawnWeightMax;
	private static int minGroup = 4;
	private static int maxGroup = 4;
	private static List<String> spawnBiomes = Lists.newArrayList("swamp");

	private static String vanillaMobName = "Pig";
	private static boolean removeVanillaPigs = true;

	public static void init(ForgeConfigSpec.Builder builder) {
		weight = builder
			//.comment("Spawn weight for the Muddy Pig (Default: " + spawnWeight + ")")
			.comment(EntityConfigHelper.weightComment(mobName, spawnWeight))
			.defineInRange("Muddy_Pig.weight", spawnWeight, mobSpawnWeightMin, mobSpawnWeightMax);
		
		min_group = builder
			//.comment("Minimum amount of Muddy Pigs to spawn in a group (Default: " + minGroup + ")")
			.comment(EntityConfigHelper.minGroupComment(mobName, minGroup))
			.define("Muddy_Pig.min_group", minGroup);
		
		max_group = builder
			//.comment("Maximum amount of Muddy Pigs to spawn in a group (Default: " + maxGroup + ")")
			.comment(EntityConfigHelper.maxGroupComment(mobName, maxGroup))
			.define("Muddy_Pig.max_group", maxGroup);
		
		spawn_biomes = builder
			//.comment("Spawn Biomes/BiomeTypes where the Muddy Pig will spawn.")
			.comment(EntityConfigHelper.spawnBiomesComment(mobName))
			.define("Muddy_Pig.spawn_biomes", spawnBiomes);
		
		remove_vanilla_pigs = builder
			//.comment("Removes Vanilla Pig Spawning in these Biomes (Default: " + removeVanillaPigs + ")")
			.comment(EntityConfigHelper.removeVanillaMobComment(vanillaMobName, removeVanillaPigs))
			.define("Muddy_Pig.remove_vanilla_pigs", removeVanillaPigs);
	}
}
