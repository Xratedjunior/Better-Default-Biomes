package xratedjunior.betterdefaultbiomes.block;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import javax.annotation.Nonnull;

import org.apache.commons.compress.utils.Lists;

import com.google.common.collect.Maps;

import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.block.DoubleWaterPlantBlockBDB;
import xratedjunior.betterdefaultbiomes.block.block.FlowerBlockBDB;
import xratedjunior.betterdefaultbiomes.block.block.GrassBlockBDB;
import xratedjunior.betterdefaultbiomes.block.block.LeavesBlockBDB;
import xratedjunior.betterdefaultbiomes.block.block.MushroomBlockBDB;
import xratedjunior.betterdefaultbiomes.block.block.PineconeBlock;
import xratedjunior.betterdefaultbiomes.block.block.SaplingBlockBDB;
import xratedjunior.betterdefaultbiomes.block.block.SimpleBlock;
import xratedjunior.betterdefaultbiomes.block.block.SmallCactusBlockBDB;
import xratedjunior.betterdefaultbiomes.block.block.SmallRockBlock;
import xratedjunior.betterdefaultbiomes.block.block.StarfishBlock;
import xratedjunior.betterdefaultbiomes.block.block.StarfishWallBlock;
import xratedjunior.betterdefaultbiomes.block.block.grower.PalmTreeGrower;
import xratedjunior.betterdefaultbiomes.block.block.grower.PineconeTreeGrower;
import xratedjunior.betterdefaultbiomes.block.block.grower.SwampWillowTreeGrower;
import xratedjunior.betterdefaultbiomes.block.property.BDBWoodTypes;
import xratedjunior.betterdefaultbiomes.item.BDBItems;
import xratedjunior.betterdefaultbiomes.item.item.SmallRockItem;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundTypes;
import xratedjunior.betterdefaultbiomes.world.generation.BDBConfiguredFeatures;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID, bus = Bus.MOD)
public class BDBBlocks {
	public static final DeferredRegister<Block> DEFERRED_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterDefaultBiomes.MOD_ID);
	public static final Map<Supplier<Block>, Supplier<Block>> POTTED_PLANTS = Maps.newHashMap();
	public static final List<Supplier<Block>> SMALL_ROCKS = Lists.newArrayList();
	public static final List<Supplier<Block>> STARFISH = Lists.newArrayList();
	
	/*********************************************************** Trees ********************************************************/

	public static final RegistryObject<Block> PALM_SAPLING = registerBlockAndBlockItem("palm_sapling", () -> new SaplingBlockBDB(new PalmTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
	public static final RegistryObject<Block> PALM_SAPLING_POTTED = registerBlock("palm_sapling_potted", () -> flowerPot(PALM_SAPLING));
	public static final RegistryObject<Block> PALM_LEAVES = registerBlockAndBlockItem("palm_leaves", () -> new LeavesBlockBDB(Properties.copy(Blocks.OAK_LEAVES)));
	public static final RegistryObject<Block> PALM_LOG = registerBlockAndBlockItem("palm_log", () -> log(MapColor.WOOD, MapColor.WOOD));
	public static final RegistryObject<Block> PALM_WOOD = registerBlockAndBlockItem("palm_wood", () -> wood(MapColor.WOOD));
	public static final RegistryObject<Block> PALM_LOG_STRIPPED = registerBlockAndBlockItem("palm_log_stripped", () -> log(MapColor.WOOD, MapColor.WOOD));
	public static final RegistryObject<Block> PALM_WOOD_STRIPPED = registerBlockAndBlockItem("palm_wood_stripped", () -> wood(MapColor.TERRACOTTA_WHITE));
	public static final RegistryObject<Block> PALM_PLANKS = registerBlockAndBlockItem("palm_planks", () -> new Block(Properties.copy(Blocks.OAK_PLANKS)));
	public static final RegistryObject<Block> PALM_STAIRS = registerBlockAndBlockItem("palm_stairs", () -> new StairBlock(() -> PALM_PLANKS.get().defaultBlockState(), Properties.copy(PALM_PLANKS.get())));
	public static final RegistryObject<Block> PALM_SLAB = registerBlockAndBlockItem("palm_slab", () -> new SlabBlock(Properties.copy(PALM_PLANKS.get())));
	public static final RegistryObject<Block> PALM_FENCE = registerBlockAndBlockItem("palm_fence", () -> new FenceBlock(Properties.copy(PALM_PLANKS.get())));
	public static final RegistryObject<Block> PALM_FENCE_GATE = registerBlockAndBlockItem("palm_fence_gate", () -> new FenceGateBlock(Properties.copy(PALM_PLANKS.get()), BDBWoodTypes.PALM));
	public static final RegistryObject<Block> PALM_DOOR = registerBlockAndBlockItem("palm_door", () -> new DoorBlock(Properties.copy(Blocks.OAK_DOOR), BDBWoodTypes.PALM.setType()));
	public static final RegistryObject<Block> PALM_TRAPDOOR = registerBlockAndBlockItem("palm_trapdoor", () -> new TrapDoorBlock(Properties.copy(Blocks.OAK_TRAPDOOR), BDBWoodTypes.PALM.setType()));
	public static final RegistryObject<Block> PALM_PRESSURE_PLATE = registerBlockAndBlockItem("palm_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.OAK_PRESSURE_PLATE), BDBWoodTypes.PALM.setType()));
	public static final RegistryObject<Block> PALM_BUTTON = registerBlockAndBlockItem("palm_button", () -> new ButtonBlock(Properties.copy(Blocks.OAK_BUTTON), BDBWoodTypes.PALM.setType(), 30, true));
	public static final RegistryObject<Block> PALM_LADDER = registerBlockAndBlockItem("palm_ladder", () -> new LadderBlock(Properties.copy(Blocks.LADDER)));

	public static final RegistryObject<Block> SWAMP_WILLOW_SAPLING = registerBlockAndBlockItem("swamp_willow_sapling", () -> new SaplingBlockBDB(new SwampWillowTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
	public static final RegistryObject<Block> SWAMP_WILLOW_SAPLING_POTTED = registerBlock("swamp_willow_sapling_potted", () -> flowerPot(SWAMP_WILLOW_SAPLING));
	public static final RegistryObject<Block> SWAMP_WILLOW_LEAVES = registerBlockAndBlockItem("swamp_willow_leaves", () -> new LeavesBlockBDB(Properties.copy(Blocks.OAK_LEAVES)));
	public static final RegistryObject<Block> SWAMP_WILLOW_LOG = registerBlockAndBlockItem("swamp_willow_log", () -> log(MapColor.WOOD, MapColor.WOOD));
	public static final RegistryObject<Block> SWAMP_WILLOW_WOOD = registerBlockAndBlockItem("swamp_willow_wood", () -> wood(MapColor.WOOD));
	public static final RegistryObject<Block> SWAMP_WILLOW_LOG_STRIPPED = registerBlockAndBlockItem("swamp_willow_log_stripped", () -> log(MapColor.WOOD, MapColor.WOOD));
	public static final RegistryObject<Block> SWAMP_WILLOW_WOOD_STRIPPED = registerBlockAndBlockItem("swamp_willow_wood_stripped", () -> wood(MapColor.TERRACOTTA_WHITE));
	public static final RegistryObject<Block> SWAMP_WILLOW_PLANKS = registerBlockAndBlockItem("swamp_willow_planks", () -> new Block(Properties.copy(Blocks.OAK_PLANKS)));
	public static final RegistryObject<Block> SWAMP_WILLOW_STAIRS = registerBlockAndBlockItem("swamp_willow_stairs", () -> new StairBlock(() -> SWAMP_WILLOW_PLANKS.get().defaultBlockState(), Properties.copy(SWAMP_WILLOW_PLANKS.get())));
	public static final RegistryObject<Block> SWAMP_WILLOW_SLAB = registerBlockAndBlockItem("swamp_willow_slab", () -> new SlabBlock(Properties.copy(SWAMP_WILLOW_PLANKS.get())));
	public static final RegistryObject<Block> SWAMP_WILLOW_FENCE = registerBlockAndBlockItem("swamp_willow_fence", () -> new FenceBlock(Properties.copy(SWAMP_WILLOW_PLANKS.get())));
	public static final RegistryObject<Block> SWAMP_WILLOW_FENCE_GATE = registerBlockAndBlockItem("swamp_willow_fence_gate", () -> new FenceGateBlock(Properties.copy(SWAMP_WILLOW_PLANKS.get()), BDBWoodTypes.SWAMP_WILLOW));
	public static final RegistryObject<Block> SWAMP_WILLOW_DOOR = registerBlockAndBlockItem("swamp_willow_door", () -> new DoorBlock(Properties.copy(Blocks.OAK_DOOR), BDBWoodTypes.SWAMP_WILLOW.setType()));
	public static final RegistryObject<Block> SWAMP_WILLOW_TRAPDOOR = registerBlockAndBlockItem("swamp_willow_trapdoor", () -> new TrapDoorBlock(Properties.copy(Blocks.OAK_TRAPDOOR), BDBWoodTypes.SWAMP_WILLOW.setType()));
	public static final RegistryObject<Block> SWAMP_WILLOW_PRESSURE_PLATE = registerBlockAndBlockItem("swamp_willow_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.OAK_PRESSURE_PLATE), BDBWoodTypes.SWAMP_WILLOW.setType()));
	public static final RegistryObject<Block> SWAMP_WILLOW_BUTTON = registerBlockAndBlockItem("swamp_willow_button", () -> new ButtonBlock(Properties.copy(Blocks.OAK_BUTTON), BDBWoodTypes.SWAMP_WILLOW.setType(), 30, true));
	public static final RegistryObject<Block> SWAMP_WILLOW_LADDER = registerBlockAndBlockItem("swamp_willow_ladder", () -> new LadderBlock(Properties.copy(Blocks.LADDER)));

	/*********************************************************** Mushrooms ********************************************************/

	public static final RegistryObject<Block> WHITE_MUSHROOM = registerBlockAndBlockItem("white_mushroom", () -> new MushroomBlockBDB(Properties.copy(Blocks.BROWN_MUSHROOM).mapColor(MapColor.SAND), BDBConfiguredFeatures.BIG_WHITE_MUSHROOM));
	public static final RegistryObject<Block> YELLOW_MUSHROOM = registerBlockAndBlockItem("yellow_mushroom", () -> new MushroomBlockBDB(Properties.copy(Blocks.BROWN_MUSHROOM).mapColor(MapColor.GOLD), BDBConfiguredFeatures.BIG_YELLOW_MUSHROOM));
	public static final RegistryObject<Block> GRAY_MUSHROOM = registerBlockAndBlockItem("gray_mushroom", () -> new MushroomBlockBDB(Properties.copy(Blocks.BROWN_MUSHROOM).mapColor(MapColor.CLAY), BDBConfiguredFeatures.BIG_GRAY_MUSHROOM));

	//Potted MUSHROOMS
	public static final RegistryObject<Block> POTTED_WHITE_MUSHROOM = registerBlock("potted_white_mushroom", () -> flowerPot(WHITE_MUSHROOM));
	public static final RegistryObject<Block> POTTED_YELLOW_MUSHROOM = registerBlock("potted_yellow_mushroom", () -> flowerPot(YELLOW_MUSHROOM));
	public static final RegistryObject<Block> POTTED_GRAY_MUSHROOM = registerBlock("potted_gray_mushroom", () -> flowerPot(GRAY_MUSHROOM));

	//Mushroom Blocks
	public static final RegistryObject<Block> WHITE_MUSHROOM_BLOCK = registerBlockAndBlockItem("white_mushroom_block", () -> new HugeMushroomBlock(Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).mapColor(MapColor.SAND)));
	public static final RegistryObject<Block> YELLOW_MUSHROOM_BLOCK = registerBlockAndBlockItem("yellow_mushroom_block", () -> new HugeMushroomBlock(Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_YELLOW)));
	public static final RegistryObject<Block> GRAY_MUSHROOM_BLOCK = registerBlockAndBlockItem("gray_mushroom_block", () -> new HugeMushroomBlock(Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).mapColor(MapColor.CLAY)));

	/*********************************************************** Flowers ********************************************************/

	public static final RegistryObject<Block> PURPLE_VERBENA = registerBlockAndBlockItem("purple_verbena", () -> flower(MobEffects.SATURATION, 7, MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block> BLUE_POPPY = registerBlockAndBlockItem("blue_poppy", () -> flower(MobEffects.DAMAGE_RESISTANCE, 7, MapColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block> DARK_VIOLET = registerBlockAndBlockItem("dark_violet", () -> flower(MobEffects.WATER_BREATHING, 7, MapColor.TERRACOTTA_PURPLE));
	public static final RegistryObject<Block> PINK_CACTUS_FLOWER = registerBlockAndBlockItem("pink_cactus_flower", () -> new SmallCactusBlockBDB(Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

	//Potted Flowers
	public static final RegistryObject<Block> POTTED_PURPLE_VERBENA = registerBlock("potted_purple_verbena", () -> flowerPot(PURPLE_VERBENA));
	public static final RegistryObject<Block> POTTED_BLUE_POPPY = registerBlock("potted_blue_poppy", () -> flowerPot(BLUE_POPPY));
	public static final RegistryObject<Block> POTTED_DARK_VIOLET = registerBlock("potted_dark_violet", () -> flowerPot(DARK_VIOLET));
	public static final RegistryObject<Block> POTTED_PINK_CACTUS_FLOWER = registerBlock("potted_pink_cactus_flower", () -> flowerPot(PINK_CACTUS_FLOWER));

	/*********************************************************** Vegetation ********************************************************/

	public static final RegistryObject<Block> FEATHER_REED_GRASS = registerBlockAndBlockItem("feather_reed_grass", () -> grass(MapColor.SAND));
	public static final RegistryObject<Block> DEAD_GRASS = registerBlockAndBlockItem("dead_grass", () -> grass(MapColor.WOOD));
	public static final RegistryObject<Block> SHORT_GRASS = registerBlockAndBlockItem("short_grass", () -> grass(MapColor.GRASS));
	public static final RegistryObject<Block> DUNE_GRASS = registerBlockAndBlockItem("dune_grass", () -> grass(MapColor.GRASS));
	public static final RegistryObject<Block> TALL_WATER_REEDS = registerBlockAndBlockItem("tall_water_reeds", () -> new DoubleWaterPlantBlockBDB(Properties.of().mapColor(MapColor.WATER).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

	// Potted Vegetation
	public static final RegistryObject<Block> POTTED_FEATHER_REED_GRASS = registerBlock("potted_feather_reed_grass", () -> flowerPot(FEATHER_REED_GRASS));
	public static final RegistryObject<Block> POTTED_DEAD_GRASS = registerBlock("potted_dead_grass", () -> flowerPot(DEAD_GRASS));
	public static final RegistryObject<Block> POTTED_SHORT_GRASS = registerBlock("potted_short_grass", () -> flowerPot(SHORT_GRASS));
	public static final RegistryObject<Block> POTTED_DUNE_GRASS = registerBlock("potted_dune_grass", () -> flowerPot(DUNE_GRASS));

	/*********************************************************** Misc ********************************************************/

	public static final RegistryObject<Block> SAND_CASTLE = registerBlockAndBlockItem("sand_castle", () -> new SimpleBlock(Properties.of().mapColor(MapColor.SAND).sound(SoundType.SAND).offsetType(OffsetType.XZ).strength(0.5F).noOcclusion().requiresCorrectToolForDrops().dynamicShape().pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> PINECONE = registerBlockAndBlockItem("pinecone", () -> new PineconeBlock(new PineconeTreeGrower(), Properties.of().mapColor(MapColor.WOOD).sound(BDBSoundTypes.SMALL_BLOCK).offsetType(OffsetType.XZ).noCollission().instabreak().noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> SEASHELL = registerBlockAndBlockItem("seashell", () -> new SimpleBlock(Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).sound(BDBSoundTypes.SMALL_BLOCK).offsetType(OffsetType.XZ).noCollission().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

	/*********************************************************** Small Rocks ********************************************************/

	public static final RegistryObject<Block> SMALL_ROCK_STONE = registerSmallRock("small_rock_stone");
	public static final RegistryObject<Block> SMALL_ROCK_COBBLE = registerSmallRock("small_rock_cobble");
	public static final RegistryObject<Block> SMALL_ROCK_MOSSY = registerSmallRock("small_rock_mossy");
	public static final RegistryObject<Block> SMALL_ROCK_ANDESITE = registerSmallRock("small_rock_andesite");
	public static final RegistryObject<Block> SMALL_ROCK_DIORITE = registerSmallRock("small_rock_diorite");
	public static final RegistryObject<Block> SMALL_ROCK_GRANITE = registerSmallRock("small_rock_granite");
	public static final RegistryObject<Block> SMALL_ROCK_SANDSTONE = registerSmallRock("small_rock_sandstone");
	public static final RegistryObject<Block> SMALL_ROCK_RED_SANDSTONE = registerSmallRock("small_rock_red_sandstone");
	public static final RegistryObject<Block> SMALL_ROCK_DEEPSLATE = registerSmallRock("small_rock_deepslate");
	public static final RegistryObject<Block> SMALL_ROCK_COBBLED_DEEPSLATE = registerSmallRock("small_rock_cobbled_deepslate");

	/*********************************************************** Starfish ********************************************************/

	// TODO Check if map colors are correct. Maybe change material to DECORATION -> Check differences working
	public static final RegistryObject<Block> STARFISH_WHITE = registerBlockAndBlockItem("starfish_white", () -> starfishBlock(DyeColor.WHITE, MapColor.SNOW));
	public static final RegistryObject<Block> STARFISH_ORANGE = registerBlockAndBlockItem("starfish_orange", () -> starfishBlock(DyeColor.ORANGE, MapColor.COLOR_ORANGE));
	public static final RegistryObject<Block> STARFISH_MAGENTA = registerBlockAndBlockItem("starfish_magenta", () -> starfishBlock(DyeColor.MAGENTA, MapColor.COLOR_MAGENTA));
	public static final RegistryObject<Block> STARFISH_LIGHT_BLUE = registerBlockAndBlockItem("starfish_light_blue", () -> starfishBlock(DyeColor.LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block> STARFISH_YELLOW = registerBlockAndBlockItem("starfish_yellow", () -> starfishBlock(DyeColor.YELLOW, MapColor.COLOR_YELLOW));
	public static final RegistryObject<Block> STARFISH_LIME = registerBlockAndBlockItem("starfish_lime", () -> starfishBlock(DyeColor.LIME, MapColor.COLOR_LIGHT_GREEN));
	public static final RegistryObject<Block> STARFISH_PINK = registerBlockAndBlockItem("starfish_pink", () -> starfishBlock(DyeColor.PINK, MapColor.COLOR_PINK));
	public static final RegistryObject<Block> STARFISH_GRAY = registerBlockAndBlockItem("starfish_gray", () -> starfishBlock(DyeColor.GRAY, MapColor.COLOR_GRAY));
	public static final RegistryObject<Block> STARFISH_LIGHT_GRAY = registerBlockAndBlockItem("starfish_light_gray", () -> starfishBlock(DyeColor.LIGHT_GRAY, MapColor.COLOR_LIGHT_GRAY));
	public static final RegistryObject<Block> STARFISH_CYAN = registerBlockAndBlockItem("starfish_cyan", () -> starfishBlock(DyeColor.CYAN, MapColor.COLOR_CYAN));
	public static final RegistryObject<Block> STARFISH_PURPLE = registerBlockAndBlockItem("starfish_purple", () -> starfishBlock(DyeColor.PURPLE, MapColor.COLOR_PURPLE));
	public static final RegistryObject<Block> STARFISH_BLUE = registerBlockAndBlockItem("starfish_blue", () -> starfishBlock(DyeColor.BLUE, MapColor.COLOR_BLUE));
	public static final RegistryObject<Block> STARFISH_BROWN = registerBlockAndBlockItem("starfish_brown", () -> starfishBlock(DyeColor.BROWN, MapColor.COLOR_BROWN));
	public static final RegistryObject<Block> STARFISH_GREEN = registerBlockAndBlockItem("starfish_green", () -> starfishBlock(DyeColor.GREEN, MapColor.COLOR_GREEN));
	public static final RegistryObject<Block> STARFISH_RED = registerBlockAndBlockItem("starfish_red", () -> starfishBlock(DyeColor.RED, MapColor.COLOR_RED));
	public static final RegistryObject<Block> STARFISH_BLACK = registerBlockAndBlockItem("starfish_black", () -> starfishBlock(DyeColor.BLACK, MapColor.COLOR_BLACK));

	public static final RegistryObject<Block> STARFISH_WALL_WHITE = registerBlock("starfish_wall_white", () -> registerStarfishWallBlock(DyeColor.WHITE));
	public static final RegistryObject<Block> STARFISH_WALL_ORANGE = registerBlock("starfish_wall_orange", () -> registerStarfishWallBlock(DyeColor.ORANGE));
	public static final RegistryObject<Block> STARFISH_WALL_MAGENTA = registerBlock("starfish_wall_magenta", () -> registerStarfishWallBlock(DyeColor.MAGENTA));
	public static final RegistryObject<Block> STARFISH_WALL_LIGHT_BLUE = registerBlock("starfish_wall_light_blue", () -> registerStarfishWallBlock(DyeColor.LIGHT_BLUE));
	public static final RegistryObject<Block> STARFISH_WALL_YELLOW = registerBlock("starfish_wall_yellow", () -> registerStarfishWallBlock(DyeColor.YELLOW));
	public static final RegistryObject<Block> STARFISH_WALL_LIME = registerBlock("starfish_wall_lime", () -> registerStarfishWallBlock(DyeColor.LIME));
	public static final RegistryObject<Block> STARFISH_WALL_PINK = registerBlock("starfish_wall_pink", () -> registerStarfishWallBlock(DyeColor.PINK));
	public static final RegistryObject<Block> STARFISH_WALL_GRAY = registerBlock("starfish_wall_gray", () -> registerStarfishWallBlock(DyeColor.GRAY));
	public static final RegistryObject<Block> STARFISH_WALL_LIGHT_GRAY = registerBlock("starfish_wall_light_gray", () -> registerStarfishWallBlock(DyeColor.LIGHT_GRAY));
	public static final RegistryObject<Block> STARFISH_WALL_CYAN = registerBlock("starfish_wall_cyan", () -> registerStarfishWallBlock(DyeColor.CYAN));
	public static final RegistryObject<Block> STARFISH_WALL_PURPLE = registerBlock("starfish_wall_purple", () -> registerStarfishWallBlock(DyeColor.PURPLE));
	public static final RegistryObject<Block> STARFISH_WALL_BLUE = registerBlock("starfish_wall_blue", () -> registerStarfishWallBlock(DyeColor.BLUE));
	public static final RegistryObject<Block> STARFISH_WALL_BROWN = registerBlock("starfish_wall_brown", () -> registerStarfishWallBlock(DyeColor.BROWN));
	public static final RegistryObject<Block> STARFISH_WALL_GREEN = registerBlock("starfish_wall_green", () -> registerStarfishWallBlock(DyeColor.GREEN));
	public static final RegistryObject<Block> STARFISH_WALL_RED = registerBlock("starfish_wall_red", () -> registerStarfishWallBlock(DyeColor.RED));
	public static final RegistryObject<Block> STARFISH_WALL_BLACK = registerBlock("starfish_wall_black", () -> registerStarfishWallBlock(DyeColor.BLACK));

	/*********************************************************** Repeated Blocks ********************************************************/

	private static RotatedPillarBlock log(MapColor innerColor, MapColor outerColor) {
		return new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor((state) -> {
			return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? innerColor : outerColor;
		}));
	}

	private static RotatedPillarBlock wood(MapColor mapColor) {
		return log(mapColor, mapColor);
	}

	private static FlowerBlockBDB flower(MobEffect suspiciousStewEffect, int effectDuration, MapColor mapColor) {
		return new FlowerBlockBDB(() -> suspiciousStewEffect, effectDuration, Properties.copy(Blocks.DANDELION).mapColor(mapColor));
	}

	private static GrassBlockBDB grass(MapColor mapColor) {
		return new GrassBlockBDB(Properties.copy(Blocks.GRASS).mapColor(mapColor));
	}

	private static FlowerPotBlock flowerPot(Supplier<Block> blockSupplier) {
		FlowerPotBlock pottedPlant = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, blockSupplier, Properties.copy(Blocks.POTTED_DANDELION));
		POTTED_PLANTS.put(blockSupplier, () -> pottedPlant);
		return pottedPlant;
	}

	private static Block starfishBlock(DyeColor dyeColor, MapColor mapColor) {
		StarfishBlock starfishBlock = new StarfishBlock(dyeColor, Properties.of().mapColor(mapColor).noCollission().instabreak().sound(SoundType.STONE).offsetType(OffsetType.XZ).lightLevel(getLightValueLit(8)).pushReaction(PushReaction.DESTROY));
		STARFISH.add(() -> starfishBlock);
		return starfishBlock;
	}

	/**
	 * Only here to set the OffsetType back to NONE, because otherwise the Starfish looks weird on walls.
	 */
	private static Block registerStarfishWallBlock(DyeColor dyeColor) {
		Supplier<Block> parentStarfish = () -> StarfishBlock.getBlockByColor(dyeColor);
		return new StarfishWallBlock(dyeColor, Properties.copy(parentStarfish.get()).offsetType(OffsetType.NONE).lootFrom(() -> parentStarfish.get()));
	}

	private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
		return (state) -> {
			return state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
		};
	}

	private static RegistryObject<Block> registerSmallRock(@Nonnull String registryName) {
		RegistryObject<Block> registryBlock = registerBlock(registryName, () -> new SmallRockBlock(Properties.of().sound(SoundType.STONE).offsetType(OffsetType.XZ).noCollission().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));
		// Add to list for Block Tag creation
		SMALL_ROCKS.add(registryBlock);
		// Blocks are registered before Items
		BDBItems.registerItem(registryName, () -> new SmallRockItem(registryBlock.get(), new Item.Properties()));
		return registryBlock;
	}

	/*********************************************************** Helper Methods ********************************************************/

	/**
	 * Helper method for registering Blocks with basic BlockItems.
	 * 
	 * @param  registryName  The name to register the Block with.
	 * @param  blockSupplier The Block to register.
	 * @param  itemGroup     The ItemGroup where the Item will show.
	 * @return               The Block that was registered
	 */
	private static <B extends Block> RegistryObject<Block> registerBlockAndBlockItem(@Nonnull String registryName, Supplier<B> blockSupplier) {
		RegistryObject<Block> registryBlock = registerBlock(registryName, blockSupplier);
		// Blocks are registered before Items
		BDBItems.registerItem(registryName, () -> new BlockItem(registryBlock.get(), new Item.Properties()));
		return registryBlock;
	}

	/**
	 * Helper method for registering all Blocks. Can also be used to register Blocks with no Items.
	 * 
	 * @param  registryName  The name to register the Block with.
	 * @param  blockSupplier The Block to register.
	 * @return               The Block that was registered
	 */
	private static <B extends Block> RegistryObject<Block> registerBlock(@Nonnull String registryName, Supplier<B> blockSupplier) {
		return DEFERRED_BLOCKS.register(registryName, blockSupplier);
	}
}
