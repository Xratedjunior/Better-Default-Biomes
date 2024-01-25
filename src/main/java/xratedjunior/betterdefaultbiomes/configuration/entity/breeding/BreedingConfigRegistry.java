package xratedjunior.betterdefaultbiomes.configuration.entity.breeding;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.1.0
 */
public class BreedingConfigRegistry {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final File breedingConfigDirectory = new File(BetterDefaultBiomes.BDBFolderPath.toFile() + "/food");

	public static final Map<String, BreedingConfig> BREEDING_CONFIGS = new HashMap<>();

	public static final String CAMEL_FOOD = "camel_food";
	public static final String DUCK_FOOD = "duck_food";
	public static final String ZEBRA_FOOD = "zebra_food";
	public static final String FROG_FOOD = "frog_food";

	/**
	 * Load or Create .json files
	 */
	public static void constructBreedingConfigs() {
		BetterDefaultBiomes.LOGGER.debug("Constructing Breeding Config Files");

		List<BreedingConfig> breedingConfigs = new ArrayList<>();
    	
    	breedingConfigs.add(new BreedingConfig(CAMEL_FOOD, Lists.newArrayList(
    			new BreedingItem(getItemName(Items.WHEAT), true, 2, 20),
    			new BreedingItem(getItemName(Items.HAY_BLOCK), true, 20, 180),
    			new BreedingItem(getItemName(Items.SUGAR), false, 1, 30),
    			new BreedingItem(getItemName(Items.APPLE), false, 3, 60),
    			new BreedingItem(getItemName(Items.CARROT), false, 3, 60),
    			new BreedingItem(getItemName(Items.GOLDEN_CARROT), false, 4, 60),
    			new BreedingItem(getItemName(Items.GOLDEN_APPLE), false, 10, 240),
    			new BreedingItem(getItemName(Items.ENCHANTED_GOLDEN_APPLE), false, 20, 240)
    	)));
		
    	breedingConfigs.add(new BreedingConfig(DUCK_FOOD, Lists.newArrayList(
    			new BreedingItem(getTagName(Tags.Items.SEEDS), true, 1, 30),
    			new BreedingItem(getItemName(Items.SWEET_BERRIES), true, 1, 40)
    	)));
    	
    	breedingConfigs.add(new BreedingConfig(ZEBRA_FOOD, Lists.newArrayList(
    			new BreedingItem(getItemName(Items.WHEAT), true, 2, 20),
    			new BreedingItem(getItemName(Items.HAY_BLOCK), true, 20, 180),
    			new BreedingItem(getItemName(Items.SUGAR), false, 1, 30),
    			new BreedingItem(getItemName(Items.APPLE), false, 3, 60),
    			new BreedingItem(getItemName(Items.CARROT), false, 3, 60),
    			new BreedingItem(getItemName(Items.GOLDEN_CARROT), false, 4, 60),
    			new BreedingItem(getItemName(Items.GOLDEN_APPLE), false, 10, 240),
    			new BreedingItem(getItemName(Items.ENCHANTED_GOLDEN_APPLE), false, 20, 240)
    	)));
    	
    	breedingConfigs.add(new BreedingConfig(FROG_FOOD, Lists.newArrayList(
    			new BreedingItem(getItemName(Items.SPIDER_EYE), true, 2, 40)
    	)));
    	
		//Generate .json files for the first time
		if (!breedingConfigDirectory.exists()) {
			breedingConfigDirectory.mkdirs();
			for (BreedingConfig breedingConfig : breedingConfigs) {
				generateJsonFile(breedingConfig);
			}
			return;
		}

		for (File file : breedingConfigDirectory.listFiles()) {
			String fileName = file.getName();
			if (fileName.endsWith(".json")) {
				try (FileReader json = new FileReader(file)) {
					BreedingConfig breedingConfig = GSON.fromJson(json, BreedingConfig.class);
					if (breedingConfig != null) {
						if (isValid(breedingConfig)) {
							BREEDING_CONFIGS.put(breedingConfig.getName(), breedingConfig);
							for (int i = 0; i < breedingConfigs.size(); i++) {
								if (breedingConfigs.get(i).getName().equals(breedingConfig.getName())) {
									breedingConfigs.remove(i);
								}
							}
						}
					} else {
						BetterDefaultBiomes.LOGGER.error("Could not load breeding entry from \"{}\".", fileName);
					}
				} catch (final Exception e) {
					BetterDefaultBiomes.LOGGER.error("Unable to load file \"{}\". Please make sure it's a valid json.", fileName);
					BetterDefaultBiomes.LOGGER.catching(e);
				}
			} else {
				BetterDefaultBiomes.LOGGER.error("Found invalid file \"{}\" in the BetterDefaultBiomes folder in the configs. It must be a .json file!", fileName);
			}
		}

		if (!breedingConfigs.isEmpty()) {
			for (BreedingConfig breedingConfig : breedingConfigs) {
				generateJsonFile(breedingConfig);
			}
		}

		BetterDefaultBiomes.LOGGER.debug("Done constructing Breeding Config Files");
	}

