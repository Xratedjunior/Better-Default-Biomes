package xratedjunior.betterdefaultbiomes.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.datagen.providers.BDBBiomeTagsProvider;
import xratedjunior.betterdefaultbiomes.datagen.providers.BDBBlockTagsProvider;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class BDBTags {

	/*********************************************************** Blocks ********************************************************/

	/**
	 * Generated from {@link BDBBlockTagsProvider}
	 */
	public static class Blocks {
		// Wood
		public static final TagKey<Block> WOODEN_LADDERS = bdbTag("wooden_ladders");
		public static final TagKey<Block> PALM_LOGS = bdbTag("palm_logs");
		public static final TagKey<Block> SWAMP_WILLOW_LOGS = bdbTag("swamp_willow_logs");

		// Plant decoration
		public static final TagKey<Block> MUSHROOMS = bdbTag("mushrooms");
		public static final TagKey<Block> PLANTS_REPLACEABLE = bdbTag("plants_replaceable");
		public static final TagKey<Block> SMALL_FLOWERS = bdbTag("small_flowers");
		public static final TagKey<Block> FLOWERS = bdbTag("flowers");
		public static final TagKey<Block> FLOWER_POTS = bdbTag("flower_pots");

		// Other Blocks
		public static final TagKey<Block> STARFISH = bdbTag("starfish");
		public static final TagKey<Block> STARFISH_WALL = bdbTag("starfish_wall");

		// Small Rock
		public static final TagKey<Block> SMALL_ROCKS = bdbTag("small_rocks");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_STONE = bdbTag("small_rock_generation/generate_stone");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_COBBLE = bdbTag("small_rock_generation/generate_cobble");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_MOSSY = bdbTag("small_rock_generation/generate_mossy");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_ANDESITE = bdbTag("small_rock_generation/generate_andesite");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_DIORITE = bdbTag("small_rock_generation/generate_diorite");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_GRANITE = bdbTag("small_rock_generation/generate_granite");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_SANDSTONE = bdbTag("small_rock_generation/generate_sandstone");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_RED_SANDSTONE = bdbTag("small_rock_generation/generate_red_sandstone");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_DEEPSLATE = bdbTag("small_rock_generation/generate_deepslate");
		public static final TagKey<Block> GENERATE_SMALL_ROCK_COBBLED_DEEPSLATE = bdbTag("small_rock_generation/generate_cobbled_deepslate");

		// Plant soil Blocks
		public static final TagKey<Block> PALM_SAPLING_SOIL = bdbTag("plant_soil/palm_sapling");
		public static final TagKey<Block> DEAD_GRASS_SOIL = bdbTag("plant_soil/dead_grass");
		public static final TagKey<Block> DUNE_GRASS_SOIL = bdbTag("plant_soil/dune_grass");
		public static final TagKey<Block> PINK_CACTUS_FLOWER_SOIL = bdbTag("plant_soil/pink_cactus_flower");

		// Ground replaceable
		public static final TagKey<Block> SWAMP_WILLOW_TREE_REPLACEABLES = bdbTag("swamp_willow_tree_replaceables");

		// Blocks Sand Castles & Starfish will generate on.
		public static final TagKey<Block> BEACH_SAND = bdbTag("beach_sand");

		/**
		 * Helper method for BDB Block Tags
		 */
		private static TagKey<Block> bdbTag(String name) {
			return BlockTags.create(BetterDefaultBiomes.locate(name));
		}
	}

	/*********************************************************** Items ********************************************************/

	public static class Items {
		// Wood
		public static final TagKey<Item> PALM_LOGS = bdbTag("palm_logs");
		public static final TagKey<Item> SWAMP_WILLOW_LOGS = bdbTag("swamp_willow_logs");
		
		// Plant decoration
		public static final TagKey<Item> MUSHROOMS = bdbTag("mushrooms");
		public static final TagKey<Item> SMALL_FLOWERS = bdbTag("small_flowers");
		public static final TagKey<Item> FLOWERS = bdbTag("flowers");
		
		//	Other Blocks
		public static final TagKey<Item> STARFISH = bdbTag("starfish");
		public static final TagKey<Item> SMALL_ROCKS = bdbTag("small_rocks");

		/**
		 * Helper method for BDB Item Tags
		 */
		private static TagKey<Item> bdbTag(String name) {
			return ItemTags.create(BetterDefaultBiomes.locate(name));
		}
	}

	/*********************************************************** EntityTypes ********************************************************/

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> ARROWS = bdbTag("arrows");
		public static final TagKey<EntityType<?>> CACTUS_IMMUNE = bdbTag("cactus_immune");

		/**
		 * Helper method for BDB Entity Tags
		 */
		private static TagKey<EntityType<?>> bdbTag(String name) {
			return TagKey.create(Registries.ENTITY_TYPE, BetterDefaultBiomes.locate(name));
		}
	}
	
	/*********************************************************** Biomes ********************************************************/

	/**
	 * Generated from {@link BDBBiomeTagsProvider}
	 */
	public static class Biomes {
		//	Grass Features
		public static final TagKey<Biome> FEATHER_REED_GRASS = bdbTag("generates_feather_reed_grass");
		public static final TagKey<Biome> DEAD_GRASS = bdbTag("generates_dead_grass");
		public static final TagKey<Biome> SHORT_GRASS = bdbTag("generates_short_grass");
		public static final TagKey<Biome> DUNE_GRASS = bdbTag("generates_dune_grass");

		//	Water Plants
		public static final TagKey<Biome> WATER_REEDS = bdbTag("generates_water_reeds");

		// Flowers
		public static final TagKey<Biome> PINK_CACTUS_FLOWER = bdbTag("generates_pink_cactus_flower");
		public static final TagKey<Biome> PURPLE_VERBENA = bdbTag("generates_purple_verbena");
		public static final TagKey<Biome> BLUE_POPPY = bdbTag("generates_blue_poppy");
		public static final TagKey<Biome> DARK_VIOLET = bdbTag("generates_dark_violet");

		//	Mushrooms
		public static final TagKey<Biome> WHITE_MUSHROOM = bdbTag("generates_white_mushroom");
		public static final TagKey<Biome> YELLOW_MUSHROOM = bdbTag("generates_yellow_mushroom");
		public static final TagKey<Biome> GRAY_MUSHROOM = bdbTag("generates_gray_mushroom");

		//	Big Mushrooms
		public static final TagKey<Biome> BIG_WHITE_MUSHROOM = bdbTag("generates_big_white_mushroom");
		public static final TagKey<Biome> BIG_YELLOW_MUSHROOM = bdbTag("generates_big_yellow_mushroom");
		public static final TagKey<Biome> BIG_GRAY_MUSHROOM = bdbTag("generates_big_gray_mushroom");

		//	Small Features
		public static final TagKey<Biome> SMALL_ROCK = bdbTag("generates_small_rock");
		public static final TagKey<Biome> PINECONE = bdbTag("generates_pinecone");
		public static final TagKey<Biome> SAND_CASTLE = bdbTag("generates_sand_castle");
		public static final TagKey<Biome> SEASHELL = bdbTag("generates_seashell");
		public static final TagKey<Biome> STARFISH = bdbTag("generates_starfish");

		//	Trees
		public static final TagKey<Biome> PALM_TREE = bdbTag("generates_palm_tree");
		public static final TagKey<Biome> SWAMP_WILLOW_TREE = bdbTag("generates_swamp_willow_tree");

		/**
		 * Helper method for BDB Biome Tags
		 */
		private static TagKey<Biome> bdbTag(String name) {
			return TagKey.create(Registries.BIOME, BetterDefaultBiomes.locate(name));
		}
	}
}
