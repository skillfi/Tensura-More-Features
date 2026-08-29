package com.github.skillfi.tensura_mf.registry.entity;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.data.annotations.Language;
import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;
import com.github.skillfi.tensura_mf.entity.monster.PrimordialDaemonEntity;
import com.github.skillfi.tensura_mf.entity.variant.PrimordialVariant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.entity.*;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonsterEntityTypes {

    @Language.English("Ogre")
    @Language.Ukrainian("Огр")
    public static final Supplier<EntityType<OgreEntity>> OGRE;
    @Language.English("Primordial White")
    @Language.Ukrainian("Первісний білий демон")
    public static final Supplier<EntityType<PrimordialDaemonEntity>> PRIMORDIAL_WHITE;
    @Language.English("Primordial Black")
    @Language.Ukrainian("Первісний чорний демон")
    public static final Supplier<EntityType<PrimordialDaemonEntity>> PRIMORDIAL_BLACK;
    @Language.English("Primordial Rouge")
    @Language.Ukrainian("Первісний червоний демон")
    public static final Supplier<EntityType<PrimordialDaemonEntity>> PRIMORDIAL_ROUGE;
    @Language.English("Primordial Vert")
    @Language.Ukrainian("Первісний зелений демон")
    public static final Supplier<EntityType<PrimordialDaemonEntity>> PRIMORDIAL_VERT;
    @Language.English("Primordial Jaune")
    @Language.Ukrainian("Первісний жовтий демон")
    public static final Supplier<EntityType<PrimordialDaemonEntity>> PRIMORDIAL_JAUNE;
    @Language.English("Primordial Violet")
    @Language.Ukrainian("Первісний фіолетовий демон")
    public static final Supplier<EntityType<PrimordialDaemonEntity>> PRIMORDIAL_VIOLET;
    @Language.English("Primordial Bleu")
    @Language.Ukrainian("Первісний синій демон")
    public static final Supplier<EntityType<PrimordialDaemonEntity>> PRIMORDIAL_BLEU;

    static {
        OGRE = registerMonsterEntity("ogre", OgreEntity::new,
                0.8F, 2.5F, 10, 0x505050, 0x606060);
        PRIMORDIAL_WHITE = registerMonsterEntity("primordial_white", PrimordialDaemonEntity::new,
                0.8F, 2.5F, 10, 0xF4F4F4, 0xB7B7B7);
        PRIMORDIAL_BLACK = registerMonsterEntity("primordial_black", PrimordialDaemonEntity::new,
                0.8F, 2.5F, 10, 0x242424, 0x090909);
        PRIMORDIAL_ROUGE = registerMonsterEntity("primordial_rouge", PrimordialDaemonEntity::new, 0.8F, 2.5F, 10, 0x8F1D2C, 0x4A0D16);
        PRIMORDIAL_VERT = registerMonsterEntity("primordial_vert", PrimordialDaemonEntity::new, 0.8F, 2.5F, 10, 0x3D8B55, 0x1E4D2F);
        PRIMORDIAL_JAUNE = registerMonsterEntity("primordial_jaune", PrimordialDaemonEntity::new, 0.8F, 2.5F, 10, 0xD9B52E, 0x756117);
        PRIMORDIAL_VIOLET = registerMonsterEntity("primordial_violet", PrimordialDaemonEntity::new, 0.8F, 2.5F, 10, 0x7B4DA3, 0x3D2754);
        PRIMORDIAL_BLEU = registerMonsterEntity("primordial_bleu", PrimordialDaemonEntity::new, 0.8F, 2.5F, 10, 0x3C6FB6, 0x1D385D);
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
