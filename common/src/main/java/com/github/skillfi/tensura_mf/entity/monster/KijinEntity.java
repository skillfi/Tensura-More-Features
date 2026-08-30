package com.github.skillfi.tensura_mf.entity.monster;

import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** Kijin and the later Oni forms share Ogre behaviour and Kijin visuals. */
public class KijinEntity extends OgreEntity {
    public KijinEntity(EntityType<? extends OgreEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected String getAnimationPrefix() {
        return "kijin";
    }

    @Override
    public String getClassName() {
        return MonsterEntityTypes.KIJIN.toString();
    }

    @Override
    public ResourceLocation getResource() {
        return MonsterEntityTypes.KIJIN.get().arch$registryName();
    }
}
