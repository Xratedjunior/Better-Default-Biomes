package xratedjunior.betterdefaultbiomes;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.item.BDBItems;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBCreativeModeTabs {
	// Create an ItemGroup with the Mod ID as the name.
	public static CreativeModeTab BETTER_DEFAULT_BIOMES_TAB;

	public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
		BetterDefaultBiomes.LOGGER.debug("Registering Creative Mode Tabs");

		BETTER_DEFAULT_BIOMES_TAB = event.registerCreativeModeTab(BetterDefaultBiomes.locate(BetterDefaultBiomes.MOD_ID), //
				(builder) -> builder //
						.icon(() -> new ItemStack(BDBItems.HUNTER_ARROW.get())) //
						.title(Component.translatable("itemGroup.betterdefaultbiomes")) //
						.displayItems((parameters, output) -> {
							addItems(output);
						}));
	}

	/**
	 * Add all Items in order of: Item, Block, Spawn Egg
	 */
	private static void addItems(CreativeModeTab.Output output) {
		for (RegistryObject<Item> spawnEgg : BDBItems.ITEMS) {
			output.accept(spawnEgg.get());
		}
	}
}
