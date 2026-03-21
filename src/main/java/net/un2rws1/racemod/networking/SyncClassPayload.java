package net.un2rws1.racemod.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.un2rws1.racemod.Racemod;

public record SyncClassPayload(String classId) implements CustomPayload {

    public static final Id<SyncClassPayload> ID =
            new Id<>(Identifier.of(Racemod.MOD_ID, "sync_class"));

    public static final PacketCodec<RegistryByteBuf, SyncClassPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING,
                    SyncClassPayload::classId,
                    SyncClassPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}