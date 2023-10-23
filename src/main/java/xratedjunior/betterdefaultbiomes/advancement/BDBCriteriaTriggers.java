package xratedjunior.betterdefaultbiomes.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.advancement.trigger.BDBCriterionTrigger;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID)
public class BDBCriteriaTriggers {
	public static final BDBCriterionTrigger AXE_FEATHER_REED_GRASS_WHEAT = CriteriaTriggers.register(new BDBCriterionTrigger(BetterDefaultBiomes.locate("tool_special_drop")));

}
