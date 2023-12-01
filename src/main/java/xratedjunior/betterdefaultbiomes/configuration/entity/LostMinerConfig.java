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
public class LostMinerConfig implements MobConfig {
	public static ForgeConfigSpec.IntValue weight;
	public static ConfigValue<Integer> min_group;
	public static ConfigValue<Integer> max_group;
	public static ConfigValue<Integer> lost_miner_drop_chance;
	public static ForgeConfigSpec.ConfigValue<List<String>> spawn_biomes;

	private static String mobName = "Lost Miner";

	private static int spawnWeight = 160;
	private static int mobSpawnWeightMin = MobSpawningConfig.mobSpawnWeightMin;
	private static int mobSpawnWeightMax = MobSpawningConfig.mobSpawnWeightMax;
	private static int minGroup = 1;
	private static int maxGroup = 3;
	private static int lostMinerDropChance = 8;
	private static List<String> spawnBiomes = Lists.newArrayList("minecraft:is_overworld");

	public static void init(ForgeConfigSpec.Builder builder) {
		weight = builder
			//.comment("Spawn weight for the Lost Miner (Default: " + spawnWeight + ")")
			.comment(EntityConfigHelper.weightComment(mobName, spawnWeight))
			.defineInRange("Lost_Miner.weight", spawnWeight, mobSpawnWeightMin, mobSpawnWeightMax);
		
		min_group = builder
			//.comment("Minimum amount of Lost Miners to spawn in a group (Default: " + minGroup + ")")
			.comment(EntityConfigHelper.minGroupComment(mobName, minGroup))
			.define("Lost_Miner.min_group", minGroup);
		
		max_group = builder
			//.comment("Maximum amount of Lost Miners to spawn in a group (Default: " + maxGroup + ")")
			.comment(EntityConfigHelper.maxGroupComment(mobName, maxGroup))
			.define("Lost_Miner.max_group", maxGroup);
		
		lost_miner_drop_chance = builder
			.comment("Drop chance for the \"Miner's Pickaxe\" and \"Miner's Helmet\" (Default: " + lostMinerDropChance + ")")
			.defineInRange("Lost_Miner.drop_chance", lostMinerDropChance, 0, 200);
		
		spawn_biomes = builder
			.comment(EntityConfigHelper.spawnBiomesComment(mobName))
			.define("Lost_Miner.spawn_biomes", spawnBiomes);
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
