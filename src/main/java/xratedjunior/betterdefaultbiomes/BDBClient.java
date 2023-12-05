package xratedjunior.betterdefaultbiomes;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.block.property.BDBWoodTypes;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.4
 */
public class BDBClient {

	@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT, modid = BetterDefaultBiomes.MOD_ID)
	public static class ClientProxy {

		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			BetterDefaultBiomes.LOGGER.debug("Setup client");
			
			// Create sign materials
			Sheets.addWoodType(BDBWoodTypes.PALM);
			Sheets.addWoodType(BDBWoodTypes.SWAMP_WILLOW);
		}

		@SubscribeEvent
		public static void onRegisterColorHandlersEvent(RegisterColorHandlersEvent.Block event) {
			BetterDefaultBiomes.LOGGER.debug("Register Block colors");
			// Grass Coloring
			event.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5D, 1.0D), new Block[] {
					// Empty
			});

			// Foliage Coloring
			event.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor(), new Block[] {
				BDBBlocks.SHORT_GRASS.get(),
				BDBBlocks.POTTED_SHORT_GRASS.get(),
				BDBBlocks.PALM_LEAVES.get(),
				BDBBlocks.SWAMP_WILLOW_LEAVES.get()
			});

			// Random Coloring
			event.register((state, world, pos, tintIndex) -> world != null && pos != null ? 0xe942f5 : 0x425af5, new Block[] {
					// Empty
			});
		}

		@SubscribeEvent
		public static void onRegisterColorHandlersEvent(RegisterColorHandlersEvent.Item event) {
			BetterDefaultBiomes.LOGGER.debug("Register Item colors");
			// Item Coloring
			event.register((stack, tintIndex) -> {
				BlockState BlockState = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
				return event.getBlockColors().getColor(BlockState, null, null, tintIndex);
			}, new Block[] {
				BDBBlocks.SHORT_GRASS.get(),
				BDBBlocks.POTTED_SHORT_GRASS.get(),
				BDBBlocks.PALM_LEAVES.get(),
				BDBBlocks.SWAMP_WILLOW_LEAVES.get()
			});
		}
	}
}
