package net.aboba.trialmod.registration;

import net.aboba.trialmod.common.item.UniversalEngineItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder; // Switch to the NeoForge holder
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    // 1. Create the Register
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems("abobasbesttrialmod");

    // 2. Register items using explicit type-safe DeferredHolders
    public static final DeferredHolder<Item, UniversalEngineItem> FLINT_NODULE = ITEMS.register("flint_nodule",
            () -> new UniversalEngineItem(new Item.Properties(), "flint_nodule", 1.0f, 0.8f));

    public static final DeferredHolder<Item, UniversalEngineItem> FLINT_FLAKE = ITEMS.register("flint_flake",
            () -> new UniversalEngineItem(new Item.Properties(), "flint_flake", 0.2f, 0.1f));
}