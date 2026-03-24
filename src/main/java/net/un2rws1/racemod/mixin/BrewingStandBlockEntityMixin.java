package net.un2rws1.racemod.mixin;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.un2rws1.racemod.item.ModItems;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {

    @Inject(
            method = "canInsert(ILnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/Direction;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void racemod$canInsert(int slot, ItemStack stack, @Nullable Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 3 && stack.isOf(Items.MELON_SLICE)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "canCraft(Lnet/minecraft/recipe/BrewingRecipeRegistry;Lnet/minecraft/util/collection/DefaultedList;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void racemod$canCraft(BrewingRecipeRegistry brewingRecipeRegistry,
                                         DefaultedList<ItemStack> slots,
                                         CallbackInfoReturnable<Boolean> cir) {
        if (isMelonFoodRecipe(slots)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "craft(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/collection/DefaultedList;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void racemod$craft(World world,
                                      BlockPos pos,
                                      DefaultedList<ItemStack> slots,
                                      CallbackInfo ci) {
        if (!isMelonFoodRecipe(slots)) {
            return;
        }

        ItemStack ingredient = slots.get(3);

        for (int i = 0; i < 3; i++) {
            ItemStack bottle = slots.get(i);

            if (bottle.isOf(Items.POTION)) {
                slots.set(i, new ItemStack(ModItems.KOOL_AID));
            }
        }

        ingredient.decrement(1);

        if (ingredient.isEmpty()) {
            slots.set(3, ItemStack.EMPTY);
        }

        ci.cancel();
    }

    private static boolean isMelonFoodRecipe(DefaultedList<ItemStack> slots) {
        ItemStack ingredient = slots.get(3);
        if (!ingredient.isOf(Items.MELON_SLICE)) {
            return false;
        }

        boolean hasPotion = false;

        for (int i = 0; i < 3; i++) {
            ItemStack bottle = slots.get(i);

            if (!bottle.isEmpty()) {
                if (!bottle.isOf(Items.POTION)) {
                    return false;
                }
                hasPotion = true;
            }
        }

        return hasPotion;
    }
}