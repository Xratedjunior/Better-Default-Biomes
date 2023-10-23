package xratedjunior.betterdefaultbiomes.entity.projectile.dispenser;

import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.projectile.BanditArrowEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.DuckEggEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.HunterArrowEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.TorchArrowEntity;
import xratedjunior.betterdefaultbiomes.item.BDBItems;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public interface CustomDispenserBehavior extends DispenseItemBehavior {

	static void init() {
		BetterDefaultBiomes.LOGGER.debug("CustomDispenserBehavior.init()");

		DispenserBlock.registerBehavior(BDBItems.HUNTER_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			/**
			 * Return the projectile entity spawned by this dispense behavior.
			 */
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				AbstractArrow abstractarrowentity = new HunterArrowEntity(worldIn, position.x(), position.y(), position.z());
				abstractarrowentity.pickup = AbstractArrow.Pickup.ALLOWED;
				return abstractarrowentity;
			}
		});
		DispenserBlock.registerBehavior(BDBItems.BANDIT_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			/**
			 * Return the projectile entity spawned by this dispense behavior.
			 */
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				AbstractArrow abstractarrowentity = new BanditArrowEntity(worldIn, position.x(), position.y(), position.z());
				abstractarrowentity.pickup = AbstractArrow.Pickup.ALLOWED;
				return abstractarrowentity;
			}
		});
		DispenserBlock.registerBehavior(BDBItems.TORCH_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			/**
			 * Return the projectile entity spawned by this dispense behavior.
			 */
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				AbstractArrow abstractarrowentity = new TorchArrowEntity(worldIn, position.x(), position.y(), position.z());
				abstractarrowentity.pickup = AbstractArrow.Pickup.ALLOWED;
				return abstractarrowentity;
			}
		});
		DispenserBlock.registerBehavior(BDBItems.DUCK_EGG.get(), new AbstractProjectileDispenseBehavior() {
			/**
			 * Return the projectile entity spawned by this dispense behavior.
			 */
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				DuckEggEntity duckEggEntity = new DuckEggEntity(worldIn, position.x(), position.y(), position.z());
				return duckEggEntity;
			}
		});
	}
}
