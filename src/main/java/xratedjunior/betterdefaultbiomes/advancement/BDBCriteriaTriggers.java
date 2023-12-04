package xratedjunior.betterdefaultbiomes.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.advancement.trigger.BDBEmptyCriterionTrigger;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID)
public class BDBCriteriaTriggers {
	public static final BDBEmptyCriterionTrigger AXE_FEATHER_REED_GRASS_WHEAT = CriteriaTriggers.register(BetterDefaultBiomes.find("tool_special_drop"), new BDBEmptyCriterionTrigger());

}
