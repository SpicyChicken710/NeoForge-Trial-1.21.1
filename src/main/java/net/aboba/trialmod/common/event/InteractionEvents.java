package net.aboba.trialmod.common.event;

import net.aboba.trialmod.registration.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = "abobasbesttrialmod")
public class InteractionEvents {
    @SubscribeEvent
    public static void onStoneKnapping(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack stack = event.getItemStack();

        // Check if the player is hitting a "Hard" block with a Flint Nodule
        if (stack.is(ItemRegistry.FLINT_NODULE.get()) && level.getBlockState(pos).is(Blocks.STONE)) {
            if (!level.isClientSide) {
                // Play a "Clink" sound
                level.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);

                // Spawn 1-3 Flakes
                int count = level.random.nextInt(3) + 1;
                Block.popResource(level, pos.above(), new ItemStack(ItemRegistry.FLINT_FLAKE.get(), count));

                // Shrink the Nodule (it broke!)
                stack.shrink(1);
            }
            event.setCanceled(true); // Stop the "Hand" animation from double-triggering
        }
    }
}