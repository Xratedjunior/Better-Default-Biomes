package xratedjunior.betterdefaultbiomes.sound;

import javax.annotation.Nonnull;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xratedjunior.betterdefaultbiomes.BetterDefaultBiomes;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
public class BDBSoundEvents {

	// Used to register all SoundEvents.
	public static final DeferredRegister<SoundEvent> DEFERRED_SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BetterDefaultBiomes.MOD_ID);

	/*********************************************************** Mob Sounds ********************************************************/

	public static final RegistryObject<SoundEvent> ENTITY_DUCK_HURT = registerSoundEvent("entity.duck.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_DUCK_HURT_BABY = registerSoundEvent("entity.duck.hurt.baby");
	public static final RegistryObject<SoundEvent> ENTITY_DUCK_DEATH = registerSoundEvent("entity.duck.death");
	public static final RegistryObject<SoundEvent> ENTITY_DUCK_DEATH_BABY = registerSoundEvent("entity.duck.death.baby");
	public static final RegistryObject<SoundEvent> ENTITY_DUCK_AMBIENT = registerSoundEvent("entity.duck.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_ZEBRA_HURT = registerSoundEvent("entity.zebra.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_ZEBRA_DEATH = registerSoundEvent("entity.zebra.death");
	public static final RegistryObject<SoundEvent> ENTITY_ZEBRA_AMBIENT = registerSoundEvent("entity.zebra.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_FROG_HURT = registerSoundEvent("entity.frog.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_FROG_DEATH = registerSoundEvent("entity.frog.death");
	public static final RegistryObject<SoundEvent> ENTITY_FROG_AMBIENT = registerSoundEvent("entity.frog.ambient");

	/*********************************************************** Miscellaneous Sounds ********************************************************/

	public static final RegistryObject<SoundEvent> ENTITY_SMALL_ROCK_THROW = registerSoundEvent("entity.small_rock.throw");
	public static final RegistryObject<SoundEvent> ENTITY_SMALL_ROCK_BREAK = registerSoundEvent("entity.small_rock.break");

	/*********************************************************** Block Sounds ********************************************************/

	public static final RegistryObject<SoundEvent> BLOCK_SMALL_ADD = registerSoundEvent("block.small.add");
	public static final RegistryObject<SoundEvent> BLOCK_SMALL_REMOVE = registerSoundEvent("block.small.remove");
	public static final RegistryObject<SoundEvent> BLOCK_SMALL_PLACE = registerSoundEvent("block.small.place");
	public static final RegistryObject<SoundEvent> BLOCK_SMALL_BREAK = registerSoundEvent("block.small.break");
	public static final RegistryObject<SoundEvent> BLOCK_SMALL_HIT = registerSoundEvent("block.small.hit");
	public static final RegistryObject<SoundEvent> BLOCK_SMALL_ROTATE = registerSoundEvent("block.small.rotate");

	/**
	 * Helper method for registering all SoundEvents
	 */
	private static RegistryObject<SoundEvent> registerSoundEvent(@Nonnull String registryName) {
		SoundEvent soundEvent = new SoundEvent(BetterDefaultBiomes.locate(registryName));
		return DEFERRED_SOUND_EVENTS.register(registryName, () -> soundEvent);
	}
}
