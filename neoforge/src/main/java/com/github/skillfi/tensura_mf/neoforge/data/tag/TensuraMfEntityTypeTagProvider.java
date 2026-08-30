package com.github.skillfi.tensura_mf.neoforge.data.tag;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.data.TensuraMfBiomeTags;
import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TensuraMfEntityTypeTagProvider extends EntityTypeTagsProvider {
    public TensuraMfEntityTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, completableFuture, TensuraMf.MOD_ID, existingFileHelper);
    }

    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(TensuraEntityTags.MONSTER).add(
                MonsterEntityTypes.OGRE.get(), MonsterEntityTypes.KIJIN.get(),
                MonsterEntityTypes.MYSTIC_ONI.get(), MonsterEntityTypes.WICKED_ONI.get(),
                MonsterEntityTypes.SPIRIT_ONI.get(), MonsterEntityTypes.DEATH_ONI.get(),
                MonsterEntityTypes.DIVINE_ONI.get(), MonsterEntityTypes.DIVINE_FIGHTER.get());
    }
}
