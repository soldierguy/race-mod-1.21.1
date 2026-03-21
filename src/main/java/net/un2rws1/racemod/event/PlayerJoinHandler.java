package net.un2rws1.racemod.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.un2rws1.racemod.classsystem.ClassManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.un2rws1.racemod.classsystem.ClassState;
import net.un2rws1.racemod.networking.SyncClassPayload;

public final class PlayerJoinHandler {
    private PlayerJoinHandler() {
    }

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            server.execute(() -> {
                System.out.println("[RaceMod] Player joined: " + handler.player.getName().getString());

                ClassManager.refreshCurrentClassEffects(handler.player);

                if (!ClassManager.hasClass(handler.player)) {
                    System.out.println("[RaceMod] Player has no race, opening GUI");
                    ClassManager.openSelection(handler.player);
                } else {
                    System.out.println("[RaceMod] Player already has a race");
                }
            });
            //coin slot eye
            ServerPlayerEntity player = handler.player;

            ClassState state = ClassManager.getState(player);
            String classId = state.getSelectedClassId();

            if (classId != null) {
                ServerPlayNetworking.send(player, new SyncClassPayload(classId));
                System.out.println("[RaceMod] Synced class on join: " + classId);
            }

        });
    }
}