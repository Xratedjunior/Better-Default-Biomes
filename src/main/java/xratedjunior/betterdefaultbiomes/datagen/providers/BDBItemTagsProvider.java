package xratedjunior.betterdefaultbiomes.datagen.providers;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.data.BDBTags;
import xratedjunior.betterdefaultbiomes.item.BDBItems;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class BDBItemTagsProvider extends ItemTagsProvider {

	public BDBItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTagsProvider, BetterDefaultBiomes.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		BetterDefaultBiomes.LOGGER.info("Generating Item Tags");
		
		/*********************************************************** Better Default Biomes ********************************************************/

		// Tree Related
		this.copy(BDBTags.Blocks.PALM_LOGS, BDBTags.Items.PALM_LOGS);
		this.copy(BDBTags.Blocks.SWAMP_WILLOW_LOGS, BDBTags.Items.SWAMP_WILLOW_LOGS);
		
		// Plant decoration
		this.copy(BDBTags.Blocks.MUSHROOMS, BDBTags.Items.MUSHROOMS);
		this.copy(BDBTags.Blocks.SMALL_FLOWERS, BDBTags.Items.SMALL_FLOWERS);
		this.copy(BDBTags.Blocks.FLOWERS, BDBTags.Items.FLOWERS);
		
		// Other Blocks
		BDBBlocks.STARFISH.forEach(starfishBlock -> this.tag(BDBTags.Items.STARFISH).add(starfishBlock.get().asItem()));
		this.copy(BDBTags.Blocks.SMALL_ROCKS, BDBTags.Items.SMALL_ROCKS);
		
		/*********************************************************** Forge ********************************************************/

		this.tag(Tags.Items.EGGS).add(BDBItems.DUCK_EGG.get());
		this.tag(Tags.Items.MUSHROOMS).addTag(BDBTags.Items.MUSHROOMS);
		
		this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);

		/*********************************************************** Minecraft ********************************************************/

		// Items
		this.tag(ItemTags.ARROWS).add(BDBItems.HUNTER_ARROW.get(), BDBItems.BANDIT_ARROW.get(), BDBItems.TORCH_ARROW.get());
		
		// Flowers
		this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
		this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS); // Empty
		this.copy(BlockTags.FLOWERS, ItemTags.FLOWERS); // Empty

		// Tree related
		this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
		this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
		this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
		this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);

		this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
		this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
	}
}