package xratedjunior.betterdefaultbiomes.world.generation.feature.feature.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

/**
 * Reference 1.16 TreeFeature
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.1.0
 */
public abstract class TreeFeatureBDB extends Feature<NoneFeatureConfiguration> {

	public TreeFeatureBDB(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	protected abstract boolean createTree(WorldGenLevel world, Random random, BlockPos pos, BiConsumer<BlockPos, BlockState> logs, BiConsumer<BlockPos, BlockState> leaves);

	public boolean checkSpace(WorldGenLevel world, BlockPos pos, int height, int radius) {
		for (int y = 0; y <= height; y++) {
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					BlockPos pos1 = pos.offset(x, y, z);
					// note, there may be a sapling on the first layer - make sure this.replace matches it!
					if (pos1.getY() >= world.getMaxBuildHeight() || !this.canBeReplacedByTree(world, pos1)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/*********************************************************** BlockState Checks ********************************************************/

	private boolean isBlockWater(BlockState state) {
		return state.is(Blocks.WATER);
	}

	public boolean isAirOrLeaves(BlockState state) {
		return state.isAir() || state.is(BlockTags.LEAVES);
	}

	private boolean isReplaceablePlant(BlockState state) {
		return state.getMaterial().equals(Material.REPLACEABLE_PLANT);
	}

	public boolean canBeReplacedByTree(LevelSimulatedReader world, BlockPos pos) {
		return world.isStateAtPosition(pos, (state) -> {
			return this.canBeReplacedByLogs(state);
		});
	}

	/**
	 * Used during tree growth to determine if newly generated leaves can replace this block.
	 *
	 * @param  state The current state
	 * @return       true if this block can be replaced by growing leaves.
	 */
	protected boolean canBeReplacedByLeaves(BlockState state) {
		return this.isAirOrLeaves(state) || this.isBlockWater(state) || this.isReplaceablePlant(state);
	}

	/**
	 * Used during tree growth to determine if newly generated logs can replace this block.
	 *
	 * @param  state The current state
	 * @return       true if this block can be replaced by growing leaves.
	 */
	protected boolean canBeReplacedByLogs(BlockState state) {
		return this.canBeReplacedByLeaves(state) || state.is(BlockTags.LOGS);
	}

	/*********************************************************** Block Placers ********************************************************/

	/**
	 * Exact copy {@link TreeFeature#setBlockKnownShape}
	 */
	private static void setBlockKnownShape(LevelWriter world, BlockPos p_67258_, BlockState p_67259_) {
		world.setBlock(p_67258_, p_67259_, 19);
	}

	protected void placeBlock(WorldGenLevel world, BlockPos pos, BlockState blockState) {
		setBlockKnownShape(world, pos, blockState);
	}

	protected void tryPlaceLog(WorldGenLevel world, BlockPos logPos, BlockState logState) {
		if (this.canBeReplacedByLogs(world.getBlockState(logPos))) {
			placeBlock(world, logPos, logState);
		}
	}

	protected void tryPlaceLeaves(WorldGenLevel world, BlockPos leavesPos, BlockState leavesState) {
		if (this.canBeReplacedByLeaves(world.getBlockState(leavesPos))) {
			placeBlock(world, leavesPos, leavesState);
		}
	}

	/*********************************************************** Almost exact copies {@link TreeFeature} ********************************************************/

	/**
	 * TODO Create a {@link FoliagePlacer} and {@link TrunkPlacer}. This will enable me to keep using {@link TreeFeature} instead of copying all the code
	 */
	@Override
	public final boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featureContext) {
		WorldGenLevel worldgenlevel = featureContext.level();
		Random random = featureContext.random();
		BlockPos blockpos = featureContext.origin();
		Set<BlockPos> posLogs = Sets.newHashSet();
		Set<BlockPos> posLeaves = Sets.newHashSet();
		Set<BlockPos> posDecorators = Sets.newHashSet();
		BiConsumer<BlockPos, BlockState> logConsumer = (logPos, logState) -> {
			posLogs.add(logPos.immutable());
			this.tryPlaceLog(worldgenlevel, logPos, logState);
		};
		BiConsumer<BlockPos, BlockState> leavesConsumer = (leavesPos, leavesState) -> {
			posLeaves.add(leavesPos.immutable());
			this.tryPlaceLeaves(worldgenlevel, leavesPos, leavesState);
		};

		boolean createTree = this.createTree(worldgenlevel, random, blockpos, logConsumer, leavesConsumer);

		if (createTree && (!posLogs.isEmpty() || !posLeaves.isEmpty())) {

			return BoundingBox.encapsulatingPositions(Iterables.concat(posLogs, posLeaves, posDecorators)).map((boundingBox) -> {
				DiscreteVoxelShape discretevoxelshape = updateLeaves(worldgenlevel, boundingBox, posLogs, posDecorators);
				StructureTemplate.updateShapeAtEdge(worldgenlevel, 3, discretevoxelshape, boundingBox.minX(), boundingBox.minY(), boundingBox.minZ());
				return true;
			}).orElse(false);
		} else {
			return false;
		}
	}

	private static DiscreteVoxelShape updateLeaves(LevelAccessor p_67203_, BoundingBox p_67204_, Set<BlockPos> p_67205_, Set<BlockPos> p_67206_) {
		List<Set<BlockPos>> list = new ArrayList<Set<BlockPos>>();
		DiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(p_67204_.getXSpan(), p_67204_.getYSpan(), p_67204_.getZSpan());
		@SuppressWarnings("unused")
		int i = 6;

		for (int j = 0; j < 6; ++j) {
			list.add(Sets.newHashSet());
		}

		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (BlockPos blockpos : Lists.newArrayList(p_67206_)) {
			if (p_67204_.isInside(blockpos)) {
				discretevoxelshape.fill(blockpos.getX() - p_67204_.minX(), blockpos.getY() - p_67204_.minY(), blockpos.getZ() - p_67204_.minZ());
			}
		}

		for (BlockPos blockpos1 : Lists.newArrayList(p_67205_)) {
			if (p_67204_.isInside(blockpos1)) {
				discretevoxelshape.fill(blockpos1.getX() - p_67204_.minX(), blockpos1.getY() - p_67204_.minY(), blockpos1.getZ() - p_67204_.minZ());
			}

			for (Direction direction : Direction.values()) {
				blockpos$mutableblockpos.setWithOffset(blockpos1, direction);
				if (!p_67205_.contains(blockpos$mutableblockpos)) {
					BlockState blockstate = p_67203_.getBlockState(blockpos$mutableblockpos);
					if (blockstate.hasProperty(BlockStateProperties.DISTANCE)) {
						list.get(0).add(blockpos$mutableblockpos.immutable());
						setBlockKnownShape(p_67203_, blockpos$mutableblockpos, blockstate.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(1)));
						if (p_67204_.isInside(blockpos$mutableblockpos)) {
							discretevoxelshape.fill(blockpos$mutableblockpos.getX() - p_67204_.minX(), blockpos$mutableblockpos.getY() - p_67204_.minY(), blockpos$mutableblockpos.getZ() - p_67204_.minZ());
						}
					}
				}
			}
		}

		for (int l = 1; l < 6; ++l) {
			Set<BlockPos> set = list.get(l - 1);
			Set<BlockPos> set1 = list.get(l);

			for (BlockPos blockpos2 : set) {
				if (p_67204_.isInside(blockpos2)) {
					discretevoxelshape.fill(blockpos2.getX() - p_67204_.minX(), blockpos2.getY() - p_67204_.minY(), blockpos2.getZ() - p_67204_.minZ());
				}

				for (Direction direction1 : Direction.values()) {
					blockpos$mutableblockpos.setWithOffset(blockpos2, direction1);
					if (!set.contains(blockpos$mutableblockpos) && !set1.contains(blockpos$mutableblockpos)) {
						BlockState blockstate1 = p_67203_.getBlockState(blockpos$mutableblockpos);
						if (blockstate1.hasProperty(BlockStateProperties.DISTANCE)) {
							int k = blockstate1.getValue(BlockStateProperties.DISTANCE);
							if (k > l + 1) {
								BlockState blockstate2 = blockstate1.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(l + 1));
								setBlockKnownShape(p_67203_, blockpos$mutableblockpos, blockstate2);
								if (p_67204_.isInside(blockpos$mutableblockpos)) {
									discretevoxelshape.fill(blockpos$mutableblockpos.getX() - p_67204_.minX(), blockpos$mutableblockpos.getY() - p_67204_.minY(), blockpos$mutableblockpos.getZ() - p_67204_.minZ());
								}

								set1.add(blockpos$mutableblockpos.immutable());
							}
						}
					}
				}
			}
		}

		return discretevoxelshape;
	}
}