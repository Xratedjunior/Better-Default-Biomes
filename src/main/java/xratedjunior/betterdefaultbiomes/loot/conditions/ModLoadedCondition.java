package xratedjunior.betterdefaultbiomes.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.fml.ModList;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
@Deprecated(since = "unused")
public class ModLoadedCondition implements LootItemCondition {
	public static final LootItemConditionType MOD_LOADED_CONDITION_TYPE = new LootItemConditionType(new ModLoadedCondition.Serializer());
	private final String modid;

	ModLoadedCondition(String modid) {
		this.modid = modid;
	}

	@Override
	public boolean test(LootContext context) {
		BetterDefaultBiomes.LOGGER.debug("TESTING FOR MODID: {}", this.modid);
		return ModList.get().isLoaded(this.modid);
	}

	@Override
	public LootItemConditionType getType() {
		return MOD_LOADED_CONDITION_TYPE;
	}

	@Override
	public String toString() {
		return "mod_loaded(\"" + this.modid + "\")";
	}

	public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ModLoadedCondition> {
		private static final String PROPERTY_NAME = "modid";

		public void serialize(JsonObject json, ModLoadedCondition condition, JsonSerializationContext context) {
			json.add(PROPERTY_NAME, context.serialize(condition.modid));
		}

		public ModLoadedCondition deserialize(JsonObject json, JsonDeserializationContext context) {
			String modid = GsonHelper.getAsString(json, PROPERTY_NAME);
			return new ModLoadedCondition(modid);
		}
	}
}
