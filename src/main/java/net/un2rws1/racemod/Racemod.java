package net.un2rws1.racemod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.un2rws1.racemod.block.ModBlocks;
import net.un2rws1.racemod.classsystem.ClassAttachmentTypes;
import net.un2rws1.racemod.classsystem.ClassManager;
import net.un2rws1.racemod.command.ClassCommand;
import net.un2rws1.racemod.event.PlayerJoinHandler;
import net.un2rws1.racemod.item.ModItemGroups;
import net.un2rws1.racemod.item.ModItems;
import net.un2rws1.racemod.networking.ModNetworking;
import net.un2rws1.racemod.sound.ModSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.un2rws1.racemod.event.PlayerRespawnHandler;

public class Racemod implements ModInitializer {
	public static final String MOD_ID = "race-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModSounds.registerSounds();

		//Classes (races)
		ClassAttachmentTypes.init();
		ModNetworking.register();
		PlayerJoinHandler.register();
		PlayerRespawnHandler.register();

		//classes (races) buffs defuffs and whatnot
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				ClassManager.tickPlayer(player);
			}
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
				ClassCommand.register(dispatcher));
	}

}



