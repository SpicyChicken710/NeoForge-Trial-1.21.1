package net.aboba.trialmod.registration;

import net.aboba.trialmod.common.network.ExhaustionSyncPayload;
import net.aboba.trialmod.client.network.ClientPayloadHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "abobasbesttrialmod", bus = EventBusSubscriber.Bus.MOD)
public class NetworkRegistration {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        // "1" is your network version. If you change packet data, change this to "2".
        final PayloadRegistrar registrar = event.registrar("1");

        // Tell the game: This packet goes FROM Server TO Client
        registrar.playToClient(
                ExhaustionSyncPayload.TYPE,
                ExhaustionSyncPayload.CODEC,
                ClientPayloadHandler::handleExhaustionSync
        );
    }
}
