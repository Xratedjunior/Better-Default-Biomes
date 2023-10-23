package xratedjunior.betterdefaultbiomes.advancement.trigger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;

/**
 * Allows Advancements that can be triggered in code.
 * Referenced from {@link SimpleCriterionTrigger}
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBCriterionTrigger implements CriterionTrigger<BDBCriterionTrigger.BDBCriterionTriggerInstance> {
	private final Map<PlayerAdvancements, BDBCriterionTriggerListeners> listeners = Maps.newHashMap();
	private final ResourceLocation criterionID;

	public BDBCriterionTrigger(ResourceLocation criterionID) {
		this.criterionID = criterionID;
	}

	@Override
	public ResourceLocation getId() {
		return this.criterionID;
	}

	@Override
	public void addPlayerListener(PlayerAdvancements playerAdvancements, Listener<BDBCriterionTriggerInstance> listener) {
		BDBCriterionTriggerListeners listeners = this.listeners.computeIfAbsent(playerAdvancements, BDBCriterionTriggerListeners::new);
		listeners.add(listener);
	}

	@Override
	public void removePlayerListener(PlayerAdvancements playerAdvancements, Listener<BDBCriterionTriggerInstance> listener) {
		BDBCriterionTriggerListeners listeners = this.listeners.get(playerAdvancements);
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty()) {
				this.listeners.remove(playerAdvancements);
			}
		}
	}

	@Override
	public void removePlayerListeners(PlayerAdvancements playerAdvancements) {
		this.listeners.remove(playerAdvancements);
	}

	@Override
	public BDBCriterionTriggerInstance createInstance(JsonObject object, DeserializationContext conditions) {
		return new BDBCriterionTriggerInstance(this.criterionID);
	}

	public void trigger(ServerPlayer player) {
		BDBCriterionTriggerListeners listeners = this.listeners.get(player.getAdvancements());
		if (listeners != null && !listeners.isEmpty()) {
			listeners.trigger();
		}
	}

	/**
	 * Referenced from {@link AbstractCriterionTriggerInstance}
	 * 
	 * @author 	Xrated_junior
	 * @version	1.18.2-1.0.0
	 */
	public static class BDBCriterionTriggerInstance implements CriterionTriggerInstance {
		private final ResourceLocation criterion;

		BDBCriterionTriggerInstance(ResourceLocation criterion) {
			super();
			this.criterion = criterion;
		}

		@Override
		public ResourceLocation getCriterion() {
			return this.criterion;
		}

		@Override
		public JsonObject serializeToJson(SerializationContext conditions) {
			return new JsonObject();
		}
	}

	static class BDBCriterionTriggerListeners {
		private final Set<Listener<BDBCriterionTriggerInstance>> listeners = new HashSet<>();
		private final PlayerAdvancements advancements;

		public BDBCriterionTriggerListeners(PlayerAdvancements advancements) {
			this.advancements = advancements;
		}
		
		public boolean isEmpty() {
			return this.listeners.isEmpty();
		}

		public void add(Listener<BDBCriterionTriggerInstance> listener) {
			this.listeners.add(listener);
		}

		public void remove(Listener<BDBCriterionTriggerInstance> listener) {
			this.listeners.remove(listener);
		}

		public void trigger() {
			this.listeners.forEach(listener -> listener.run(this.advancements));
		}
	}
}