	/*
	 * Returns the registry name of an Item.
	 */
	private static String getItemName(Item item) {
		return item.getRegistryName().toString();
	}

	/*
	 * Returns the registry name of an Item.
	 */
	private static String getTagName(TagKey<Item> itemTag) {
		return "tag:" + itemTag.location().toString();
	}

	/*
	 * Generate json file
	 */
	private static void generateJsonFile(BreedingConfig breedingConfig) {
		try (FileWriter writer = new FileWriter(new File(breedingConfigDirectory, breedingConfig.getName() + ".json"))) {
			GSON.toJson(breedingConfig, writer);
			writer.flush();
			BREEDING_CONFIGS.put(breedingConfig.getName(), breedingConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Returns if the BreedingConfig has BreedingItems.
	 */
	private static boolean isValid(BreedingConfig breedingConfig) {
		boolean foundErrors = false;
		if (breedingConfig.getBreedingItems().isEmpty()) {
			foundErrors = true;
			BetterDefaultBiomes.LOGGER.error("\"{}\" errored. No valid Items.", breedingConfig.getName());
		}
		return !foundErrors;
	}

	/*********************************************************** Items List ********************************************************/

	public static List<Item> getItemList(BreedingItem input) {
		String itemName = input.getItemName();
		final List<Item> items = new ArrayList<>();
		final String[] parts = itemName.split(":");
		if (parts.length > 0) {
			if (parts[0].equalsIgnoreCase("tag") && parts.length == 3) {
				final ResourceLocation tagLocation = new ResourceLocation(parts[1], parts[2]);
				TagKey<Item> tagkey = TagKey.create(Registry.ITEM_REGISTRY, tagLocation);
				if (ForgeRegistries.ITEMS.tags().isKnownTagName(tagkey)) {
					@NotNull
					ITag<Item> tagContents = ForgeRegistries.ITEMS.tags().getTag(tagkey);
					if (!tagContents.isEmpty()) {
						for (Item item : tagContents) {
							items.add(item);
						}
					}
				}
			} else if (parts.length > 1) {
				final ResourceLocation itemLocation = new ResourceLocation(parts[0], parts[1]);
				Item item = ForgeRegistries.ITEMS.getValue(itemLocation);
				if (item != null) {
					items.add(item);
				} else {
					BetterDefaultBiomes.LOGGER.error("Couldn't find an Item for \"{}\".", itemName);
				}
			}
		}

		return items;
	}

	/*********************************************************** Breeding Items ********************************************************/

	/*
	 * Adds the Items from the json file to the foodItems ingredient
	 */
	public static List<ItemStack> getTemptationItemStacks(String breedingConfigName) {
		List<ItemStack> itemStackList = new ArrayList<ItemStack>();
		BreedingConfig breedingConfig = BreedingConfigRegistry.BREEDING_CONFIGS.get(breedingConfigName);
		for (BreedingItem breedingItem : breedingConfig.getBreedingItems()) {
			List<Item> itemList = BreedingConfigRegistry.getItemList(breedingItem);
			for (Item item : itemList) {
				itemStackList.add(new ItemStack(item));
			}
		}
		if (itemStackList.isEmpty()) {

		}
		return itemStackList;
	}

	/*
	 * Adds the Items from the json file to the breedingItems ingredient
	 */
	public static List<ItemStack> getBreedingItemStacks(String breedingConfigName) {
		List<ItemStack> itemStackList = new ArrayList<ItemStack>();
		BreedingConfig breedingConfig = BreedingConfigRegistry.BREEDING_CONFIGS.get(breedingConfigName);
		for (BreedingItem breedingItem : breedingConfig.getBreedingItems()) {
			if (breedingItem.getBreeding()) {
				List<Item> itemList = BreedingConfigRegistry.getItemList(breedingItem);
				for (Item item : itemList) {
					itemStackList.add(new ItemStack(item));
				}
			}
		}
		return itemStackList;
	}
}