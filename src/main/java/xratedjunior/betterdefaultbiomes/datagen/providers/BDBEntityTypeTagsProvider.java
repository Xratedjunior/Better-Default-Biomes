package xratedjunior.betterdefaultbiomes.datagen.providers;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.data.BDBTags;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class BDBEntityTypeTagsProvider extends EntityTypeTagsProvider {

	public BDBEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper existingFileHelper) {
		super(output, provider, BetterDefaultBiomes.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		BetterDefaultBiomes.LOGGER.info("Generating EntityType Tags");

		/*********************************************************** Better Default Biomes ********************************************************/

		this.tag(BDBTags.EntityTypes.ARROWS).add(BDBEntityTypes.HUNTER_ARROW.get(), BDBEntityTypes.BANDIT_ARROW.get(), BDBEntityTypes.TORCH_ARROW.get());
		this.tag(BDBTags.EntityTypes.CACTUS_IMMUNE).add(EntityType.BEE);

		/*********************************************************** Minecraft ********************************************************/

		this.tag(EntityTypeTags.ARROWS).addTag(BDBTags.EntityTypes.ARROWS);
	}
}