package xratedjunior.betterdefaultbiomes.world.generation.feature.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;
import xratedjunior.betterdefaultbiomes.block.block.PineconeBlock;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class PineconeFeature extends Feature<NoneFeatureConfiguration> {

	public PineconeFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		// Get position
		BlockPos pineconePos = context.origin();
		WorldGenLevel worldgenlevel = context.level();

		// Check if there is space to generate
		if (!worldgenlevel.isEmptyBlock(pineconePos)) {
			// No space, exit!
			return false;
		}

		// Generate random Pinecone state
		Random random = context.random();
		Direction randomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		// 50% chance to generate sideways
		boolean chanceSideways = random.nextInt(2) == 0 ? true : false;
		boolean waterlogged = false;

		// Set Pinecone state for generation
		BlockState pinecone = BDBBlocks.PINECONE.get().defaultBlockState().setValue(PineconeBlock.FACING, randomDirection).setValue(PineconeBlock.SIDEWAYS, chanceSideways);

		if (pinecone.canSurvive(worldgenlevel, pineconePos)) {
			// Check if the generating Block is in water
			if (worldgenlevel.getBlockState(pineconePos).is(Blocks.WATER)) {
				// Waterlog the Pinecone
				waterlogged = true;
			}

			worldgenlevel.setBlock(pineconePos, pinecone.setValue(PineconeBlock.WATERLOGGED, waterlogged), Block.UPDATE_CLIENTS);
			return true;

		}
		return false;
	}
}
