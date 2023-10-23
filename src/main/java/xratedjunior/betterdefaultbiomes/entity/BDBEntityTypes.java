package xratedjunior.betterdefaultbiomes.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;
import xratedjunior.betterdefaultbiomes.entity.hostile.FrozenZombieEntity;
import xratedjunior.betterdefaultbiomes.entity.hostile.JungleCreeperEntity;
import xratedjunior.betterdefaultbiomes.entity.hostile.LostMinerEntity;
import xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit.DesertBanditArbalistEntity;
import xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit.DesertBanditArcherEntity;
import xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit.DesertBanditEntity;
import xratedjunior.betterdefaultbiomes.entity.hostile.desertbandit.DesertBanditMasterEntity;
import xratedjunior.betterdefaultbiomes.entity.hostile.hunter.HeadHunterEntity;
import xratedjunior.betterdefaultbiomes.entity.hostile.hunter.HunterEntity;
import xratedjunior.betterdefaultbiomes.entity.passive.CamelEntity;
import xratedjunior.betterdefaultbiomes.entity.passive.DuckEntity;
import xratedjunior.betterdefaultbiomes.entity.passive.FrogEntity;
import xratedjunior.betterdefaultbiomes.entity.passive.MuddyPigEntity;
import xratedjunior.betterdefaultbiomes.entity.passive.ZebraEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.BanditArrowEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.DuckEggEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.HunterArrowEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.SmallRockEntity;
import xratedjunior.betterdefaultbiomes.entity.projectile.TorchArrowEntity;
import xratedjunior.betterdefaultbiomes.item.BDBItems;

/**
 * Register EntityTypes, Spawn Eggs, Entity Attributes and Entity Spawn Placement.
 * 
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 * @info    Extra info about 'clientTrackingRange': {@linkplain https://forums.minecraftforge.net/topic/110926-setting-clienttrackingrange-to-adequate-value/#comment-494765}
 *          -> In short: The server will only send the entity data to the client when it is within the specified range. Once the entity leaves that range, it is despawned from the client and not rendered or loaded there at all.
 *          For reference check {@link EntityType}
 */
@Mod.EventBusSubscriber(modid = BetterDefaultBiomes.MOD_ID, bus = Bus.MOD)
public class BDBEntityTypes {

