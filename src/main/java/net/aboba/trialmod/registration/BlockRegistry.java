package net.aboba.trialmod.registration;

import net.aboba.trialmod.TrialMod;
import net.aboba.trialmod.common.block.LooseRockBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistry {
    // Create the Register for Blocks
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(TrialMod.MOD_ID);

    // Register the Loose Rock Block
    // We use "BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)" to give it stone sounds/properties
    public static final DeferredBlock<Block> LOOSE_ROCK = registerBlock("loose_rock",
            () -> new LooseRockBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                    .noOcclusion() // Tells the game it's not a full cube
                    .noCollission() // You can walk through it like grass
                    .instabreak())); // It's just a small rock, breaks instantly

    // Helper method to register the block AND its item version at the same time
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // Helper method to register the BlockItem (so it shows in your hand/inventory)
    private static <T extends Block> DeferredItem<Item> registerBlockItem(String name, DeferredBlock<T> block) {
        return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
