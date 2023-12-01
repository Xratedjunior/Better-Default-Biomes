package xratedjunior.betterdefaultbiomes.configuration.extra;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.configuration.DebugConfig;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class InfoFileEventHandler {
	public static List<TagKey<Biome>> BIOME_TAGS = Lists.newArrayList();

	@SubscribeEvent
	public void onServerStart(ServerStartingEvent event) {
		if (DebugConfig.extra_info_files.get()) {
			BetterDefaultBiomes.LOGGER.debug("Creating Biome Tag Info File.");
			// Get Biome registry
			Registry<Biome> biomeRegistry = event.getServer().registryAccess().registryOrThrow(Registries.BIOME);
			// Get all Biome Tags and filter for Forge and Minecraft only
			biomeRegistry.getTagNames().filter(tagKey -> {
				String nameSpace = tagKey.location().getNamespace();
				return nameSpace.equals("minecraft") || nameSpace.equals("forge");
			}).forEach(filteredTagKey -> {
				BIOME_TAGS.add(filteredTagKey);
			});

			// Make text file
			BiomeTagInfoGenerator.makeInfoTextFiles(event.getServer().getWorldData().getLevelName(), biomeRegistry, BIOME_TAGS);
		}
	}
}
