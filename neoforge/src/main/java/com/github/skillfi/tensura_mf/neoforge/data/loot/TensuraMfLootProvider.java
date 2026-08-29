package com.github.skillfi.tensura_mf.neoforge.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TensuraMfLootProvider extends LootTableProvider{
    public TensuraMfLootProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, Set.of(), List.of(new LootTableProvider.SubProviderEntry(TensuraMfEntityLootProvider::new, LootContextParamSets.ENTITY)
                ), provider);
    }
}
