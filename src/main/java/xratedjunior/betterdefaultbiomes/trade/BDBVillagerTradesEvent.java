package xratedjunior.betterdefaultbiomes.trade;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.configuration.TradingConfig;
import xratedjunior.betterdefaultbiomes.item.BDBItems;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBVillagerTradesEvent {

	@SubscribeEvent
	public void onWandererTradesEvent(WandererTradesEvent event) {
		if (TradingConfig.enable_trades.get()) {
			//Grass
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.FEATHER_REED_GRASS.get().asItem(), 1, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.DEAD_GRASS.get().asItem(), 1, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.SHORT_GRASS.get().asItem(), 1, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.DUNE_GRASS.get().asItem(), 1, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.TALL_WATER_REEDS.get().asItem(), 1, 1, 8, 1));
			//Flowers
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.PINK_CACTUS_FLOWER.get().asItem(), 1, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.PURPLE_VERBENA.get().asItem(), 1, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.BLUE_POPPY.get().asItem(), 1, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.DARK_VIOLET.get().asItem(), 1, 1, 8, 1));
			//Mushrooms
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.GRAY_MUSHROOM.get().asItem(), 1, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.WHITE_MUSHROOM.get().asItem(), 1, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.YELLOW_MUSHROOM.get().asItem(), 1, 1, 8, 1));
			//Saplings
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.PALM_SAPLING.get().asItem(), 3, 1, 8, 1));
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBBlocks.SWAMP_WILLOW_SAPLING.get().asItem(), 3, 1, 8, 1));
			//Food
			event.getGenericTrades().add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBItems.FROZEN_FLESH.get(), 3, 1, 8, 1));

			BetterDefaultBiomes.LOGGER.debug("Added Wandering Trader Trades");
		} else {
			BetterDefaultBiomes.LOGGER.debug("Wandering Trader trades have been disabled.");
		}
	}

	@SubscribeEvent
	public void onVillagerTradesEvent(VillagerTradesEvent event) {
		//Enchanted Books for Librarian is added Automatically
		if (event.getType() == VillagerProfession.FLETCHER) {
			if (TradingConfig.enable_trades.get()) {
				event.getTrades().get(3).add(new VillagerTradeUtils.ItemsForEmeraldsTrade(BDBItems.TORCH_ARROW.get(), 2, 16, 1));
				BetterDefaultBiomes.LOGGER.debug("Added Fletcher Villager Trades");
			} else {
				BetterDefaultBiomes.LOGGER.debug("Fletcher trades have been disabled.");
			}
		}
	}
}
