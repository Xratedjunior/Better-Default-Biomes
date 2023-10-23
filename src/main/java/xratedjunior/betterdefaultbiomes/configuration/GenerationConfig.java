package xratedjunior.betterdefaultbiomes.configuration;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.block.SmallRockBlock.RockVariant;
import xratedjunior.betterdefaultbiomes.configuration.generation.flower.BluePoppyConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.flower.DarkVioletConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.flower.PinkCactusFlowerConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.flower.PurpleVerbenaConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.mushroom.MushroomConfigs;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.PineconeConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.SandCastleConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.SeaShellConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.SmallRocksConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.other.StarfishConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.DeadGrassConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.DuneGrassConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.FeatherReedGrassConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.ShortGrassConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.plant.TallWaterReedsConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.tree.PalmTreeConfig;
import xratedjunior.betterdefaultbiomes.configuration.generation.tree.SwampWillowTreeConfig;
import xratedjunior.betterdefaultbiomes.world.generation.feature.feature.StarfishFeature;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID, bus = Bus.MOD)
public class GenerationConfig {
	// Try to never change this
	public static final String CONFIG_FILE_NAME = "generation";

	public static int maxConfigCount = Integer.MAX_VALUE;

	public static class Common {

		public Common(ForgeConfigSpec.Builder builder) {

			/*********************************************************** Generation Features ********************************************************/

			builder.comment(new String[] {
				"Extensive Config options for all the Generation Features.",
				"For more information check the wiki."
			}).push("Generation");

			builder.push("Trees");
				PalmTreeConfig.init(builder);
				SwampWillowTreeConfig.init(builder);
			builder.pop();

			builder.push("Plants");
				FeatherReedGrassConfig.init(builder);
				DeadGrassConfig.init(builder);
				ShortGrassConfig.init(builder);
				DuneGrassConfig.init(builder);
				TallWaterReedsConfig.init(builder);
			builder.pop();

			builder.push("Flowers");
				PinkCactusFlowerConfig.init(builder);
				PurpleVerbenaConfig.init(builder);
				BluePoppyConfig.init(builder);
				DarkVioletConfig.init(builder);
			builder.pop();

			MushroomConfigs.init(builder);

			builder.push("Small_Features");
				SmallRocksConfig.init(builder);
				StarfishConfig.init(builder);
				SeaShellConfig.init(builder);
				PineconeConfig.init(builder);
				SandCastleConfig.init(builder);
			builder.pop();

			builder.pop();

			BetterDefaultBiomes.LOGGER.debug("Finished building the \"generation\" config");
		}
	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onConfigFileLoad(final ModConfigEvent.Loading configEvent) {
		BetterDefaultBiomes.LOGGER.debug("Loaded Better Default Biomes Worldgen Config.");
		updateGenerationValues();
	}

	@SubscribeEvent
	public static void onConfigFileChange(final ModConfigEvent.Reloading configEvent) {
		BetterDefaultBiomes.LOGGER.debug("Changed Better Default Biomes Worldgen Config.");
		updateGenerationValues();
	}

	/**
	 * Update values after loading or changing the config
	 */
	private static void updateGenerationValues() {
		BetterDefaultBiomes.LOGGER.debug("Initialising Small Rock settings from config.");
		RockVariant.setDefaultVariant();
		RockVariant.setAllPossibleVariants();
		RockVariant.setPossibleStoneVariants();

		// Load Starfish colors for generation from config file.
		StarfishFeature.starfishGenerationColorsInit();
	}
}
