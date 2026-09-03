package com.github.skillfi.tensura_mf.registry.block;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.block.MagicIncubatorBlock;
import com.github.skillfi.tensura_mf.block.MagicEngineGeneratorBlock;
import com.github.skillfi.tensura_mf.block.PipeBlock;
import com.github.skillfi.tensura_mf.data.annotations.Language;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura.item.misc.SimpleBlockItem;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import io.github.manasmods.tensura.storage.chunk.ChunkStorage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfBlocks {
    private static final DeferredRegister<Block> BLOCKS;

    public static final RegistrySupplier<Block> MAGICULE_INCUBATOR;
    public static final RegistrySupplier<Block> PIPE;
    @Language.English("Bricks Magic Engine") @Language.Ukrainian("Цегляний магічний двигун")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> BRICKS_MAGIC_ENGINE;
    @Language.English("Stone Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з кам'яної цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> STONE_BRICKS_MAGIC_ENGINE;
    @Language.English("Tuff Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з туфової цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> TUFF_BRICKS_MAGIC_ENGINE;
    @Language.English("Deepslate Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з цегли глибосланцю")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> DEEPSLATE_BRICKS_MAGIC_ENGINE;
    @Language.English("Mud Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з багнистої цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> MUD_BRICKS_MAGIC_ENGINE;
    @Language.English("Prismarine Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з призмаринової цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> PRISMARINE_BRICK_MAGIC_ENGINE;
    @Language.English("Nether Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з незерської цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE;
    @Language.English("Red Nether Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з червоної незерської цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> RED_NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE;
    @Language.English("Polished Blackstone Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з цегли полірованого чорнокаменю")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> POLISHED_BLACKSTONE_BRICKS_MAGIC_ENGINE;
    @Language.English("Quartz Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з кварцової цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> QUARTZ_BRICKS_MAGIC_ENGINE;
    @Language.English("End Stone Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з ендернякової цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> END_STONE_BRICKS_MAGIC_ENGINE;
    @Language.English("Purpur Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з пурпурової цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> PURPUR_BRICKS_MAGIC_ENGINE;
    @Language.English("Low-Quality Magic Crystal Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з низькоякісної магічної кристалічної цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE;
    @Language.English("Medium-Quality Magic Crystal Bricks Magic Engine") @Language.Ukrainian("Магічний двигун із середньоякісної магічної кристалічної цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE;
    @Language.English("High-Quality Magic Crystal Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з високоякісної магічної кристалічної цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE;
    @Language.English("Labyrinth Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з лабіринтової цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> LABYRINTH_BRICKS_MAGIC_ENGINE;
    @Language.English("Cream Labyrinth Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з кремової лабіринтової цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> CREAM_LABYRINTH_BRICKS_MAGIC_ENGINE;
    @Language.English("Dark Labyrinth Bricks Magic Engine") @Language.Ukrainian("Магічний двигун з темної лабіринтової цегли")
    public static final RegistrySupplier<MagicEngineGeneratorBlock> DARK_LABYRINTH_BRICKS_MAGIC_ENGINE;

    public static void init(){
        BLOCKS.register();
        Items.ITEMS.register();
    }

    static {
        BLOCKS = DeferredRegister.create(TensuraMf.MOD_ID, Registries.BLOCK);
        MAGICULE_INCUBATOR = registerBlock("magicule_incubator", ()->new MagicIncubatorBlock(BlockBehaviour.Properties.of().noOcclusion()));
        PIPE = registerBlock("pipe", PipeBlock::new);
        BRICKS_MAGIC_ENGINE = registerBlock("bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        STONE_BRICKS_MAGIC_ENGINE = registerBlock("stone_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        TUFF_BRICKS_MAGIC_ENGINE = registerBlock("tuff_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        DEEPSLATE_BRICKS_MAGIC_ENGINE = registerBlock("deepslate_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        MUD_BRICKS_MAGIC_ENGINE = registerBlock("mud_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        PRISMARINE_BRICK_MAGIC_ENGINE = registerBlock("prismarine_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE = BLOCKS.register("nether_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        RED_NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE = BLOCKS.register("red_nether_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_NETHER_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        POLISHED_BLACKSTONE_BRICKS_MAGIC_ENGINE = BLOCKS.register("polished_blackstone_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        QUARTZ_BRICKS_MAGIC_ENGINE = BLOCKS.register("quartz_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        END_STONE_BRICKS_MAGIC_ENGINE = BLOCKS.register("end_stone_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        PURPUR_BRICKS_MAGIC_ENGINE = BLOCKS.register("purpur_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPUR_BLOCK).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE = BLOCKS.register("low_quality_magic_crystal_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.5F).sound(SoundType.AMETHYST).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE = BLOCKS.register("medium_quality_magic_crystal_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get()).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE = BLOCKS.register("high_quality_magic_crystal_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(BlockBehaviour.Properties.ofFullCopy(LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get()).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        LABYRINTH_BRICKS_MAGIC_ENGINE = BLOCKS.register("labyrinth_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(ChunkStorage.CONFIG.labyrinthMagicEngineReduction, ChunkStorage.CONFIG.labyrinthMagicEngineRange, true, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).strength(-1.0F, 3600000.0F).noLootTable().lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        CREAM_LABYRINTH_BRICKS_MAGIC_ENGINE = BLOCKS.register("cream_labyrinth_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(ChunkStorage.CONFIG.labyrinthMagicEngineReduction, ChunkStorage.CONFIG.labyrinthMagicEngineRange, true, BlockBehaviour.Properties.ofFullCopy(LABYRINTH_BRICKS_MAGIC_ENGINE.get()).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
        DARK_LABYRINTH_BRICKS_MAGIC_ENGINE = BLOCKS.register("dark_labyrinth_bricks_magic_engine", () -> new MagicEngineGeneratorBlock(ChunkStorage.CONFIG.labyrinthMagicEngineReduction, ChunkStorage.CONFIG.labyrinthMagicEngineRange, true, BlockBehaviour.Properties.ofFullCopy(LABYRINTH_BRICKS_MAGIC_ENGINE.get()).lightLevel(MagicEngineGeneratorBlock.getLightEmission())));
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String id, Supplier<T> block) {
        return BLOCKS.register(id, block);
    }

    @NoArgsConstructor
    public static class Items {
        private static final DeferredRegister<Item> ITEMS;
        public static final RegistrySupplier<Item> MAGICULE_INCUBATOR;
        public static final RegistrySupplier<Item> PIPE;
        public static final RegistrySupplier<Item> BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> STONE_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> TUFF_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> DEEPSLATE_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> MUD_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> PRISMARINE_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> RED_NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> POLISHED_BLACKSTONE_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> QUARTZ_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> END_STONE_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> PURPUR_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> LABYRINTH_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> CREAM_LABYRINTH_BRICKS_MAGIC_ENGINE;
        public static final RegistrySupplier<Item> DARK_LABYRINTH_BRICKS_MAGIC_ENGINE;
        static {
            ITEMS = DeferredRegister.create(TensuraMf.MOD_ID, Registries.ITEM);
            MAGICULE_INCUBATOR = fireResistedBlockItem(TensuraMfBlocks.MAGICULE_INCUBATOR, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            PIPE = fireResistedBlockItem(TensuraMfBlocks.PIPE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            STONE_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.STONE_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            TUFF_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.TUFF_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            DEEPSLATE_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.DEEPSLATE_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            MUD_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.MUD_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            PRISMARINE_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.PRISMARINE_BRICK_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            RED_NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.RED_NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            POLISHED_BLACKSTONE_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.POLISHED_BLACKSTONE_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            QUARTZ_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.QUARTZ_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            END_STONE_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.END_STONE_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            PURPUR_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.PURPUR_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE = simpleBlockItem(TensuraMfBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            LABYRINTH_BRICKS_MAGIC_ENGINE = fireResistedBlockItem(TensuraMfBlocks.LABYRINTH_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            CREAM_LABYRINTH_BRICKS_MAGIC_ENGINE = fireResistedBlockItem(TensuraMfBlocks.CREAM_LABYRINTH_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            DARK_LABYRINTH_BRICKS_MAGIC_ENGINE = fireResistedBlockItem(TensuraMfBlocks.DARK_LABYRINTH_BRICKS_MAGIC_ENGINE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
        }

        public static <T extends Block> RegistrySupplier<Item> fireResistedBlockItem(RegistrySupplier<T> block, DeferredSupplier<CreativeModeTab> tab) {
            return ITEMS.register(block.getId().getPath(), () -> new SimpleBlockItem(block.get(), (new Item.Properties()).arch$tab(tab).fireResistant()));
        }

        public static <T extends Block> RegistrySupplier<Item> simpleBlockItem(RegistrySupplier<T> block, DeferredSupplier<CreativeModeTab> tab) {
            return ITEMS.register(block.getId().getPath(), () -> new SimpleBlockItem(block.get(), tab));
        }
    }
}
