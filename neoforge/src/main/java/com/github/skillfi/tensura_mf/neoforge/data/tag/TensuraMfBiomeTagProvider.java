package com.github.skillfi.tensura_mf.neoforge.data.tag;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.data.TensuraMfBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TensuraMfBiomeTagProvider extends BiomeTagsProvider {
    public TensuraMfBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, completableFuture, TensuraMf.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.tag(TensuraMfBiomeTags.EntitySpawn.OGRE).addOptional(Tags.Biomes.IS_FOREST.location()).addTag(BiomeTags.IS_FOREST).addTag(BiomeTags.HAS_WOODLAND_MANSION);
    }
}
