package xratedjunior.betterdefaultbiomes.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.data.BDBTags;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBBiomeTagsProvider extends BiomeTagsProvider {

	public BDBBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, BetterDefaultBiomes.MOD_ID, existingFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider provider) {
		/*********************************************************** Plants ********************************************************/
		//	Grass Features
		this.tag(BDBTags.Biomes.FEATHER_REED_GRASS).add(Biomes.BEACH, Biomes.RIVER).addTags(BiomeTags.IS_JUNGLE, BiomeTags.IS_SAVANNA, BiomeTags.IS_FOREST).addOptionalTag(Tags.Biomes.IS_PLAINS.location()).addOptionalTag(Tags.Biomes.IS_SWAMP.location());
		this.tag(BDBTags.Biomes.DEAD_GRASS).addTag(BiomeTags.IS_OVERWORLD);
		this.tag(BDBTags.Biomes.SHORT_GRASS).addTag(BiomeTags.IS_OVERWORLD);
		this.tag(BDBTags.Biomes.DUNE_GRASS).add(Biomes.BEACH);
		//	Water Features
		this.tag(BDBTags.Biomes.WATER_REEDS).addTag(BiomeTags.IS_OVERWORLD);

		/*********************************************************** Flowers ********************************************************/
		this.tag(BDBTags.Biomes.PINK_CACTUS_FLOWER).add(Biomes.BADLANDS, Biomes.ERODED_BADLANDS).addOptionalTag(Tags.Biomes.IS_DESERT.location());
		this.tag(BDBTags.Biomes.PURPLE_VERBENA).add(Biomes.PLAINS).addTag(BiomeTags.IS_FOREST);
		this.tag(BDBTags.Biomes.BLUE_POPPY).addTag(BiomeTags.IS_MOUNTAIN);
		this.tag(BDBTags.Biomes.DARK_VIOLET).addTag(BiomeTags.IS_FOREST).addOptionalTag(Tags.Biomes.IS_SWAMP.location());

		/*********************************************************** Mushrooms ********************************************************/
		//	Mushrooms
		this.tag(BDBTags.Biomes.WHITE_MUSHROOM).add(Biomes.FOREST).addOptionalTag(Tags.Biomes.IS_SWAMP.location());
		this.tag(BDBTags.Biomes.YELLOW_MUSHROOM).add(Biomes.FOREST).addOptionalTag(Tags.Biomes.IS_SWAMP.location());
		this.tag(BDBTags.Biomes.GRAY_MUSHROOM).add(Biomes.FOREST).addOptionalTag(Tags.Biomes.IS_SWAMP.location());

		//	Big Mushrooms
		this.tag(BDBTags.Biomes.BIG_WHITE_MUSHROOM).addOptionalTag(Tags.Biomes.IS_SWAMP.location());
		this.tag(BDBTags.Biomes.BIG_YELLOW_MUSHROOM).addOptionalTag(Tags.Biomes.IS_SWAMP.location());
		this.tag(BDBTags.Biomes.BIG_GRAY_MUSHROOM).addOptionalTag(Tags.Biomes.IS_SWAMP.location());

		/*********************************************************** Small Features ********************************************************/
		this.tag(BDBTags.Biomes.SMALL_ROCK).addTag(BiomeTags.IS_OVERWORLD);
		this.tag(BDBTags.Biomes.PINECONE).addTag(BiomeTags.IS_FOREST).addOptionalTag(Tags.Biomes.IS_CONIFEROUS.location());
		this.tag(BDBTags.Biomes.SEASHELL).addTags(BiomeTags.IS_BEACH, BiomeTags.IS_OCEAN, BiomeTags.IS_RIVER);
		this.tag(BDBTags.Biomes.SAND_CASTLE).add(Biomes.BEACH, Biomes.OCEAN, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN);
		this.tag(BDBTags.Biomes.STARFISH).add(Biomes.BEACH, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN);

		/*********************************************************** Trees ********************************************************/
		this.tag(BDBTags.Biomes.PALM_TREE).add(Biomes.BEACH);
		this.tag(BDBTags.Biomes.SWAMP_WILLOW_TREE).addOptionalTag(Tags.Biomes.IS_SWAMP.location());
	}
}