package xratedjunior.betterdefaultbiomes.item.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xratedjunior.betterdefaultbiomes.entity.projectile.HunterArrowEntity;
import xratedjunior.betterdefaultbiomes.util.BDBHelper;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class HunterArrowItem extends ArrowItem {
	// Level 1 and duration of 10 seconds
	private MobEffectInstance poisonEffect = new MobEffectInstance(MobEffects.POISON, 200, 0);
	// Level 1 and duration of 10 seconds
	private MobEffectInstance glowingEffect = new MobEffectInstance(MobEffects.GLOWING, 200, 0);

	public HunterArrowItem(Item.Properties builder) {
		super(builder);
	}

	@Override
	public HunterArrowEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		return new HunterArrowEntity(worldIn, shooter);
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			if (flagIn.isAdvanced()) {
				tooltip.add(Component.translatable("tooltip.betterdefaultbiomes.hunter_arrow").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
			}

			// Add effect tooltips
			BDBHelper.addPotionTooltip(this.poisonEffect, tooltip);
			BDBHelper.addPotionTooltip(this.glowingEffect, tooltip, ChatFormatting.GOLD);
		}

		// Player is not holding shift
		else {
			tooltip.add(BDBHelper.HOLD_SHIFT_TOOLTIP);
		}
	}
}