package xratedjunior.betterdefaultbiomes.advancement;

import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.advancement.eventhandler.AxeFeatherReedGrassHandler;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID)
public class AdvancementEventSubscriber {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onBreak(BlockEvent.BreakEvent event) {
		AxeFeatherReedGrassHandler.blockBreakHandler(event);
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
		AxeFeatherReedGrassHandler.onEntityJoinWorldHandler(event);
	}
}
