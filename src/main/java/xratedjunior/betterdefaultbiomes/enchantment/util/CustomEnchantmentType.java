package xratedjunior.betterdefaultbiomes.enchantment.util;

import java.util.function.Predicate;

import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class CustomEnchantmentType {
	public static final EnchantmentCategory SHOOTER = createEnchantmentType("shooter", ProjectileWeaponItem.class::isInstance);
	public static final EnchantmentCategory HORSE_ARMOR = createEnchantmentType("horse_armor", HorseArmorItem.class::isInstance);
	public static final EnchantmentCategory SHIELD = createEnchantmentType("shield", ShieldItem.class::isInstance);

	private static EnchantmentCategory createEnchantmentType(String name, Predicate<Item> item) {
		return EnchantmentCategory.create(name, item);
	}
}