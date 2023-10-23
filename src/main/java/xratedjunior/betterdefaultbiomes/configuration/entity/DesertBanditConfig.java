package xratedjunior.betterdefaultbiomes.configuration.entity;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import xratedjunior.betterdefaultbiomes.configuration.MobSpawningConfig;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class DesertBanditConfig {
	public static ForgeConfigSpec.IntValue weight;
	public static ForgeConfigSpec.ConfigValue<List<String>> spawn_biomes;

	private static String mobName = "Desert Bandits";

	private static int spawnWeight = 70;
	private static int mobSpawnWeightMin = MobSpawningConfig.mobSpawnWeightMin;
	private static int mobSpawnWeightMax = MobSpawningConfig.mobSpawnWeightMax;
	private static List<String> spawnBiomes = Lists.newArrayList("hot & sandy");

	public static void init(ForgeConfigSpec.Builder builder) {
		weight = builder
			.comment(EntityConfigHelper.weightComment(mobName, spawnWeight))
			.defineInRange("Desert_Bandit.weight", spawnWeight, mobSpawnWeightMin, mobSpawnWeightMax);
		
		spawn_biomes = builder
			.comment(EntityConfigHelper.spawnBiomesComment(mobName))
			.define("Desert_Bandit.spawn_biomes", spawnBiomes);
	}
}
