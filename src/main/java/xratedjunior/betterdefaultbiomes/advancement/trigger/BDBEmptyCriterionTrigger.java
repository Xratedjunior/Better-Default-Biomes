package xratedjunior.betterdefaultbiomes.advancement.trigger;

import java.util.Optional;

import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import xratedjunior.betterdefaultbiomes.advancement.trigger.BDBEmptyCriterionTrigger.BDBEmptyInstance;

/**
 * Allows Advancements that can be triggered in code.
 * Referenced from {@link SimpleCriterionTrigger}
 * 
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class BDBEmptyCriterionTrigger extends SimpleCriterionTrigger<BDBEmptyInstance> {

	@Override
	protected BDBEmptyInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> predicate, DeserializationContext context) {
		return new BDBEmptyCriterionTrigger.BDBEmptyInstance(predicate);
	}

	public void trigger(ServerPlayer player) {
		this.trigger(player, (predicate) -> {
			return true;
		});
	}

	/**
	 * @author  Xrated_junior
	 * @version 1.20.2-5.0.0
	 */
	static class BDBEmptyInstance extends AbstractCriterionTriggerInstance {

		public BDBEmptyInstance(Optional<ContextAwarePredicate> predicate) {
			super(predicate);
		}
	}
}