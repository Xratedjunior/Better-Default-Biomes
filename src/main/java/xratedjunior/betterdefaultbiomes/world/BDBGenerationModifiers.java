package xratedjunior.betterdefaultbiomes.world;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.compress.utils.Lists;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.data.BDBTags;
import xratedjunior.betterdefaultbiomes.world.biomemodifier.AddFeaturesWithLogger;
import xratedjunior.betterdefaultbiomes.world.generation.BDBPlacedFeatures;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.1
 */
public class BDBGenerationModifiers {
	/*********************************************************** Configured Plants ********************************************************/

	//	Grass Features
	public static final ResourceKey<BiomeModifier> FEATHER_REED_GRASS = createKey("add_patch_feather_reed_grass");
	public static final ResourceKey<BiomeModifier> DEAD_GRASS = createKey("add_patch_dead_grass");
	public static final ResourceKey<BiomeModifier> SHORT_GRASS = createKey("add_patch_short_grass");
	public static final ResourceKey<BiomeModifier> DUNE_GRASS = createKey("add_patch_dune_grass");

	//	Water Features
	public static final ResourceKey<BiomeModifier> WATER_REEDS = createKey("add_patch_water_reeds");

	/*********************************************************** Flowers ********************************************************/

	public static final ResourceKey<BiomeModifier> PINK_CACTUS_FLOWER = createKey("add_patch_pink_cactus_flower");
	public static final ResourceKey<BiomeModifier> PURPLE_VERBENA = createKey("add_patch_purple_verbena");
	public static final ResourceKey<BiomeModifier> BLUE_POPPY = createKey("add_patch_blue_poppy");
	public static final ResourceKey<BiomeModifier> DARK_VIOLET = createKey("add_patch_dark_violet");

	/*********************************************************** Configured Mushrooms ********************************************************/

	//	Mushrooms
	public static final ResourceKey<BiomeModifier> WHITE_MUSHROOM = createKey("add_patch_white_mushroom");
	public static final ResourceKey<BiomeModifier> YELLOW_MUSHROOM = createKey("add_patch_yellow_mushroom");
	public static final ResourceKey<BiomeModifier> GRAY_MUSHROOM = createKey("add_patch_gray_mushroom");

	//	Big Mushrooms
	public static final ResourceKey<BiomeModifier> BIG_WHITE_MUSHROOM = createKey("add_big_white_mushroom");
	public static final ResourceKey<BiomeModifier> BIG_YELLOW_MUSHROOM = createKey("add_big_yellow_mushroom");
	public static final ResourceKey<BiomeModifier> BIG_GRAY_MUSHROOM = createKey("add_big_gray_mushroom");

	/*********************************************************** Configured Small Features ********************************************************/

	public static final ResourceKey<BiomeModifier> SMALL_ROCK = createKey("add_small_rock");
	public static final ResourceKey<BiomeModifier> SMALL_ROCK_UNDERGROUND = createKey("add_small_rock_underground");
	public static final ResourceKey<BiomeModifier> PINECONE = createKey("add_pinecone");
	public static final ResourceKey<BiomeModifier> SAND_CASTLE = createKey("add_sand_castle");
	public static final ResourceKey<BiomeModifier> SEASHELL = createKey("add_seashell");

	//	Starfish
	public static final ResourceKey<BiomeModifier> STARFISH = createKey("add_starfish");
	public static final ResourceKey<BiomeModifier> STARFISH_CORAL = createKey("add_starfish_coral");

	/*********************************************************** Configured Trees ********************************************************/

