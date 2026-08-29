package com.github.skillfi.tensura_mf.neoforge.data.model;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.registry.item.TensuraMfSpawnEggs;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TensuraMfItemModelProvider extends ItemModelProvider {
    public TensuraMfItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TensuraMf.MOD_ID, existingFileHelper);
    }

    protected void registerModels() {
        generateSpawnEggs();
    }

    private void generateSpawnEggs() {
        this.spawnEggItem((Item) TensuraMfSpawnEggs.OGRE.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.PRIMORDIAL_WHITE.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.PRIMORDIAL_BLACK.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.PRIMORDIAL_ROUGE.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.PRIMORDIAL_VERT.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.PRIMORDIAL_JAUNE.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.PRIMORDIAL_VIOLET.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.PRIMORDIAL_BLEU.get());
    }
}
