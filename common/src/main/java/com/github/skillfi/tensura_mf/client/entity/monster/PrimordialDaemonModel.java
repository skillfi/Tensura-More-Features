package com.github.skillfi.tensura_mf.client.entity.monster;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.entity.monster.PrimordialDaemonEntity;
import com.github.skillfi.tensura_mf.entity.variant.PrimordialVariant;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

/** Shared GeckoLib model for both primordial daemon variants. */
public class PrimordialDaemonModel extends DefaultedEntityGeoModel<PrimordialDaemonEntity> {

    public PrimordialDaemonModel() {
        super(TensuraMf.create("primordial_daemon"), true);
    }

    public ResourceLocation getTextureResource(PrimordialDaemonEntity instance) {
        return PrimordialVariant.Skin.getTextureLocation(instance);
    }

    public @Nullable RenderType getRenderType(PrimordialDaemonEntity animatable, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(texture, false);
    }

    @Override
    public void setCustomAnimations(PrimordialDaemonEntity entity, long instanceId, AnimationState<PrimordialDaemonEntity> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);
    }
}
