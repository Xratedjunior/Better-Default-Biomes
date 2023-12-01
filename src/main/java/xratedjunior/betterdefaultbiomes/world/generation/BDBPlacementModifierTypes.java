package xratedjunior.betterdefaultbiomes.world.generation;

import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.world.generation.placement.BDBPlacement;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBPlacementModifierTypes {
	public static final DeferredRegister<PlacementModifierType<?>> DEFERRED_PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, BetterDefaultBiomes.MOD_ID);

	public static final RegistryObject<PlacementModifierType<BDBPlacement>> COUNT = register("count", BDBPlacement.CODEC);

	/**
	 * Helper method for registering all Items
	 */
	private static <P extends PlacementModifier> RegistryObject<PlacementModifierType<P>> register(String name, Codec<P> codec) {
		return DEFERRED_PLACEMENT_MODIFIER_TYPES.register(name, () -> () -> {
			return codec;
		});
	}
}
