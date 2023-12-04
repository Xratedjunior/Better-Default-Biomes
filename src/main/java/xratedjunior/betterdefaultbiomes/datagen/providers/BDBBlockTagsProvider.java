package xratedjunior.betterdefaultbiomes.datagen.providers;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.data.BDBTags;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class BDBBlockTagsProvider extends BlockTagsProvider {

	public BDBBlockTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, BetterDefaultBiomes.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		BetterDefaultBiomes.LOGGER.info("Generating Block Tags");
		/*********************************************************** Better Default Biomes ********************************************************/
		
		// Tree related
		this.tag(BDBTags.Blocks.WOODEN_LADDERS).add(BDBBlocks.PALM_LADDER.get(), BDBBlocks.SWAMP_WILLOW_LADDER.get());
		this.tag(BDBTags.Blocks.PALM_LOGS).add(BDBBlocks.PALM_LOG.get(), BDBBlocks.PALM_WOOD.get(), BDBBlocks.PALM_LOG_STRIPPED.get(), BDBBlocks.PALM_WOOD_STRIPPED.get());
		this.tag(BDBTags.Blocks.SWAMP_WILLOW_LOGS).add(BDBBlocks.SWAMP_WILLOW_LOG.get(), BDBBlocks.SWAMP_WILLOW_WOOD.get(), BDBBlocks.SWAMP_WILLOW_LOG_STRIPPED.get(), BDBBlocks.SWAMP_WILLOW_WOOD_STRIPPED.get());
		
		// Plant decoration
		this.tag(BDBTags.Blocks.MUSHROOMS).add(BDBBlocks.WHITE_MUSHROOM.get(), BDBBlocks.YELLOW_MUSHROOM.get(), BDBBlocks.GRAY_MUSHROOM.get());
		this.tag(BDBTags.Blocks.PLANTS_REPLACEABLE).add(BDBBlocks.FEATHER_REED_GRASS.get(), BDBBlocks.DEAD_GRASS.get(), BDBBlocks.SHORT_GRASS.get(), BDBBlocks.DUNE_GRASS.get());
		this.tag(BDBTags.Blocks.SMALL_FLOWERS).add(BDBBlocks.PURPLE_VERBENA.get(), BDBBlocks.BLUE_POPPY.get(), BDBBlocks.DARK_VIOLET.get(), BDBBlocks.PINK_CACTUS_FLOWER.get());
		this.tag(BDBTags.Blocks.FLOWERS); // Empty
		BDBBlocks.POTTED_PLANTS.values().forEach(pottedPlant -> this.tag(BDBTags.Blocks.FLOWER_POTS).add(pottedPlant.get()));
		
		// Other Blocks
		this.tag(BDBTags.Blocks.STARFISH).addTag(BDBTags.Blocks.STARFISH_WALL).add(BDBBlocks.STARFISH_WHITE.get(), BDBBlocks.STARFISH_ORANGE.get(), BDBBlocks.STARFISH_MAGENTA.get(), BDBBlocks.STARFISH_LIGHT_BLUE.get(), BDBBlocks.STARFISH_YELLOW.get(), BDBBlocks.STARFISH_LIME.get(), BDBBlocks.STARFISH_PINK.get(), BDBBlocks.STARFISH_GRAY.get(), BDBBlocks.STARFISH_LIGHT_GRAY.get(), BDBBlocks.STARFISH_CYAN.get(), BDBBlocks.STARFISH_PURPLE.get(), BDBBlocks.STARFISH_BLUE.get(), BDBBlocks.STARFISH_BROWN.get(), BDBBlocks.STARFISH_GREEN.get(), BDBBlocks.STARFISH_RED.get(), BDBBlocks.STARFISH_BLACK.get());
		this.tag(BDBTags.Blocks.STARFISH_WALL).add(BDBBlocks.STARFISH_WALL_WHITE.get(), BDBBlocks.STARFISH_WALL_ORANGE.get(), BDBBlocks.STARFISH_WALL_MAGENTA.get(), BDBBlocks.STARFISH_WALL_LIGHT_BLUE.get(), BDBBlocks.STARFISH_WALL_YELLOW.get(), BDBBlocks.STARFISH_WALL_LIME.get(), BDBBlocks.STARFISH_WALL_PINK.get(), BDBBlocks.STARFISH_WALL_GRAY.get(), BDBBlocks.STARFISH_WALL_LIGHT_GRAY.get(), BDBBlocks.STARFISH_WALL_CYAN.get(), BDBBlocks.STARFISH_WALL_PURPLE.get(), BDBBlocks.STARFISH_WALL_BLUE.get(), BDBBlocks.STARFISH_WALL_BROWN.get(), BDBBlocks.STARFISH_WALL_GREEN.get(), BDBBlocks.STARFISH_WALL_RED.get(), BDBBlocks.STARFISH_WALL_BLACK.get());
		
		// Small Rock
		BDBBlocks.SMALL_ROCKS.forEach(smallRock -> this.tag(BDBTags.Blocks.SMALL_ROCKS).add(smallRock.get()));
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_STONE).add(Blocks.STONE, Blocks.INFESTED_STONE, Blocks.STONE_BRICKS, Blocks.INFESTED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS, Blocks.INFESTED_CHISELED_STONE_BRICKS);
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_COBBLE).add(Blocks.COBBLESTONE, Blocks.INFESTED_COBBLESTONE, Blocks.CRACKED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS);
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_MOSSY).add(Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_STONE_BRICKS, Blocks.INFESTED_MOSSY_STONE_BRICKS);
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_ANDESITE).add(Blocks.ANDESITE, Blocks.POLISHED_ANDESITE);
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_DIORITE).add(Blocks.DIORITE, Blocks.POLISHED_DIORITE);
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_GRANITE).add(Blocks.GRANITE, Blocks.POLISHED_GRANITE);
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_SANDSTONE).add(Blocks.SAND, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.SMOOTH_SANDSTONE);
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_RED_SANDSTONE).add(Blocks.RED_SAND, Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE);
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_DEEPSLATE).add(Blocks.DEEPSLATE, Blocks.INFESTED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES, Blocks.CHISELED_DEEPSLATE);
		this.tag(BDBTags.Blocks.GENERATE_SMALL_ROCK_COBBLED_DEEPSLATE).add(Blocks.COBBLED_DEEPSLATE, Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_TILES);

		// Plant soil Blocks
		this.tag(BDBTags.Blocks.PALM_SAPLING_SOIL).addTag(BlockTags.DIRT).addTag(BlockTags.SAND).add(Blocks.FARMLAND);
		this.tag(BDBTags.Blocks.DEAD_GRASS_SOIL).addTag(BlockTags.DIRT).addTag(BlockTags.SAND).addTag(BlockTags.TERRACOTTA).add(Blocks.FARMLAND);
		this.tag(BDBTags.Blocks.DUNE_GRASS_SOIL).addTag(BlockTags.DIRT).addTag(BlockTags.SAND).add(Blocks.FARMLAND);
		this.tag(BDBTags.Blocks.PINK_CACTUS_FLOWER_SOIL).addTag(BlockTags.DIRT).addTag(BlockTags.SAND).addTag(BlockTags.TERRACOTTA).add(Blocks.FARMLAND);

		// Ground replaceable
		this.tag(BDBTags.Blocks.SWAMP_WILLOW_TREE_REPLACEABLES).addTag(BlockTags.DIRT);

		// Blocks Sand Castles & Starfish will generate on.
		this.tag(BDBTags.Blocks.BEACH_SAND).add(Blocks.SAND);
		
		/*********************************************************** Forge ********************************************************/

		this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(BDBBlocks.PALM_FENCE_GATE.get(), BDBBlocks.SWAMP_WILLOW_FENCE_GATE.get());
		
		/*********************************************************** Minecraft ********************************************************/
		
		// Mineable
		this.tag(BlockTags.MINEABLE_WITH_AXE).addTag(BDBTags.Blocks.WOODEN_LADDERS);
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BDBBlocks.SAND_CASTLE.get());

		// Flowers
		this.tag(BlockTags.SMALL_FLOWERS).addTag(BDBTags.Blocks.SMALL_FLOWERS);
		this.tag(BlockTags.TALL_FLOWERS); // Empty
		this.tag(BlockTags.FLOWERS).addTag(BDBTags.Blocks.FLOWERS);
		this.tag(BlockTags.FLOWER_POTS).addTag(BDBTags.Blocks.FLOWER_POTS);
		
		// Plants
		this.tag(BlockTags.REPLACEABLE).addTag(BDBTags.Blocks.PLANTS_REPLACEABLE);
		this.tag(BlockTags.REPLACEABLE_BY_TREES).addTag(BDBTags.Blocks.PLANTS_REPLACEABLE);
		this.tag(BlockTags.SWORD_EFFICIENT).addTag(BDBTags.Blocks.PLANTS_REPLACEABLE);
		
		// Tree related
		this.tag(BlockTags.LOGS_THAT_BURN).addTag(BDBTags.Blocks.PALM_LOGS).addTag(BDBTags.Blocks.SWAMP_WILLOW_LOGS);
		this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).addTag(BDBTags.Blocks.PALM_LOGS).addTag(BDBTags.Blocks.SWAMP_WILLOW_LOGS);
		this.tag(BlockTags.LEAVES).add(BDBBlocks.PALM_LEAVES.get(), BDBBlocks.SWAMP_WILLOW_LEAVES.get());
		this.tag(BlockTags.SAPLINGS).add(BDBBlocks.PALM_SAPLING.get(), BDBBlocks.SWAMP_WILLOW_SAPLING.get());
		
		this.tag(BlockTags.PLANKS).add(BDBBlocks.PALM_PLANKS.get(), BDBBlocks.SWAMP_WILLOW_PLANKS.get());
		this.tag(BlockTags.FENCE_GATES).add(BDBBlocks.PALM_FENCE_GATE.get(), BDBBlocks.SWAMP_WILLOW_FENCE_GATE.get());
		this.tag(BlockTags.CLIMBABLE).addTag(BDBTags.Blocks.WOODEN_LADDERS);
		
		this.tag(BlockTags.WOODEN_BUTTONS).add(BDBBlocks.PALM_BUTTON.get(), BDBBlocks.SWAMP_WILLOW_BUTTON.get());
		this.tag(BlockTags.WOODEN_DOORS).add(BDBBlocks.PALM_DOOR.get(), BDBBlocks.SWAMP_WILLOW_DOOR.get());
		this.tag(BlockTags.WOODEN_FENCES).add(BDBBlocks.PALM_FENCE.get(), BDBBlocks.SWAMP_WILLOW_FENCE.get());
		this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(BDBBlocks.PALM_PRESSURE_PLATE.get(), BDBBlocks.SWAMP_WILLOW_PRESSURE_PLATE.get());
		this.tag(BlockTags.WOODEN_SLABS).add(BDBBlocks.PALM_SLAB.get(), BDBBlocks.SWAMP_WILLOW_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(BDBBlocks.PALM_STAIRS.get(), BDBBlocks.SWAMP_WILLOW_STAIRS.get());
		this.tag(BlockTags.WOODEN_TRAPDOORS).add(BDBBlocks.PALM_TRAPDOOR.get(), BDBBlocks.SWAMP_WILLOW_TRAPDOOR.get());
	}
}