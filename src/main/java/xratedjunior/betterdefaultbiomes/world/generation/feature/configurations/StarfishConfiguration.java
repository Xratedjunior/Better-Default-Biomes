package xratedjunior.betterdefaultbiomes.world.generation.feature.configurations;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class StarfishConfiguration implements FeatureConfiguration {
	public static final Codec<StarfishConfiguration> CODEC = RecordCodecBuilder.create(builder -> builder.group( //
			DyeColor.CODEC.listOf().fieldOf("generation_colors").forGetter(config -> config.generationColors) // Colors of Starfish that will naturally generate.
	).apply(builder, StarfishConfiguration::new));
	
	private final List<DyeColor> generationColors;

	public StarfishConfiguration(List<DyeColor> biomes) {
		this.generationColors = biomes;
	}

	public StarfishConfiguration() {
		this(Lists.newArrayList(DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE, DyeColor.PINK, DyeColor.LIME, DyeColor.BLUE, DyeColor.RED));
	}
	
	/*********************************************************** Getters ********************************************************/
	
	public List<DyeColor> getGenerationColors() {
		return this.generationColors;
	}
}
