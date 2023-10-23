package xratedjunior.betterdefaultbiomes.configuration.entity.breeding;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BreedingConfig {
	@Expose
	private String name;
	@Expose
	private String requirement = "A minimum of 1 Item is required.";
	@Expose
	private String[] info = new String[] {
		"item; Can be an Item name like: minecraft:wheat. Or it can be an Item Tag like: tag:forge:seeds.",
		"breeding; Determines if this Item can be used to breed.",
		"healAmount; Is the amount of half a hearts this Item will heal.",
		"growthAmount; Is the amount of ticks this Item will advance the Age of a Baby. A Baby needs 24000 ticks to become an adult.",
		"(20 ticks is 1 second. This means that a Baby needs 1200 seconds, or 20 minutes, to become an adult.)"
	};
	@Expose
	private List<BreedingItem> items = new ArrayList<>();

	public BreedingConfig(String configName, List<BreedingItem> breedingItems) {
		this.name = configName;
		this.items = breedingItems;
	}

	public String getName() {
		return name;
	}

	public List<BreedingItem> getBreedingItems() {
		return this.items;
	}
}