package xratedjunior.betterdefaultbiomes.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import xratedjunior.betterdefaultbiomes.entity.hostile.JungleCreeperEntity;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class StealthGoal<T extends LivingEntity> extends Goal {
	protected final JungleCreeperEntity jungleCreeper;
	protected LivingEntity targetLivingEntity;
	protected final float closestStealthDistance;
	// Minimum interval that prevents the Jungle Creeper from flickering translucent if the player stays close to the minimum stealth distance.
	private int minStealthDuration = 20;
	private int stealthDurationTick = 0;

	public StealthGoal(JungleCreeperEntity jungleCreeperIn, float closestDistance) {
		this.jungleCreeper = jungleCreeperIn;
		this.closestStealthDistance = closestDistance;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean canUse() {
		// Is always updating Jungle Creeper state.
		return true;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		this.targetLivingEntity = this.jungleCreeper.getTarget();
		// Check if the Jungle Creeper has a target.
		if (this.targetLivingEntity != null) {
			// Check if the target is alive.
			if (this.targetLivingEntity.isAlive()) {
				// Check if the Jungle Creeper is close enough to be visible.
				if (this.jungleCreeper.distanceToSqr(this.targetLivingEntity) < this.closestStealthDistance) {
					// Check if enough time has passed to disable stealth. (Interval)
					if (this.jungleCreeper.isStealth() && this.stealthDurationTick >= this.minStealthDuration) {
						this.jungleCreeper.setStealth(false);
						return;
					}
				}
			}
		}

		// No requirements were met to turn stealth off.
		if (this.jungleCreeper.canStealth()) {
			this.jungleCreeper.setStealth(true);
			this.stealthDurationTick = 0;
		}

		// Keep count of the minimum stealth duration.
		if (this.jungleCreeper.isAlive() && this.jungleCreeper.isStealth() && this.stealthDurationTick < this.minStealthDuration) {
			this.stealthDurationTick++;
		}

	}
}