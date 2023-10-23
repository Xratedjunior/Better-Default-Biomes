package xratedjunior.betterdefaultbiomes.configuration;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * Initiate the config files for BDB.
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID, bus = Bus.MOD)
public class BDBModConfig {
	private static Logger LOGGER = BetterDefaultBiomes.LOGGER;

	/**
	 * Build the common config file.
	 */
	public static class Common {
		public Common(ForgeConfigSpec.Builder builder) {
			/*********************************************************** General ********************************************************/

			MobSpawningConfig.init(builder);
			EnchantmentConfig.init(builder);
			ItemConfig.init(builder);
			TradingConfig.init(builder);

			/*********************************************************** Debug Options ********************************************************/

			DebugConfig.init(builder);
			LOGGER.debug("Finished building the \"betterdefaultbiomes\" config");
		}
	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	/*********************************************************** ModConfigEvents ********************************************************/

	@SubscribeEvent
	public static void onConfigFileLoad(final ModConfigEvent.Loading configEvent) {
		LOGGER.debug("Loaded Better Default Biomes Config");
	}

	/**
	 * Also runs during startup
	 */
	@SubscribeEvent
	public static void onConfigFileChange(final ModConfigEvent.Reloading configEvent) {
		LOGGER.debug("Changed Better Default Biomes Config");
	}
}
