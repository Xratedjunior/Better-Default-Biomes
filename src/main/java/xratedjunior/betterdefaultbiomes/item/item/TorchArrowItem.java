package xratedjunior.betterdefaultbiomes.item.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.configuration.ItemConfig;
import xratedjunior.betterdefaultbiomes.entity.projectile.TorchArrowEntity;
import xratedjunior.betterdefaultbiomes.util.BDBHelper;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class TorchArrowItem extends ArrowItem {

	public TorchArrowItem(Item.Properties builder) {
		super(builder);
	}

	@Override
	public TorchArrowEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		return new TorchArrowEntity(worldIn, shooter);
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			if (ItemConfig.torch_arrow_fire.get()) {
				tooltip.add(new TranslatableComponent("tooltip.betterdefaultbiomes.torch_arrow_fire").withStyle(ChatFormatting.GOLD));
			}
			tooltip.add(new TranslatableComponent("tooltip.betterdefaultbiomes.torch_arrow").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
		} else {
			tooltip.add(BDBHelper.HOLD_SHIFT_TOOLTIP);
		}
	}
}