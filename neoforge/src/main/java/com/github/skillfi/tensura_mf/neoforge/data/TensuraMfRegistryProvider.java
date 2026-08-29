package com.github.skillfi.tensura_mf.neoforge.data;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.data.existence.TensuraMfEntityExistenceData;
import com.github.skillfi.tensura_mf.neoforge.data.world.TensuraMfBiomeModifiers;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TensuraMfRegistryProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER;

    public TensuraMfRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(TensuraMf.MOD_ID));
    }

    static {
        BUILDER = (new RegistrySetBuilder())
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, TensuraMfBiomeModifiers::bootstrap)
                .add(TensuraCustomData.ENTITY_EXISTENCE, TensuraMfEntityExistenceData::bootstrap);
    }
}
