package xratedjunior.betterdefaultbiomes.world.generation.feature.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BigYellowMushroomFeature extends AbstractHugeMushroomFeature {

	public BigYellowMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
		super(codec);
	}

	/*
	 * Get size to check if can grow??
	 */
	@Override
	protected int getTreeRadiusForHeight(int noIdea, int ground, int foliageRadius, int height) {
		int i = 0;
		if (height < ground && height >= ground - 3) {
			i = foliageRadius;
		} else if (height == ground) {
			i = foliageRadius;
		}

		return i;
	}

	/*
	 * Generate Leaves
	 */
	@Override
	protected void makeCap(LevelAccessor worldIn, Random rand, BlockPos pos, int heightIn, MutableBlockPos mutableIn, HugeMushroomFeatureConfiguration config) {
		int bottomHeight = heightIn - 3;
		for (int height = bottomHeight; height <= heightIn; ++height) {

			int radiusIn = config.foliageRadius;
			boolean reachedTop = height < heightIn;
			int radius = reachedTop ? radiusIn : radiusIn - 1;

			int insideRadius = config.foliageRadius - 2;

			for (int x = -radius; x <= radius; ++x) {
				for (int z = -radius; z <= radius; ++z) {

					boolean sideNegX = x == -radius;
					boolean sidePosX = x == radius;
					boolean sideNegZ = z == -radius;
					boolean sidePosZ = z == radius;

					boolean isSideX = sideNegX || sidePosX;
					boolean isSideZ = sideNegZ || sidePosZ;

					boolean isCorner = isSideX == isSideZ;

					if (height >= heightIn || !isCorner) {
						mutableIn.setWithOffset(pos, x, height, z);
						if (this.canBeReplacedByLeaves(worldIn, mutableIn)) {
							if (height == bottomHeight) {
								if (rand.nextInt(3) == 0) {
									this.placeBlock(worldIn, rand, pos, heightIn, mutableIn, config, height, x, z, insideRadius);
								}
							} else {
								this.placeBlock(worldIn, rand, pos, heightIn, mutableIn, config, height, x, z, insideRadius);
							}
						}
					}
				}
			}
		}
	}

	private boolean canBeReplacedByLeaves(LevelAccessor worldIn, MutableBlockPos mutableIn) {
		return worldIn.getBlockState(mutableIn).isAir() || worldIn.getBlockState(mutableIn).is(BlockTags.LEAVES);
	}

	/*
	 * Place mushroom block
	 */
	private void placeBlock(LevelAccessor worldIn, Random rand, BlockPos pos, int heightIn, MutableBlockPos mutableIn, HugeMushroomFeatureConfiguration config, int height, int x, int z, int insideRadius) {
		this.setBlock(worldIn, mutableIn, config.capProvider.getState(rand, pos)
			.setValue(HugeMushroomBlock.UP, Boolean.valueOf(height >= heightIn - 1))
			.setValue(HugeMushroomBlock.WEST, Boolean.valueOf(x < -insideRadius || Math.abs(z) == 2))
			.setValue(HugeMushroomBlock.EAST, Boolean.valueOf(x > insideRadius || Math.abs(z) == 2))
			.setValue(HugeMushroomBlock.NORTH, Boolean.valueOf(z < -insideRadius || Math.abs(x) == 2))
			.setValue(HugeMushroomBlock.SOUTH, Boolean.valueOf(z > insideRadius || Math.abs(x) == 2)));
	}
}
