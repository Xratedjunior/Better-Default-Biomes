package xratedjunior.betterdefaultbiomes.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBDataGenerators {

	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		// Blocks and Items
		generator.addProvider(event.includeClient(), new BDBBlockStateProvider(packOutput, existingFileHelper));
		
		// Tags
		generator.addProvider(event.includeServer(), new BDBBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
		
		// Generation and Spawning
		generator.addProvider(event.includeServer(), new BDBWorldGenProvider(packOutput, lookupProvider));
	}

}
