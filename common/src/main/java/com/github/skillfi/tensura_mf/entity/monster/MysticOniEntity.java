package com.github.skillfi.tensura_mf.entity.monster;

import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class MysticOniEntity extends KijinEntity {
    public MysticOniEntity(EntityType<? extends OgreEntity> type, Level level) { super(type, level); }
    @Override public String getClassName() { return MonsterEntityTypes.MYSTIC_ONI.toString(); }
    @Override public ResourceLocation getResource() { return MonsterEntityTypes.MYSTIC_ONI.get().arch$registryName(); }
}
