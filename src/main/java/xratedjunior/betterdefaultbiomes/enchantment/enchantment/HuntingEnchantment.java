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
public class HuntingEnchantment extends Enchantment {
	
	public HuntingEnchantment(String name) {
        super(Rarity.COMMON, CustomEnchantmentType.SHOOTER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
		this.setRegistryName(name);
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment level passed.
	 */
	@Override
	public int getMinCost(int enchantmentLevel) {
		return 1 + (enchantmentLevel - 1) * 10;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return super.getMinCost(enchantmentLevel) + 15;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 3;
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
		return EnchantmentConfig.hunting.get() && super.canEnchant(stack);
	}

	/**
	 * This applies specifically to applying at the enchanting table. The other method {@link #canEnchant(ItemStack)}
	 * applies for <i>all possible</i> enchantments.
	 */
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return EnchantmentConfig.hunting.get() && super.canApplyAtEnchantingTable(stack);
	}

	/**
	 * Is this enchantment allowed to be enchanted on books via Enchantment Table
	 * 
	 * @return false to disable the vanilla feature
	 */
	@Override
	public boolean isAllowedOnBooks() {
		return EnchantmentConfig.hunting.get();
	}
}