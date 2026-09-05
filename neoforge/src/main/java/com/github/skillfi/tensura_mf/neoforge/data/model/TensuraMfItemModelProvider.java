package com.github.skillfi.tensura_mf.neoforge.data.model;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocks;
import com.github.skillfi.tensura_mf.registry.item.TensuraMfSpawnEggs;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TensuraMfItemModelProvider extends ItemModelProvider {
    public TensuraMfItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TensuraMf.MOD_ID, existingFileHelper);
    }

    protected void registerModels() {
        generateSpawnEggs();
        this.getBuilder("magic_engine")
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        ResourceLocation.fromNamespaceAndPath("tensura", "block/magic_engine")));
        withExistingParent("magisteel_glass", modLoc("block/magisteel_glass"));
        this.cubeBlockItemTensura(TensuraMfBlocks.BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.STONE_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.TUFF_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.DEEPSLATE_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.MUD_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.PRISMARINE_BRICK_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.RED_NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.POLISHED_BLACKSTONE_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.QUARTZ_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.END_STONE_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.PURPUR_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.LABYRINTH_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.CREAM_LABYRINTH_BRICKS_MAGIC_ENGINE);
        this.cubeBlockItemTensura(TensuraMfBlocks.DARK_LABYRINTH_BRICKS_MAGIC_ENGINE);
    }

    public void cubeBlockItemTensura(RegistrySupplier<? extends Block> block) {
        String var10001 = "tensura_mf:" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
        String var10003 = BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
        this.withExistingParent(var10001, modLoc("block/" + var10003));
    }

    private void generateSpawnEggs() {
        this.spawnEggItem( TensuraMfSpawnEggs.OGRE.get());
        this.spawnEggItem( TensuraMfSpawnEggs.KIJIN.get());
        this.spawnEggItem( TensuraMfSpawnEggs.MYSTIC_ONI.get());
        this.spawnEggItem( TensuraMfSpawnEggs.WICKED_ONI.get());
        this.spawnEggItem( TensuraMfSpawnEggs.SPIRIT_ONI.get());
        this.spawnEggItem( TensuraMfSpawnEggs.DEATH_ONI.get());
        this.spawnEggItem( TensuraMfSpawnEggs.DIVINE_ONI.get());
        this.spawnEggItem( TensuraMfSpawnEggs.DIVINE_FIGHTER.get());
    }

    private ItemModelBuilder incubatorModel(RegistrySupplier<? extends Item> item, ResourceLocation parent, ResourceLocation bottom, ResourceLocation glass,ResourceLocation top, ResourceLocation particle) {
        return ((((this.withExistingParent(item.getId().getPath(), parent))
                .texture("0", bottom))
                .texture("1", top))
                .texture("2", glass))
                .texture("particle", particle);
    }
}
