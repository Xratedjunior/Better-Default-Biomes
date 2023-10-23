package xratedjunior.betterdefaultbiomes.item;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class FuelEventHandler {
	private static Map<Item, Integer> fuelValues = new HashMap<>();

	/**
	 * Register fuel values for Blocks and Items.
	 */
	public static void registerFuels() {
		BetterDefaultBiomes.LOGGER.debug("FuelEventHandler.registerFuels()");
		addFuel(BDBBlocks.PINECONE.get(), 100);
	}

	/**
	 * Helper method for adding fuel Items.
	 */
	public static void addFuel(Item item, int fuelTicks) {
		BetterDefaultBiomes.LOGGER.debug("Added: " + item + " as a fuel with a value of: " + fuelTicks);
		if (fuelTicks > 0 && item != null) {
			fuelValues.put(item, fuelTicks);
		}
	}

	/**
	 * Helper method for adding fuel Blocks.
	 */
	public static void addFuel(Block block, int fuelTicks) {
		addFuel(block.asItem(), fuelTicks);
	}

	/**
	 * Gets called every time you try to place an Item in a Furnace
	 */
	@SubscribeEvent
	public void getFuel(final FurnaceFuelBurnTimeEvent event) {
		Item item = event.getItemStack().getItem();
		if (fuelValues.containsKey(item)) {
			event.setBurnTime(fuelValues.get(item));
		}
	}
}
