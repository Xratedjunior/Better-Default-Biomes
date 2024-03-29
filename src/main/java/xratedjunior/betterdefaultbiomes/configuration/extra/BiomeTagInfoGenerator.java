package xratedjunior.betterdefaultbiomes.configuration.extra;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BiomeTagInfoGenerator {
	private static final String BDB_CONFIG_FOLDER = BetterDefaultBiomes.BDBFolderPath.toString();
	private static final File EXTRA_INFO_FOLDER = new File(BDB_CONFIG_FOLDER, "Biome Tag Info");

	/**
	 * Create folder and text file
	 */
	public static void makeInfoTextFiles(String worldName, Registry<Biome> biomeRegistry, List<TagKey<Biome>> tagList) {
		// Check if folder already exists.
		if (!EXTRA_INFO_FOLDER.exists()) {
			// Create folder if it doesn't exist.
			EXTRA_INFO_FOLDER.mkdirs();
		}
		textFileGenerator(worldName, biomeRegistry, tagList);
	}

	/**
	 * Generate Biome Tag Info text file
	 */
	private static void textFileGenerator(String worldName, Registry<Biome> biomeRegistry, List<TagKey<Biome>> tagList) {
		ArrayList<String> masterList = new ArrayList<String>();
		masterList.add("This is a text file generated by Better Default Biomes.");
		masterList.add("The generation of this file can be turned on/off in the mod config.");
		masterList.add("A new file will be generated when opening a world.");
		masterList.add("This file was generated when opening: \"" + worldName + "\".");
		masterList.add("The purpose of this file is to help you customise mods.");
		masterList.add("");

		List<BiomeTagList> allBiomeTagLists = new ArrayList<BiomeTagList>();

		for (TagKey<Biome> tagKey : tagList) {
			// Get tag name
			String tagName = tagKey.location().toString();
			// Create list for tag
			BiomeTagList biomeTagList = new BiomeTagList(tagName);
			// Get registered tag
			Optional<Named<Biome>> tagContents = biomeRegistry.getTag(tagKey);
			if (!tagContents.isEmpty()) {
				// Get Biomes belonging to Biome Tag
				if (tagContents.get().size() > 0) {
					// Add introduction for new Tag
					biomeTagList.createSubheader(newBiomeInfo(tagName));

					tagContents.get().forEach((biome) -> {
						String biomeName = biomeRegistry.getKey(biome.get()).toString();
						biomeTagList.addBiome(biomeName);
					});
				} else {
					// No Biomes in this Tag found
					biomeTagList.createSubheader(newEmptyBiomeInfo(tagName));
				}
			} else {
				// No matching Biome Tag found
				BetterDefaultBiomes.LOGGER.error("No matching Biome Tag found for: {}", tagName);
			}

			// Add list to file
			allBiomeTagLists.add(biomeTagList);
		}

		// Sort Lists by Biome Tag name
		Collections.sort(allBiomeTagLists);

		// Sort Biomes in lists
		for (BiomeTagList biomeTagList : allBiomeTagLists) {
			// Add introduction line
			masterList.add(biomeTagList.getSubheader());

			//Sort Biomes alphabetically
			Collections.sort(biomeTagList.getBiomeList());
			// Add Biomes to final list
			masterList.addAll(biomeTagList.getBiomeList());

			// Spacing between Tags
			masterList.add("");
		}

		List<String> finalText = masterList;

		try {
			String fileName = "info";
			File infoFile = new File(EXTRA_INFO_FOLDER.getPath(), fileName.concat(".txt"));
			Files.write(Paths.get(infoFile.getPath()), finalText, StandardCharsets.UTF_8);
			BetterDefaultBiomes.LOGGER.debug("Finished creating Biome Tag Info File.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * First line of text for every Biome Tag
	 */
	private static String newBiomeInfo(String tagName) {
		return ("These Biomes are part of the \"" + tagName + "\" Biome Tag:");
	}

	/**
	 * Line of text for every Biome Tag not containing any Biomes
	 */
	private static String newEmptyBiomeInfo(String tagName) {
		return ("The \"" + tagName + "\" Biome Tag does not contain any Biomes.");
	}

	private static class BiomeTagList implements Comparable<BiomeTagList> {
		private String name;
		private String subheader;
		private ArrayList<String> biomeList = new ArrayList<String>();

		private BiomeTagList(String name) {
			this.name = name;
		}

		private String getName() {
			return this.name;
		}

		private String getSubheader() {
			return this.subheader;
		}

		private ArrayList<String> getBiomeList() {
			return this.biomeList;
		}

		private void createSubheader(String subheader) {
			this.subheader = subheader;
		}

		private void addBiome(String biome) {
			this.biomeList.add(biome);
		}

		/**
		 * Used for sorting in lists
		 */
		@Override
		public int compareTo(BiomeTagList otherList) {
			return this.getName().compareTo(otherList.getName());
		}
	}
}
