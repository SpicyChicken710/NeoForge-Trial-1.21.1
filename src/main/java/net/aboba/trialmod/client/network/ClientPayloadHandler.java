package net.aboba.trialmod.client.network;

import net.aboba.trialmod.common.network.ExhaustionSyncPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleExhaustionSync(final ExhaustionSyncPayload payload, final IPayloadContext context) {
        // EnqueueWork ensures this runs on the Main Game Thread,
        // which is required for touching player data safely!
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.getFoodData().setExhaustion(payload.exhaustion());
            }
        });
    }
}
