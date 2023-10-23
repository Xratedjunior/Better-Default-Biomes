package xratedjunior.betterdefaultbiomes.item.item;

import java.util.Random;

import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import xratedjunior.betterdefaultbiomes.entity.projectile.SmallRockEntity;
import xratedjunior.betterdefaultbiomes.sound.BDBSoundEvents;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class SmallRockItem extends BlockItem {
	private static final Random random = new Random();

	public SmallRockItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	/**
	 * Called when the Block is not placed on the ground
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		worldIn.playSound((Player) null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), BDBSoundEvents.ENTITY_SMALL_ROCK_THROW.get(), SoundSource.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isClientSide) {
			SmallRockEntity smallRockEntity = new SmallRockEntity(worldIn, playerIn);
			smallRockEntity.setItem(itemstack);
			smallRockEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F, 1.0F);
			worldIn.addFreshEntity(smallRockEntity);
		}

		playerIn.awardStat(Stats.ITEM_USED.get(this));
		if (!playerIn.getAbilities().instabuild) {
			itemstack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}
