package net.un2rws1.racemod.access;

import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public interface PiglinTraderAccess {
    void racemod$setLastTrader(@Nullable UUID uuid);
    @Nullable UUID racemod$getLastTrader();
}