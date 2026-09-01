package com.github.skillfi.tensura_mf.registry.block;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.block.MagiculeIncubatorBlock;
import com.github.skillfi.tensura_mf.block.PipeBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura.item.misc.SimpleBlockItem;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfBlocks {
    private static final DeferredRegister<Block> BLOCKS;

    public static final RegistrySupplier<Block> MAGICULE_INCUBATOR;
    public static final RegistrySupplier<Block> PIPE;

    public static void init(){
        BLOCKS.register();
        Items.ITEMS.register();
    }

    static {
        BLOCKS = DeferredRegister.create(TensuraMf.MOD_ID, Registries.BLOCK);
        MAGICULE_INCUBATOR = registerBlock("magicule_incubator", ()->new MagiculeIncubatorBlock(BlockBehaviour.Properties.of().noOcclusion()));
        PIPE = registerBlock("pipe", PipeBlock::new);
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String id, Supplier<T> block) {
        return BLOCKS.register(id, block);
    }

    public static class Items {
        private static final DeferredRegister<Item> ITEMS;
        public static final RegistrySupplier<Item> MAGICULE_INCUBATOR;
        public static final RegistrySupplier<Item> PIPE;
        static {
            ITEMS = DeferredRegister.create(TensuraMf.MOD_ID, Registries.ITEM);
            MAGICULE_INCUBATOR = fireResistedBlockItem(TensuraMfBlocks.MAGICULE_INCUBATOR, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
            PIPE = fireResistedBlockItem(TensuraMfBlocks.PIPE, TensuraCreativeTabs.FUNCTIONAL_BLOCKS);
        }

        public static <T extends Block> RegistrySupplier<Item> fireResistedBlockItem(RegistrySupplier<T> block, DeferredSupplier<CreativeModeTab> tab) {
            return ITEMS.register(block.getId().getPath(), () -> new SimpleBlockItem((Block)block.get(), (new Item.Properties()).arch$tab(tab).fireResistant()));
        }
    }
}
