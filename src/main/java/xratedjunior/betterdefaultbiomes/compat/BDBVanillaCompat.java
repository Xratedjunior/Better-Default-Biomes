package xratedjunior.betterdefaultbiomes.compat;

import com.google.common.collect.Maps;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBVanillaCompat {

	public static void setup() {
		BetterDefaultBiomes.LOGGER.debug("BDBVanillaCompat.setup()");
		
		registerStrippable(BDBBlocks.PALM_LOG.get(), BDBBlocks.PALM_LOG_STRIPPED.get());
		registerStrippable(BDBBlocks.PALM_WOOD.get(), BDBBlocks.PALM_WOOD_STRIPPED.get());
		registerStrippable(BDBBlocks.SWAMP_WILLOW_LOG.get(), BDBBlocks.SWAMP_WILLOW_LOG_STRIPPED.get());
		registerStrippable(BDBBlocks.SWAMP_WILLOW_WOOD.get(), BDBBlocks.SWAMP_WILLOW_WOOD_STRIPPED.get());

		registerCompostable(0.3F, BDBBlocks.PALM_LEAVES.get());
		registerCompostable(0.3F, BDBBlocks.PALM_SAPLING.get());
		registerCompostable(0.3F, BDBBlocks.SWAMP_WILLOW_LEAVES.get());
		registerCompostable(0.3F, BDBBlocks.SWAMP_WILLOW_SAPLING.get());
		registerCompostable(0.3F, BDBBlocks.DEAD_GRASS.get());
		registerCompostable(0.3F, BDBBlocks.DUNE_GRASS.get());
		registerCompostable(0.3F, BDBBlocks.SHORT_GRASS.get());
		registerCompostable(0.3F, BDBBlocks.PINECONE.get());

		registerCompostable(0.5F, BDBBlocks.FEATHER_REED_GRASS.get());
		registerCompostable(0.5F, BDBBlocks.TALL_WATER_REEDS.get());

		registerCompostable(0.65F, BDBBlocks.BLUE_POPPY.get());
		registerCompostable(0.65F, BDBBlocks.DARK_VIOLET.get());
		registerCompostable(0.65F, BDBBlocks.PURPLE_VERBENA.get());
		registerCompostable(0.65F, BDBBlocks.PINK_CACTUS_FLOWER.get());
		registerCompostable(0.65F, BDBBlocks.GRAY_MUSHROOM.get());
		registerCompostable(0.65F, BDBBlocks.WHITE_MUSHROOM.get());
		registerCompostable(0.65F, BDBBlocks.YELLOW_MUSHROOM.get());

		registerCompostable(0.85F, BDBBlocks.GRAY_MUSHROOM_BLOCK.get());
		registerCompostable(0.85F, BDBBlocks.WHITE_MUSHROOM_BLOCK.get());
		registerCompostable(0.85F, BDBBlocks.YELLOW_MUSHROOM_BLOCK.get());

		registerFlammable(BDBBlocks.PALM_LEAVES.get(), 30, 60);
		registerFlammable(BDBBlocks.PALM_LOG.get(), 5, 5);
		registerFlammable(BDBBlocks.PALM_LOG_STRIPPED.get(), 5, 5);
		registerFlammable(BDBBlocks.PALM_WOOD.get(), 5, 5);
		registerFlammable(BDBBlocks.PALM_WOOD_STRIPPED.get(), 5, 5);
		registerFlammable(BDBBlocks.PALM_PLANKS.get(), 5, 20);
		registerFlammable(BDBBlocks.PALM_SLAB.get(), 5, 20);
		registerFlammable(BDBBlocks.PALM_FENCE_GATE.get(), 5, 20);
		registerFlammable(BDBBlocks.PALM_FENCE.get(), 5, 20);
		registerFlammable(BDBBlocks.PALM_STAIRS.get(), 5, 20);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_LEAVES.get(), 30, 60);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_LOG.get(), 5, 5);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_LOG_STRIPPED.get(), 5, 5);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_WOOD.get(), 5, 5);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_WOOD_STRIPPED.get(), 5, 5);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_PLANKS.get(), 5, 20);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_SLAB.get(), 5, 20);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_FENCE_GATE.get(), 5, 20);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_FENCE.get(), 5, 20);
		registerFlammable(BDBBlocks.SWAMP_WILLOW_STAIRS.get(), 5, 20);

		registerFlammable(BDBBlocks.BLUE_POPPY.get(), 60, 100);
		registerFlammable(BDBBlocks.DARK_VIOLET.get(), 60, 100);
		registerFlammable(BDBBlocks.PURPLE_VERBENA.get(), 60, 100);
		registerFlammable(BDBBlocks.PINK_CACTUS_FLOWER.get(), 60, 100);

		registerFlammable(BDBBlocks.DEAD_GRASS.get(), 60, 100);
		registerFlammable(BDBBlocks.DUNE_GRASS.get(), 60, 100);
		registerFlammable(BDBBlocks.SHORT_GRASS.get(), 60, 100);
		registerFlammable(BDBBlocks.FEATHER_REED_GRASS.get(), 60, 100);
	}

	public static void registerStrippable(Block log, Block stripped_log) {
		AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
		AxeItem.STRIPPABLES.put(log, stripped_log);
	}

	public static void registerCompostable(float chance, ItemLike itemIn) {
		ComposterBlock.COMPOSTABLES.put(itemIn.asItem(), chance);
	}

	public static void registerFlammable(Block blockIn, int encouragement, int flammability) {
		FireBlock fireBlock = (FireBlock) Blocks.FIRE;
		fireBlock.setFlammable(blockIn, encouragement, flammability);
	}
}