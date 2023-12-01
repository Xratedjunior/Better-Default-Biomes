package xratedjunior.betterdefaultbiomes.configuration.entity;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import xratedjunior.betterdefaultbiomes.configuration.MobSpawningConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.util.EntityConfigHelper;
import xratedjunior.betterdefaultbiomes.configuration.entity.util.MobConfig;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class ZebraConfig implements MobConfig {
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
	private static List<String> spawnBiomes = Lists.newArrayList("minecraft:is_savanna", "minecraft:plains");

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
	
	/*********************************************************** Implementation ********************************************************/
	
	@Override
	public int getWeight() {
		return weight.get();
	}

	@Override
	public int getMinGroup() {
		return min_group.get();
	}

	@Override
	public int getMaxGroup() {
		return max_group.get();
	}

	@Override
	public List<String> getSpawnBiomes() {
		return spawn_biomes.get();
	}
}
