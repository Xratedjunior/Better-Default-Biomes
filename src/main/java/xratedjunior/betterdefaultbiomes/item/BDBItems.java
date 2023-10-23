package xratedjunior.betterdefaultbiomes.item;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.item.item.BanditArrowItem;
import xratedjunior.betterdefaultbiomes.item.item.DuckEggItem;
import xratedjunior.betterdefaultbiomes.item.item.HunterArrowItem;
import xratedjunior.betterdefaultbiomes.item.item.TorchArrowItem;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID, bus = Bus.MOD)
public class BDBItems {
	public static final DeferredRegister<Item> DEFERRED_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterDefaultBiomes.MOD_ID);
	private static final CreativeModeTab BETTERDEFAULTBIOMES = BetterDefaultBiomes.BETTERDEFAULTBIOMESTAB;

	// Arrows
	public static final RegistryObject<Item> HUNTER_ARROW = registerItem("hunter_arrow", () -> new HunterArrowItem((new Item.Properties()).tab(BETTERDEFAULTBIOMES)));
	public static final RegistryObject<Item> BANDIT_ARROW = registerItem("bandit_arrow", () -> new BanditArrowItem((new Item.Properties()).tab(BETTERDEFAULTBIOMES)));
	public static final RegistryObject<Item> TORCH_ARROW = registerItem("torch_arrow", () -> new TorchArrowItem((new Item.Properties()).tab(BETTERDEFAULTBIOMES)));

	// Misc
	public static final RegistryObject<Item> DUCK_EGG = registerItem("duck_egg", () -> new DuckEggItem((new Item.Properties()).stacksTo(16).tab(BETTERDEFAULTBIOMES)));

	// Foods
	public static final RegistryObject<Item> FROZEN_FLESH = registerItem("frozen_flesh", () -> new Item((new Item.Properties()).tab(BETTERDEFAULTBIOMES).food(BDBFoods.FROZEN_FLESH)));
	public static final RegistryObject<Item> DUCK = registerItem("duck", () -> new Item((new Item.Properties()).tab(BETTERDEFAULTBIOMES).food(BDBFoods.DUCK)));
	public static final RegistryObject<Item> COOKED_DUCK = registerItem("cooked_duck", () -> new Item((new Item.Properties()).tab(BETTERDEFAULTBIOMES).food(BDBFoods.COOKED_DUCK)));
	public static final RegistryObject<Item> FROG_LEG = registerItem("frog_leg", () -> new Item((new Item.Properties()).tab(BETTERDEFAULTBIOMES).food(BDBFoods.FROG_LEG)));
	public static final RegistryObject<Item> COOKED_FROG_LEG = registerItem("cooked_frog_leg", () -> new Item((new Item.Properties()).tab(BETTERDEFAULTBIOMES).food(BDBFoods.COOKED_FROG_LEG)));

	/**
	 * Helper method for registering all Items
	 */
	public static <I extends Item> RegistryObject<Item> registerItem(@Nonnull String registryName, Supplier<I> item) {
		return DEFERRED_ITEMS.register(registryName, item);
	}
}
