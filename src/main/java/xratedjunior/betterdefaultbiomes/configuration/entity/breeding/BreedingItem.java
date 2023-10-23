package xratedjunior.betterdefaultbiomes.configuration.entity.breeding;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BreedingItem {
	private String item;
	private boolean breeding;
	private int healAmount;
	private int growthAmount;

	public BreedingItem(String itemName, boolean breeding, int healAmount, int growthAmount) {
		this.item = itemName;
		this.breeding = breeding;
		this.healAmount = healAmount;
		this.growthAmount = growthAmount;
	}

	public String getItemName() {
		return this.item;
	}

	/**
	 * Should item be used for breeding, or only as healing/aging food?
	 */
	public boolean getBreeding() {
		return this.breeding;
	}

	/**
	 * Hitpoints to heal.
	 */
	public int getHealAmount() {
		return this.healAmount;
	}

	/**
	 * Ticks to increase mob age.
	 */
	public int getGrowthAmount() {
		return this.growthAmount;
	}
}