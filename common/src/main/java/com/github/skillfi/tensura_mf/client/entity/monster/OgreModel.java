package com.github.skillfi.tensura_mf.client.entity.monster;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;
import com.github.skillfi.tensura_mf.entity.variant.OgreVariant;
import io.github.manasmods.tensura.entity.monster.OrcEntity;
import io.github.manasmods.tensura.entity.variant.OrcVariant;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class OgreModel extends DefaultedEntityGeoModel<OgreEntity> {
    public OgreModel() {
        super(TensuraMf.create("ogre"), true);
    }

    public ResourceLocation getTextureResource(OgreEntity instance) {
        return OgreVariant.Skin.getTextureLocation(instance);
    }

    public @Nullable RenderType getRenderType(OgreEntity animatable, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(texture, false);
    }

    public void setCustomAnimations(OgreEntity ogre, long instanceId, AnimationState<OgreEntity> animationState) {
        boolean hasOneHorn = (ogre.getHorns().getId() == 1);
        boolean hasDualHorns = (ogre.getHorns().getId() == 2);
        GeoBone middleHorn = this.getAnimationProcessor().getBone("MiddleHorn");
        GeoBone rightHorn = this.getAnimationProcessor().getBone("RightHorn");
        GeoBone leftHorn = this.getAnimationProcessor().getBone("LeftHorn");
        if (hasOneHorn) {
            rightHorn.setHidden(true);
            leftHorn.setHidden(true);
            middleHorn.setHidden(false);
        }

        if (hasDualHorns){
            middleHorn.setHidden(true);
            rightHorn.setHidden(false);
            leftHorn.setHidden(false);
        }

        if (!ogre.isSleeping() && ogre.isAlive()) {
            super.setCustomAnimations(ogre, instanceId, animationState);
        }
    }
}
