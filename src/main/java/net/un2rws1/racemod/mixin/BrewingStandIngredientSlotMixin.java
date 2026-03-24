package net.un2rws1.racemod.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$IngredientSlot")
public class BrewingStandIngredientSlotMixin {

    @Inject(
            method = "canInsert(Lnet/minecraft/item/ItemStack;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void racemod$allowMelon(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(Items.MELON_SLICE)) {
            cir.setReturnValue(true);
        }
    }
}