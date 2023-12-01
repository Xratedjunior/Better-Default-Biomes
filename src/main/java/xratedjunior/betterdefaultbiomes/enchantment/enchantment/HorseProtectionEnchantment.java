package xratedjunior.betterdefaultbiomes.enchantment.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import xratedjunior.betterdefaultbiomes.configuration.EnchantmentConfig;
import xratedjunior.betterdefaultbiomes.enchantment.util.CustomEnchantmentType;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class HorseProtectionEnchantment extends Enchantment {
	public final HorseProtectionEnchantment.Type protectionType;

	public HorseProtectionEnchantment(HorseProtectionEnchantment.Type protectionType, Enchantment.Rarity rarity) {
		super(rarity, CustomEnchantmentType.HORSE_ARMOR, new EquipmentSlot[] {
			EquipmentSlot.CHEST
		});
		this.protectionType = protectionType;
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment level passed.
	 */
	@Override
	public int getMinCost(int enchantmentLevel) {
		return this.protectionType.getMinimalEnchantability() + (enchantmentLevel - 1) * this.protectionType.getEnchantIncreasePerLevel();
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + this.protectionType.getEnchantIncreasePerLevel();
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 4;
	}

	/**
	 * Determines if the enchantment passed can be applied together with this enchantment.
	 */
	@Override
	public boolean checkCompatibility(Enchantment enchantment) {
		if (enchantment instanceof HorseProtectionEnchantment) {
			HorseProtectionEnchantment horseTestEnchantment = (HorseProtectionEnchantment) enchantment;
			if (this.protectionType == horseTestEnchantment.protectionType) {
				return false;
			} else {
				return this.protectionType == HorseProtectionEnchantment.Type.FALL || horseTestEnchantment.protectionType == HorseProtectionEnchantment.Type.FALL;
			}
		} else {
			return super.checkCompatibility(enchantment);
		}
	}

	/**
	 * Determines if this enchantment can be applied to a specific ItemStack.
	 */
	@Override
	public boolean canEnchant(ItemStack stack) {
		return EnchantmentConfig.horse_protection.get() && super.canEnchant(stack);
	}

	/**
	 * This applies specifically to applying at the enchanting table. The other method {@link #canEnchant(ItemStack)}
	 * applies for all possible enchantments.
	 */
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return EnchantmentConfig.horse_protection.get() && super.canApplyAtEnchantingTable(stack);
	}

	/**
	 * Is this enchantment allowed to be enchanted on books via Enchantment Table
	 * 
	 * @return false to disable the vanilla feature
	 */
	@Override
	public boolean isAllowedOnBooks() {
		return EnchantmentConfig.horse_protection.get();
	}

	/**
	 * Checks if the enchantment can be applied to loot table drops.
	 */
	@Override
	public boolean isDiscoverable() {
		return EnchantmentConfig.horse_protection.get();
	}

	public static enum Type {
		ALL("all", 1, 11),
		FIRE("fire", 10, 8),
		FALL("fall", 5, 6),
		EXPLOSION("explosion", 5, 8),
		PROJECTILE("projectile", 3, 6);

		@SuppressWarnings("unused")
		private final String typeName;
		private final int minEnchantability;
		private final int levelCost;

		private Type(String typeName, int minEnchantability, int levelCost) {
			this.typeName = typeName;
			this.minEnchantability = minEnchantability;
			this.levelCost = levelCost;
		}

		public int getMinimalEnchantability() {
			return this.minEnchantability;
		}

		public int getEnchantIncreasePerLevel() {
			return this.levelCost;
		}
	}
}