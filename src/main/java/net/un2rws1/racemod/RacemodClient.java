package net.un2rws1.racemod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.un2rws1.racemod.classsystem.ClassAttachmentTypes;
import net.un2rws1.racemod.classsystem.PlayerClass;
import net.un2rws1.racemod.client.ClientClassState;
import net.un2rws1.racemod.client.screen.ClassSelectionScreen;
import net.un2rws1.racemod.entity.ModEntities;
import net.un2rws1.racemod.event.PlayerJoinHandler;
import net.un2rws1.racemod.networking.ModNetworking;
import net.un2rws1.racemod.networking.OpenClassSelectionPayload;
import net.un2rws1.racemod.networking.StealAttemptPayload;
import net.un2rws1.racemod.networking.SyncClassPayload;
import org.lwjgl.glfw.GLFW;


public class RacemodClient implements ClientModInitializer{
    private static KeyBinding stealKey; // ✅ declare here

    @Override
    public void onInitializeClient() {
            EntityRendererRegistry.register(ModEntities.POOP, FlyingItemEntityRenderer::new);
            ClassAttachmentTypes.init();
            ModNetworking.register();
            PlayerJoinHandler.register();
        stealKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.racemod.steal",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.racemod"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (stealKey.wasPressed()) {
                if (client.player == null || client.world == null) return;
                Entity hit = client.targetedEntity;
                if (!(hit instanceof PlayerEntity targetPlayer)) {
                    client.player.sendMessage(Text.literal("Look at a player to steal from."), true);
                    continue;
                }

                ClientPlayNetworking.send(new StealAttemptPayload(targetPlayer.getUuid()));
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(OpenClassSelectionPayload.ID, (payload, context) -> {
            System.out.println("[RaceMod] Received OpenClassSelectionPayload");
            context.client().execute(() -> {
                System.out.println("[RaceMod] Opening ClassSelectionScreen");
                context.client().setScreen(new ClassSelectionScreen());
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(SyncClassPayload.ID, (payload, context) -> {

            System.out.println("[RaceMod] RECEIVED: " + payload.classId());

            context.client().execute(() -> {
                PlayerClass parsed = PlayerClass.fromId(payload.classId());
                System.out.println("[RaceMod] PARSED: " + parsed);

                ClientClassState.setPlayerClass(parsed);
            });
        });
        System.out.println("[RaceMod] Client initializer loaded");
    }

}