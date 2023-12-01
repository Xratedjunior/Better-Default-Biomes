package xratedjunior.betterdefaultbiomes.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.biome.Biome;

/**
 * @author  Xrated_junior
 * @version 1.19.4-Alpha 4.0.0
 */
public class BDBHelper {
	public static final Component HOLD_SHIFT_TOOLTIP = Component.translatable("tooltip.betterdefaultbiomes.hold_shift").withStyle(ChatFormatting.DARK_GRAY);

	/*********************************************************** Potion Tooltip ********************************************************/

	/**
	 * Add potion tooltip with no additional duration tweaks.
	 */
	public static void addPotionTooltip(MobEffectInstance mobEffectInstance, List<Component> tooltip) {
		addPotionTooltip(mobEffectInstance, tooltip, (ChatFormatting) null);
	}

	public static void addPotionTooltip(MobEffectInstance mobEffectInstance, List<Component> tooltip, @Nullable ChatFormatting customColor) {
		addPotionTooltip(mobEffectInstance, tooltip, 1, customColor);
	}

	/**
	 * Copied from {@link PotionUtils#addPotionTooltip(ItemStack, List, float)}
	 */
	public static void addPotionTooltip(MobEffectInstance mobEffectInstance, List<Component> tooltip, float durationMultiplier, @Nullable ChatFormatting customColor) {
		// Create new list to store attributes
		List<Pair<Attribute, AttributeModifier>> attributeList = Lists.newArrayList();

		MutableComponent effectName = Component.translatable(mobEffectInstance.getDescriptionId());
		MobEffect mobEffect = mobEffectInstance.getEffect();
		Map<Attribute, AttributeModifier> attributeModifiers = mobEffect.getAttributeModifiers();

		// Apply effect amplifier (level) to attribute modifiers
		if (!attributeModifiers.isEmpty()) {
			for (Entry<Attribute, AttributeModifier> modifier : attributeModifiers.entrySet()) {
				AttributeModifier attributemodifier = modifier.getValue();
				AttributeModifier amplifiedAttributeModifier = new AttributeModifier(attributemodifier.getName(), mobEffect.getAttributeModifierValue(mobEffectInstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
				attributeList.add(new Pair<>(modifier.getKey(), amplifiedAttributeModifier));
			}
		}

		// Add effect amplifier (level) to tooltip
		if (mobEffectInstance.getAmplifier() > 0) {
			effectName = Component.translatable("potion.withAmplifier", effectName, Component.translatable("potion.potency." + mobEffectInstance.getAmplifier()));
		}

		// Add effect duration to tooltip
		if (mobEffectInstance.getDuration() > 20) {
			effectName = Component.translatable("potion.withDuration", effectName, MobEffectUtil.formatDuration(mobEffectInstance, durationMultiplier));
		}

		// Add effect with correct level and time to tooltip with formatting in correct color
		if (customColor == null) {
			tooltip.add(effectName.withStyle(mobEffect.getCategory().getTooltipFormatting()));
		} else {
			tooltip.add(effectName.withStyle(customColor));
		}

		// Show applied effect on player
		if (!attributeList.isEmpty()) {
			// Empty line
			tooltip.add(Component.literal(""));
			tooltip.add((Component.translatable("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

			for (Pair<Attribute, AttributeModifier> attributeAndModifier : attributeList) {
				AttributeModifier amplifiedAttributeModifier = attributeAndModifier.getSecond();
				double modificationAmount = amplifiedAttributeModifier.getAmount();
				double multipliedAmount;
				if (amplifiedAttributeModifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && amplifiedAttributeModifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
					multipliedAmount = amplifiedAttributeModifier.getAmount();
				} else {
					multipliedAmount = amplifiedAttributeModifier.getAmount() * 100.0D;
				}

				if (modificationAmount > 0.0D) {
					tooltip.add((Component.translatable("attribute.modifier.plus." + amplifiedAttributeModifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(multipliedAmount), Component.translatable(attributeAndModifier.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
				} else if (modificationAmount < 0.0D) {
					multipliedAmount *= -1.0D;
					tooltip.add((Component.translatable("attribute.modifier.take." + amplifiedAttributeModifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(multipliedAmount), Component.translatable(attributeAndModifier.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
				}
			}
		}
	}

	/*********************************************************** Spawn/Generation Helper ********************************************************/

	/**
	 * Check if 'string' has matching Biome or Tag.
	 * Also supports advanced config inputs containing "&"
	 */
	public static boolean matchBiomeOrTag(Holder<Biome> biome, List<String> spawnInputs) {
		if (!spawnInputs.isEmpty()) {
			for (String spawnInput : spawnInputs) {

				// Check for advanced inputs
				if (spawnInput.contains("&")) {
					// Split all inputs
					String[] tagArray = spawnInput.split("&");
					// Go over each Biome Tag
					for (int tag = 0; tag < tagArray.length; tag++) {
						// Check if Biome Tag has a match with the current Biome.
						if (matchTag(biome, tagArray[tag].trim())) {
							// Matched!
							// Check if this was the last BiomeType
							if (tag == (tagArray.length - 1)) {
								// All BiomeTypes match the current Biome
								return true;
							}
							// Go to next Tag in array
							continue;
						}
						// No match between Biome Tag and this Biome, so exit array loop.
						break;
					}
					// Go to next 'spawnInput'
					continue;
				}
				// Check singular Biomes and BiomeTypes
				return matchBiomeOrTag(biome, spawnInput);
			}
		}

		return false;
	}

	/**
	 * Check if 'string' has matching Biome or Tag.
	 */
	private static boolean matchBiomeOrTag(Holder<Biome> biome, String spawnInput) {
		// Do quick check for matching Biome.
		if (matchBiome(biome, spawnInput)) {
			return true;
		}

		// Check for matching Biome Tag.
		return matchTag(biome, spawnInput);
	}

	/**
	 * Check if 'string' matches Biome.
	 */
	private static boolean matchBiome(Holder<Biome> biome, String biomeInput) {
		// SpawnInput is "minecraft:plains" for example.
		ResourceLocation location = new ResourceLocation(biomeInput);
		ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, location);
		return biome.is(biomeKey);
	}

	/**
	 * Check if 'string' has matching Biome Tag.
	 */
	private static boolean matchTag(Holder<Biome> biome, String tagInput) {
		// SpawnInput is "minecraft:plains" for example.
		ResourceLocation tagLocation = new ResourceLocation(tagInput);
		TagKey<Biome> tagKey = TagKey.create(Registries.BIOME, tagLocation);
		return biome.containsTag(tagKey);
	}
}
