package xratedjunior.betterdefaultbiomes.world.generation.feature;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BigGrayMushroomFeature extends AbstractHugeMushroomFeature {

	public BigGrayMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
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
	protected void makeCap(LevelAccessor worldIn, RandomSource rand, BlockPos pos, int heightIn, MutableBlockPos mutableIn, HugeMushroomFeatureConfiguration config) {
		int bottomHeight = heightIn - 3;
		for (int height = bottomHeight; height <= heightIn; ++height) {

			int radiusIn = config.foliageRadius;
			boolean reachedTop = height < heightIn;
			int radius = reachedTop ? radiusIn : radiusIn - 1;

			for (int x = -radius; x <= radius; ++x) {
				for (int z = -radius; z <= radius; ++z) {

					boolean sideNegX = x == -radius;
					boolean sidePosX = x == radius;
					boolean sideNegZ = z == -radius;
					boolean sidePosZ = z == radius;

					boolean isSideX = sideNegX || sidePosX;
					boolean isSideZ = sideNegZ || sidePosZ;

					boolean isCorner = isSideX == isSideZ;

					mutableIn.setWithOffset(pos, x, height, z);

					//	Top
					if (height == heightIn && (!isCorner || (x == 0 && z == 0))) {
						this.placeBlock(worldIn, rand, pos, heightIn, mutableIn, config, height, x, z, radius);
					}

					//	Layer 2
					else if (height == heightIn - 1 && (((x == 0 && isSideZ) || (z == 0 && isSideX)) || (Math.abs(x) == 1) && Math.abs(z) == 1)) {
						this.placeBlock(worldIn, rand, pos, heightIn, mutableIn, config, height, x, z, radius);
					}

					//	Bottom Layers
					else if (height < heightIn - 1 && !isCorner) {
						if (height == bottomHeight) {
							if (rand.nextInt(3) == 0) {
								this.placeBlock(worldIn, rand, pos, heightIn, mutableIn, config, height, x, z, radius);
							}
						} else {
							this.placeBlock(worldIn, rand, pos, heightIn, mutableIn, config, height, x, z, radius);
						}
					}
				}
			}
		}
	}

	/*
	 * Place mushroom block
	 */
	private void placeBlock(LevelAccessor worldIn, RandomSource rand, BlockPos pos, int heightIn, MutableBlockPos mutableIn, HugeMushroomFeatureConfiguration config, int height, int x, int z, int radius) {
		if (this.canBeReplacedByLeaves(worldIn, mutableIn)) {
			this.setBlock(worldIn, mutableIn, config.capProvider.getState(rand, pos).setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(height < heightIn - 1)).setValue(HugeMushroomBlock.WEST, Boolean.valueOf(x <= 0 || Math.abs(z) == 2)).setValue(HugeMushroomBlock.EAST, Boolean.valueOf(x >= 0 || Math.abs(z) == 2)).setValue(HugeMushroomBlock.NORTH, Boolean.valueOf(z <= 0 || Math.abs(x) == 2)).setValue(HugeMushroomBlock.SOUTH, Boolean.valueOf(z >= 0 || Math.abs(x) == 2)));
		}
	}

	private boolean canBeReplacedByLeaves(LevelAccessor worldIn, MutableBlockPos mutableIn) {
		return worldIn.getBlockState(mutableIn).isAir() || worldIn.getBlockState(mutableIn).is(BlockTags.LEAVES);
	}
}
