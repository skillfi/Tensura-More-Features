package com.github.skillfi.tensura_mf.registry.entity;

import com.github.skillfi.tensura_mf.config.entity.SpawnRateConfig;
import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap.Types;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfEntityTypes {
    public static final SpawnRateConfig CONFIG = (SpawnRateConfig) ConfigRegistry.getConfig(SpawnRateConfig.class);

    public static void init() {
        MonsterEntityTypes.init();
        createAttributes();
        spawnPlacements();
    }

    public static void createAttributes() {
        EntityAttributeRegistry.register(MonsterEntityTypes.OGRE, OgreEntity::setAttributes);
    }

    public static void spawnPlacements() {
        SpawnPlacementsRegistry.register(MonsterEntityTypes.OGRE, SpawnPlacementTypes.ON_GROUND, Types.WORLD_SURFACE, TensuraTamableEntity::checkTensuraMobSpawnRules);
    }

}
