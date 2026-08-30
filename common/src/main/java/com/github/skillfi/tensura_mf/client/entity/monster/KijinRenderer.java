package com.github.skillfi.tensura_mf.client.entity.monster;

import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class KijinRenderer extends OgreRenderer {
    public KijinRenderer(EntityRendererProvider.Context context) { super(context, new KijinModel()); }
}
