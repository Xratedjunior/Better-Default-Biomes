package xratedjunior.betterdefaultbiomes.loot;

import com.google.common.base.Supplier;
import com.mojang.serialization.Codec;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.loot.modifiers.AddItemModifier;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class BDBGlobalLootModifiers {
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> DEFERRED_GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BetterDefaultBiomes.MOD_ID);

	public static final RegistryObject<Codec<AddItemModifier>> ADD_ITEM = register("add_item", AddItemModifier.CODEC);

	private static <LM extends IGlobalLootModifier> RegistryObject<Codec<LM>> register(String name, Supplier<Codec<LM>> codecSupplier) {
		BetterDefaultBiomes.LOGGER.debug("Global Loot Modifier: {}", name);
		return DEFERRED_GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(name, codecSupplier);
	}
}
