package xratedjunior.betterdefaultbiomes.enchantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import xratedjunior.betterdefaultbiomes.configuration.EnchantmentConfig;
import xratedjunior.betterdefaultbiomes.enchantment.util.CustomEnchantmentType;

/**
 * @author 	Xrated_junior
 * @version	1.18.2-1.0.0
 */
public class FloatingEnchantment extends Enchantment {

	public FloatingEnchantment(String name) {
        super(Rarity.RARE, CustomEnchantmentType.HORSE_ARMOR, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		this.setRegistryName(name);
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment level passed.
	 */
	@Override
	public int getMinCost(int enchantmentLevel) {
		return 5 + 20 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return super.getMinCost(enchantmentLevel) + 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 1;
	}

	/**
	 * Determines if the enchantment passed can be applied together with this enchantment.
	 */
	@Override
	public boolean checkCompatibility(Enchantment ench) {
		return super.checkCompatibility(ench);
	}

	/**
	 * Determines if this enchantment can be applied to a specific ItemStack.
	 */
	@Override
	public boolean canEnchant(ItemStack stack) {
		return EnchantmentConfig.floating.get() && super.canEnchant(stack);
	}

	/**
	 * This applies specifically to applying at the enchanting table. The other method {@link #canEnchant(ItemStack)}
	 * applies for all possible enchantments.
	 */
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return EnchantmentConfig.floating.get() && super.canApplyAtEnchantingTable(stack);
	}

	/**
	 * Is this enchantment allowed to be enchanted on books via Enchantment Table
	 * 
	 * @return false to disable the vanilla feature
	 */
	@Override
	public boolean isAllowedOnBooks() {
		return EnchantmentConfig.floating.get();
	}
}