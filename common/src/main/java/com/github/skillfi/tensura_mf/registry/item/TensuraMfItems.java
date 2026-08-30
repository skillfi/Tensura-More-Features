package com.github.skillfi.tensura_mf.registry.item;

import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import java.util.function.Supplier;

public final class TensuraMfItems {
    public static final Supplier<BlockItem> MAGICULE_INCUBATOR = TensuraMfPlatform.registerItem(
            "magicule_incubator", () -> new BlockItem(TensuraMfBlocks.MAGICULE_INCUBATOR_BLOCK.get(),
                    new Item.Properties()));
    public static final Supplier<BlockItem> PIPE = TensuraMfPlatform.registerItem(
            "pipe", () -> new BlockItem(TensuraMfBlocks.PIPE.get(), new Item.Properties()));
    private TensuraMfItems() {}
    public static void init() {}
}
