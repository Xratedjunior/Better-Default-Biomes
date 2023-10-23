package xratedjunior.betterdefaultbiomes.world;

import java.util.Iterator;
import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.configuration.DebugConfig;
import xratedjunior.betterdefaultbiomes.configuration.MobSpawningConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.CamelConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.DesertBanditConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.DuckConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.FrogConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.FrozenZombieConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.HunterConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.JungleCreeperConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.LostMinerConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.MuddyPigConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.ZebraConfig;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.util.BDBHelper;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID)
public class BDBSpawns {

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void removeEntityWorldSpawns(BiomeLoadingEvent event) {
		Boolean removeSpawnsLogger = DebugConfig.remove_vanilla_spawn_logger.get();
		ResourceLocation biomeName = event.getName();
		Biome biome = ForgeRegistries.BIOMES.getValue(biomeName);
		if (biome != null) {
			/*********************************************************** Hostile ********************************************************/

			removeMobSpawn(event, biomeName, EntityType.ZOMBIE, FrozenZombieConfig.remove_vanilla_zombies.get(), MobSpawningConfig.spawn_frozen_zombie.get(), FrozenZombieConfig.spawn_biomes.get(), removeSpawnsLogger);

			/*********************************************************** Passive ********************************************************/

			removeMobSpawn(event, biomeName, EntityType.PIG, MuddyPigConfig.remove_vanilla_pigs.get(), MobSpawningConfig.spawn_muddy_pig.get(), MuddyPigConfig.spawn_biomes.get(), removeSpawnsLogger);
			removeMobSpawn(event, biomeName, EntityType.CHICKEN, DuckConfig.remove_vanilla_chickens.get(), MobSpawningConfig.spawn_duck.get(), DuckConfig.spawn_biomes.get(), removeSpawnsLogger);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void registerEntityWorldSpawns(BiomeLoadingEvent event) {
		// Get debug spawn logger from config
		boolean spawnLogger = DebugConfig.spawn_logger.get();
		boolean allSpawnLogger = DebugConfig.spawn_logger_all.get();

		ResourceLocation biomeName = event.getName();
		Biome biome = ForgeRegistries.BIOMES.getValue(biomeName);
		if (biome != null) {
			// TODO Extend all Entity Configs so we don't have these long lines of code. (Reference Feature Configs)
			/*********************************************************** Hostile ********************************************************/

			// TODO Spawn Lost Miner in structures instead of Biomes
			addMobSpawn(event, biomeName, BDBEntityTypes.LOST_MINER.get(), MobSpawningConfig.spawn_lost_miners.get(), LostMinerConfig.spawn_biomes.get(), LostMinerConfig.weight.get(), LostMinerConfig.min_group.get(), LostMinerConfig.max_group.get(), allSpawnLogger);
			addMobSpawn(event, biomeName, BDBEntityTypes.HUNTER.get(), MobSpawningConfig.spawn_hunters.get(), HunterConfig.spawn_biomes.get(), HunterConfig.weight.get(), HunterConfig.min_group.get(), HunterConfig.max_group.get(), spawnLogger);
			addMobSpawn(event, biomeName, BDBEntityTypes.HEAD_HUNTER.get(), MobSpawningConfig.spawn_hunters.get(), HunterConfig.spawn_biomes.get(), HunterConfig.head_hunter_weight.get(), 1, 1, spawnLogger);
			addMobSpawn(event, biomeName, BDBEntityTypes.JUNGLE_CREEPER.get(), MobSpawningConfig.spawn_jungle_creepers.get(), JungleCreeperConfig.spawn_biomes.get(), JungleCreeperConfig.weight.get(), JungleCreeperConfig.min_group.get(), JungleCreeperConfig.max_group.get(), spawnLogger);
			addMobSpawn(event, biomeName, BDBEntityTypes.DESERT_BANDIT.get(), MobSpawningConfig.spawn_desert_bandits.get(), DesertBanditConfig.spawn_biomes.get(), DesertBanditConfig.weight.get(), 1, 3, spawnLogger);
			addMobSpawn(event, biomeName, BDBEntityTypes.FROZEN_ZOMBIE.get(), MobSpawningConfig.spawn_frozen_zombie.get(), FrozenZombieConfig.spawn_biomes.get(), FrozenZombieConfig.weight.get(), FrozenZombieConfig.min_group.get(), FrozenZombieConfig.max_group.get(), spawnLogger);

			/*********************************************************** Passive ********************************************************/

			addMobSpawn(event, biomeName, BDBEntityTypes.MUDDY_PIG.get(), MobSpawningConfig.spawn_muddy_pig.get(), MuddyPigConfig.spawn_biomes.get(), MuddyPigConfig.weight.get(), MuddyPigConfig.min_group.get(), MuddyPigConfig.max_group.get(), spawnLogger);
			addMobSpawn(event, biomeName, BDBEntityTypes.CAMEL.get(), MobSpawningConfig.spawn_camel.get(), CamelConfig.spawn_biomes.get(), CamelConfig.weight.get(), CamelConfig.min_group.get(), CamelConfig.max_group.get(), spawnLogger);
			addMobSpawn(event, biomeName, BDBEntityTypes.DUCK.get(), MobSpawningConfig.spawn_duck.get(), DuckConfig.spawn_biomes.get(), DuckConfig.weight.get(), DuckConfig.min_group.get(), DuckConfig.max_group.get(), spawnLogger);
			addMobSpawn(event, biomeName, BDBEntityTypes.ZEBRA.get(), MobSpawningConfig.spawn_zebra.get(), ZebraConfig.spawn_biomes.get(), ZebraConfig.weight.get(), ZebraConfig.min_group.get(), ZebraConfig.max_group.get(), spawnLogger);
			addMobSpawn(event, biomeName, BDBEntityTypes.FROG.get(), MobSpawningConfig.spawn_frog.get(), FrogConfig.spawn_biomes.get(), FrogConfig.weight.get(), FrogConfig.min_group.get(), FrogConfig.max_group.get(), spawnLogger);

			// Return so error doesn't show for every Biome.
			return;
		}

		BetterDefaultBiomes.LOGGER.error("Could not match \"" + biomeName + "\" to any registered Biome.");
	}

	/**
	 * Helper method for removing Mobs from Biomes.
	 */
	private static void removeMobSpawn(BiomeLoadingEvent event, ResourceLocation biomeName, EntityType<?> entityType, boolean removeMob, boolean spawnBDB, List<String> spawn_biomes, boolean spawnLogger) {
		if (removeMob && spawnBDB) {
			if (BDBHelper.matchBiomeOrType(biomeName, spawn_biomes)) {
				Iterator<SpawnerData> biomeSpawns = event.getSpawns().getSpawner(entityType.getCategory()).iterator();
				while (biomeSpawns.hasNext()) {
					SpawnerData entry = biomeSpawns.next();
					if (entry.type.equals(entityType)) {
						biomeSpawns.remove();

						// Show log info if switched on by the player in the config.
						if (spawnLogger) {
							String entityName = entityType.getRegistryName().toString();
							BetterDefaultBiomes.LOGGER.info("Removed \"" + entityName.toUpperCase() + "\" Spawning in: " + event.getName().toString());
						}
					}
				}
			}
		}
	}

	/**
	 * Helper method for adding Mobs to Biomes.
	 */
	private static void addMobSpawn(BiomeLoadingEvent event, ResourceLocation biomeName, EntityType<?> entityType, boolean spawnMob, List<String> spawn_biomes, int weight, int minGroup, int maxGroup, boolean spawnLogger) {
		if (spawnMob) {
			if (BDBHelper.matchBiomeOrType(biomeName, spawn_biomes) && weight > 0) {
				String spawnProperties = "";
				String entityName = entityType.getRegistryName().toString();

				// Check for correct values.
				if (minGroup <= maxGroup) {
					event.getSpawns().getSpawner(entityType.getCategory()).add(new MobSpawnSettings.SpawnerData(entityType, weight, minGroup, maxGroup));

					// Create correct text for the 'spawnLogger'
					if (spawnLogger) {
						spawnProperties = "Weight: " + weight + ", Min Group: " + minGroup + ", Max Group: " + maxGroup;
					}
				}

				// Swap values if they are not correct.
				else {
					event.getSpawns().getSpawner(entityType.getCategory()).add(new MobSpawnSettings.SpawnerData(entityType, weight, maxGroup, minGroup));

					// Output debug log for players to know.
					BetterDefaultBiomes.LOGGER.debug("The minGroup in the Config file is bigger than the maxGroup for the \"{}\".", entityName);
					BetterDefaultBiomes.LOGGER.debug("Normally this would cause the game to crash, but we switched the values around.");

					// Create correct text for the 'spawnLogger'
					if (spawnLogger) {
						spawnProperties = "Weight: " + weight + ", Min Group: " + maxGroup + ", Max Group: " + minGroup;
					}
				}

				// Show log info if switched on by the player in the config.
				if (spawnLogger) {
					BetterDefaultBiomes.LOGGER.info("The " + entityName.toUpperCase() + " spawns in the: " + biomeName.toString() + ". (" + spawnProperties + ")");
				}
			}
		}
	}

}
