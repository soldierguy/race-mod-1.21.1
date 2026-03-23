package net.un2rws1.racemod.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.un2rws1.racemod.Racemod;
import net.un2rws1.racemod.classsystem.ClassManager;
import net.un2rws1.racemod.classsystem.PlayerClass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class BedrockBreakMixin {

    @Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
    private void racemod$allowBlacksBreakBedrock(BlockState state,
                                                  PlayerEntity player,
                                                  BlockView world,
                                                  BlockPos pos,
                                                  CallbackInfoReturnable<Float> cir) {
        if (!state.isOf(Blocks.BEDROCK)) {
            return;
        }
        if (ClassManager.getPlayerClass(player) != PlayerClass.BLACK){
            return;
        }
        cir.setReturnValue(0.05f);
    }

    }
//}