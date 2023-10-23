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
public class FrozenZombieConfig {
	public static ForgeConfigSpec.IntValue weight;
	public static ConfigValue<Integer> min_group;
	public static ConfigValue<Integer> max_group;
	public static ForgeConfigSpec.BooleanValue remove_vanilla_zombies;
	public static ForgeConfigSpec.ConfigValue<List<String>> spawn_biomes;

	private static String mobName = "Frozen Zombie";

	private static int spawnWeight = 95;
	private static int mobSpawnWeightMin = MobSpawningConfig.mobSpawnWeightMin;
	private static int mobSpawnWeightMax = MobSpawningConfig.mobSpawnWeightMax;
	private static int minGroup = 4;
	private static int maxGroup = 4;
	private static List<String> spawnBiomes = Lists.newArrayList("coniferous", "snowy");

	private static boolean removeVanillaZombies = true;
	private static String vanillaMobName = "Zombie";

	public static void init(ForgeConfigSpec.Builder builder) {
		weight = builder
			//.comment("Spawn weight for the Frozen Zombie (Default: " + spawnWeight + ")")
			.comment(EntityConfigHelper.weightComment(mobName, spawnWeight))
			.defineInRange("Frozen_Zombie.weight", spawnWeight, mobSpawnWeightMin, mobSpawnWeightMax);
		
		min_group = builder
			//.comment("Minimum amount of Frozen Zombies to spawn in a group (Default: " + minGroup + ")")
			.comment(EntityConfigHelper.minGroupComment(mobName, minGroup))
			.define("Frozen_Zombie.min_group", minGroup);
		
		max_group = builder
			//.comment("Maximum amount of Frozen Zombies to spawn in a group (Default: " + maxGroup + ")")
			.comment(EntityConfigHelper.maxGroupComment(mobName, maxGroup))
			.define("Frozen_Zombie.max_group", maxGroup);
		
		spawn_biomes = builder
			//.comment("Spawn Biomes/BiomeTypes where the Frozen Zombie will spawn.")
			.comment(EntityConfigHelper.spawnBiomesComment(mobName))
			.define("Frozen_Zombie.spawn_biomes", spawnBiomes);
		
		remove_vanilla_zombies = builder
			//.comment("Removes Vanilla Zombie Spawning in these Biomes (Default: " + removeVanillaZombies + ")")
			.comment(EntityConfigHelper.removeVanillaMobComment(vanillaMobName, removeVanillaZombies))
			.define("Frozen_Zombie.remove_vanilla_zombies", removeVanillaZombies);
	}
}