	public static final ResourceKey<BiomeModifier> PALM_TREE = createKey("add_palm_tree");
	public static final ResourceKey<BiomeModifier> SWAMP_WILLOW_TREE = createKey("add_swamp_willow_tree");

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);

		List<Holder.Reference<Biome>> mushroomFieldsExcluded = biomeList(biomeGetter, Arrays.asList(Biomes.MUSHROOM_FIELDS));

		/*********************************************************** Plants ********************************************************/
		//	Grass Features
		addFeature(context, FEATHER_REED_GRASS, BDBPlacedFeatures.FEATHER_REED_GRASS_FEATURE, BDBTags.Biomes.FEATHER_REED_GRASS, placedFeatures, biomeGetter);
		addFeatureWithExclusion(context, DEAD_GRASS, BDBPlacedFeatures.DEAD_GRASS_FEATURE, BDBTags.Biomes.DEAD_GRASS, mushroomFieldsExcluded, placedFeatures, biomeGetter);
		addFeatureWithExclusion(context, SHORT_GRASS, BDBPlacedFeatures.SHORT_GRASS_FEATURE, BDBTags.Biomes.SHORT_GRASS, mushroomFieldsExcluded, placedFeatures, biomeGetter);
		addFeature(context, DUNE_GRASS, BDBPlacedFeatures.DUNE_GRASS_FEATURE, BDBTags.Biomes.DUNE_GRASS, placedFeatures, biomeGetter);

		//	Water Features
		addFeatureWithExclusion(context, WATER_REEDS, BDBPlacedFeatures.WATER_REEDS_FEATURE, BDBTags.Biomes.WATER_REEDS, mushroomFieldsExcluded, placedFeatures, biomeGetter);

		/*********************************************************** Flowers ********************************************************/
		addFeature(context, PINK_CACTUS_FLOWER, BDBPlacedFeatures.PINK_CACTUS_FLOWER_FEATURE, BDBTags.Biomes.PINK_CACTUS_FLOWER, placedFeatures, biomeGetter);
		addFeature(context, PURPLE_VERBENA, BDBPlacedFeatures.PURPLE_VERBENA_FEATURE, BDBTags.Biomes.PURPLE_VERBENA, placedFeatures, biomeGetter);
		addFeature(context, BLUE_POPPY, BDBPlacedFeatures.BLUE_POPPY_FEATURE, BDBTags.Biomes.BLUE_POPPY, placedFeatures, biomeGetter);
		addFeature(context, DARK_VIOLET, BDBPlacedFeatures.DARK_VIOLET_FEATURE, BDBTags.Biomes.DARK_VIOLET, placedFeatures, biomeGetter);

		/*********************************************************** Mushrooms ********************************************************/
		//	Mushrooms
		addFeature(context, WHITE_MUSHROOM, BDBPlacedFeatures.WHITE_MUSHROOM_FEATURE, BDBTags.Biomes.WHITE_MUSHROOM, placedFeatures, biomeGetter);
		addFeature(context, YELLOW_MUSHROOM, BDBPlacedFeatures.YELLOW_MUSHROOM_FEATURE, BDBTags.Biomes.YELLOW_MUSHROOM, placedFeatures, biomeGetter);
		addFeature(context, GRAY_MUSHROOM, BDBPlacedFeatures.GRAY_MUSHROOM_FEATURE, BDBTags.Biomes.GRAY_MUSHROOM, placedFeatures, biomeGetter);

		//	Big Mushrooms
		addFeature(context, BIG_WHITE_MUSHROOM, BDBPlacedFeatures.BIG_WHITE_MUSHROOM_FEATURE, BDBTags.Biomes.BIG_WHITE_MUSHROOM, placedFeatures, biomeGetter);
		addFeature(context, BIG_YELLOW_MUSHROOM, BDBPlacedFeatures.BIG_YELLOW_MUSHROOM_FEATURE, BDBTags.Biomes.BIG_YELLOW_MUSHROOM, placedFeatures, biomeGetter);
		addFeature(context, BIG_GRAY_MUSHROOM, BDBPlacedFeatures.BIG_GRAY_MUSHROOM_FEATURE, BDBTags.Biomes.BIG_GRAY_MUSHROOM, placedFeatures, biomeGetter);

		/*********************************************************** Small Features ********************************************************/
		addFeatureWithExclusion(context, SMALL_ROCK, BDBPlacedFeatures.SMALL_ROCK_FEATURE, BDBTags.Biomes.SMALL_ROCK, mushroomFieldsExcluded, placedFeatures, biomeGetter);
		addFeature(context, SMALL_ROCK_UNDERGROUND, BDBPlacedFeatures.SMALL_ROCK_UNDERGROUND_FEATURE, BDBTags.Biomes.SMALL_ROCK, placedFeatures, biomeGetter);
		addFeature(context, PINECONE, BDBPlacedFeatures.PINECONE_FEATURE, BDBTags.Biomes.PINECONE, placedFeatures, biomeGetter);
		addFeature(context, SEASHELL, BDBPlacedFeatures.SEASHELL_FEATURE, BDBTags.Biomes.SEASHELL, placedFeatures, biomeGetter);
		addFeature(context, SAND_CASTLE, BDBPlacedFeatures.SAND_CASTLE_FEATURE, BDBTags.Biomes.SAND_CASTLE, placedFeatures, biomeGetter);
		//	Starfish
		addFeature(context, STARFISH, BDBPlacedFeatures.STARFISH_FEATURE, BDBTags.Biomes.STARFISH, placedFeatures, biomeGetter);
		addFeature(context, STARFISH_CORAL, BDBPlacedFeatures.STARFISH_CORAL_FEATURE, BDBTags.Biomes.STARFISH, placedFeatures, biomeGetter);

		/*********************************************************** Trees ********************************************************/
		addFeature(context, PALM_TREE, BDBPlacedFeatures.PALM_TREE_FEATURE, BDBTags.Biomes.PALM_TREE, placedFeatures, biomeGetter);
		addFeature(context, SWAMP_WILLOW_TREE, BDBPlacedFeatures.SWAMP_WILLOW_TREE_FEATURE, BDBTags.Biomes.SWAMP_WILLOW_TREE, placedFeatures, biomeGetter);
	}

	/*********************************************************** Helper Methods ********************************************************/

	private static ResourceKey<BiomeModifier> createKey(String name) {
		return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, BetterDefaultBiomes.locate(name));
	}
	

	/**
	 * Helper method for registering most features
	 * 
	 * @param biomeTag can be a Minecraft {@link BiomeTags} or Forge {@link Tags.Biomes}
	 */
	private static void addFeature(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> name, ResourceKey<PlacedFeature> feature, TagKey<Biome> biomeTag, HolderGetter<PlacedFeature> featureRegistry, HolderGetter<Biome> biomeRegistry) {
		context.register(name, new AddFeaturesWithLogger( //
				biomeRegistry.getOrThrow(biomeTag), // Biomes to add Feature
				Optional.empty(), // Biomes to exclude Feature
				HolderSet.direct(featureRegistry.getOrThrow(feature)), // Feature to generate
				GenerationStep.Decoration.VEGETAL_DECORATION // Generation Step
		));
	}

	private static void addFeatureWithExclusion(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> name, ResourceKey<PlacedFeature> feature, TagKey<Biome> biomeTag, List<Holder.Reference<Biome>> excludedBiomes, HolderGetter<PlacedFeature> featureRegistry, HolderGetter<Biome> biomeRegistry) {
		context.register(name, new AddFeaturesWithLogger( //
				biomeRegistry.getOrThrow(biomeTag), // Biomes to add Feature
				Optional.of(HolderSet.direct(excludedBiomes)), // Biomes to exclude Feature
				HolderSet.direct(featureRegistry.getOrThrow(feature)), // Feature to generate
				GenerationStep.Decoration.VEGETAL_DECORATION // Generation Step
		));
	}

	private static List<Holder.Reference<Biome>> biomeList(HolderGetter<Biome> biomeGetter, List<ResourceKey<Biome>> biomeList) {
		List<Holder.Reference<Biome>> list = Lists.newArrayList();
		biomeList.forEach(biome -> list.add(biomeGetter.getOrThrow(biome)));
		return list;
	}
}
