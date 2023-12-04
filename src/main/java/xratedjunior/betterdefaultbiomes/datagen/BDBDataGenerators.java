package xratedjunior.betterdefaultbiomes.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import xratedjunior.betterdefaultbiomes.datagen.providers.BDBBiomeTagsProvider;
import xratedjunior.betterdefaultbiomes.datagen.providers.BDBBlockStateProvider;
import xratedjunior.betterdefaultbiomes.datagen.providers.BDBBlockTagsProvider;
import xratedjunior.betterdefaultbiomes.datagen.providers.BDBEntityTypeTagsProvider;
import xratedjunior.betterdefaultbiomes.datagen.providers.BDBItemTagsProvider;
import xratedjunior.betterdefaultbiomes.datagen.providers.BDBWorldGenProvider;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class BDBDataGenerators {

	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		/*********************************************************** Client-side ********************************************************/

		// BlockStates and Models
		generator.addProvider(event.includeClient(), new BDBBlockStateProvider(packOutput, existingFileHelper));

		/*********************************************************** Tags ********************************************************/

		// Biome Tags
		generator.addProvider(event.includeServer(), new BDBBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
		// Block Tags
		BDBBlockTagsProvider blockTagGenerator = generator.addProvider(event.includeServer(), new BDBBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
		// Item Tags
		generator.addProvider(event.includeServer(), new BDBItemTagsProvider(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
		// EntityType Tags
		generator.addProvider(event.includeServer(), new BDBEntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));

		/*********************************************************** World ********************************************************/

		// Generation and Spawning
		generator.addProvider(event.includeServer(), new BDBWorldGenProvider(packOutput, lookupProvider));
	}

}
