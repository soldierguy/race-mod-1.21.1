package net.un2rws1.racemod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.un2rws1.racemod.util.Green_Card_Helper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {

    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    private void racemod$blockMexicanNetherPortal(BlockState state, World world, BlockPos pos,
                                                  Entity entity, CallbackInfo ci) {
        if (!(entity instanceof PlayerEntity player)) {
            return;
        }

        if (world.isClient) {
            return;
        }

        if (Green_Card_Helper.mexicanNeedsTicket(player)) {
            Green_Card_Helper.sendNoTicketMessage(player, "enter the Nether");
            player.playSound(SoundEvents.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            ci.cancel();
        }
    }
}