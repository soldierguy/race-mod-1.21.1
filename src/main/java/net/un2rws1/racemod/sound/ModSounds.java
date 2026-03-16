package net.un2rws1.racemod.sound;

import net.un2rws1.racemod.Racemod;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;


public class ModSounds {
    public static final SoundEvent HAVA_NAGILA = registerSoundEvent("hava_nagila");
    public static final RegistryKey<JukeboxSong> HAVA_NAGILA_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(Racemod.MOD_ID, "hava_nagila"));




    private static SoundEvent registerSoundEvent(String name){
        Identifier id = Identifier.of(Racemod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    public static void registerSounds(){
        Racemod.LOGGER.info("Registering Mod Sounds for " + Racemod.MOD_ID);
    }
}
