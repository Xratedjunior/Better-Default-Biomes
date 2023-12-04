package xratedjunior.betterdefaultbiomes.datagen.providers;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.block.block.StarfishBlock;
import xratedjunior.betterdefaultbiomes.block.block.StarfishBlock.WallFacing;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBBlockStateProvider extends BlockStateProvider{

	public BDBBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, BetterDefaultBiomes.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		BetterDefaultBiomes.LOGGER.info("Generating Block States and Models");
		generateStarfishBlocks();
	}

	/**
	 * Generate Starfish Models and BlockStates
	 */
	private void generateStarfishBlocks() {
		starfishBlock(BDBBlocks.STARFISH_WHITE.get());
		starfishBlock(BDBBlocks.STARFISH_ORANGE.get());
		starfishBlock(BDBBlocks.STARFISH_MAGENTA.get());
		starfishBlock(BDBBlocks.STARFISH_LIGHT_BLUE.get());
		starfishBlock(BDBBlocks.STARFISH_YELLOW.get());
		starfishBlock(BDBBlocks.STARFISH_LIME.get());
		starfishBlock(BDBBlocks.STARFISH_PINK.get());
		starfishBlock(BDBBlocks.STARFISH_GRAY.get());
		starfishBlock(BDBBlocks.STARFISH_LIGHT_GRAY.get());
		starfishBlock(BDBBlocks.STARFISH_CYAN.get());
		starfishBlock(BDBBlocks.STARFISH_PURPLE.get());
		starfishBlock(BDBBlocks.STARFISH_BLUE.get());
		starfishBlock(BDBBlocks.STARFISH_BROWN.get());
		starfishBlock(BDBBlocks.STARFISH_GREEN.get());
		starfishBlock(BDBBlocks.STARFISH_RED.get());
		starfishBlock(BDBBlocks.STARFISH_BLACK.get());
		
		starfishBlock(BDBBlocks.STARFISH_WALL_WHITE.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_ORANGE.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_MAGENTA.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_LIGHT_BLUE.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_YELLOW.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_LIME.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_PINK.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_GRAY.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_LIGHT_GRAY.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_CYAN.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_PURPLE.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_BLUE.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_BROWN.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_GREEN.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_RED.get());
		starfishBlock(BDBBlocks.STARFISH_WALL_BLACK.get());
	}
	
	private void starfishBlock(Block starfish) {
		String starfishName = ForgeRegistries.BLOCKS.getKey(starfish).getPath().replace("wall_", "");
		String starfishColor = starfishName.replace("starfish_", "");

		BlockModelBuilder floor = this.models()
			.withExistingParent(this.setBlockName(starfishName), modLoc("block/starfish_template"))
			.texture("texture",mcLoc("block/"+starfishColor+"_wool"));
		
		this.makeBlockItemFromExistingModel(starfish, starfishName);
		
		starfishName = starfishName.replace("starfish_", "starfish_wall_");
		
		ModelFile wall_up = this.models()
				.withExistingParent(this.setBlockName(starfishName.concat("_up")), modLoc("block/starfish_template_up"))
				.texture("texture", mcLoc("block/"+starfishColor+"_wool"));
		
		ModelFile wall_right = this.models()
				.withExistingParent(this.setBlockName(starfishName.concat("_right")), modLoc("block/starfish_template_right"))
				.texture("texture",mcLoc("block/"+starfishColor+"_wool"));
		
		ModelFile wall_left = this.models()
				.withExistingParent(this.setBlockName(starfishName.concat("_left")), modLoc("block/starfish_template_left"))
				.texture("texture",mcLoc("block/"+starfishColor+"_wool"));

		this.getVariantBuilder(starfish).forAllStatesExcept(includeState -> {
			AttachFace face = includeState.getValue(StarfishBlock.FACE);
			Direction facing = includeState.getValue(StarfishBlock.FACING);
			int y = 0;
			int x = 0;
			switch (face) {
			default:
			case WALL:
				x = 90;

				if (facing.equals(Direction.EAST)) {
					y = 90;
				} else if (facing.equals(Direction.SOUTH)) {
					y = 180;
				} else if (facing.equals(Direction.WEST)) {
					y = 270;
				}
				break;
			case FLOOR:
				if (facing.equals(Direction.WEST)) {
					y = 90;
				} else if (facing.equals(Direction.NORTH)) {
					y = 180;
				} else if (facing.equals(Direction.EAST)) {
					y = 270;
				}
				break;
			case CEILING:
				x = 180;
				
				if (facing.equals(Direction.EAST)) {
					y = 90;
				} else if (facing.equals(Direction.SOUTH)) {
					y = 180;
				} else if (facing.equals(Direction.WEST)) {
					y = 270;
				}
				break;
			}
			
			WallFacing wallFacing = includeState.getValue(StarfishBlock.WALL_FACING);

			return ConfiguredModel.builder().modelFile(wallFacing == WallFacing.UP ? wall_up : wallFacing == WallFacing.RIGHT ? wall_right : wallFacing == WallFacing.LEFT ? wall_left : floor).rotationY(y).rotationX(x).build();
		}, StarfishBlock.LIT, StarfishBlock.WATERLOGGED);
	}
	
	/*********************************************************** Helper Methods ********************************************************/
	
	private String setBlockName(String blockName) {
		return BetterDefaultBiomes.find("block/"+blockName);
	}
	
	private void makeBlockItemFromExistingModel(Block block, String name) {
		final ModelFile model = this.models().getExistingFile(modLoc(name));
		this.simpleBlockItem(block, model);
	}
}