	public static final DeferredRegister<EntityType<?>> DEFERRED_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, BetterDefaultBiomes.MOD_ID);

	/*********************************************************** Hostile ********************************************************/

	public static final RegistryObject<EntityType<HunterEntity>> HUNTER = registerEntityType("hunter", 0x699564, 0x665339, EntityType.Builder.<HunterEntity>of(HunterEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
	public static final RegistryObject<EntityType<HeadHunterEntity>> HEAD_HUNTER = registerEntityType("head_hunter", 0x6B785B, 0x424530, EntityType.Builder.<HeadHunterEntity>of(HeadHunterEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
	public static final RegistryObject<EntityType<JungleCreeperEntity>> JUNGLE_CREEPER = registerEntityType("jungle_creeper", 0x286926, 0x1a1a1a, EntityType.Builder.<JungleCreeperEntity>of(JungleCreeperEntity::new, MobCategory.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8));
	public static final RegistryObject<EntityType<LostMinerEntity>> LOST_MINER = registerEntityType("lost_miner", 0xd1d1d1, 0x5e5e5e, EntityType.Builder.<LostMinerEntity>of(LostMinerEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
	public static final RegistryObject<EntityType<FrozenZombieEntity>> FROZEN_ZOMBIE = registerEntityType("frozen_zombie", 0x47636d, 0xcdd1c7, EntityType.Builder.<FrozenZombieEntity>of(FrozenZombieEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));

	public static final RegistryObject<EntityType<DesertBanditEntity>> DESERT_BANDIT = registerEntityType("desert_bandit", 0xefd382, 0x573d30, EntityType.Builder.<DesertBanditEntity>of(DesertBanditEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
	public static final RegistryObject<EntityType<DesertBanditArcherEntity>> DESERT_BANDIT_ARCHER = registerEntityType("desert_bandit_archer", 0xefd382, 0x734834, EntityType.Builder.<DesertBanditArcherEntity>of(DesertBanditArcherEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
	public static final RegistryObject<EntityType<DesertBanditArbalistEntity>> DESERT_BANDIT_ARBALIST = registerEntityType("desert_bandit_arbalist", 0xefd382, 0x734834, EntityType.Builder.<DesertBanditArbalistEntity>of(DesertBanditArbalistEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
	public static final RegistryObject<EntityType<DesertBanditMasterEntity>> DESERT_BANDIT_MASTER = registerEntityType("desert_bandit_master", 0xefd382, 0x16395a, EntityType.Builder.<DesertBanditMasterEntity>of(DesertBanditMasterEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));

	/*********************************************************** Passive ********************************************************/

	public static final RegistryObject<EntityType<MuddyPigEntity>> MUDDY_PIG = registerEntityType("muddy_pig", 0xf0a5a2, 0x55372d, EntityType.Builder.<MuddyPigEntity>of(MuddyPigEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10));
	public static final RegistryObject<EntityType<CamelEntity>> CAMEL = registerEntityType("camel", 0xB09460, 0x7F6B45, EntityType.Builder.<CamelEntity>of(CamelEntity::new, MobCategory.CREATURE).sized(1.1F, 1.95F).clientTrackingRange(10));
	public static final RegistryObject<EntityType<DuckEntity>> DUCK = registerEntityType("duck", 0xD1C3B5, 0x006600, EntityType.Builder.<DuckEntity>of(DuckEntity::new, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(10));
	public static final RegistryObject<EntityType<ZebraEntity>> ZEBRA = registerEntityType("zebra", 0xE6E6E6, 0x111111, EntityType.Builder.<ZebraEntity>of(ZebraEntity::new, MobCategory.CREATURE).sized(1.4F, 1.5F).clientTrackingRange(10));
	public static final RegistryObject<EntityType<FrogEntity>> FROG = registerEntityType("frog", 0x479e2e, 0xcab67b, EntityType.Builder.<FrogEntity>of(FrogEntity::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(10));

	/*********************************************************** Neutral ********************************************************/

	/*********************************************************** Projectile ********************************************************/

	// Misc
	public static final RegistryObject<EntityType<DuckEggEntity>> DUCK_EGG = registerEntityType("duck_egg", EntityType.Builder.<DuckEggEntity>of(DuckEggEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
	public static final RegistryObject<EntityType<SmallRockEntity>> SMALL_ROCK = registerEntityType("small_rock", EntityType.Builder.<SmallRockEntity>of(SmallRockEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));

	// Arrows
	public static final RegistryObject<EntityType<HunterArrowEntity>> HUNTER_ARROW = registerEntityType("hunter_arrow", EntityType.Builder.<HunterArrowEntity>of(HunterArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
	public static final RegistryObject<EntityType<BanditArrowEntity>> BANDIT_ARROW = registerEntityType("bandit_arrow", EntityType.Builder.<BanditArrowEntity>of(BanditArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
	public static final RegistryObject<EntityType<TorchArrowEntity>> TORCH_ARROW = registerEntityType("torch_arrow", EntityType.Builder.<TorchArrowEntity>of(TorchArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
		/*********************************************************** Hostile ********************************************************/

		registerSpawnPlacement(HUNTER.get(), HunterEntity::checkHunterSpawnRules);
		registerSpawnPlacement(HEAD_HUNTER.get(), HunterEntity::checkHunterSpawnRules);
		registerSpawnPlacement(JUNGLE_CREEPER.get(), JungleCreeperEntity::checkJungleCreeperSpawnRules);
		registerSpawnPlacement(LOST_MINER.get(), LostMinerEntity::checkLostMinerSpawnRules);
		registerSpawnPlacement(FROZEN_ZOMBIE.get(), Monster::checkMonsterSpawnRules);

		registerSpawnPlacement(DESERT_BANDIT.get(), DesertBanditEntity::checkBanditSpawnRules);
		registerSpawnPlacement(DESERT_BANDIT_ARCHER.get(), DesertBanditEntity::checkBanditSpawnRules);
		registerSpawnPlacement(DESERT_BANDIT_ARBALIST.get(), DesertBanditEntity::checkBanditSpawnRules);
		registerSpawnPlacement(DESERT_BANDIT_MASTER.get(), DesertBanditEntity::checkBanditSpawnRules);

		/*********************************************************** Passive ********************************************************/

		registerSpawnPlacement(MUDDY_PIG.get(), Animal::checkAnimalSpawnRules);
		registerSpawnPlacement(CAMEL.get(), CamelEntity::canCamelSpawn);
		registerSpawnPlacement(DUCK.get(), DuckEntity::checkAnimalSpawnRules);
		registerSpawnPlacement(ZEBRA.get(), Animal::checkAnimalSpawnRules);
		registerSpawnPlacement(FROG.get(), Animal::checkAnimalSpawnRules);

		/*********************************************************** Neutral ********************************************************/

	}

	@SubscribeEvent
	public static void addEntityAttributes(EntityAttributeCreationEvent event) {

		/*********************************************************** Hostile ********************************************************/

		event.put(HUNTER.get(), HeadHunterEntity.createHunterAttributes().build());
		event.put(HEAD_HUNTER.get(), HeadHunterEntity.createHeadHunterAttributes().build());
		event.put(JUNGLE_CREEPER.get(), JungleCreeperEntity.createJungleCreeperAttributes().build());
		event.put(LOST_MINER.get(), AbstractSkeleton.createAttributes().build());
		event.put(FROZEN_ZOMBIE.get(), Zombie.createAttributes().build());

		event.put(DESERT_BANDIT.get(), DesertBanditEntity.createDesertBanditAttributes().build());
		event.put(DESERT_BANDIT_ARCHER.get(), DesertBanditEntity.createDesertBanditAttributes().build());
		event.put(DESERT_BANDIT_ARBALIST.get(), DesertBanditArbalistEntity.createDesertArbalistAttributes().build());
		event.put(DESERT_BANDIT_MASTER.get(), DesertBanditMasterEntity.createDesertMasterAttributes().build());

		/*********************************************************** Passive ********************************************************/

		event.put(MUDDY_PIG.get(), Pig.createAttributes().build());
		event.put(CAMEL.get(), Llama.createAttributes().build());
		event.put(DUCK.get(), DuckEntity.createDuckAttributes().build());
		event.put(ZEBRA.get(), ZebraEntity.createBDBAnimalAttributes().build());
		event.put(FROG.get(), FrogEntity.createFrogAttributes().build());

		/*********************************************************** Neutral ********************************************************/

	}

	/**
	 * Helper method for registering Mob EntityTypes
	 */
	private static <T extends Mob> RegistryObject<EntityType<T>> registerEntityType(String registryName, int eggBackgroundColor, int eggHighlightColor, EntityType.Builder<T> builder) {
		// Register Mob
		RegistryObject<EntityType<T>> entityType = DEFERRED_ENTITY_TYPES.register(registryName, () -> builder.build(BetterDefaultBiomes.find(registryName)));
		// Register Spawn Egg
		BDBItems.registerItem(registryName + "_spawn_egg", () -> new ForgeSpawnEggItem(entityType, eggBackgroundColor, eggHighlightColor, new Item.Properties().tab(BetterDefaultBiomes.BETTERDEFAULTBIOMESTAB)));
		return entityType;
	}

	/**
	 * Helper method for registering all EntityTypes
	 */
	private static <T extends Entity> RegistryObject<EntityType<T>> registerEntityType(String registryName, EntityType.Builder<T> builder) {
		return DEFERRED_ENTITY_TYPES.register(registryName, () -> builder.build(registryName));
	}

	private static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacements.SpawnPredicate<T> placementPredicate) {
		SpawnPlacements.register(entityType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, placementPredicate);
	}
}
