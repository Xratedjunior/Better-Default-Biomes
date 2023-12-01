package xratedjunior.betterdefaultbiomes.world.biomemodifier;

import java.util.List;

import com.mojang.serialization.Codec;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
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
import xratedjunior.betterdefaultbiomes.configuration.entity.util.MobConfig;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.util.BDBHelper;
import xratedjunior.betterdefaultbiomes.world.BDBBiomeModifiers;

/**
 * REFERENCE: {@linkplain https://github.com/starfish-studios/Naturalist/blob/1.19.2/forge/src/main/java/com/starfish_studios/naturalist/world/forge/AddAnimalsBiomeModifier.java}
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public record AddSpawnsWithConfig() implements BiomeModifier {
	public static final Codec<AddSpawnsWithConfig> CODEC = Codec.unit(AddSpawnsWithConfig::new);

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD) {
			// Get debug spawn logger from config
			boolean spawnLogger = DebugConfig.spawn_logger.get();
			boolean allSpawnLogger = DebugConfig.spawn_logger_all.get();

			/*********************************************************** Hostile ********************************************************/

			// TODO Spawn Lost Miner in structures instead of Biomes
			addSpawn(builder, biome, BDBEntityTypes.LOST_MINER.get(), MobSpawningConfig.spawn_lost_miners.get(), new LostMinerConfig(), allSpawnLogger);
			addSpawn(builder, biome, BDBEntityTypes.HUNTER.get(), MobSpawningConfig.spawn_hunters.get(), new HunterConfig(), spawnLogger);
			addSpawn(builder, biome, BDBEntityTypes.HEAD_HUNTER.get(), (MobSpawningConfig.spawn_hunters.get() && HunterConfig.spawn_head_hunter.get()), HunterConfig.spawn_biomes.get(), HunterConfig.head_hunter_weight.get(), 1, 1, spawnLogger);
			addSpawn(builder, biome, BDBEntityTypes.JUNGLE_CREEPER.get(), MobSpawningConfig.spawn_jungle_creepers.get(), new JungleCreeperConfig(), spawnLogger);
			addSpawn(builder, biome, BDBEntityTypes.DESERT_BANDIT.get(), MobSpawningConfig.spawn_desert_bandits.get(), new DesertBanditConfig(), spawnLogger);
			addSpawn(builder, biome, BDBEntityTypes.FROZEN_ZOMBIE.get(), MobSpawningConfig.spawn_frozen_zombie.get(), new FrozenZombieConfig(), spawnLogger);

			/*********************************************************** Passive ********************************************************/

			addSpawn(builder, biome, BDBEntityTypes.MUDDY_PIG.get(), MobSpawningConfig.spawn_muddy_pig.get(), new MuddyPigConfig(), spawnLogger);
			addSpawn(builder, biome, BDBEntityTypes.CAMEL.get(), MobSpawningConfig.spawn_camel.get(), new CamelConfig(), spawnLogger);
			addSpawn(builder, biome, BDBEntityTypes.DUCK.get(), MobSpawningConfig.spawn_duck.get(), new DuckConfig(), spawnLogger);
			addSpawn(builder, biome, BDBEntityTypes.ZEBRA.get(), MobSpawningConfig.spawn_zebra.get(), new ZebraConfig(), spawnLogger);
			addSpawn(builder, biome, BDBEntityTypes.FROG.get(), MobSpawningConfig.spawn_frog.get(), new FrogConfig(), spawnLogger);
		}
	}

	/*********************************************************** Codec ********************************************************/

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return BDBBiomeModifiers.ADD_SPAWNS_CODEC.get();
	}

	/*********************************************************** Helper Methods ********************************************************/

	/**
	 * Helper method for adding Mobs to Biomes.
	 */
	private <MC extends MobConfig> void addSpawn(Builder builder, Holder<Biome> biome, EntityType<?> entityType, boolean spawnMob, MC mobConfig, boolean spawnLogger) {
		addSpawn(builder, biome, entityType, spawnMob, mobConfig.getSpawnBiomes(), mobConfig.getWeight(), mobConfig.getMinGroup(), mobConfig.getMaxGroup(), spawnLogger);
	}

	private static void addSpawn(Builder builder, Holder<Biome> biome, EntityType<?> entityType, boolean spawnMob, List<String> spawn_biomes, int weight, int minGroup, int maxGroup, boolean spawnLogger) {
		if (spawnMob) {
			if (BDBHelper.matchBiomeOrTag(biome, spawn_biomes) && weight > 0) {
				// Check for correct values.
				if (minGroup <= maxGroup) {
					addSpawn(builder, biome, entityType, weight, minGroup, maxGroup, spawnLogger);
				}

				// Swap values if they are not correct.
				else {
					addSpawn(builder, biome, entityType, weight, maxGroup, minGroup, spawnLogger);

					// Output debug warning for players to know.
					BetterDefaultBiomes.LOGGER.warn("The minGroup in the Config file is bigger than the maxGroup for the \"{}\".", entityType.toShortString());
					BetterDefaultBiomes.LOGGER.warn("Normally this would cause the game to crash, but we switched the values around.");
				}
			}
		}
	}

	private static void addSpawn(Builder builder, Holder<Biome> biome, EntityType<?> entityType, int weight, int minGroup, int maxGroup, boolean spawnLogger) {
		builder.getMobSpawnSettings().addSpawn(entityType.getCategory(), new MobSpawnSettings.SpawnerData(entityType, weight, minGroup, maxGroup));

		// Show log info if switched on by the player in the config.
		if (spawnLogger) {
			String spawnProperties = "Weight: " + weight + ", Min Group: " + minGroup + ", Max Group: " + maxGroup;
			BetterDefaultBiomes.LOGGER.info("The " + entityType.toShortString().toUpperCase() + " spawns in the: " + biome.unwrapKey().get().location().toString() + ". (" + spawnProperties + ")");
		}
	}
}
