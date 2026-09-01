package com.github.skillfi.tensura_mf.neoforge.data.model;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocks;
import com.github.skillfi.tensura_mf.registry.item.TensuraMfSpawnEggs;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class TensuraMfItemModelProvider extends ItemModelProvider {
    public TensuraMfItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TensuraMf.MOD_ID, existingFileHelper);
    }

    protected void registerModels() {
        generateSpawnEggs();
        withExistingParent("pipe", modLoc("block/pipe_core"));
        incubatorModel(TensuraMfBlocks.Items.MAGICULE_INCUBATOR,
                ResourceLocation.fromNamespaceAndPath(TensuraMf.MOD_ID, "item/magicule_incubator"),
                ResourceLocation.fromNamespaceAndPath(TensuraMf.MOD_ID, "block/magicule_incubator_bottom"),
                ResourceLocation.fromNamespaceAndPath(TensuraMf.MOD_ID, "block/magicule_incubator_top"),
                ResourceLocation.withDefaultNamespace("block/obsidian"));
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

    private ItemModelBuilder incubatorModel(RegistrySupplier<? extends Item> item, ResourceLocation parent, ResourceLocation bottom, ResourceLocation top, ResourceLocation particle) {
        return (((this.withExistingParent(item.getId().getPath(), parent))
                .texture("0", bottom))
                .texture("1", top))
                .texture("particle", particle);
    }
}
