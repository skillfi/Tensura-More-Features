package com.github.skillfi.tensura_mf.client.block;

import com.github.skillfi.tensura_mf.block.entity.MagiculeIncubatorBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MagiculeIncubatorRenderer extends GeoBlockRenderer<MagiculeIncubatorBlockEntity> {
    public MagiculeIncubatorRenderer(BlockEntityRendererProvider.Context context) {
        super(new MagiculeIncubatorModel());
    }
}
