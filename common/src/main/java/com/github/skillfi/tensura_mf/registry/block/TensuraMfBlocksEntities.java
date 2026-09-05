package com.github.skillfi.tensura_mf.registry.block;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.block.entity.MagicIncubatorBlockEntity;
import com.github.skillfi.tensura_mf.block.entity.MagicEngineBlockEntity;
import com.github.skillfi.tensura_mf.block.entity.PipeBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfBlocksEntities {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY;
    public static final RegistrySupplier<BlockEntityType<MagicIncubatorBlockEntity>> MAGICAL_INCUBATOR;
    public static final RegistrySupplier<BlockEntityType<MagicEngineBlockEntity>> MAGIC_ENGINE;
    public static final RegistrySupplier<BlockEntityType<PipeBlockEntity>> PIPE;

    public static void init() {
        BLOCK_ENTITY.register();
    }


    static {
        BLOCK_ENTITY = DeferredRegister.create(TensuraMf.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
        MAGICAL_INCUBATOR = registerBlockEntity("magical_incubator", () -> BlockEntityType.Builder.of(MagicIncubatorBlockEntity::new,
                TensuraMfBlocks.MAGICAL_INCUBATOR.get(),
                TensuraMfBlocks.MAGICAL_INCUBATOR_MITHRIL.get(),
                TensuraMfBlocks.MAGICAL_INCUBATOR_ORICHALCUM.get(),
                TensuraMfBlocks.MAGICAL_INCUBATOR_HIHIIROKANE.get()).build(null));
        MAGIC_ENGINE = registerBlockEntity("magic_engine", () -> BlockEntityType.Builder.of(MagicEngineBlockEntity::new, new Block[]{
                TensuraMfBlocks.BRICKS_MAGIC_ENGINE.get(),
                TensuraMfBlocks.STONE_BRICKS_MAGIC_ENGINE.get(),
                TensuraMfBlocks.TUFF_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.DEEPSLATE_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.MUD_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.PRISMARINE_BRICK_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.RED_NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.POLISHED_BLACKSTONE_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.QUARTZ_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.END_STONE_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.PURPUR_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.LABYRINTH_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.CREAM_LABYRINTH_BRICKS_MAGIC_ENGINE.get(), 
                TensuraMfBlocks.DARK_LABYRINTH_BRICKS_MAGIC_ENGINE.get()}).build(null));
        PIPE = registerBlockEntity("pipe", () -> BlockEntityType.Builder.of(PipeBlockEntity::new, TensuraMfBlocks.PIPE.get()).build(null));
    }

    private static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> registerBlockEntity(String id, Supplier<BlockEntityType<T>> block) {
        return BLOCK_ENTITY.register(id, block);
    }
}
