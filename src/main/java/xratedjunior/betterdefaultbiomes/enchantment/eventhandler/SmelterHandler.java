package xratedjunior.betterdefaultbiomes.enchantment.eventhandler;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.configuration.EnchantmentConfig;
import xratedjunior.betterdefaultbiomes.enchantment.BDBEnchantments;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class SmelterHandler {
	protected final static Random rand = new Random();

	/**
	 * Handles the breaking of Blocks
	 */
	public static void blockBreakHandler(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		ItemStack playerItem = player.getItemInHand(InteractionHand.MAIN_HAND);

		// Checks for Smelting Touch on the Player
		if (playerItem.getEnchantmentLevel(BDBEnchantments.SMELTING_TOUCH.get()) == 0)
			return;

		BlockState state = event.getState();
		Block block = state.getBlock();
		Level world = (Level) event.getLevel();
		BlockPos pos = event.getPos();

		// Cancels the Enchantment
		if (state == null || !ForgeHooks.isCorrectToolForDrops(state, player) || player.isCreative()) {
			return;
		}

		// Continue
		else {
			// TODO Use Mixin? {@link Block#getDrops}

			// Generate drops with Player Effects and Tool Enchantments.
			List<ItemStack> blockDrops = Block.getDrops(state, (ServerLevel) world, pos, (BlockEntity) null, player, playerItem);
			BetterDefaultBiomes.LOGGER.debug(blockDrops);

			for (ItemStack drop : blockDrops) {
				// Check furnace recipes for each drop.
				Optional<SmeltingRecipe> furnaceRecipe = world.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(drop), world);
				if (furnaceRecipe.isPresent()) {
					// Get Smelting result.
					ItemStack smeltedItem = furnaceRecipe.get().getResultItem(world.registryAccess());
					if (!smeltedItem.isEmpty()) {
						// Get drop count with Fortune, etc.
						int itemCount = drop.getCount();
						// Drop Smelting experience.
						float smeltingXP = furnaceRecipe.get().getExperience();
						// Round XP up to the nearest integer.
						block.popExperience((ServerLevel) world, pos, Mth.ceil(smeltingXP * itemCount));

						//Drop Items
						ItemEntity item = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(smeltedItem.getItem(), itemCount));
						world.addFreshEntity(item);
						// Remove Block
						world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					}
				} else {
					return;
				}
			}
		}
	}

	/**
	 * Sets mobs on fire if enabled in the config options
	 */
	public static void attackHandler(LivingHurtEvent event) {
		Entity source = event.getSource().getEntity();
		LivingEntity target = (LivingEntity) event.getEntity();
		if (!(source instanceof LivingEntity))
			return;
		LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
		ItemStack stack = attacker.getItemInHand(attacker.getUsedItemHand());
		int fortuneLevel = stack.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
		if ((stack.getEnchantmentLevel(BDBEnchantments.SMELTING_TOUCH.get()) > 0 && EnchantmentConfig.smelting_touch_fire.get())) {
			int multiplier = 1;
			if (fortuneLevel > 1) {
				multiplier = 2;
			}
			int seconds = 2;
			target.setSecondsOnFire(multiplier * seconds);
		} else
			return;
	}
}
