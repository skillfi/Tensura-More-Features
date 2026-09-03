package com.github.skillfi.tensura_mf.neoforge.data.model;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.block.MagicEngineGeneratorBlock;
import com.github.skillfi.tensura_mf.block.MagicIncubatorBlock;
import com.github.skillfi.tensura_mf.block.PipeBlock;
import com.github.skillfi.tensura_mf.block.part.IncubatorPart;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TensuraMfBlockStateProvider extends BlockStateProvider {
    public TensuraMfBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TensuraMf.MOD_ID, exFileHelper);
    }

    protected void registerStatesAndModels() {
        magiculeIncubator(TensuraMfBlocks.MAGICULE_INCUBATOR.get(), TensuraMf.create("block/magicule_incubator_bottom"));
        registerPipe();
        this.magicEngine(TensuraMfBlocks.BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/bricks"));
        this.magicEngine(TensuraMfBlocks.STONE_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/stone_bricks"));
        this.magicEngine(TensuraMfBlocks.TUFF_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/tuff_bricks"));
        this.magicEngine(TensuraMfBlocks.DEEPSLATE_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/deepslate_bricks"));
        this.magicEngine(TensuraMfBlocks.MUD_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/mud_bricks"));
        this.magicEngine(TensuraMfBlocks.PRISMARINE_BRICK_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/prismarine_bricks"));
        this.magicEngine(TensuraMfBlocks.NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/nether_bricks"));
        this.magicEngine(TensuraMfBlocks.RED_NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/red_nether_bricks"));
        this.magicEngine(TensuraMfBlocks.POLISHED_BLACKSTONE_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/polished_blackstone_bricks"));
        this.magicEngine(TensuraMfBlocks.QUARTZ_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/quartz_bricks"));
        this.magicEngine(TensuraMfBlocks.END_STONE_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/end_stone_bricks"));
        this.magicEngine(TensuraMfBlocks.PURPUR_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.withDefaultNamespace("block/purpur_block"));
        this.magicEngine(TensuraMfBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.fromNamespaceAndPath("tensura","block/low_quality_magic_crystal_bricks"));
        this.magicEngine(TensuraMfBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.fromNamespaceAndPath("tensura","block/medium_quality_magic_crystal_bricks"));
        this.magicEngine(TensuraMfBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.fromNamespaceAndPath("tensura","block/high_quality_magic_crystal_bricks"));
        this.magicEngine(TensuraMfBlocks.LABYRINTH_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.fromNamespaceAndPath("tensura","block/labyrinth_bricks"));
        this.magicEngine(TensuraMfBlocks.CREAM_LABYRINTH_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.fromNamespaceAndPath("tensura","block/cream_labyrinth_bricks"));
        this.magicEngine(TensuraMfBlocks.DARK_LABYRINTH_BRICKS_MAGIC_ENGINE.get(), ResourceLocation.fromNamespaceAndPath("tensura","block/dark_labyrinth_bricks"));
    }

    protected void magiculeIncubator(Block block, ResourceLocation particleTexture) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile bottom = this.models().getExistingFile(this.modLoc("block/" + name + "_bottom"));
        ModelFile bottomLit = ((this.models().withExistingParent(name + "_bottom_lit", this.modLoc("block/"+name+"_bottom"))).texture("0", this.modLoc("block/" + name + "_bottom_lit"))).texture("particle", particleTexture);
        ModelFile glass = ((this.models().withExistingParent(name + "_glass_lit", this.modLoc("block/"+name+"_glass"))).texture("0", this.modLoc("block/" + name + "_glass_lit"))).texture("particle", particleTexture);
        ModelFile glsssLit = ((this.models().withExistingParent(name + "_glass_lit", this.modLoc("block/"+name+"_glass"))).texture("0", this.modLoc("block/" + name + "_glass_lit"))).texture("particle", particleTexture);
        ModelFile top = this.models().getExistingFile(this.modLoc("block/" + name + "_top"));
        ModelFile topLit = ((this.models().withExistingParent(name + "_top_lit", this.modLoc("block/"+name+"_top"))).texture("0", this.modLoc("block/" + name + "_top_lit"))).texture("particle", particleTexture);
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);

        for(Direction facing : Direction.Plane.HORIZONTAL) {
            short var10000;
            switch (facing) {
                case NORTH -> var10000 = 0;
                case EAST -> var10000 = 90;
                case SOUTH -> var10000 = 180;
                case WEST -> var10000 = 270;
                default -> var10000 = 0;
            }

            int rotY = var10000;
            this.addIncubatorState(builder, facing, false, IncubatorPart.BASE, bottom, rotY);
            this.addIncubatorState(builder, facing, true, IncubatorPart.BASE, bottomLit, rotY);
            this.addIncubatorState(builder, facing, false, IncubatorPart.GLASS, glass, rotY);
            this.addIncubatorState(builder, facing, true, IncubatorPart.GLASS, glsssLit, rotY);
            this.addIncubatorState(builder, facing, false, IncubatorPart.TOP, top, rotY);
            this.addIncubatorState(builder, facing, true, IncubatorPart.TOP, topLit, rotY);
        }

    }

    protected void magicEngine(Block block, ResourceLocation baseTexture) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile inactive = ((this.models().withExistingParent(name, ResourceLocation.fromNamespaceAndPath("tensura","block/magic_engine"))).texture("1", baseTexture)).texture("particle", baseTexture);
        ModelFile active = (((this.models().withExistingParent(name + "_active", ResourceLocation.fromNamespaceAndPath("tensura","block/magic_engine"))).texture("1", baseTexture)).texture("2", ResourceLocation.fromNamespaceAndPath("tensura","block/magic_engine_active"))).texture("particle", baseTexture);
        VariantBlockStateBuilder stateBuilder = this.getVariantBuilder(block);

        for(boolean enabled : new boolean[]{false, true}) {
            ModelFile file = enabled ? active : inactive;

            for(Direction facing : Direction.values()) {
                int x = rotXForFacing(facing);
                int y = rotYForFacing(facing);
                ConfiguredModel.Builder<VariantBlockStateBuilder> builder = stateBuilder.
                        partialState().
                        with(MagicEngineGeneratorBlock.FACING, facing).
                        with(MagicEngineGeneratorBlock.ENABLED, enabled).
                        modelForState().
                        modelFile(file);
                if (x != 0) {
                    builder = builder.rotationX(x);
                }

                if (y != 0) {
                    builder = builder.rotationY(y);
                }

                builder.addModel();
            }
        }

        this.simpleBlockItem(block, inactive);
    }

    private static int rotXForFacing(Direction direction) {
        short var10000;
        switch (direction) {
            case UP -> var10000 = 0;
            case DOWN -> var10000 = 180;
            default -> var10000 = 90;
        }

        return var10000;
    }

    private static int rotYForFacing(Direction direction) {
        short var10000;
        switch (direction) {
            case EAST -> var10000 = 90;
            case SOUTH -> var10000 = 180;
            case WEST -> var10000 = 270;
            default -> var10000 = 0;
        }

        return var10000;
    }

    private void addIncubatorState(VariantBlockStateBuilder builder, Direction facing, boolean lit, IncubatorPart part, ModelFile model, int rotY) {
        builder.partialState().with(MagicIncubatorBlock.FACING, facing)
                .with(MagicIncubatorBlock.LIT, lit)
                .with(MagicIncubatorBlock.PART, part)
                .modelForState()
                .modelFile(model)
                .rotationY(rotY)
                .addModel();
    }

    private void registerPipe() {
        Block pipe = TensuraMfBlocks.PIPE.get();
        ModelFile core = models().getExistingFile(modLoc("block/pipe_core"));
        ModelFile north = models().getExistingFile(modLoc("block/pipe_arm_north"));
        ModelFile south = models().getExistingFile(modLoc("block/pipe_arm_south"));
        ModelFile east = models().getExistingFile(modLoc("block/pipe_arm_east"));
        ModelFile west = models().getExistingFile(modLoc("block/pipe_arm_west"));
        ModelFile up = models().getExistingFile(modLoc("block/pipe_arm_up"));
        ModelFile down = models().getExistingFile(modLoc("block/pipe_arm_down"));

        getMultipartBuilder(pipe)
                .part().modelFile(core).addModel().end()
                .part().modelFile(north).addModel().condition(PipeBlock.NORTH, true).end()
                .part().modelFile(south).addModel().condition(PipeBlock.SOUTH, true).end()
                .part().modelFile(east).addModel().condition(PipeBlock.EAST, true).end()
                .part().modelFile(west).addModel().condition(PipeBlock.WEST, true).end()
                .part().modelFile(up).addModel().condition(PipeBlock.UP, true).end()
                .part().modelFile(down).addModel().condition(PipeBlock.DOWN, true).end();
    }
}
