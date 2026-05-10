package net.aboba.trialmod.event;

import net.aboba.trialmod.TrialMod;
import net.aboba.trialmod.registration.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = TrialMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void onBlockClick(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        BlockState state = level.getBlockState(pos);

        // Check if the player is holding a Flint Nodule and hitting a hard block (Stone)
        if (stack.is(ItemRegistry.FLINT_NODULE.get()) && (state.is(Blocks.STONE) || state.is(Blocks.DEEPSLATE))) {

            if (!level.isClientSide) {
                // 1. Consume 1 Nodule
                stack.shrink(1);

                // 2. Drop 1-3 Flint Flakes
                int count = level.random.nextInt(3) + 1;
                Block.popResource(level, pos.above(), new ItemStack(ItemRegistry.FLINT_FLAKE.get(), count));

                // 3. Play a sharp "clinking" sound
                level.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.PLAYERS, 1.0f, 1.5f);
            }

            // Cancel the event so the player doesn't start mining the block behind it
            event.setCanceled(true);
        }
    }
}
