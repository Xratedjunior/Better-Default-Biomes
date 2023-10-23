package xratedjunior.betterdefaultbiomes.enchantment;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.FloatingHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.HorseProtectionHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.HuntingHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.ScoutHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.ShieldHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.SmelterHandler;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID, bus = Bus.FORGE)
public class EnchantmentEventSubscriber {

	@SubscribeEvent
	public static void onAttack(LivingHurtEvent event) {
		SmelterHandler.attackHandler(event);
		HuntingHandler.attackHandler(event);
		HorseProtectionHandler.attackHandler(event);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onBreak(BlockEvent.BreakEvent event) {
		SmelterHandler.blockBreakHandler(event);
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		Player player = event.player;
		ScoutHandler.playerTickHandler(player);
	}

	@SubscribeEvent
	public static void onShieldBlock(LivingAttackEvent event) {
		ShieldHandler.shieldBlockHandler(event);
	}

	@SubscribeEvent
	public static void onPlayerTick(LivingEvent event) {
		FloatingHandler.livingEventHandler(event);
	}
}
