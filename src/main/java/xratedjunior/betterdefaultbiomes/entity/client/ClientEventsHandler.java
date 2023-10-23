package xratedjunior.betterdefaultbiomes.entity.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.FrozenZombieRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.JungleCreeperRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.LostMinerRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.desertbandit.DesertBanditArbalistRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.desertbandit.DesertBanditArcherRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.desertbandit.DesertBanditMasterRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.desertbandit.DesertBanditRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.hunter.HeadHunterRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.hostile.hunter.HunterRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.passive.DuckRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.passive.FrogRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.passive.MuddyPigRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.passive.ZebraRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.passive.camel.CamelRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.projectile.BanditArrowRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.projectile.DuckEggRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.projectile.HunterArrowRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.projectile.SmallRockRenderer;
import xratedjunior.betterdefaultbiomes.entity.client.renderer.projectile.TorchArrowRenderer;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventsHandler {

	/**
	 * Register EntityModelLayers
	 */
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		BetterDefaultBiomes.LOGGER.debug("Register Model Layers");
		BDBModelLayers.BDB_MODELS.forEach((model) -> event.registerLayerDefinition(model.getModelLayerLocation(), model.getModelSupplier()));
	}

	/**
	 * Register EntityRenderers
	 */
	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		BetterDefaultBiomes.LOGGER.debug("Register Entity Renderers");
		/*********************************************************** Hostile ********************************************************/

		event.registerEntityRenderer(BDBEntityTypes.HUNTER.get(), HunterRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.HEAD_HUNTER.get(), HeadHunterRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.JUNGLE_CREEPER.get(), JungleCreeperRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.LOST_MINER.get(), LostMinerRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.FROZEN_ZOMBIE.get(), FrozenZombieRenderer::new);

		event.registerEntityRenderer(BDBEntityTypes.DESERT_BANDIT.get(), DesertBanditRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.DESERT_BANDIT_ARCHER.get(), DesertBanditArcherRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.DESERT_BANDIT_ARBALIST.get(), DesertBanditArbalistRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.DESERT_BANDIT_MASTER.get(), DesertBanditMasterRenderer::new);

		/*********************************************************** Passive ********************************************************/

		event.registerEntityRenderer(BDBEntityTypes.MUDDY_PIG.get(), MuddyPigRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.CAMEL.get(), CamelRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.DUCK.get(), DuckRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.ZEBRA.get(), ZebraRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.FROG.get(), FrogRenderer::new);

		/*********************************************************** Projectile ********************************************************/

		// Miscellaneous
		event.registerEntityRenderer(BDBEntityTypes.DUCK_EGG.get(), DuckEggRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.SMALL_ROCK.get(), SmallRockRenderer::new);

		// Arrows
		event.registerEntityRenderer(BDBEntityTypes.HUNTER_ARROW.get(), HunterArrowRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.BANDIT_ARROW.get(), BanditArrowRenderer::new);
		event.registerEntityRenderer(BDBEntityTypes.TORCH_ARROW.get(), TorchArrowRenderer::new);
	}
}