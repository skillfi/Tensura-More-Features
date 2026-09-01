package com.github.skillfi.tensura_mf.neoforge.data.model;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.block.MagiculeIncubatorBlock;
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

    private void addIncubatorState(VariantBlockStateBuilder builder, Direction facing, boolean lit, IncubatorPart part, ModelFile model, int rotY) {
        builder.partialState().with(MagiculeIncubatorBlock.FACING, facing)
                .with(MagiculeIncubatorBlock.LIT, lit)
                .with(MagiculeIncubatorBlock.PART, part)
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
