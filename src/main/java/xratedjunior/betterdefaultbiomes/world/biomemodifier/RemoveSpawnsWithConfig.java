package xratedjunior.betterdefaultbiomes.world.biomemodifier;

import java.util.List;

import com.mojang.serialization.Codec;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.registries.ForgeRegistries;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.configuration.DebugConfig;
import xratedjunior.betterdefaultbiomes.configuration.MobSpawningConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.DuckConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.FrozenZombieConfig;
import xratedjunior.betterdefaultbiomes.configuration.entity.MuddyPigConfig;
import xratedjunior.betterdefaultbiomes.util.BDBHelper;
import xratedjunior.betterdefaultbiomes.world.BDBBiomeModifiers;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public record RemoveSpawnsWithConfig() implements BiomeModifier {
	public static final Codec<RemoveSpawnsWithConfig> CODEC = Codec.unit(RemoveSpawnsWithConfig::new);
	private static Boolean REMOVE_SPAWNS_LOGGER = false;

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.REMOVE) {
			// Get debug logger from config
			REMOVE_SPAWNS_LOGGER = DebugConfig.remove_vanilla_spawn_logger.get();

			MobSpawnSettingsBuilder spawnBuilder = builder.getMobSpawnSettings();
			for (MobCategory category : MobCategory.values()) {
				List<SpawnerData> spawns = spawnBuilder.getSpawner(category);
				spawns.removeIf(spawnerData -> {
					/*********************************************************** Hostile ********************************************************/

					if (removeSpawn(biome, spawnerData, EntityType.ZOMBIE, FrozenZombieConfig.remove_vanilla_zombies.get(), MobSpawningConfig.spawn_frozen_zombie.get(), FrozenZombieConfig.spawn_biomes.get(), REMOVE_SPAWNS_LOGGER)) {
						return true;
					} 
					
					/*********************************************************** Passive ********************************************************/
					
					else if (removeSpawn(biome, spawnerData, EntityType.PIG, MuddyPigConfig.remove_vanilla_pigs.get(), MobSpawningConfig.spawn_muddy_pig.get(), MuddyPigConfig.spawn_biomes.get(), REMOVE_SPAWNS_LOGGER)) {
						return true;
					} else if (removeSpawn(biome, spawnerData, EntityType.CHICKEN, DuckConfig.remove_vanilla_chickens.get(), MobSpawningConfig.spawn_duck.get(), DuckConfig.spawn_biomes.get(), REMOVE_SPAWNS_LOGGER)) {
						return true;
					}
					return false;
				});
			}
		}
	}

	/*********************************************************** Codec ********************************************************/

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return BDBBiomeModifiers.REMOVE_SPAWNS_CODEC.get();
	}

	/*********************************************************** Helper Methods ********************************************************/

	/**
	 * Helper method for removing Mobs from Biomes.
	 */
	private static boolean removeSpawn(Holder<Biome> biome, SpawnerData spawnerData, EntityType<?> entityType, boolean removeMob, boolean spawnBDB, List<String> spawn_biomes, boolean logger) {
		return removeMobSpawn(biome, spawnerData, entityType, removeMob, spawnBDB, spawn_biomes, logger);
	}

	/**
	 * Helper method for removing Mobs from Biomes.
	 */
	private static boolean removeMobSpawn(Holder<Biome> biome, SpawnerData spawnerData, EntityType<?> entityType, boolean removeMob, boolean spawnBDB, List<String> spawn_biomes, boolean logger) {
		if (removeMob && spawnBDB) {
			if (BDBHelper.matchBiomeOrTag(biome, spawn_biomes)) {
				if (entityType.equals(ForgeRegistries.ENTITY_TYPES.getHolder(spawnerData.type).get().get())) {
					// Show log info if switched on by the player in the config.
					if (logger) {
						String entityName = entityType.toShortString().toUpperCase();
						BetterDefaultBiomes.LOGGER.info("Removed \"" + entityName.toUpperCase() + "\" Spawning in: " + biome.unwrapKey().get().location().toString());
					}
					
					return true;
				}
			}
		}

		return false;
	}
}
