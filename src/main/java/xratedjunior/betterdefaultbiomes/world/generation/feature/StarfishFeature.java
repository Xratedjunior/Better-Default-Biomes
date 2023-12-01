package xratedjunior.betterdefaultbiomes.world.generation.feature;

import java.util.List;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import xratedjunior.betterdefaultbiomes.block.block.StarfishBlock;
import xratedjunior.betterdefaultbiomes.block.block.StarfishBlock.WallFacing;
import xratedjunior.betterdefaultbiomes.data.BDBTags;
import xratedjunior.betterdefaultbiomes.world.generation.feature.configurations.StarfishConfiguration;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class StarfishFeature extends Feature<StarfishConfiguration> {
	private boolean isCoralFeature;
	// Used for testing. Triggers the generation of Glowstone and SeaLanterns with Starfish Blocks.
	private boolean debugMode = false;

	public StarfishFeature(Codec<StarfishConfiguration> codec, boolean isCoralFeatureIn) {
		super(codec);
		this.isCoralFeature = isCoralFeatureIn;
	}
	
	/*********************************************************** Placement ********************************************************/

	@Override
	public boolean place(FeaturePlaceContext<StarfishConfiguration> context) {
		// Get position
		BlockPos pos = context.origin();
		WorldGenLevel worldgenlevel = context.level();

		// Get random
		RandomSource rand = context.random();
		
		// Get generation colors
		List<DyeColor> generationColors = context.config().getGenerationColors();

		// Test for CoralBlocks in a small radius of 3x3 and 6 Blocks up
		if (this.isCoralFeature) {
			// Used to determine if placement was successful
			int starfishCount = 0;

			// loop over radius of Blocks
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					for (int y = 0; y <= 6; y++) {

						BlockPos coralBlockPos = pos.offset(x, y, z);
						BlockState coralBlock = worldgenlevel.getBlockState(coralBlockPos);
						if (coralBlock.getBlock() instanceof CoralBlock) {

							// Place the starfish on the bottom of the Block.
							if (worldgenlevel.getBlockState(coralBlockPos.below()).is(Blocks.WATER) && rand.nextInt(2) == 0) { // 50% chance
								BlockState coralStarfishBlockState = StarfishBlock.getBlockByColor(this.getRandomColor(rand, generationColors)).defaultBlockState().setValue(StarfishBlock.FACING, this.getRandomHorizontalDirection(rand)).setValue(StarfishBlock.FACE, AttachFace.CEILING).setValue(StarfishBlock.WATERLOGGED, true).setValue(StarfishBlock.LIT, rand.nextInt(4) == 0); // 25% chance to be LIT
								worldgenlevel.setBlock(coralBlockPos.below(), coralStarfishBlockState, 2);
								starfishCount++;
							}

							// Place the starfish on the top of the Block.
							if (worldgenlevel.getBlockState(coralBlockPos.above()).is(Blocks.WATER) && rand.nextInt(4) == 0) { // 25% chance
								BlockState coralStarfishBlockState = StarfishBlock.getBlockByColor(this.getRandomColor(rand, generationColors)).defaultBlockState().setValue(StarfishBlock.FACING, this.getRandomHorizontalDirection(rand)).setValue(StarfishBlock.FACE, AttachFace.FLOOR).setValue(StarfishBlock.WATERLOGGED, true).setValue(StarfishBlock.LIT, rand.nextInt(4) == 0); // 25% chance to be LIT
								worldgenlevel.setBlock(coralBlockPos.above(), coralStarfishBlockState, 2);
								starfishCount++;
							}

							// Place Starfish on the side of Blocks
							for (Direction direction : Direction.Plane.HORIZONTAL) {
								if (worldgenlevel.getBlockState(coralBlockPos.relative(direction)).is(Blocks.WATER)) {
									if (rand.nextInt(4) == 0) { // 25% chance
										BlockState coralStarfishBlockState = StarfishBlock.getWallBlockByColor(this.getRandomColor(rand, generationColors)).defaultBlockState().setValue(StarfishBlock.WALL_FACING, WallFacing.getRandomWallFacingDirection(rand)).setValue(StarfishBlock.FACING, direction).setValue(StarfishBlock.FACE, AttachFace.WALL).setValue(StarfishBlock.WATERLOGGED, true).setValue(StarfishBlock.LIT, rand.nextInt(4) == 0); // 25% chance to be LIT
										worldgenlevel.setBlock(coralBlockPos.relative(direction), coralStarfishBlockState, 2);
										starfishCount++;
									}
								}
							}

							if (this.debugMode) {
								// Set all the found CoralBlocks to a Sea Lantern for testing purposes.
								worldgenlevel.setBlock(coralBlockPos, Blocks.SEA_LANTERN.defaultBlockState(), 2);
							}
						}
					}
				}
			}
			// More than 1 starfish placed is successful
			return starfishCount > 0;
		}

		// Not a coral feature
		else {
			DyeColor randomColor = this.getRandomColor(rand, generationColors);
			Direction randomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(rand);

			BlockState starfishBlockState = StarfishBlock.getBlockByColor(randomColor).defaultBlockState().setValue(StarfishBlock.FACING, randomDirection);

			if (starfishBlockState.canSurvive(worldgenlevel, pos)) {
				// Place non-coral starfish in water
				if (worldgenlevel.getBlockState(pos).is(Blocks.WATER)) {
					this.placeStarfish(worldgenlevel, rand, pos, starfishBlockState, randomColor, true);
					return true;
				}

				// Place non-coral starfish on the beach
				else if (worldgenlevel.isEmptyBlock(pos) && worldgenlevel.getBlockState(pos.below()).is(BDBTags.Blocks.BEACH_SAND)) {
					this.placeStarfish(worldgenlevel, rand, pos, starfishBlockState, randomColor, false);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Place non-coral Starfish
	 */
	private void placeStarfish(WorldGenLevel world, RandomSource rand, BlockPos blockpos, BlockState starfishBlockState, DyeColor starfishColor, boolean waterlogged) {
		if (waterlogged && rand.nextInt(4) <= 2) { // 75% of the time
			// Try to place the starfish on the side of a Block.
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				if (world.getBlockState(blockpos.below().relative(direction)).is(Blocks.WATER)) {
					starfishBlockState = StarfishBlock.getWallBlockByColor(starfishColor).defaultBlockState().setValue(StarfishBlock.FACING, direction).setValue(StarfishBlock.FACE, AttachFace.WALL).setValue(StarfishBlock.WALL_FACING, WallFacing.getRandomWallFacingDirection(rand));
					world.setBlock(blockpos.below().relative(direction), starfishBlockState.setValue(StarfishBlock.WATERLOGGED, waterlogged), 2);

					if (this.debugMode) {
						// Set the block the Starfish is placed on to Glowstone for testing purposes.
						world.setBlock(blockpos.below(), Blocks.GLOWSTONE.defaultBlockState(), 2);
					}
					return;
				}
			}
		}

		// Place the starfish on top of the Block.
		world.setBlock(blockpos, starfishBlockState.setValue(StarfishBlock.WATERLOGGED, waterlogged), 2);

		if (this.debugMode) {
			// Set the block the Starfish is placed on to Glowstone for testing purposes.
			world.setBlock(blockpos.below(), Blocks.GLOWSTONE.defaultBlockState(), 2);
		}
	}
	
	/*********************************************************** Getters ********************************************************/

	private Direction getRandomHorizontalDirection(RandomSource rand) {
		return Direction.Plane.HORIZONTAL.getRandomDirection(rand);
	}

	private DyeColor getRandomColor(RandomSource rand, List<DyeColor> colorList) {
		return colorList.get(rand.nextInt(colorList.size()));
	}
}
