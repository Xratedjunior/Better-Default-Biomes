package xratedjunior.betterdefaultbiomes.advancement.eventhandler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import xratedjunior.betterdefaultbiomes.advancement.BDBCriteriaTriggers;
import xratedjunior.betterdefaultbiomes.block.BDBBlocks;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class AxeFeatherReedGrassHandler {
	private static Boolean advancementCriteria = false;

	public static void blockBreakHandler(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		ItemStack playerItem = player.getItemInHand(InteractionHand.MAIN_HAND);

		if (playerItem.is(ItemTags.AXES)) {
			Block block = event.getState().getBlock();
			if (block == BDBBlocks.FEATHER_REED_GRASS.get()) {
				advancementCriteria = true;
			}
		}
	}

	/**
	 * Dispensers can cheat it, but doesn't really matter that much.
	 */
	public static void onEntityJoinWorldHandler(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (entity.getType() == EntityType.ITEM && advancementCriteria) {
			ItemEntity itemEntity = (ItemEntity) entity;
			if (itemEntity.getItem().getItem() == Items.WHEAT) {
				Player player = entity.getCommandSenderWorld().getNearestPlayer(entity, 5);
				if (player != null) {
					ItemStack playerItem = player.getItemInHand(InteractionHand.MAIN_HAND);
					if (playerItem.is(ItemTags.AXES)) {
						if (!player.getCommandSenderWorld().isClientSide()) {
							BDBCriteriaTriggers.AXE_FEATHER_REED_GRASS_WHEAT.trigger((ServerPlayer) player);
						}
					}
				}
			}
		}
		advancementCriteria = false;
	}
}
