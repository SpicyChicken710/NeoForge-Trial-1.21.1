package net.aboba.trialmod.common.world;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = "abobasbesttrialmod", bus = EventBusSubscriber.Bus.GAME)
public class TimeHandler {

    // 1.0 is vanilla (20 mins). 0.166 is 2 hours.
    private static final double TIME_MULTIPLIER = 0.1666666666666667;
    private static double accumulatedTime = 0.0;

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();

        // Only manage time on the server in the Overworld
        if (level.isClientSide() || level.dimension() != Level.OVERWORLD) return;

        ServerLevel serverLevel = (ServerLevel) level;

        // Force the vanilla cycle OFF so it doesn't fight us
        if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            serverLevel.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(false, serverLevel.getServer());
        }

        accumulatedTime += TIME_MULTIPLIER;

        // When we accumulate a full 1.0, we move the world time by 1 tick
        if (accumulatedTime >= 1.0) {
            long currentDayTime = serverLevel.getDayTime();
            serverLevel.setDayTime(currentDayTime + 1);
            accumulatedTime -= 1.0;
        }

        // Note: Because we aren't "pulling back" the time anymore,
        // the client will see a perfectly smooth (though slower) sun.
    }
}
