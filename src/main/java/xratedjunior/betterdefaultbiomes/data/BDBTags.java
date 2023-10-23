package xratedjunior.betterdefaultbiomes.data;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBTags {

	public static class Blocks {

		/*********************************************************** BDB Tags ********************************************************/

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

		public static final TagKey<Block> PALM_SAPLING_SOIL = bdbTag("plant_soil/palm_sapling");
		public static final TagKey<Block> DEAD_GRASS_SOIL = bdbTag("plant_soil/dead_grass");
		public static final TagKey<Block> DUNE_GRASS_SOIL = bdbTag("plant_soil/dune_grass");
		public static final TagKey<Block> PINK_CACTUS_FLOWER_SOIL = bdbTag("plant_soil/pink_cactus_flower");
		
		// Blocks Sand Castles & Starfish will generate on.
		public static final TagKey<Block> BEACH_SAND = bdbTag("beach_sand");
		
		public static final TagKey<Block> SWAMP_WILLOW_TREE_REPLACEABLES = bdbTag("swamp_willow_tree_replaceables");

		/*********************************************************** Helper Methods ********************************************************/

		/**
		 * Helper method for BDB Block Tags
		 */
		private static TagKey<Block> bdbTag(String name) {
			return BlockTags.create(BetterDefaultBiomes.locate(name));
		}
	}

	public static class Items {

		/*********************************************************** Forge Tags ********************************************************/

		public static final TagKey<Item> AXES = forgeTag("tools/axes");

		/*********************************************************** BDB Tags ********************************************************/

		public static final TagKey<Item> SMALL_ROCKS = bdbTag("small_rocks");

		/*********************************************************** Helper Methods ********************************************************/

		/**
		 * Helper method for Forge Item Tags
		 */
		private static TagKey<Item> forgeTag(String name) {
			return ItemTags.create(new ResourceLocation("forge", name));
		}

		/**
		 * Helper method for BDB Item Tags
		 */
		private static TagKey<Item> bdbTag(String name) {
			return ItemTags.create(BetterDefaultBiomes.locate(name));
		}
	}

	public static class EntityTypes {

		/*********************************************************** BDB Tags ********************************************************/

		public static final TagKey<EntityType<?>> CACTUS_IMMUNE = bdbTag("cactus_immune");

		/*********************************************************** Helper Methods ********************************************************/

		private static TagKey<EntityType<?>> bdbTag(String name) {
			return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, BetterDefaultBiomes.locate(name));
		}
	}
}
