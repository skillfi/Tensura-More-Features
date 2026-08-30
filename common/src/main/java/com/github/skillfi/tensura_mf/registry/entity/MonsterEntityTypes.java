package com.github.skillfi.tensura_mf.registry.entity;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.data.annotations.Language;
import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.entity.*;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonsterEntityTypes {

    @Language.English("Ogre")
    @Language.Ukrainian("Огр")
    public static final Supplier<EntityType<OgreEntity>> OGRE;

    static {
        OGRE = registerMonsterEntity("ogre", OgreEntity::new,
                0.8F, 2.5F, 10, 0x505050, 0x606060);
    }

    public static <T extends Mob> Supplier<EntityType<T>> registerMonsterEntity(
            String name,
            EntityType.EntityFactory<T> factory,
            float width,
            float height,
            int trackingRange,
            int primaryEggColor,
            int secondaryEggColor) {

        return TensuraMfPlatform.registerEntity(name, () ->
                EntityType.Builder.of(factory, MobCategory.MONSTER)
                        .sized(width, height)
                        .clientTrackingRange(trackingRange)
                        .build(TensuraMf.create(name).toString()));
    }



    public static void init() {

    }

}
