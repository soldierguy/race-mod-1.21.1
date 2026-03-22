package net.un2rws1.racemod.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.un2rws1.racemod.classsystem.ClassManager;
import net.un2rws1.racemod.classsystem.PlayerClass;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends HostileEntity {

    protected EndermanEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void racemod$dontTargetBlacks(@Nullable LivingEntity target, CallbackInfo ci) {
        if (target instanceof PlayerEntity player) {
            PlayerClass playerClass = ClassManager.getPlayerClass(player);
            if (playerClass == PlayerClass.BLACK) {
                super.setTarget(null);
                ci.cancel();
            }
        }
    }
}