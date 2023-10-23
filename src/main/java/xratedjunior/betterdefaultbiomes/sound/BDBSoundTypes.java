package xratedjunior.betterdefaultbiomes.sound;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBSoundTypes {

	public static final SoundType SMALL_BLOCK = new ForgeSoundType(1.0F, 1.0F, BDBSoundEvents.BLOCK_SMALL_BREAK, () -> SoundEvents.STONE_STEP, BDBSoundEvents.BLOCK_SMALL_PLACE, BDBSoundEvents.BLOCK_SMALL_HIT, () -> SoundEvents.STONE_FALL);
}
