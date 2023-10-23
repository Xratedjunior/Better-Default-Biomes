package xratedjunior.betterdefaultbiomes.entity.passive;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import xratedjunior.betterdefaultbiomes.configuration.entity.breeding.BreedingConfigRegistry;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class ZebraEntity extends BDBAnimalEntityAbstract {

	public ZebraEntity(EntityType<? extends ZebraEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	/*********************************************************** Breeding Items ********************************************************/

	@Override
	protected String getBreedingConfig() {
		return BreedingConfigRegistry.ZEBRA_FOOD;
	}

	/*********************************************************** Baby ********************************************************/

	@Override
	protected ZebraEntity createChild(ServerLevel serverWorld) {
		return BDBEntityTypes.ZEBRA.get().create(serverWorld);
	}

	/*********************************************************** Sounds ********************************************************/

	@Override
	protected SoundEvent getAnimalEatingSound() {
		return SoundEvents.HORSE_EAT;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return BDBSoundEvents.ENTITY_ZEBRA_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return BDBSoundEvents.ENTITY_ZEBRA_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return BDBSoundEvents.ENTITY_ZEBRA_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		if (!blockIn.getMaterial().isLiquid()) {
			BlockState blockstate = this.level.getBlockState(pos.above());
			SoundType soundtype = blockIn.getSoundType(level, pos, this);
			if (blockstate.is(Blocks.SNOW)) {
				soundtype = blockstate.getSoundType(level, pos, this);
			} else if (soundtype == SoundType.WOOD) {
				this.playSound(SoundEvents.HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
			} else {
				this.playSound(SoundEvents.HORSE_STEP, soundtype.getVolume() * 0.15F, soundtype.getPitch());
			}
		}
	}
}