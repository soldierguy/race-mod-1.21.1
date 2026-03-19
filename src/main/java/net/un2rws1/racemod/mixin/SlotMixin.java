package net.un2rws1.racemod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.un2rws1.racemod.Racemod;
import net.un2rws1.racemod.classsystem.ClassManager;
import net.un2rws1.racemod.item.ModItems;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Shadow @Final public Inventory inventory;
    @Shadow private int index;
    @Shadow public abstract ItemStack getStack();

    @Unique
    private boolean racemod$isHelmetSlot(PlayerEntity player) {
        return inventory == player.getInventory() && index == 39;
    }

    @Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
    private void racemod$lockKnightHelmet(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
        if (!racemod$isHelmetSlot(player)) return;
        ItemStack stack = this.getStack();

        if (ClassManager.isJew(serverPlayer) && stack.isOf(ModItems.KIPPAH)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
    private void racemod$restrictHelmetInsertion(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Object self = this;
        if (!((Slot)(Object)self).inventory.toString().contains("player")) {
        }

        ItemStack current = this.getStack();
        if (current.isOf(ModItems.KIPPAH)) {
            cir.setReturnValue(false);
        }
    }
}