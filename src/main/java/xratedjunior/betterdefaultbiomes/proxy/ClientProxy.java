package xratedjunior.betterdefaultbiomes.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class ClientProxy extends CommonProxy {
	public ClientProxy() {

	}

	@Override
	public void init() {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		ItemColors itemColors = Minecraft.getInstance().getItemColors();

		//Grass Coloring
		blockColors.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5D, 1.0D));

		//Foliage Coloring
		blockColors.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor(), new Block[] {
			BDBBlocks.SHORT_GRASS.get(),
			BDBBlocks.POTTED_SHORT_GRASS.get(),
			BDBBlocks.PALM_LEAVES.get(),
			BDBBlocks.SWAMP_WILLOW_LEAVES.get()
		});

		//Random Coloring
		blockColors.register((state, world, pos, tintIndex) -> world != null && pos != null ? 0xe942f5 : 0x425af5);

		//Item Coloring
		itemColors.register((stack, tintIndex) -> {
			BlockState BlockState = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
			return blockColors.getColor(BlockState, null, null, tintIndex);
		}, new Block[] {
			BDBBlocks.SHORT_GRASS.get(),
			BDBBlocks.POTTED_SHORT_GRASS.get(),
			BDBBlocks.PALM_LEAVES.get(),
			BDBBlocks.SWAMP_WILLOW_LEAVES.get()
		});
	}
}
