package xratedjunior.betterdefaultbiomes.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class TradingConfig {
	private static boolean tradingDefault = true;
	public static ForgeConfigSpec.BooleanValue enable_trades;

	public static void init(ForgeConfigSpec.Builder builder) {
		builder
			.comment("Trading configurations for Better Default Biomes. (Default: \"true\" for all settings.)")
			.push("Trading");
		
		enable_trades = builder
			.comment("Enable trades added by Better Default Biomes.")
			.define("enable_trades", tradingDefault);
		
		builder.pop();
	}
}
