package xratedjunior.betterdefaultbiomes.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.phys.Vec3;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.passive.CamelEntity;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class CamelFollowCaravanGoal extends Goal {
	public final CamelEntity camel;
	private double speedModifier;
	private int distCheckCounter;

	public CamelFollowCaravanGoal(CamelEntity camelIn, double speedModifierIn) {
		this.camel = camelIn;
		this.speedModifier = speedModifierIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	@Override
	public boolean canUse() {
		if (!this.camel.isLeashed() && !this.camel.inCaravan()) {
			List<Entity> list = this.camel.level().getEntities(this.camel, this.camel.getBoundingBox().inflate(9.0D, 4.0D, 9.0D), (p_220719_0_) -> {
				EntityType<?> entitytype = p_220719_0_.getType();
				return entitytype == BDBEntityTypes.CAMEL.get();
			});
			CamelEntity CamelEntity = null;
			double d0 = Double.MAX_VALUE;

			for (Entity entity : list) {
				CamelEntity CamelEntity1 = (CamelEntity) entity;
				if (CamelEntity1.inCaravan() && !CamelEntity1.hasCaravanTail()) {
					double d1 = this.camel.distanceToSqr(CamelEntity1);
					if (!(d1 > d0)) {
						d0 = d1;
						CamelEntity = CamelEntity1;
					}
				}
			}

			if (CamelEntity == null) {
				for (Entity entity1 : list) {
					CamelEntity CamelEntity2 = (CamelEntity) entity1;
					if (CamelEntity2.isLeashed() && !CamelEntity2.hasCaravanTail()) {
						double d2 = this.camel.distanceToSqr(CamelEntity2);
						if (!(d2 > d0)) {
							d0 = d2;
							CamelEntity = CamelEntity2;
						}
					}
				}
			}

			if (CamelEntity == null) {
				return false;
			} else if (d0 < 4.0D) {
				return false;
			} else if (!CamelEntity.isLeashed() && !this.firstIsLeashed(CamelEntity, 1)) {
				return false;
			} else {
				this.camel.joinCaravan(CamelEntity);
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		if (this.camel.inCaravan() && this.camel.getCaravanHead().isAlive() && this.firstIsLeashed(this.camel, 0)) {
			double d0 = this.camel.distanceToSqr(this.camel.getCaravanHead());
			if (d0 > 676.0D) {
				if (this.speedModifier <= 3.0D) {
					this.speedModifier *= 1.2D;
					this.distCheckCounter = 40;
					return true;
				}

				if (this.distCheckCounter == 0) {
					return false;
				}
			}

			if (this.distCheckCounter > 0) {
				--this.distCheckCounter;
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	@Override
	public void stop() {
		this.camel.leaveCaravan();
		this.speedModifier = 2.1D;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (this.camel.inCaravan()) {
			if (!(this.camel.getLeashHolder() instanceof LeashFenceKnotEntity)) {
				CamelEntity CamelEntity = (CamelEntity) this.camel.getCaravanHead();
				double d0 = (double) this.camel.distanceTo(CamelEntity);
				Vec3 vector3d = (new Vec3(CamelEntity.getX() - this.camel.getX(), CamelEntity.getY() - this.camel.getY(), CamelEntity.getZ() - this.camel.getZ())).normalize().scale(Math.max(d0 - 2.0D, 0.0D));
				this.camel.getNavigation().moveTo(this.camel.getX() + vector3d.x, this.camel.getY() + vector3d.y, this.camel.getZ() + vector3d.z, this.speedModifier);
			}
		}
	}

	private boolean firstIsLeashed(CamelEntity camel, int p_190858_2_) {
		if (p_190858_2_ > 8) {
			return false;
		} else if (camel.inCaravan()) {
			if (camel.getCaravanHead().isLeashed()) {
				return true;
			} else {
				CamelEntity CamelEntity = (CamelEntity) camel.getCaravanHead();
				++p_190858_2_;
				return this.firstIsLeashed(CamelEntity, p_190858_2_);
			}
		} else {
			return false;
		}
	}
}