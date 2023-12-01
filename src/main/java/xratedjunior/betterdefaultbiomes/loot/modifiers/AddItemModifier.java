package xratedjunior.betterdefaultbiomes.loot.modifiers;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

/**
 * UPDATE 1.20, FarmersDelight is not updated to 1.19.4
 * For now at least not spamming any errors, TODO implement and check ItemStack working?
 * 
 * Prevent error spamming in the console if the Farmers Delight mod is not loaded.
 * REFERENCE: {@linkplain https://github.com/vectorwing/FarmersDelight/blob/1.19/src/main/java/vectorwing/farmersdelight/common/loot/modifier/AddItemModifier.java}
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class AddItemModifier extends LootModifier {
	public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).and(ItemStack.CODEC.fieldOf("result").forGetter(AddItemModifier::getItemStack)).apply(inst, AddItemModifier::new)));
	public final ItemStack addedItem;

	/**
	 * This loot modifier adds an item to the loot table, given the conditions specified.
	 */
	protected AddItemModifier(LootItemCondition[] conditionsIn, ItemStack addedItemIn) {
		super(conditionsIn);
		this.addedItem = addedItemIn;
	}
	
	public ItemStack getItemStack() {
		return this.addedItem;
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		generatedLoot.add(this.addedItem);
		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}
