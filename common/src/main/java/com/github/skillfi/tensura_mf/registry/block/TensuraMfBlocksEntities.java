package com.github.skillfi.tensura_mf.registry.block;

import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.block.entity.MagiculeIncubatorBlockEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfBlocksEntities {

    public static final Supplier<BlockEntityType<MagiculeIncubatorBlockEntity>> MAGICULE_INCUBATOR;

    public static void init() {}


    static {
        MAGICULE_INCUBATOR = registerBlockEntity("magicule_incubator", () -> BlockEntityType.Builder.of(MagiculeIncubatorBlockEntity::new, TensuraMfBlocks.MAGICULE_INCUBATOR.get()).build(null));
    }

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String id, Supplier<BlockEntityType<T>> block) {
        return TensuraMfPlatform.registerBlockEntity(id, block);
    }
}
