package xratedjunior.betterdefaultbiomes.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class ItemConfig {
	public static boolean itemsDefault = true;
	public static ForgeConfigSpec.BooleanValue torch_arrow_fire;

	public static void init(ForgeConfigSpec.Builder builder) {
		builder
			.comment("Item configurations for Better Default Biomes. (Default: \"true\" for all settings.)")
			.push("Item");

			builder.push("Torch_Arrow");
			torch_arrow_fire = builder
				.comment("Set Mobs/Players on fire when hit by a Torch Arrow.")
				.define("set_on_fire", itemsDefault);
			builder.pop();

		builder.pop();
	}
}
