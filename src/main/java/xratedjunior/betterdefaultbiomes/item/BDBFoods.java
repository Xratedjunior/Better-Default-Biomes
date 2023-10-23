package xratedjunior.betterdefaultbiomes.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBFoods {
	public static final FoodProperties FROZEN_FLESH = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 0), 0.8F).meat().build();
	public static final FoodProperties DUCK = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build();
	public static final FoodProperties COOKED_DUCK = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).meat().build();
	public static final FoodProperties FROG_LEG = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build();
	public static final FoodProperties COOKED_FROG_LEG = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).meat().build();
}
