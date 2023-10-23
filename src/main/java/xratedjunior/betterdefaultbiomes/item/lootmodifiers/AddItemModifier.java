package xratedjunior.betterdefaultbiomes.item.lootmodifiers;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Prevent error spamming in the console if the Farmers Delight mod is not loaded.
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class AddItemModifier extends LootModifier {

	private final Item addedItem;

	/**
	 * This loot modifier adds an item to the loot table, given the conditions specified.
	 */
	protected AddItemModifier(LootItemCondition[] conditionsIn, Item addedItemIn) {
		super(conditionsIn);
		this.addedItem = addedItemIn;
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		generatedLoot.add(new ItemStack(this.addedItem));
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<AddItemModifier> {

		@Override
		public AddItemModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
			Item addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation((GsonHelper.getAsString(object, "item"))));
			return new AddItemModifier(ailootcondition, addedItem);
		}

		@Override
		public JsonObject write(AddItemModifier instance) {
			return new JsonObject();
		}
	}
}
