package net.un2rws1.racemod.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.un2rws1.racemod.Racemod;

public record StealAttemptPayload(java.util.UUID targetUuid) implements CustomPayload {

    public static final Id<StealAttemptPayload> ID =
            new Id<>(Identifier.of(Racemod.MOD_ID, "steal_attempt"));

    public static final PacketCodec<RegistryByteBuf, StealAttemptPayload> CODEC =
            PacketCodec.of(
                    (payload, buf) -> buf.writeUuid(payload.targetUuid()),
                    buf -> new StealAttemptPayload(buf.readUuid())
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}