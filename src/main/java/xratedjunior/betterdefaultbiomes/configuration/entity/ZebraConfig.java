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
public class ZebraConfig {
	public static ForgeConfigSpec.IntValue weight;
	public static ConfigValue<Integer> min_group;
	public static ConfigValue<Integer> max_group;
	public static ForgeConfigSpec.ConfigValue<List<String>> spawn_biomes;

	private static String mobName = "Zebra";

	private static int spawnWeight = 12;
	private static int mobSpawnWeightMin = MobSpawningConfig.mobSpawnWeightMin;
	private static int mobSpawnWeightMax = MobSpawningConfig.mobSpawnWeightMax;
	private static int minGroup = 2;
	private static int maxGroup = 4;
	private static List<String> spawnBiomes = Lists.newArrayList("savanna", "minecraft:plains");

	public static void init(ForgeConfigSpec.Builder builder) {
		weight = builder
			.comment(EntityConfigHelper.weightComment(mobName, spawnWeight))
			.defineInRange(mobName+".weight", spawnWeight, mobSpawnWeightMin, mobSpawnWeightMax);
		
		min_group = builder
			.comment(EntityConfigHelper.minGroupComment(mobName, minGroup))
			.define(mobName+".min_group", minGroup);
		
		max_group = builder
			.comment(EntityConfigHelper.maxGroupComment(mobName, maxGroup))
			.define(mobName+".max_group", maxGroup);
		
		spawn_biomes = builder
			.comment(EntityConfigHelper.spawnBiomesComment(mobName))
			.define(mobName+".spawn_biomes", spawnBiomes);
	}
}
