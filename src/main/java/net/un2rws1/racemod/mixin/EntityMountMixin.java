package net.un2rws1.racemod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.un2rws1.racemod.classsystem.ClassManager;
import net.un2rws1.racemod.classsystem.PlayerClass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMountMixin {

    @Inject(method = "startRiding(Lnet/minecraft/entity/Entity;Z)Z", at = @At("HEAD"), cancellable = true)
    private void racemod$blockChineseMount(Entity vehicle, boolean force, CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity) (Object) this;

        if (!(self instanceof PlayerEntity player)) {
            return;
        }

        PlayerClass playerClass = ClassManager.getPlayerClass(player);
        if (playerClass != PlayerClass.CHINESE) {
            return;
        }

        if (isBlockedMount(vehicle)) {
            if (!player.getWorld().isClient) {
                player.sendMessage(Text.literal("You're Chinese, you can't drive"), true);
            }
            cir.setReturnValue(false);
        }
    }
    private boolean isBlockedMount(Entity entity) {
        return entity instanceof AbstractHorseEntity
                || entity instanceof CamelEntity
                || entity instanceof PigEntity
                || entity instanceof StriderEntity
                || entity instanceof GhastEntity;
    }
}