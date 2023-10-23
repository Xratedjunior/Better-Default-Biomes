package xratedjunior.betterdefaultbiomes.block.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Only here to set the OffsetType back to NONE, because otherwise the Starfish looks weird on walls.
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class StarfishWallBlock extends StarfishBlock {

	public StarfishWallBlock(DyeColor color, Properties properties) {
		super(color, properties);
	}

	/**
	 * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
	 */
	@Override
	public BlockBehaviour.OffsetType getOffsetType() {
		return BlockBehaviour.OffsetType.NONE;
	}
}
