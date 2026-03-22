package net.un2rws1.racemod.mixin;

import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.un2rws1.racemod.access.PiglinTraderAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinEntity.class)
public abstract class PiglinInteractMixin {

    @Inject(method = "interactMob", at = @At("HEAD"))
    private void racemod$rememberTrader(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        PiglinEntity piglin = (PiglinEntity)(Object)this;
        if (player.getStackInHand(hand).isOf(Items.GOLD_INGOT)) {
            ((PiglinTraderAccess)piglin).racemod$setLastTrader(player.getUuid());
        }
    }
}