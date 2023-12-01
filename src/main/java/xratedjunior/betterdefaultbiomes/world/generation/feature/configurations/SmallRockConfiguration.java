package xratedjunior.betterdefaultbiomes.world.generation.feature.configurations;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import xratedjunior.betterdefaultbiomes.block.block.SmallRockBlock.RockVariant;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class SmallRockConfiguration implements FeatureConfiguration {
	public static final Codec<SmallRockConfiguration> CODEC = RecordCodecBuilder.create(builder -> builder.group( //
            // Choose the default variant for Small Rocks to generate. (Default: "stone")
            // Note: This will overwrite the variants that have been turned off.
			RockVariant.CODEC.fieldOf("default_variant").forGetter(config -> config.defaultVariant), //
			RockVariant.CODEC.listOf().fieldOf("stone_variants").forGetter(config -> config.stoneVariants), // Variants to place randomly.
			RockVariant.CODEC.listOf().fieldOf("all_variants").forGetter(config -> config.allVariants) // Variants to match with ground Blocks.
	).apply(builder, SmallRockConfiguration::new));

	private final RockVariant defaultVariant;
	private final List<RockVariant> stoneVariants;
	private final List<RockVariant> allVariants;

	public SmallRockConfiguration(RockVariant defaultVariant, List<RockVariant> stoneVariants, List<RockVariant> allVariants) {
		this.defaultVariant = defaultVariant;
		this.stoneVariants = stoneVariants;
		this.allVariants = allVariants;
	}

	public SmallRockConfiguration() {
		this(RockVariant.DEFAULT_VARIANT, List.of(RockVariant.STONE_VARIANTS), List.of(RockVariant.ALL_VARIANTS));
	}

	/*********************************************************** Getters ********************************************************/

	public RockVariant getDefaultVariant() {
		return this.defaultVariant;
	}
	
	public List<RockVariant> getPossibleStoneVariants() {
		return this.stoneVariants;
	}
	
	public List<RockVariant> getAllPossibleVariants() {
		return this.allVariants;
	}
}
