package net.un2rws1.racemod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.un2rws1.racemod.Racemod;
import net.un2rws1.racemod.classsystem.ClassManager;
import net.un2rws1.racemod.classsystem.PlayerClass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
        @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
        private void racemod$blockBlacksEnchanting(BlockState state, World world, BlockPos pos,
                                                    PlayerEntity player, BlockHitResult hit,
                                                    CallbackInfoReturnable<ActionResult> cir) {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
            if (world.isClient) return;

            PlayerClass playerClass = ClassManager.getPlayerClass(player);
            if (playerClass == PlayerClass.BLACK) {
                player.sendMessage(Text.literal("You're Black, you don't know how this stuff works. Stick to robbing people"), true);
                cir.setReturnValue(ActionResult.FAIL);
            }
        }
    }