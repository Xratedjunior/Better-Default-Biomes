package xratedjunior.betterdefaultbiomes;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import xratedjunior.betterdefaultbiomes.item.BDBItems;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBItemGroup extends CreativeModeTab {

	/**
	 * Create an ItemGroup with the Mod ID as the name.
	 */
	public BDBItemGroup() {
		super(BetterDefaultBiomes.MOD_ID);
	}

	/**
	 * Create Icon for the ItemGroup.
	 */
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(BDBItems.HUNTER_ARROW.get());
	}
}
