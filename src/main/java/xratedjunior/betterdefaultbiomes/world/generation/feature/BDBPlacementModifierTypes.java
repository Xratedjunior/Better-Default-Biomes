package xratedjunior.betterdefaultbiomes.world.generation.feature;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.world.generation.feature.placement.ConfigPlacement;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBPlacementModifierTypes {
	public static final DeferredRegister<PlacementModifierType<?>> DEFERRED_PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, BetterDefaultBiomes.MOD_ID);

	public static final RegistryObject<PlacementModifierType<ConfigPlacement>> CONFIG = DEFERRED_PLACEMENT_MODIFIER_TYPES.register("config", () -> () -> ConfigPlacement.CODEC);
}
