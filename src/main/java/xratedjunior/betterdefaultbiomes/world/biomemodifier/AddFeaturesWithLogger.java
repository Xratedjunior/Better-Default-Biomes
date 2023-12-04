package xratedjunior.betterdefaultbiomes.world.biomemodifier;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.configuration.DebugConfig;
import xratedjunior.betterdefaultbiomes.world.BDBBiomeModifiers;

/**
 * COPY: {@link AddFeaturesBiomeModifier}
 * 
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.1
 */
public record AddFeaturesWithLogger(HolderSet<Biome> biomes, Optional<HolderSet<Biome>> excludedBiomes, HolderSet<PlacedFeature> features, Decoration step) implements BiomeModifier {

	public static final Codec<AddFeaturesWithLogger> CODEC = RecordCodecBuilder.create(builder -> builder.group( //
			Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddFeaturesWithLogger::biomes), // Biomes to add features to.
			Biome.LIST_CODEC.optionalFieldOf("excluded_biomes").forGetter(modifier -> modifier.excludedBiomes.isEmpty() ? Optional.empty() : Optional.of(modifier.excludedBiomes.get())), // Biomes to add features to.
			PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(AddFeaturesWithLogger::features), // PlacedFeatures to add to biomes.
			Decoration.CODEC.fieldOf("step").forGetter(AddFeaturesWithLogger::step) // Decoration step to run features in.
	).apply(builder, AddFeaturesWithLogger::new));

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		// Check correct modifier phase
		if (phase == Phase.ADD && this.biomes.contains(biome)) {

			// Check excluded Biomes
			if (!this.excludedBiomes.isEmpty()) {
				if (this.excludedBiomes.get().contains(biome)) {
					this.features.forEach((featureHolder) -> {
						// Output to log
						if (DebugConfig.generation_logger.get()) {
							BetterDefaultBiomes.LOGGER.info("The \"{}\" is excluded from: {}.", featureHolder.unwrapKey().get().location().getPath().toUpperCase(), biome.unwrapKey().get().location());
						}
					});
					// Don't add features
					return;
				}
			}

			// Continue for matching Biomes
			else {
				BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
				this.features.forEach((featureHolder) -> {
					// Output to log
					if (DebugConfig.generation_logger.get()) {
						BetterDefaultBiomes.LOGGER.info("The \"{}\" generates in the: {}.", featureHolder.unwrapKey().get().location().getPath().toUpperCase(), biome.unwrapKey().get().location());
					}
					// Add feature
					generationSettings.addFeature(this.step, featureHolder);
				});
			}
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return BDBBiomeModifiers.ADD_FEATURES_CODEC.get();
	}
}
