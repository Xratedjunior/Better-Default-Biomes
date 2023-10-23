package xratedjunior.betterdefaultbiomes.enchantment;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.Tuple;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.FloatingEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.GuardEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.HorseProtectionEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.HorseProtectionEnchantment.Type;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.HuntingEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.ScoutEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.SmeltingTouchEnchantment;
import xratedjunior.betterdefaultbiomes.enchantment.enchantment.SpikesEnchantment;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@ObjectHolder(BetterDefaultBiomes.MOD_ID)
public class BDBEnchantments {
	public static final Enchantment SMELTING_TOUCH;
	public static final Enchantment SCOUT;
	public static final Enchantment HUNTING;
	public static final Enchantment HORSE_PROTECTION;
	public static final Enchantment HORSE_FIRE_PROTECTION;
	public static final Enchantment HORSE_FEATHER_FALLING;
	public static final Enchantment HORSE_BLAST_PROTECTION;
	public static final Enchantment HORSE_PROJECTILE_PROTECTION;
	public static final Enchantment SPIKES;
	public static final Enchantment GUARD;
	public static final Enchantment FLOATING;
	public static Map<String, Tuple<Enchantment, String>> enchantments = Maps.newHashMap();

	static {
		enchantments.put("smelting_touch", new Tuple<>(SMELTING_TOUCH = new SmeltingTouchEnchantment("smelting_touch"), "Smelt blocks when mining."));
		enchantments.put("scout", new Tuple<>(SCOUT = new ScoutEnchantment("scout"), "Mobs in a small area around the Player get the Glowing effect."));
		enchantments.put("hunting", new Tuple<>(HUNTING = new HuntingEnchantment("hunting"), "Bows/Crossbows do more damage against animals."));
		enchantments.put("horse_protection", new Tuple<>(HORSE_PROTECTION = new HorseProtectionEnchantment("horse_protection", Type.ALL, Rarity.COMMON), "Horse Protection Enchantments like the Vanilla Protection Enchantments for the Player."));
		enchantments.put("horse_fire_protection", new Tuple<>(HORSE_FIRE_PROTECTION = new HorseProtectionEnchantment("horse_fire_protection", Type.FIRE, Rarity.UNCOMMON), getComment("horse_protection")));
		enchantments.put("horse_feather_falling", new Tuple<>(HORSE_FEATHER_FALLING = new HorseProtectionEnchantment("horse_feather_falling", Type.FALL, Rarity.UNCOMMON), getComment("horse_protection")));
		enchantments.put("horse_blast_protection", new Tuple<>(HORSE_BLAST_PROTECTION = new HorseProtectionEnchantment("horse_blast_protection", Type.EXPLOSION, Rarity.RARE), getComment("horse_protection")));
		enchantments.put("horse_projectile_protection", new Tuple<>(HORSE_PROJECTILE_PROTECTION = new HorseProtectionEnchantment("horse_projectile_protection", Type.PROJECTILE, Rarity.UNCOMMON), getComment("horse_protection")));
		enchantments.put("spikes", new Tuple<>(SPIKES = new SpikesEnchantment("spikes"), "Thorns for Shields."));
		enchantments.put("guard", new Tuple<>(GUARD = new GuardEnchantment("guard"), "Knockback for Shields."));
		enchantments.put("floating", new Tuple<>(FLOATING = new FloatingEnchantment("floating"), "Horses float in water."));

	}

	public static String getComment(String name) {
		return enchantments.get(name).getB();
	}

	@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID, bus = Bus.MOD)
	public static class EnchantmentRegistryEvent {

		@SubscribeEvent
		public static void registerEnchantments(RegistryEvent.Register<Enchantment> registryEvent) {
			enchantments.values().forEach(triple -> registryEvent.getRegistry().register(triple.getA()));
			BetterDefaultBiomes.LOGGER.debug("Enchantments registered");
		}
	}
}
