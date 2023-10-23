package xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class DesertBanditArbalistEntity extends DesertBanditEntity {

	public DesertBanditArbalistEntity(EntityType<? extends DesertBanditArbalistEntity> banditArbalist, Level worldIn) {
		super(banditArbalist, worldIn);
	}

	/*********************************************************** Attributes ********************************************************/

	// TODO Pillager is faster and better in combat
	public static AttributeSupplier.Builder createDesertArbalistAttributes() {
		return DesertBanditArbalistEntity.createDesertBanditAttributes().add(Attributes.MOVEMENT_SPEED, 0.35D).add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.FOLLOW_RANGE, 32.0D);
	}

	/*********************************************************** Spawn-Equipment ********************************************************/

	@Override
	protected void setDefaultEquipmentAndEnchants(DifficultyInstance difficulty) {
		desertBanditArbalistDefaultEquipment(this);
	}

	public static void desertBanditArbalistDefaultEquipment(DesertBanditArbalistEntity entity) {
		ItemStack desertArbalistCrossBow = new ItemStack(Items.CROSSBOW);
		int multiShotChance = DesertBanditEntity.multiShotChance;
		Map<Enchantment, Integer> map = Maps.newHashMap();
		map.put(Enchantments.PIERCING, 1);
		if (entity.level.getDifficulty() == Difficulty.HARD) {
			map.put(Enchantments.QUICK_CHARGE, 2);
			if (entity.random.nextInt(multiShotChance) == 0) {
				map.put(Enchantments.MULTISHOT, 1);
			}
		} else {
			map.put(Enchantments.QUICK_CHARGE, 1);
		}
		EnchantmentHelper.setEnchantments(map, desertArbalistCrossBow);
		entity.setItemSlot(EquipmentSlot.MAINHAND, desertArbalistCrossBow);
		entity.getItemBySlot(EquipmentSlot.MAINHAND).setHoverName(new TranslatableComponent("equipment.betterdefaultbiomes.bandit_crossbow").withStyle(ChatFormatting.YELLOW));
		float armorDropChance = DesertBanditEntity.armorDropChance;
		entity.setDropChance(EquipmentSlot.MAINHAND, armorDropChance);
	}

	/*********************************************************** EXTRA ********************************************************/

	// TODO No idea what this is????
	//	@SuppressWarnings("deprecation")
	//	@Override
	//	public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
	//		BlockState blockstate = worldIn.getBlockState(pos.below());
	//		return !blockstate.is(Blocks.GRASS_BLOCK) && !blockstate.is(Blocks.SAND) ? 0.5F - worldIn.getBrightness(pos) : 10.0F;
	//	}
}