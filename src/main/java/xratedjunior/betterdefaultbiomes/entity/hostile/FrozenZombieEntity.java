package xratedjunior.betterdefaultbiomes.entity.hostile;

import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class FrozenZombieEntity extends Zombie {

	public FrozenZombieEntity(EntityType<? extends FrozenZombieEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	public boolean doHurtTarget(Entity entityIn) {
		if (super.doHurtTarget(entityIn)) {
			Difficulty difficulty = this.level.getDifficulty();
			if (entityIn instanceof LivingEntity && !difficulty.equals(Difficulty.PEACEFUL)) {
				int seconds = 4;
				int level = 1;
				if (this.level.getDifficulty().equals(Difficulty.NORMAL)) {
					seconds = 8;
					level = 2;
				} else if (this.level.getDifficulty().equals(Difficulty.HARD)) {
					seconds = 16;
					level = 3;
				}

				int tps = 20; // Ticks Per Second
				((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, seconds * tps, level - 1));
				if (!difficulty.equals(Difficulty.EASY)) {
					((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, seconds * tps, level - 1));
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
