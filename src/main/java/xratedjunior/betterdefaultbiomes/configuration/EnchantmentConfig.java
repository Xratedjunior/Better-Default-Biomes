package xratedjunior.betterdefaultbiomes.configuration;

import net.minecraftforge.common.ForgeConfigSpec;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class EnchantmentConfig {
	public static boolean enchantmentsDefault = true;
	public static ForgeConfigSpec.BooleanValue smelting_touch;
	public static ForgeConfigSpec.BooleanValue smelting_touch_fire;
	public static ForgeConfigSpec.BooleanValue scout;
	public static ForgeConfigSpec.BooleanValue hunting;
	public static ForgeConfigSpec.BooleanValue horse_protection;
	public static ForgeConfigSpec.BooleanValue spikes;
	public static ForgeConfigSpec.BooleanValue guard;
	public static ForgeConfigSpec.BooleanValue floating;

	public static void init(ForgeConfigSpec.Builder builder) {
		builder
			.comment("Disable Enchantments from Better Default Biomes. (Default: \"true\" for all Enchantments.)")
			.push("Enchanting");
		
		builder
			.push("Smelting_Touch");
		smelting_touch = builder
			.comment(BDBEnchantments.getComment("smelting_touch"))
			.define("enable_smelting_touch", enchantmentsDefault);
		smelting_touch_fire = builder
			.comment("Sets Mobs/Players on fire when attacked.")
			.define("set_on_fire", enchantmentsDefault);
		builder.pop();
		
		scout = builder
			.comment(BDBEnchantments.getComment("scout"))
			.define("enable_scout", enchantmentsDefault);
		
		hunting = builder
			.comment(BDBEnchantments.getComment("hunting"))
			.define("enable_hunting", enchantmentsDefault);
		
		horse_protection = builder
			.comment(BDBEnchantments.getComment("horse_protection"))
			.define("enable_horse_protection", enchantmentsDefault);
		
		spikes = builder
			.comment(BDBEnchantments.getComment("spikes"))
			.define("enable_spikes", enchantmentsDefault);
		
		guard = builder
			.comment(BDBEnchantments.getComment("guard"))
			.define("enable_guard", enchantmentsDefault);
		
		floating = builder
			.comment(BDBEnchantments.getComment("floating"))
			.define("enable_floating", enchantmentsDefault);
		
		builder.pop();
	}
}
