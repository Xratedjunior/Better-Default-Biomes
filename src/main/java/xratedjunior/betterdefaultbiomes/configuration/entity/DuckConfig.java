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
public class DuckConfig {
	public static ForgeConfigSpec.IntValue weight;
	public static ConfigValue<Integer> min_group;
	public static ConfigValue<Integer> max_group;
	public static ForgeConfigSpec.ConfigValue<List<String>> spawn_biomes;

	public static ForgeConfigSpec.BooleanValue remove_vanilla_chickens;
	public static ForgeConfigSpec.BooleanValue duck_jockey;

	private static String mobConfigName = "Duck";
	private static String mobName = "Duck";

	private static int spawnWeight = 10;
	private static int mobSpawnWeightMin = MobSpawningConfig.mobSpawnWeightMin;
	private static int mobSpawnWeightMax = MobSpawningConfig.mobSpawnWeightMax;
	private static int minGroup = 4;
	private static int maxGroup = 4;
	private static List<String> spawnBiomes = Lists.newArrayList("swamp");

	private static String vanillaMobName = "Chicken";
	private static boolean removeVanillaChickens = true;

	private static boolean duckJockey = false;

	public static void init(ForgeConfigSpec.Builder builder) {
		weight = builder
			.comment(EntityConfigHelper.weightComment(mobName, spawnWeight))
			.defineInRange(mobConfigName+".weight", spawnWeight, mobSpawnWeightMin, mobSpawnWeightMax);
		
		min_group = builder
			.comment(EntityConfigHelper.minGroupComment(mobName, minGroup))
			.define(mobConfigName+".min_group", minGroup);
		
		max_group = builder
			.comment(EntityConfigHelper.maxGroupComment(mobName, maxGroup))
			.define(mobConfigName+".max_group", maxGroup);
		
		spawn_biomes = builder
			.comment(EntityConfigHelper.spawnBiomesComment(mobName))
			.define(mobConfigName+".spawn_biomes", spawnBiomes);
		
		remove_vanilla_chickens = builder
			.comment(EntityConfigHelper.removeVanillaMobComment(vanillaMobName, removeVanillaChickens))
			.define(mobConfigName+".remove_vanilla_"+vanillaMobName.toLowerCase()+"s", removeVanillaChickens);
		
		duck_jockey = builder
//			.comment(EntityConfigHelper.jockeyComment(mobName, duckJockey))
			.comment(EntityConfigHelper.Disabled())
			.define(mobConfigName+".jockey", duckJockey);
	}
}
