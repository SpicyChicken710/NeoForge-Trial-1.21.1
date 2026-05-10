package net.aboba.trialmod.registration;

import net.aboba.trialmod.TrialMod;
import net.aboba.trialmod.common.item.UniversalEngineItem; // Import your new engine class
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ItemRegistry {
    // 1. Create the Register
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems("abobasbesttrialmod");

    // 2. Put your Flint Nodule here
    // Weight = 1.0f (Heavy), Inertia = 0.8f (Slow movement)
    public static final Supplier<Item> FLINT_NODULE = ITEMS.register("flint_nodule",
            () -> new UniversalEngineItem(new Item.Properties(), "flint_nodule",1.0f, 0.8f));
    public static final Supplier<Item> FLINT_FLAKE = ITEMS.register("flint_flake",
            () -> new UniversalEngineItem(new Item.Properties(), "flint_flake", 0.2f, 0.1f));
}