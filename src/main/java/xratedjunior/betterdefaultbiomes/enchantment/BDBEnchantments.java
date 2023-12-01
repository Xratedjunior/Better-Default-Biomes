package xratedjunior.betterdefaultbiomes.enchantment;

import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.FloatingEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.GuardEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.HorseProtectionEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.HuntingEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.ScoutEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.SmeltingTouchEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.SpikesEnchantment;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBEnchantments {
	public static final DeferredRegister<Enchantment> DEFERRED_ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BetterDefaultBiomes.MOD_ID);
	// Map of Enchantment descriptions.
	public static Map<String, String> ENCHANTMENTS = Maps.newHashMap();

	public static final RegistryObject<Enchantment> SMELTING_TOUCH = registerEnchantment("smelting_touch", "Smelt blocks when mining.", () -> new SmeltingTouchEnchantment());
	public static final RegistryObject<Enchantment> SCOUT = registerEnchantment("scout", "Mobs in a small area around the Player get the Glowing effect.", () -> new ScoutEnchantment());
	public static final RegistryObject<Enchantment> HUNTING = registerEnchantment("hunting", "Bows/Crossbows do more damage against animals.", () -> new HuntingEnchantment());
	public static final RegistryObject<Enchantment> HORSE_PROTECTION = registerEnchantment("horse_protection", "Horse Protection Enchantments like the Vanilla Protection Enchantments for the Player.", () -> new HorseProtectionEnchantment(HorseProtectionEnchantment.Type.ALL, Rarity.COMMON));
	public static final RegistryObject<Enchantment> HORSE_FIRE_PROTECTION = registerEnchantment("horse_fire_protection", getComment("horse_protection"), () -> new HorseProtectionEnchantment(HorseProtectionEnchantment.Type.FIRE, Rarity.UNCOMMON));
	public static final RegistryObject<Enchantment> HORSE_FEATHER_FALLING = registerEnchantment("horse_feather_falling", getComment("horse_protection"), () -> new HorseProtectionEnchantment(HorseProtectionEnchantment.Type.FALL, Rarity.UNCOMMON));
	public static final RegistryObject<Enchantment> HORSE_BLAST_PROTECTION = registerEnchantment("horse_blast_protection", getComment("horse_protection"), () -> new HorseProtectionEnchantment(HorseProtectionEnchantment.Type.EXPLOSION, Rarity.RARE));
	public static final RegistryObject<Enchantment> HORSE_PROJECTILE_PROTECTION = registerEnchantment("horse_projectile_protection", getComment("horse_protection"), () -> new HorseProtectionEnchantment(HorseProtectionEnchantment.Type.PROJECTILE, Rarity.UNCOMMON));
	public static final RegistryObject<Enchantment> SPIKES = registerEnchantment("spikes", "Thorns for Shields.", () -> new SpikesEnchantment());
	public static final RegistryObject<Enchantment> GUARD = registerEnchantment("guard", "Knockback for Shields.", () -> new GuardEnchantment());
	public static final RegistryObject<Enchantment> FLOATING = registerEnchantment("floating", "Horses float in water.", () -> new FloatingEnchantment());

	/**
	 * Used in config builder
	 */
	public static String getComment(String enchantmentName) {
		return ENCHANTMENTS.get(enchantmentName);
	}

	/**
	 * Helper method for registering all Items
	 */
	public static <E extends Enchantment> RegistryObject<E> registerEnchantment(@Nonnull String registryName, @Nonnull String comment, Supplier<E> enchantment) {
		ENCHANTMENTS.put(registryName, comment);
		return DEFERRED_ENCHANTMENTS.register(registryName, enchantment);
	}
}
