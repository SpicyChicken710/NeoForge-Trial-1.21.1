package net.aboba.trialmod.common.world;

import net.aboba.trialmod.common.network.ExhaustionSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.UUID;

@EventBusSubscriber(modid = "abobasbesttrialmod", bus = EventBusSubscriber.Bus.GAME)
public class HungerHandler {

    // Matches our 2-hour day (1/6th speed)
    private static final float HUNGER_MULTIPLIER = 0.1666667f;

    // We track the "last known" exhaustion to see how much it increased this tick
    private static final HashMap<UUID, Float> lastExhaustionMap = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // Only run logic on the server to keep the "true" hunger synced
        if (player.level().isClientSide) return;

        FoodData foodData = player.getFoodData();
        float currentExhaustion = foodData.getExhaustionLevel();
        UUID uuid = player.getUUID();

        if (lastExhaustionMap.containsKey(uuid)) {
            float prevExhaustion = lastExhaustionMap.get(uuid);

            // If exhaustion increased (player ran, jumped, or mined)
            if (currentExhaustion > prevExhaustion) {
                float increase = currentExhaustion - prevExhaustion;

                // Calculate how much we want to "give back" to the player
                // to make it run at our slower speed.
                float reduction = increase * (1.0f - HUNGER_MULTIPLIER);

                // Manually set the exhaustion back down
                foodData.setExhaustion(currentExhaustion - reduction);
                if (player instanceof ServerPlayer serverPlayer) {
                    // Send the synced value to the client
                    PacketDistributor.sendToPlayer(serverPlayer, new ExhaustionSyncPayload(foodData.getExhaustionLevel()));
                }
            }
        }

        // Update the map for the next tick
        lastExhaustionMap.put(uuid, foodData.getExhaustionLevel());
    }
}
