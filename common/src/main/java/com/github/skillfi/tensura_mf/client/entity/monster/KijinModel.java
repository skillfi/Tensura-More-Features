package com.github.skillfi.tensura_mf.client.entity.monster;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;
import com.github.skillfi.tensura_mf.entity.variant.OgreVariant;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class KijinModel extends DefaultedEntityGeoModel<OgreEntity> {
    public KijinModel() { super(TensuraMf.create("kijin"), true); }
    @Override public ResourceLocation getTextureResource(OgreEntity entity) { return OgreVariant.Skin.getTextureLocation(entity); }
    @Override public @Nullable RenderType getRenderType(OgreEntity entity, ResourceLocation texture) { return RenderType.entityCutoutNoCull(texture, false); }
    @Override public void setCustomAnimations(OgreEntity entity, long id, AnimationState<OgreEntity> state) {
        GeoBone middle = getAnimationProcessor().getBone("MiddleHorn");
        GeoBone right = getAnimationProcessor().getBone("RightHorn");
        GeoBone left = getAnimationProcessor().getBone("LeftHorn");
        boolean one = entity.getHorns().getId() == 1;
        middle.setHidden(!one); right.setHidden(one); left.setHidden(one);
        if (!entity.isSleeping() && entity.isAlive()) super.setCustomAnimations(entity, id, state);
    }
}
