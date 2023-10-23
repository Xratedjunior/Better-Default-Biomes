package xratedjunior.betterdefaultbiomes.trade;

import java.util.Random;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public final class VillagerTradeUtils {
	public static class ItemsForEmeraldsTrade implements VillagerTrades.ItemListing {
		private final ItemStack itemstack;
		private final int emeraldCount;
		private final int recievedItemCount;
		private final int maxTrades;
		private final int givenExp;
		private final float priceMultiplier;

		public ItemsForEmeraldsTrade(Block block, int emeraldCount, int recievedItemCount, int maxTrades, int givenExp) {
			this(new ItemStack(block), emeraldCount, recievedItemCount, maxTrades, givenExp);
		}

		public ItemsForEmeraldsTrade(Item item, int emeraldCount, int recievedItemCount, int givenExp) {
			this(new ItemStack(item), emeraldCount, recievedItemCount, 12, givenExp);
		}

		/*
		 * Buy Items.
		 */
		public ItemsForEmeraldsTrade(Item item, int emeraldCount, int recievedItemCount, int maxTrades, int givenExp) {
			this(new ItemStack(item), emeraldCount, recievedItemCount, maxTrades, givenExp);
		}

		public ItemsForEmeraldsTrade(ItemStack stack, int emeraldCount, int recievedItemCount, int maxTrades, int givenExp) {
			this(stack, emeraldCount, recievedItemCount, maxTrades, givenExp, 0.05F);
		}

		public ItemsForEmeraldsTrade(ItemStack stack, int emeraldCount, int recievedItemCount, int maxTrades, int givenExp, float priceMultiplier) {
			this.itemstack = stack;
			this.emeraldCount = emeraldCount;
			this.recievedItemCount = recievedItemCount;
			this.maxTrades = maxTrades;
			this.givenExp = givenExp;
			this.priceMultiplier = priceMultiplier;
		}

		public MerchantOffer getOffer(Entity trader, Random random) {
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.itemstack.getItem(), this.recievedItemCount), this.maxTrades, this.givenExp, this.priceMultiplier);
		}
	}

	public static class EmeraldsForItemsTrade implements VillagerTrades.ItemListing {
		private final ItemStack tradeItem;
		private final int itemCount;
		private final int emeraldCount;
		private final int maxTrades;
		private final int givenExp;
		private final float priceMultiplier;

		public EmeraldsForItemsTrade(Block tradeBlock, int blockCount, int recievedEmeraldCount, int maxTrades, int givenExp) {
			this(new ItemStack(tradeBlock), blockCount, recievedEmeraldCount, maxTrades, givenExp);
		}

		public EmeraldsForItemsTrade(Item tradeItem, int itemCount, int recievedEmeraldCount, int givenExp) {
			this(new ItemStack(tradeItem), itemCount, recievedEmeraldCount, 12, givenExp);
		}

		public EmeraldsForItemsTrade(Item tradeItem, int itemCount, int recievedEmeraldCount, int maxTrades, int givenExp) {
			this(new ItemStack(tradeItem), itemCount, recievedEmeraldCount, maxTrades, givenExp);
		}

		public EmeraldsForItemsTrade(ItemStack tradeItemStack, int stackSize, int recievedEmeraldCount, int maxTrades, int givenExp) {
			this(tradeItemStack, stackSize, recievedEmeraldCount, maxTrades, givenExp, 0.05F);
		}

		public EmeraldsForItemsTrade(ItemStack tradeItemStack, int stackSize, int recievedEmeraldCount, int maxTrades, int givenExp, float priceMultiplier) {
			this.tradeItem = tradeItemStack;
			this.itemCount = stackSize;
			this.emeraldCount = recievedEmeraldCount;
			this.maxTrades = maxTrades;
			this.givenExp = givenExp;
			this.priceMultiplier = priceMultiplier;
		}

		public MerchantOffer getOffer(Entity trader, Random random) {
			return new MerchantOffer(new ItemStack(this.tradeItem.getItem(), this.itemCount), new ItemStack(Items.EMERALD, this.emeraldCount), this.maxTrades, this.givenExp, this.priceMultiplier);
		}
	}

	public static class ItemsForEmeraldsAndItemsTrade implements VillagerTrades.ItemListing {
		private final ItemStack buyingItem;
		private final int buyingItemCount;
		private final int emeraldCount;
		private final ItemStack sellingItem;
		private final int sellingItemCount;
		private final int maxTrades;
		private final int givenExp;
		private final float priceMultiplier;

		public ItemsForEmeraldsAndItemsTrade(Item buyingItem, int buyingItemCount, int emeraldCount, Item sellingItem, int sellingItemCount, int maxTrades, int givenExp) {
			this(new ItemStack(buyingItem), buyingItemCount, emeraldCount, new ItemStack(sellingItem), sellingItemCount, maxTrades, givenExp, 0.05F);
		}

		public ItemsForEmeraldsAndItemsTrade(Block buyingItem, int buyingItemCount, int emeraldCount, Block sellingItem, int sellingItemCount, int maxTrades, int givenExp) {
			this(new ItemStack(buyingItem), buyingItemCount, emeraldCount, new ItemStack(sellingItem), sellingItemCount, maxTrades, givenExp, 0.05F);
		}

		public ItemsForEmeraldsAndItemsTrade(Block buyingItem) {
			this(new ItemStack(buyingItem), 1, 5, new ItemStack(buyingItem), 2, 6, 30, 0.05F);
		}

		public ItemsForEmeraldsAndItemsTrade(ItemStack buyingItem, int buyingItemCount, int emeraldCount, ItemStack sellingItem, int sellingItemCount, int maxTrades, int givenExp, float priceMultiplier) {
			this.buyingItem = buyingItem;
			this.buyingItemCount = buyingItemCount;
			this.emeraldCount = emeraldCount;
			this.sellingItem = sellingItem;
			this.sellingItemCount = sellingItemCount;
			this.maxTrades = maxTrades;
			this.givenExp = givenExp;
			this.priceMultiplier = priceMultiplier;
		}

		public MerchantOffer getOffer(Entity trader, Random random) {
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.buyingItem.getItem(), this.buyingItemCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), this.maxTrades, this.givenExp, this.priceMultiplier);
		}
	}

	//EnchantedBook for Emeralds

	public static class EnchantedItemForEmeraldsTrade implements VillagerTrades.ItemListing {
		private final ItemStack sellingStack;
		private final int emeraldCount;
		private final int maxTrades;
		private final int givenExp;
		private final float priceMultiplier;

		public EnchantedItemForEmeraldsTrade(Item sellItem, int emeraldCount, int maxTrades, int givenExp) {
			this(sellItem, emeraldCount, maxTrades, givenExp, 0.05F);
		}

		public EnchantedItemForEmeraldsTrade(Item sellItem, int emeraldCount, int maxTrades, int givenExp, float priceMultiplier) {
			this.sellingStack = new ItemStack(sellItem);
			this.emeraldCount = emeraldCount;
			this.maxTrades = maxTrades;
			this.givenExp = givenExp;
			this.priceMultiplier = priceMultiplier;
		}

		public MerchantOffer getOffer(Entity trader, Random rand) {
			int i = 5 + rand.nextInt(15);
			ItemStack itemstack = EnchantmentHelper.enchantItem(rand, new ItemStack(this.sellingStack.getItem()), i, false);
			int j = Math.min(this.emeraldCount + i, 64);
			ItemStack itemstack1 = new ItemStack(Items.EMERALD, j);
			return new MerchantOffer(itemstack1, itemstack, this.maxTrades, this.givenExp, this.priceMultiplier);
		}
	}
}
