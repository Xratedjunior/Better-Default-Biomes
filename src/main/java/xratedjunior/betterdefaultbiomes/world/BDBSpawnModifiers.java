package xratedjunior.betterdefaultbiomes.world;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.world.biomemodifier.AddSpawnsWithConfig;
import xratedjunior.betterdefaultbiomes.world.biomemodifier.RemoveSpawnsWithConfig;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBSpawnModifiers {
	public static final ResourceKey<BiomeModifier> ADD_SPAWNS_WITH_CONFIG = registerKey("add_spawns_with_config");
	public static final ResourceKey<BiomeModifier> REMOVE_SPAWNS_WITH_CONFIG = registerKey("remove_spawns_with_config");

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		context.register(ADD_SPAWNS_WITH_CONFIG, new AddSpawnsWithConfig());
		context.register(REMOVE_SPAWNS_WITH_CONFIG, new RemoveSpawnsWithConfig());
	}

	/*********************************************************** Helper Methods ********************************************************/

	private static ResourceKey<BiomeModifier> registerKey(String name) {
		return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, BetterDefaultBiomes.locate(name));
	}
}
