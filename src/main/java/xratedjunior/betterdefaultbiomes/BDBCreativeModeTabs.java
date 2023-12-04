package xratedjunior.betterdefaultbiomes;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.item.BDBItems;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class BDBCreativeModeTabs {
	// Create an ItemGroup with the Mod ID as the name.
	public static final ResourceKey<CreativeModeTab> BETTER_DEFAULT_BIOMES_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, BetterDefaultBiomes.locate(BetterDefaultBiomes.MOD_ID));

	public static void registerCreativeModeTabs(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB)) {
			BetterDefaultBiomes.LOGGER.debug("Registering Creative Mode Tabs");

			event.register(Registries.CREATIVE_MODE_TAB, helper -> {
				helper.register(BETTER_DEFAULT_BIOMES_TAB, CreativeModeTab.builder() // Start builder
						.icon(() -> new ItemStack(BDBItems.HUNTER_ARROW.get())) // Icon from item
						.title(Component.translatable("itemGroup.betterdefaultbiomes")) // Translatable title
						.displayItems((parameters, output) -> {
							addItems(output); // Add all Items
						}).build()); // Build
			});
		}
	}

	/**
	 * Add all Items in order of: Item, Block, Spawn Egg
	 */
	private static void addItems(CreativeModeTab.Output output) {
		for (RegistryObject<Item> item : BDBItems.ITEMS) {
			output.accept(item.get());
		}
	}
}
