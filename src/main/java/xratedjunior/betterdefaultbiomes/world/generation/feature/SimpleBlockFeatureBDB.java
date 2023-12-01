package xratedjunior.betterdefaultbiomes.world.generation.feature;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.block.block.SimpleBlock;
import xratedjunior.betterdefaultbiomes.block.block.SmallRockBlock;

/**
 * Handle the surface generation of Small Rocks
 * 
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class SimpleBlockFeatureBDB extends Feature<SimpleBlockConfiguration> {

	public SimpleBlockFeatureBDB(Codec<SimpleBlockConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
		// Get position
		BlockPos blockPos = context.origin();
		WorldGenLevel worldgenlevel = context.level();

		// Get Block for generation
		SimpleBlockConfiguration config = context.config();
		BlockState blockState = config.toPlace().getState(context.random(), blockPos);

		// Check for correct Blocks
		if (!(blockState.getBlock() instanceof SimpleBlock)) {
			BetterDefaultBiomes.LOGGER.error(blockState.getBlock() + ", is not a 'SimpleBlock'.");
			return false;
		}

		// Get surface y-level
		int worldSurfaceHeight = worldgenlevel.getHeight(Types.OCEAN_FLOOR_WG, blockPos.getX(), blockPos.getZ());

		// Check if there is space to generate
		BlockPos smallBlockPos = new BlockPos(blockPos.getX(), worldSurfaceHeight, blockPos.getZ());
		if (!worldgenlevel.isEmptyBlock(smallBlockPos) && !worldgenlevel.getBlockState(smallBlockPos).is(Blocks.WATER)) {
			// No space, exit!
			return false;
		}

		// Place Block
		return this.placeBlock(worldgenlevel, context.random(), blockPos, blockState);
	}

	/**
	 * Place Block
	 */
	protected boolean placeBlock(WorldGenLevel world, RandomSource rand, BlockPos blockPos, BlockState blockState) {
		// Face the Block in a random direction
		Direction randomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(rand);

		// Check if position meets Block requirements
		if (!blockState.canSurvive(world, blockPos)) {
			return false;
		}

		// Set the Block to be waterlogged if needed
		boolean waterlogged = false;
		if (world.getBlockState(blockPos).is(Blocks.WATER)) {
			waterlogged = true;
		}

		// Place the Sea Shell
		world.setBlock(blockPos, blockState.getBlock().defaultBlockState().setValue(SimpleBlock.FACING, randomDirection).setValue(SmallRockBlock.WATERLOGGED, waterlogged), 2);
		return true;
	}
}