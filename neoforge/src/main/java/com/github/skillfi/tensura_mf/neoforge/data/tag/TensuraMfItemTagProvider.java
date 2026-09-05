package com.github.skillfi.tensura_mf.neoforge.data.tag;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.data.TensuraMfItemTags;
import io.github.manasmods.tensura.registry.block.TensuraBlocks;
import io.github.manasmods.tensura.registry.item.TensuraMaterialItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class TensuraMfItemTagProvider extends ItemTagsProvider {
    public TensuraMfItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagsProvider.TagLookup<Block>> provider, ExistingFileHelper helper) {
        super(output, future, provider, TensuraMf.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        tag(TensuraMfItemTags.LOW_MAGISTEEL_ITEMS).add(TensuraBlocks.Items.LOW_MAGISTEEL_BLOCK.get(), TensuraMaterialItems.LOW_MAGISTEEL_BONE_GOLEM.get(), TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get(), TensuraMaterialItems.LOW_MAGISTEEL_NUGGET.get());
        tag(TensuraMfItemTags.HIGH_MAGISTEEL_ITEMS).add(TensuraBlocks.Items.HIGH_MAGISTEEL_BLOCK.get(), TensuraMaterialItems.HIGH_MAGISTEEL_BONE_GOLEM.get(), TensuraMaterialItems.HIGH_MAGISTEEL_INGOT.get(), TensuraMaterialItems.HIGH_MAGISTEEL_NUGGET.get());
        tag(TensuraMfItemTags.PURE_MAGISTEEL_ITEMS).add(TensuraBlocks.Items.PURE_MAGISTEEL_BLOCK.get(), TensuraMaterialItems.PURE_MAGISTEEL_BONE_GOLEM.get(), TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get(), TensuraMaterialItems.PURE_MAGISTEEL_NUGGET.get());

    }
}
