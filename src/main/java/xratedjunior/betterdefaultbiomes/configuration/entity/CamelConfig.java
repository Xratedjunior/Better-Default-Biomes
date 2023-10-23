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
public class CamelConfig {
	public static ForgeConfigSpec.IntValue weight;
	public static ConfigValue<Integer> min_group;
	public static ConfigValue<Integer> max_group;
	public static ForgeConfigSpec.ConfigValue<List<String>> spawn_biomes;

	private static String mobName = "Camel";

	private static int spawnWeight = 1;
	private static int mobSpawnWeightMin = MobSpawningConfig.mobSpawnWeightMin;
	private static int mobSpawnWeightMax = MobSpawningConfig.mobSpawnWeightMax;
	private static int minGroup = 2;
	private static int maxGroup = 3;
	private static List<String> spawnBiomes = Lists.newArrayList("hot & sandy");

	public static void init(ForgeConfigSpec.Builder builder) {
		weight = builder
			.comment(EntityConfigHelper.weightComment(mobName, spawnWeight))
			.defineInRange("Camel.weight", spawnWeight, mobSpawnWeightMin, mobSpawnWeightMax);
		
		min_group = builder
			.comment(EntityConfigHelper.minGroupComment(mobName, minGroup))
			.define("Camel.min_group", minGroup);
		
		max_group = builder
			.comment(EntityConfigHelper.maxGroupComment(mobName, maxGroup))
			.define("Camel.max_group", maxGroup);
		
		spawn_biomes = builder
			.comment(EntityConfigHelper.spawnBiomesComment(mobName))
			.define("Camel.spawn_biomes", spawnBiomes);
	}
}
