package com.github.skillfi.tensura_mf.registry.block;

import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.block.MagiculeIncubatorBlock;
import com.github.skillfi.tensura_mf.block.PipeBlock;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfBlocks {

    public static final Supplier<Block> MAGICULE_INCUBATOR_BLOCK;
    public static final Supplier<Block> PIPE;

    public static void init(){}

    static {
        MAGICULE_INCUBATOR_BLOCK = registerBlock("magicule_incubator", MagiculeIncubatorBlock::new);
        PIPE = registerBlock("pipe", PipeBlock::new);
    }

    private static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        return TensuraMfPlatform.registerBlock(id, block);
    }
}
