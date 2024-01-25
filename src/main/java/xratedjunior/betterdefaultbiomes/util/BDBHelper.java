package xratedjunior.betterdefaultbiomes.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.1.0
 */
@SuppressWarnings("deprecation")
public class BDBHelper {
	public static final Component HOLD_SHIFT_TOOLTIP = new TranslatableComponent("tooltip.betterdefaultbiomes.hold_shift").withStyle(ChatFormatting.DARK_GRAY);

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
		List<Pair<Attribute, AttributeModifier>> attributeList = new ArrayList<Pair<Attribute,AttributeModifier>>();

		MutableComponent effectName = new TranslatableComponent(mobEffectInstance.getDescriptionId());
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
			effectName = new TranslatableComponent("potion.withAmplifier", effectName, new TranslatableComponent("potion.potency." + mobEffectInstance.getAmplifier()));
		}

		// Add effect duration to tooltip
		if (mobEffectInstance.getDuration() > 20) {
			effectName = new TranslatableComponent("potion.withDuration", effectName, MobEffectUtil.formatDuration(mobEffectInstance, durationMultiplier));
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
			tooltip.add(TextComponent.EMPTY);
			tooltip.add((new TranslatableComponent("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

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
					tooltip.add((new TranslatableComponent("attribute.modifier.plus." + amplifiedAttributeModifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(multipliedAmount), new TranslatableComponent(attributeAndModifier.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
				} else if (modificationAmount < 0.0D) {
					multipliedAmount *= -1.0D;
					tooltip.add((new TranslatableComponent("attribute.modifier.take." + amplifiedAttributeModifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(multipliedAmount), new TranslatableComponent(attributeAndModifier.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
				}
			}
		}
	}

	/*********************************************************** Spawn/Generation Helper ********************************************************/

	/**
	 * Check if the given Biome is in the list Biomes/Types of possible spawn Biomes for an entity.
	 * TODO Add back the logger output for wrong 'spawnInput'
	 */
	public static boolean matchBiomeOrType(ResourceLocation biomeName, List<String> spawnInputs) {
		if (!spawnInputs.isEmpty()) {
			for (String spawnInput : spawnInputs) {
				
				// Check for advanced inputs
				if (spawnInput.contains("&")) {
					// Split all inputs
					String[] typeArray = spawnInput.split("&");
					// Go over each BiomeType
					for (int type = 0; type < typeArray.length; type++) {
						// Check if BiomeType has a match with the current Biome.
						if (matchBiomeOrType(biomeName, typeArray[type].trim())) {
							// Matched!
							// Check if this was the last BiomeType
							if (type == (typeArray.length - 1)) {
								// All BiomeTypes match the current Biome
								return true;
							}
							
							// Go to next entry
							continue;
						}
						
						// No match between BiomeType and Biome
						break;
					}
				}

				// Check singular Biomes and BiomeTypes
				if (matchBiomeOrType(biomeName, spawnInput)) {
					return true;
				}
			}
		}

		// Biome does not match with config input.
		return false;
	}

	private static boolean matchBiomeOrType(ResourceLocation biomeName, String spawnInput) {
		// Check if 'spawnInput' is a BiomeType.
		if (BiomeDictionary.Type.hasType(spawnInput)) {
			// Check if 'spawnInput' matches a BiomeType of the current Biome.
			return matchType(biomeName, spawnInput);
		}

		// Wrong/Misspelled config input for BiomeType. (Exclude Biomes and multiple BiomeTypes)
		else if (!spawnInput.contains(":") && !spawnInput.contains("&")) {
			BetterDefaultBiomes.LOGGER.error("Unable to find BiomeType: {}", spawnInput);
		}

		// Check if 'spawnInput' matches 'biomeName'.
		return matchBiome(biomeName, spawnInput);
	}

	private static boolean matchType(ResourceLocation biomeName, String spawnInput) {
		// Get current Biome
		ResourceKey<Biome> biome = ResourceKey.create(ForgeRegistries.Keys.BIOMES, biomeName);

		// Get all BiomeTypes associated with the Biome
		Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
		for (BiomeDictionary.Type biomeType : biomeTypes) {
			// Check if 'spawnInput' matches with 'biomeType'
			if (biomeType.getName().equals(spawnInput.toUpperCase(Locale.ENGLISH))) {
				// We have found a match!
				return true;
			}
		}

		return false;
	}

	/**
	 * Check if 'spawnInput' matches 'biomeName'.
	 */
	private static boolean matchBiome(ResourceLocation biomeName, String spawnInput) {
		return biomeName.toString().equals(spawnInput.toLowerCase());
	}
}
