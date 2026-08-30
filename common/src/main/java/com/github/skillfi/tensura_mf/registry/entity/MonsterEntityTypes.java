package com.github.skillfi.tensura_mf.registry.entity;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.data.annotations.Language;
import com.github.skillfi.tensura_mf.entity.monster.OgreEntity;
import com.github.skillfi.tensura_mf.entity.monster.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.entity.*;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonsterEntityTypes {

    @Language.English("Ogre")
    @Language.Ukrainian("Огр")
    public static final Supplier<EntityType<OgreEntity>> OGRE;
    @Language.English("Kijin") @Language.Ukrainian("Кіджин")
    public static final Supplier<EntityType<KijinEntity>> KIJIN;
    @Language.English("Mystic Oni") @Language.Ukrainian("Містичний Оні")
    public static final Supplier<EntityType<MysticOniEntity>> MYSTIC_ONI;
    @Language.English("Wicked Oni") @Language.Ukrainian("Злий Оні")
    public static final Supplier<EntityType<WickedOniEntity>> WICKED_ONI;
    @Language.English("Spirit Oni") @Language.Ukrainian("Духовний Оні")
    public static final Supplier<EntityType<SpiritOniEntity>> SPIRIT_ONI;
    @Language.English("Death Oni") @Language.Ukrainian("Оні Смерті")
    public static final Supplier<EntityType<DeathOniEntity>> DEATH_ONI;
    @Language.English("Divine Oni") @Language.Ukrainian("Божественний Оні")
    public static final Supplier<EntityType<DivineOniEntity>> DIVINE_ONI;
    @Language.English("Divine Fighter") @Language.Ukrainian("Божественний Боєць")
    public static final Supplier<EntityType<DivineFighterEntity>> DIVINE_FIGHTER;

    static {
        OGRE = registerMonsterEntity("ogre", OgreEntity::new,
                0.8F, 2.5F, 10, 0x505050, 0x606060);
        KIJIN = registerMonsterEntity("kijin", KijinEntity::new, 0.8F, 2.5F, 10, 0x8A6B57, 0x5D4037);
        MYSTIC_ONI = registerMonsterEntity("mystic_oni", MysticOniEntity::new, 0.8F, 2.5F, 10, 0x8A6B57, 0x5D4037);
        WICKED_ONI = registerMonsterEntity("wicked_oni", WickedOniEntity::new, 0.8F, 2.5F, 10, 0x8A6B57, 0x5D4037);
        SPIRIT_ONI = registerMonsterEntity("spirit_oni", SpiritOniEntity::new, 0.8F, 2.5F, 10, 0x8A6B57, 0x5D4037);
        DEATH_ONI = registerMonsterEntity("death_oni", DeathOniEntity::new, 0.8F, 2.5F, 10, 0x8A6B57, 0x5D4037);
        DIVINE_ONI = registerMonsterEntity("divine_oni", DivineOniEntity::new, 0.8F, 2.5F, 10, 0x8A6B57, 0x5D4037);
        DIVINE_FIGHTER = registerMonsterEntity("divine_fighter", DivineFighterEntity::new, 0.8F, 2.5F, 10, 0x8A6B57, 0x5D4037);
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
