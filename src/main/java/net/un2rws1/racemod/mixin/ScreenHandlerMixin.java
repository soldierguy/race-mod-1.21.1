package net.un2rws1.racemod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.un2rws1.racemod.classsystem.ClassManager;
import net.un2rws1.racemod.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void racemod$lockKnightHelmet(
            int slotIndex,
            int button,
            SlotActionType actionType,
            PlayerEntity player,
            CallbackInfo ci
    ) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
        if (!(((Object) this) instanceof PlayerScreenHandler)) return;

        if (slotIndex != 5) return;

        ItemStack headStack = player.getEquippedStack(net.minecraft.entity.EquipmentSlot.HEAD);
        boolean isKnight = ClassManager.isJew(serverPlayer);

        if (isKnight && headStack.isOf(ModItems.KIPPAH)) {
            ci.cancel();
            return;
        }

        if (!isKnight && headStack.isOf(ModItems.KIPPAH)) {
            ci.cancel();
        }
    }
}