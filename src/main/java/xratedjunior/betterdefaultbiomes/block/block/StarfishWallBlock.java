package xratedjunior.betterdefaultbiomes.block.block;

import net.minecraft.world.item.DyeColor;

/**
 * Only here to set the OffsetType back to NONE, because otherwise the Starfish looks weird on walls.
 * 
 * TODO: Delete
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class StarfishWallBlock extends StarfishBlock {

	public StarfishWallBlock(DyeColor color, Properties properties) {
		super(color, properties);
	}
}
