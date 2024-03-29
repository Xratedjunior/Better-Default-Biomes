package xratedjunior.betterdefaultbiomes.world.generation;

import java.util.function.Supplier;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BigGrayMushroomFeature;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BigWhiteMushroomFeature;
import xratedjunior.betterdefaultbiomes.world.generation.feature.BigYellowMushroomFeature;
import xratedjunior.betterdefaultbiomes.world.generation.feature.PineconeFeature;
import xratedjunior.betterdefaultbiomes.world.generation.feature.SimpleBlockFeatureBDB;
import xratedjunior.betterdefaultbiomes.world.generation.feature.SmallRockFeature;
import xratedjunior.betterdefaultbiomes.world.generation.feature.StarfishFeature;
import xratedjunior.betterdefaultbiomes.world.generation.feature.configurations.SmallRockConfiguration;
import xratedjunior.betterdefaultbiomes.world.generation.feature.configurations.StarfishConfiguration;
import xratedjunior.betterdefaultbiomes.world.generation.feature.tree.PalmTreeFeature;
import xratedjunior.betterdefaultbiomes.world.generation.feature.tree.PalmTreeFeatureBig;
import xratedjunior.betterdefaultbiomes.world.generation.feature.tree.SwampWillowTreeFeature;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBFeatures {

	public static final DeferredRegister<Feature<?>> DEFERRED_FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BetterDefaultBiomes.MOD_ID);

	/*********************************************************** Big Mushrooms ********************************************************/

	public static final RegistryObject<BigWhiteMushroomFeature> BIG_WHITE_MUSHROOM = registerFeature("big_white_mushroom", () -> new BigWhiteMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
	public static final RegistryObject<BigYellowMushroomFeature> BIG_YELLOW_MUSHROOM = registerFeature("big_yellow_mushroom", () -> new BigYellowMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
	public static final RegistryObject<BigGrayMushroomFeature> BIG_GRAY_MUSHROOM = registerFeature("big_gray_mushroom", () -> new BigGrayMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));

	/*********************************************************** Small Features ********************************************************/

	public static final RegistryObject<Feature<SmallRockConfiguration>> SMALL_ROCK = registerFeature("small_rock", () -> new SmallRockFeature(SmallRockConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> PINECONE = registerFeature("pinecone", () -> new PineconeFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<StarfishConfiguration>> STARFISH = registerFeature("starfish", () -> new StarfishFeature(StarfishConfiguration.CODEC, false));
	public static final RegistryObject<Feature<StarfishConfiguration>> STARFISH_CORAL = registerFeature("starfish_coral", () -> new StarfishFeature(StarfishConfiguration.CODEC, true));

	// Other
	public static final RegistryObject<Feature<SimpleBlockConfiguration>> SIMPLE_BLOCK = registerFeature("simple_block", () -> new SimpleBlockFeatureBDB(SimpleBlockConfiguration.CODEC));

	/*********************************************************** Trees ********************************************************/

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> PALM_TREE = registerFeature("palm_tree", () -> new PalmTreeFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> PALM_TREE_BIG = registerFeature("palm_tree_big", () -> new PalmTreeFeatureBig(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> SWAMP_WILLOW_TREE = registerFeature("swamp_willow_tree", () -> new SwampWillowTreeFeature(NoneFeatureConfiguration.CODEC));

	/**
	 * Helper method for registering all features
	 */
	private static <F extends Feature<?>> RegistryObject<F> registerFeature(String registryName, Supplier<F> feature) {
		return DEFERRED_FEATURES.register(registryName, feature);
	}
}
