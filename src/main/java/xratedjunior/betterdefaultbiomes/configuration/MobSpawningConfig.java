package xratedjunior.betterdefaultbiomes.configuration;

import net.minecraftforge.common.ForgeConfigSpec;
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

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class MobSpawningConfig {
	// Hostile
	public static ForgeConfigSpec.BooleanValue spawn_hunters;
	public static ForgeConfigSpec.BooleanValue spawn_jungle_creepers;
	public static ForgeConfigSpec.BooleanValue spawn_lost_miners;
	public static ForgeConfigSpec.BooleanValue spawn_desert_bandits;
	public static ForgeConfigSpec.BooleanValue spawn_frozen_zombie;
	public static ForgeConfigSpec.BooleanValue spawn_robin_hood;

	// Passive
	public static ForgeConfigSpec.BooleanValue spawn_muddy_pig;
	public static ForgeConfigSpec.BooleanValue spawn_camel;
	public static ForgeConfigSpec.BooleanValue spawn_duck;
	public static ForgeConfigSpec.BooleanValue spawn_zebra;
	public static ForgeConfigSpec.BooleanValue spawn_frog;

	private static boolean mobConfigDefault = true;
	
	// Referenced in all the Mob configs.
	public static int mobSpawnWeightMin = 0;
	public static int mobSpawnWeightMax = 10000;

	public static void init(ForgeConfigSpec.Builder builder) {	
		builder
			.comment("Define which mobs should spawn. (Default: \"" + mobConfigDefault + "\" for all mobs.)")
			.push("Mob_Spawning");
		
			spawn_hunters = builder.define("Hunter", mobConfigDefault);
			spawn_jungle_creepers = builder.define("Jungle_Creeper", mobConfigDefault);
			spawn_lost_miners = builder.define("Lost_Miner", mobConfigDefault);
			spawn_desert_bandits = builder.define("Desert_Bandit", mobConfigDefault);
			spawn_frozen_zombie = builder.define("Frozen_Zombie", mobConfigDefault);
			
			//Passive
			spawn_muddy_pig = builder.define("Muddy_Pig", mobConfigDefault);
			spawn_duck = builder.define("Duck", mobConfigDefault);
			spawn_zebra = builder.define("Zebra", mobConfigDefault);
			spawn_camel = builder
					.comment("Turned off by default since this is now a Minecraft feature.")
					.comment("You can choose to enable spawning by setting 'true'.")
					.define("Camel", false);
			spawn_frog = builder
					.comment("Turned off by default since this is now a Minecraft feature.")
					.comment("You can choose to enable spawning by setting 'true'.")
					.define("Frog", false);
		
			builder
			.comment("Extensive Config options for all the Mobs.")
			.push("Mobs");
				// Hostile
				HunterConfig.init(builder);
				JungleCreeperConfig.init(builder);
				LostMinerConfig.init(builder);
				DesertBanditConfig.init(builder);
				FrozenZombieConfig.init(builder);

				// Passive
				MuddyPigConfig.init(builder);
				DuckConfig.init(builder);
				ZebraConfig.init(builder);
				CamelConfig.init(builder);
				FrogConfig.init(builder);
				
			builder.pop();
		
		builder.pop();
	}
}
