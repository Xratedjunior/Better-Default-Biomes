package xratedjunior.betterdefaultbiomes.entity.projectile;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import xratedjunior.betterdefaultbiomes.configuration.ItemConfig;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;
import xratedjunior.betterdefaultbiomes.entity.projectile.dispenser.CustomDispenserBehavior;
import xratedjunior.betterdefaultbiomes.item.BDBItems;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class TorchArrowEntity extends AbstractArrow {
	private static final Supplier<Item> ARROW_TYPE = BDBItems.TORCH_ARROW;
	private int secondsFireDuration = 2;
	private int animateFireTick = 0;

	public TorchArrowEntity(EntityType<? extends TorchArrowEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public TorchArrowEntity(Level worldIn, LivingEntity shooter) {
		super(BDBEntityTypes.TORCH_ARROW.get(), shooter, worldIn);
	}

	/**
	 * Used for Dispensers in: {@link CustomDispenserBehavior}
	 */
	public TorchArrowEntity(Level worldIn, double x, double y, double z) {
		super(BDBEntityTypes.TORCH_ARROW.get(), x, y, z, worldIn);
	}

	/*********************************************************** Data ********************************************************/

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Duration")) {
			this.secondsFireDuration = compound.getInt("Duration");
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Duration", this.secondsFireDuration);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(ARROW_TYPE.get());
	}

	/*********************************************************** Shoot Arrow ********************************************************/

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		super.tick();

		// Add particles during flight
		if (this.level.isClientSide() && !this.inGround) {
			// Add smoke particle
			this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			if (this.animateFireTick >= 4) {
				// Add flame particle with decreased movement relative to the arrow
				this.level.addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), this.getDeltaMovement().x() * 0.5F, this.getDeltaMovement().y() * 0.8F, this.getDeltaMovement().z() * 0.5F);
				// Reset particle cooldown to a random interval
				this.animateFireTick = -this.random.nextInt(12);
			}
			this.animateFireTick++;
		}
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		if (ItemConfig.torch_arrow_fire.get()) {
			living.setSecondsOnFire(this.secondsFireDuration);
		}
	}

	/*********************************************************** Place Torch ********************************************************/

	/**
	 * Called when the arrow hits a block or an entity
	 */
	private enum BlockResult {
		PLACE,
		BREAK,
		DROP
	}

	@Override
	protected void onHit(HitResult raytraceResultIn) {
		super.onHit(raytraceResultIn);
		HitResult.Type raytraceresult$type = raytraceResultIn.getType();
		if (raytraceresult$type == HitResult.Type.BLOCK) {
			BlockHitResult blockraytraceresult = (BlockHitResult) raytraceResultIn;
			BlockPos pos = blockraytraceresult.getBlockPos();
			BlockState blockState = this.level.getBlockState(pos);
			Block block = blockState.getBlock();
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			this.discard();

			Block torch = Blocks.TORCH;
			BlockState defaultTorchState = torch.defaultBlockState();
			Block wallTorch = Blocks.WALL_TORCH;
			BlockState defaultWallTorchState = wallTorch.defaultBlockState();

			BlockResult result = BlockResult.PLACE;
			Direction blockFace = blockraytraceresult.getDirection();
			DirectionProperty HORIZONTAL_FACING = HorizontalDirectionalBlock.FACING;

			switch (blockFace) {
			case UP:
				y++;
				break;
			case DOWN:
				y--;
				result = BlockResult.DROP;
				break;
			case NORTH:
				z--;
				defaultWallTorchState = defaultWallTorchState.setValue(HORIZONTAL_FACING, blockFace);
				break;
			case EAST:
				x++;
				defaultWallTorchState = defaultWallTorchState.setValue(HORIZONTAL_FACING, blockFace);
				break;
			case SOUTH:
				z++;
				defaultWallTorchState = defaultWallTorchState.setValue(HORIZONTAL_FACING, blockFace);
				break;
			case WEST:
				x--;
				defaultWallTorchState = defaultWallTorchState.setValue(HORIZONTAL_FACING, blockFace);
				break;
			}

			// Check if this is a block to place a torch on.
			if (!blockState.isFaceSturdy(this.level, pos, blockFace)) {
				if (blockFace.equals(Direction.UP) && (block instanceof FenceBlock || block instanceof WallBlock)) {
				} else {
					result = BlockResult.DROP;
				}
			}

			pos = new BlockPos(x, y, z);

			if (!this.level.isEmptyBlock(pos)) {
				result = BlockResult.DROP;
			}

			if (this.level.getBlockState(pos).getMaterial().equals(Material.REPLACEABLE_PLANT)) {
				result = BlockResult.BREAK;
			}

			switch (result) {
			case PLACE:
				if (blockFace.equals(Direction.UP)) {
					this.level.setBlockAndUpdate(pos, defaultTorchState);
				} else {
					this.level.setBlockAndUpdate(pos, defaultWallTorchState);
				}
				break;
			case BREAK:
				this.level.destroyBlock(pos, true);
				if (blockFace.equals(Direction.UP)) {
					this.level.setBlockAndUpdate(pos, defaultTorchState);
				} else if (blockState.isFaceSturdy(level, pos, blockFace)) {
					this.level.setBlockAndUpdate(pos, defaultWallTorchState);
				}
				break;
			case DROP:
				ItemEntity torchItem = new ItemEntity(level, x, y, z, new ItemStack(ARROW_TYPE.get()));
				this.level.addFreshEntity(torchItem);
				break;
			}
		}
	}

	/*********************************************************** Networking ********************************************************/

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}