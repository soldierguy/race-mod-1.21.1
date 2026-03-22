package net.un2rws1.racemod.mixin;

import java.util.UUID;

import net.minecraft.entity.mob.PiglinEntity;
import net.un2rws1.racemod.access.PiglinTraderAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PiglinEntity.class)
public class PiglinEntityMixin implements PiglinTraderAccess {

    @Unique
    private UUID racemod$lastTrader;

    @Override
    public void racemod$setLastTrader(@Nullable UUID uuid) {
        this.racemod$lastTrader = uuid;
    }

    @Override
    public @Nullable UUID racemod$getLastTrader() {
        return this.racemod$lastTrader;
    }
}