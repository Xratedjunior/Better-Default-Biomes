package xratedjunior.betterdefaultbiomes.world;

import com.google.common.base.Supplier;
import com.mojang.serialization.Codec;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.world.biomemodifier.AddFeaturesWithLogger;
import xratedjunior.betterdefaultbiomes.world.biomemodifier.AddSpawnsWithConfig;
import xratedjunior.betterdefaultbiomes.world.biomemodifier.RemoveSpawnsWithConfig;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBBiomeModifiers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> DEFERRED_BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, BetterDefaultBiomes.MOD_ID);

	public static final RegistryObject<Codec<AddSpawnsWithConfig>> ADD_SPAWNS_CODEC = register("add_spawns", () -> AddSpawnsWithConfig.CODEC);
	public static final RegistryObject<Codec<RemoveSpawnsWithConfig>> REMOVE_SPAWNS_CODEC = register("remove_spawns", () -> RemoveSpawnsWithConfig.CODEC);
	public static final RegistryObject<Codec<AddFeaturesWithLogger>> ADD_FEATURES_CODEC = register("add_features", () -> AddFeaturesWithLogger.CODEC);

	/**
	 * Data generation for Biome Modifiers
	 */
	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		BDBGenerationModifiers.bootstrap(context);
		BDBSpawnModifiers.bootstrap(context);
	}

	/*********************************************************** Helper Methods ********************************************************/

	/**
	 * Helper method for registering all Biome Modifiers
	 */
	private static <BM extends BiomeModifier> RegistryObject<Codec<BM>> register(String registryName, Supplier<Codec<BM>> codecSupplier) {
		return DEFERRED_BIOME_MODIFIER_SERIALIZERS.register(registryName, codecSupplier);
	}
}
