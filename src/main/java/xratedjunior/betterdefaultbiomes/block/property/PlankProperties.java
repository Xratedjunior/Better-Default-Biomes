package xratedjunior.betterdefaultbiomes.block.property;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class PlankProperties {
	private final MaterialColor materialColor;
	private final Properties blockProperties;

	public PlankProperties(MaterialColor materialColor) {
		this.materialColor = materialColor;
		this.blockProperties = Properties.of(Material.WOOD, materialColor).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	}

	public MaterialColor getMaterialColor() {
		return this.materialColor;
	}

	public Properties getProperties() {
		return this.blockProperties;
	}
}
