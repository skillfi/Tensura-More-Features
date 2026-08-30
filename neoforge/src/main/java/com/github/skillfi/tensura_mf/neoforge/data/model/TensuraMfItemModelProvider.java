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
        this.spawnEggItem((Item) TensuraMfSpawnEggs.KIJIN.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.MYSTIC_ONI.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.WICKED_ONI.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.SPIRIT_ONI.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.DEATH_ONI.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.DIVINE_ONI.get());
        this.spawnEggItem((Item) TensuraMfSpawnEggs.DIVINE_FIGHTER.get());
    }
}
