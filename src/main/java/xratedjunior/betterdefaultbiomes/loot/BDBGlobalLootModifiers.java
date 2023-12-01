package xratedjunior.betterdefaultbiomes.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.loot.modifiers.AddItemModifier;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBGlobalLootModifiers {
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> DEFERRED_GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BetterDefaultBiomes.MOD_ID);

	public static final RegistryObject<Codec<AddItemModifier>> ADD_ITEM = register("add_item", AddItemModifier.CODEC.get());

	private static RegistryObject<Codec<AddItemModifier>> register(String name, Codec<AddItemModifier> codec) {
		BetterDefaultBiomes.LOGGER.debug("Global Loot Modifier: {}", name);
		return DEFERRED_GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(name, Suppliers.memoize(() -> codec));
	}
}
