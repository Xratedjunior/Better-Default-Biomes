package xratedjunior.betterdefaultbiomes.datagen.providers;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.world.BDBBiomeModifiers;
import xratedjunior.betterdefaultbiomes.world.generation.BDBConfiguredFeatures;
import xratedjunior.betterdefaultbiomes.world.generation.BDBPlacedFeatures;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBWorldGenProvider extends DatapackBuiltinEntriesProvider {
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, (RegistrySetBuilder.RegistryBootstrap) BDBConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, BDBPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, BDBBiomeModifiers::bootstrap);

	public BDBWorldGenProvider(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries, BUILDER, Set.of(BetterDefaultBiomes.MOD_ID));
	}
}
