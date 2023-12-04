package xratedjunior.betterdefaultbiomes.entity.passive;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;

/**
 * @author  Xrated_junior
 * @version 1.20.2-Alpha 5.0.0
 */
public class MuddyPigEntity extends Pig {

	public MuddyPigEntity(EntityType<? extends MuddyPigEntity> entity, Level worldIn) {
		super(entity, worldIn);
	}

	@Override
	public Pig getBreedOffspring(ServerLevel serverWorld, AgeableMob ageableEntity) {
		int chance = this.random.nextInt(8);
		Pig baby = BDBEntityTypes.MUDDY_PIG.get().create(serverWorld);
		// 12.5% chance to spawn a vanilla pig.
		if (chance == 0) {
			baby = EntityType.PIG.create(serverWorld);
		}
		return baby;
	}

	/**
	 * Drop vanilla pig loot table, but also the muddy pig loot table.
	 * This makes sure that people still have customization.
	 * Copy from: {@link LivingEntity#dropFromLootTable}
	 */
	@Override
	protected void dropFromLootTable(DamageSource damageSource, boolean lastHurtByPlayer) {
		// Get LootTable Parameters
		LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.DAMAGE_SOURCE, damageSource).withOptionalParameter(LootContextParams.KILLER_ENTITY, damageSource.getEntity()).withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, damageSource.getDirectEntity());
		if (lastHurtByPlayer && this.lastHurtByPlayer != null) {
			lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
		}
		LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);

		// Drop vanilla pig loot table.
		ResourceLocation pigLootTable = EntityType.PIG.getDefaultLootTable();
		LootTable loottable = this.level().getServer().getLootData().getLootTable(pigLootTable);
		loottable.getRandomItems(lootparams, this.getLootTableSeed(), this::spawnAtLocation);

		// Drop muddy pig loot table.
		ResourceLocation muddyPigLootTable = this.getLootTable();
		loottable = this.level().getServer().getLootData().getLootTable(muddyPigLootTable);
		loottable.getRandomItems(lootparams, this.getLootTableSeed(), this::spawnAtLocation);
	}
}
