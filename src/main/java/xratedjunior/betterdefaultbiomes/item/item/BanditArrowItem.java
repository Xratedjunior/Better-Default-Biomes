package xratedjunior.betterdefaultbiomes.item.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Difficulty;
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
import xratedjunior.betterdefaultbiomes.entity.projectile.BanditArrowEntity;
import xratedjunior.betterdefaultbiomes.util.BDBHelper;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BanditArrowItem extends ArrowItem {

	public BanditArrowItem(Item.Properties builder) {
		super(builder);
	}

	/*********************************************************** Arrow ********************************************************/

	@Override
	public BanditArrowEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		return new BanditArrowEntity(worldIn, shooter);
	}

	/*********************************************************** Tooltip ********************************************************/

	/**
	 * Allows items to add custom lines of information to the mouseover description.
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			if (flagIn.isAdvanced()) {
				tooltip.add(Component.translatable("tooltip.betterdefaultbiomes.bandit_arrow").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
			}

			// Level 1 and duration of 10 seconds
			int effectLevel = 1;

			// Hard difficulty setting
			if (worldIn.getDifficulty() == Difficulty.HARD) {
				// Level 2 and duration of 10 seconds
				effectLevel = 2;
			}

			// 10 seconds (1 second is 20 ticks)
			int effectDurationTicks = 200;
			MobEffectInstance weaknessEffect = new MobEffectInstance(MobEffects.WEAKNESS, effectDurationTicks, effectLevel - 1);
			// Add effect tooltip
			BDBHelper.addPotionTooltip(weaknessEffect, tooltip);
		}

		// Player is not holding shift
		else {
			tooltip.add(BDBHelper.HOLD_SHIFT_TOOLTIP);
		}
	}
}