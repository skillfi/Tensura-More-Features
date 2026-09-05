package com.github.skillfi.tensura_mf.neoforge.data.tag;

import com.github.skillfi.tensura_mf.TensuraMf;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TensuraMfBlockTagProvider extends BlockTagsProvider {
    public TensuraMfBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, completableFuture, TensuraMf.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {

    }
}
