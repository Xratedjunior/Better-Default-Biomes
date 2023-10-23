package xratedjunior.betterdefaultbiomes.item.recipeconditions;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * Prevent unknown recipe condition errors of the Enhanced Mushrooms mod.
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class EnhancedMushroomsCondition implements ICondition {
	private final ResourceLocation location;

	public EnhancedMushroomsCondition(ResourceLocation location) {
		this.location = location;
	}

	@Override
	public ResourceLocation getID() {
		return this.location;
	}

	@Override
	public boolean test(IContext context) {
		if (ModList.get().isLoaded("enhanced_mushrooms")) {
			JsonObject dummyObject = new JsonObject();
			dummyObject.addProperty("type", "enhanced_mushrooms:foods_enabled");
			return CraftingHelper.getCondition(dummyObject).test(context);
		}
		return false;
	}

	public static class Serializer implements IConditionSerializer<EnhancedMushroomsCondition> {
		private final ResourceLocation location;

		public Serializer() {
			this.location = new ResourceLocation(BetterDefaultBiomes.MOD_ID, "em_foods_enabled");
		}

		@Override
		public void write(JsonObject json, EnhancedMushroomsCondition value) {
		}

		@Override
		public EnhancedMushroomsCondition read(JsonObject json) {
			return new EnhancedMushroomsCondition(this.location);
		}

		@Override
		public ResourceLocation getID() {
			return this.location;
		}
	}

	/**
	 * Old and never used
	 */
	@Override
	public boolean test() {
		return false;
	}
}
