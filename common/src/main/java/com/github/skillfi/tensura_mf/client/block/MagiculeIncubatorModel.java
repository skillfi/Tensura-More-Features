package com.github.skillfi.tensura_mf.client.block;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.block.entity.MagiculeIncubatorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class MagiculeIncubatorModel extends DefaultedBlockGeoModel<MagiculeIncubatorBlockEntity> {
    /**
     * Create a new instance of this model class
     * <p>
     * The asset path should be the truncated relative path from the base folder
     * <p>
     * E.G.
     * <pre>{@code
     * 	new ResourceLocation("myMod", "workbench/sawmill")
     * }</pre>
     *
     * @param assetSubpath
     */
    public MagiculeIncubatorModel() {
        super(TensuraMf.create("magicule_incubator"));
    }
}
