package com.github.skillfi.tensura_mf.neoforge;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.client.TensuraMfClient;
import com.github.skillfi.tensura_mf.neoforge.data.EnglishProviderImpl;
import com.github.skillfi.tensura_mf.neoforge.data.loot.TensuraMfEntityLootProvider;
import com.github.skillfi.tensura_mf.neoforge.data.loot.TensuraMfLootProvider;
import com.github.skillfi.tensura_mf.neoforge.data.model.TensuraMfItemModelProvider;
import com.github.skillfi.tensura_mf.neoforge.data.tag.TensuraMfEntityTypeTagProvider;
import dev.architectury.platform.Platform;
import io.github.manasmods.tensura.neoforge.data.TensuraRegistryProvider;
import io.github.manasmods.tensura.neoforge.data.loot.TensuraLootProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.concurrent.CompletableFuture;

@Mod(TensuraMf.MOD_ID)
public final class TensuraMfNeoForge {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, TensuraMf.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, TensuraMf.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TensuraMf.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, TensuraMf.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TensuraMf.MOD_ID);
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, TensuraMf.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, TensuraMf.MOD_ID);

    public TensuraMfNeoForge(IEventBus modEventBus) {
        SOUND_EVENTS.register(modEventBus);
        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        ENTITIES.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
        ARMOR_MATERIALS.register(modEventBus);
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::gatherData);
        TensuraMf.init();
        if (Platform.getEnv() == Dist.CLIENT) {
            TensuraMfClient.init();
        }
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new TensuraMfItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new EnglishProviderImpl(output));
        generator.addProvider(event.includeServer(), new TensuraMfEntityTypeTagProvider(output, lookupProvider, helper));
        DatapackBuiltinEntriesProvider registryProvider = new TensuraRegistryProvider(output, lookupProvider);
        CompletableFuture<HolderLookup.Provider> lookup = registryProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), new TensuraMfLootProvider(output, lookup));
    }
}
