package xratedjunior.betterdefaultbiomes.enchantment;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.FloatingHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.HorseProtectionHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.HuntingHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.ScoutHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.ShieldHandler;
import xratedjunior.betterdefaultbiomes.enchantment.eventhandler.SmelterHandler;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class EnchantmentEventSubscriber {

	@SubscribeEvent
	public void onAttack(LivingHurtEvent event) {
		SmelterHandler.attackHandler(event);
		HuntingHandler.attackHandler(event);
		HorseProtectionHandler.attackHandler(event);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onBreak(BlockEvent.BreakEvent event) {
		SmelterHandler.blockBreakHandler(event);
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		Player player = event.player;
		ScoutHandler.playerTickHandler(player);
	}

	@SubscribeEvent
	public void onShieldBlock(LivingAttackEvent event) {
		ShieldHandler.shieldBlockHandler(event);
	}

	@SubscribeEvent
	public void onLivingTick(LivingEvent event) {
		FloatingHandler.livingEventHandler(event);
	}
}
