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
public class HunterConfig {
	//Hunter
	public static ForgeConfigSpec.IntValue weight;
	public static ConfigValue<Integer> min_group;
	public static ConfigValue<Integer> max_group;
	public static ConfigValue<Integer> hunter_bow_drop_chance;
	public static ForgeConfigSpec.ConfigValue<List<String>> spawn_biomes;

	//Head Hunter
	public static ForgeConfigSpec.BooleanValue spawn_head_hunter;
	public static ForgeConfigSpec.IntValue head_hunter_weight;
	public static ConfigValue<Integer> head_hunter_firing_speed;
	public static ConfigValue<Integer> head_hunter_firing_speed_hard;

	//Default Values
	private static int spawnWeight = 90;
	private static int mobSpawnWeightMin = MobSpawningConfig.mobSpawnWeightMin;
	private static int mobSpawnWeightMax = MobSpawningConfig.mobSpawnWeightMax;
	private static int minGroup = 1;
	private static int maxGroup = 3;
	private static int hunterBowDropChance = 8;
	private static List<String> spawnBiomes = Lists.newArrayList("jungle");

	private static String mobName = "Hunter";
	private static String mobNameSpecial = "Head Hunter";

	//Default Head Hunter Values
	private static boolean spawnHeadHunter = true;
	private static int headHunterSpawnWeight = 10;
	private static int headHunterFiringSpeed = 20;
	private static int headHunterFiringSpeedHard = 10;

	public static void init(ForgeConfigSpec.Builder builder) {
		builder
			.comment("Config options for the Hunter and Variants.")
			.push("Hunter");
		
		weight = builder
			//.comment("Spawn weight for the Hunter (Default: " + spawnWeight + ")")
			.comment(EntityConfigHelper.weightComment(mobName, spawnWeight))
			.defineInRange("weight", spawnWeight, mobSpawnWeightMin, mobSpawnWeightMax);
		
		min_group = builder
			//.comment("Minimum amount of Hunters to spawn in a group (Default: " + minGroup + ")")
			.comment(EntityConfigHelper.minGroupComment(mobName, minGroup))
			.define("min_group", minGroup);
		
		max_group = builder
			//.comment("Maximum amount of Hunters to spawn in a group (Default: " + maxGroup + ")")
			.comment(EntityConfigHelper.maxGroupComment(mobName, maxGroup))
			.define("max_group", maxGroup);
		
		hunter_bow_drop_chance = builder
			.comment("Drop chance for the \"Hunter's Bow\" (Default: " + hunterBowDropChance + ")")
			.defineInRange("drop_chance", hunterBowDropChance, 0, 200);
		
		spawn_biomes = builder
			//.comment("Spawn Biomes/BiomeTypes where the Hunter will spawn.")
			.comment(EntityConfigHelper.spawnBiomesComment(mobName))
			.define("spawn_biomes", spawnBiomes);
		
		builder
			.comment("The Head Hunter is a special variant of the Hunter. It's even more deadly!")
			.push("Head_Hunter");
	
		spawn_head_hunter = builder
			.comment("Enables the spawning of the Head Hunter. (Default: " + spawnHeadHunter + ")")
			.define("head_hunter", spawnHeadHunter);
	
		head_hunter_weight = builder
			//.comment("Spawn weight for the Head Hunter. (Default: " + headHunterSpawnWeight + ")")
			.comment(EntityConfigHelper.weightComment(mobNameSpecial, headHunterSpawnWeight))
			.defineInRange("weight", headHunterSpawnWeight, mobSpawnWeightMin, mobSpawnWeightMax);
		
		builder
			.comment("This is the interval between shots.")
			.push("Firing_Speed");
		
		head_hunter_firing_speed = builder
			.comment(new String[] {
					"Tweak the firing speed of the Head Hunter. (Default: " + headHunterFiringSpeed + ")",
					"For reference: The Vanilla Skeleton firing speed is: 40.",
				})
			.define("firing_speed", headHunterFiringSpeed);
	
		head_hunter_firing_speed_hard = builder
			.comment(new String[] {
					"Tweak the firing speed of the Head Hunter for the Hard Difficulty. (Default: " + headHunterFiringSpeedHard + ")",
					"For reference: The Vanilla Skeleton firing speed for the Hard difficulty is: 20."
				})
			.define("firing_speed_hard", headHunterFiringSpeedHard);
	
		builder.pop();

		builder.pop(2);
	}
}
