package xratedjunior.betterdefaultbiomes.block.property;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * DELETE Might be needed for signs? I think not since all sounds are general wood sounds.
 * Reference: {@linkplain https://youtu.be/ARoH2sRFY4o?si=VZ64ZysNBQIRBjYf&t=306}
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBWoodTypes {
	public static final WoodType PALM = WoodType.register(new WoodType(BetterDefaultBiomes.find("palm"), BlockSetType.OAK));
	public static final WoodType SWAMP_WILLOW = WoodType.register(new WoodType(BetterDefaultBiomes.find("swamp_willow"), BlockSetType.OAK));
}
