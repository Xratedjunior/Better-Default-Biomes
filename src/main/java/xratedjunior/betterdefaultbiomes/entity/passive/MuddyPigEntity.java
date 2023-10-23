package xratedjunior.betterdefaultbiomes.entity.passive;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import xratedjunior.betterdefaultbiomes.entity.BDBEntityTypes;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
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
	 */
	@Override
	protected void dropFromLootTable(DamageSource damageSource, boolean lastHurtByPlayer) {
		LootContext.Builder lootcontext$builder = this.createLootContext(lastHurtByPlayer, damageSource);
		LootContext lootContext = lootcontext$builder.create(LootContextParamSets.ENTITY);
		// Drop vanilla pig loot table.
		ResourceLocation pigLootTable = EntityType.PIG.getDefaultLootTable();
		LootTable lootTable = this.level.getServer().getLootTables().get(pigLootTable);
		lootTable.getRandomItems(lootContext).forEach(this::spawnAtLocation);
		// Drop muddy pig loot table.
		ResourceLocation muddyPigLoottable = this.getLootTable();
		lootTable = this.level.getServer().getLootTables().get(muddyPigLoottable);
		lootTable.getRandomItems(lootContext).forEach(this::spawnAtLocation);
	}
}
