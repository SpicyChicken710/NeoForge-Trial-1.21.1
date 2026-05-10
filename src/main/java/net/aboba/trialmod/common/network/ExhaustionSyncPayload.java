package net.aboba.trialmod.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ExhaustionSyncPayload(float exhaustion) implements CustomPacketPayload {
    public static final Type<ExhaustionSyncPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("abobasbesttrialmod", "exhaustion_sync"));

    public static final StreamCodec<FriendlyByteBuf, ExhaustionSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, ExhaustionSyncPayload::exhaustion,
            ExhaustionSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}