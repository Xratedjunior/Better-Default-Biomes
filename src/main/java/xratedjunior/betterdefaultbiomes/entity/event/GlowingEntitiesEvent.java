package xratedjunior.betterdefaultbiomes.entity.event;

import java.util.function.Supplier;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xratedjunior.betterdefaultbiomes.configuration.DebugConfig;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;

/**
 * Gives mobs the glowing outline to make them easier to identify in your world.
 * This could be helpful when configuring spawn weights and spawn biomes for example.
 * This doesn't interfere with the glowing effect from spectral arrows. (Tested)
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class GlowingEntitiesEvent {

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if (!event.getWorld().isClientSide()) {

			/*********************************************************** Hostile ********************************************************/

			setEntityGlowing(entity, () -> BDBEntityTypes.HUNTER.get(), DebugConfig.glowing_hunters.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.HEAD_HUNTER.get(), DebugConfig.glowing_hunters.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.JUNGLE_CREEPER.get(), DebugConfig.glowing_jungle_creepers.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.LOST_MINER.get(), DebugConfig.glowing_lost_miners.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.FROZEN_ZOMBIE.get(), DebugConfig.glowing_frozen_zombie.get());

			setEntityGlowing(entity, () -> BDBEntityTypes.DESERT_BANDIT.get(), DebugConfig.glowing_desert_bandits.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.DESERT_BANDIT_ARCHER.get(), DebugConfig.glowing_desert_bandits.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.DESERT_BANDIT_ARBALIST.get(), DebugConfig.glowing_desert_bandits.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.DESERT_BANDIT_MASTER.get(), DebugConfig.glowing_desert_bandits.get());

			/*********************************************************** Passive ********************************************************/

			setEntityGlowing(entity, () -> BDBEntityTypes.MUDDY_PIG.get(), DebugConfig.glowing_muddy_pigs.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.CAMEL.get(), DebugConfig.glowing_camels.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.DUCK.get(), DebugConfig.glowing_ducks.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.ZEBRA.get(), DebugConfig.glowing_zebra.get());
			setEntityGlowing(entity, () -> BDBEntityTypes.FROG.get(), DebugConfig.glowing_frogs.get());
		}
	}

	private void setEntityGlowing(Entity entity, Supplier<EntityType<?>> type, Boolean configValue) {
		if (entity.getType().equals(type.get())) {
			entity.setGlowingTag(configValue);
		}
	}
}
