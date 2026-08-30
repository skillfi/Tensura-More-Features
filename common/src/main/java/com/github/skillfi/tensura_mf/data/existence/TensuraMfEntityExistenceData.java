package com.github.skillfi.tensura_mf.data.existence;

import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.race.OgreConfig;
import io.github.manasmods.tensura.data.existence.EntityExistenceData;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static io.github.manasmods.tensura.data.existence.TensuraEntityExistenceData.register;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfEntityExistenceData {
    public static void bootstrap(BootstrapContext<EntityExistenceData> context) {
        OgreConfig config = (OgreConfig) ConfigRegistry.getConfig(OgreConfig.class);
        register(context, EntityExistenceData.getDefault(
                MonsterEntityTypes.OGRE.get().arch$registryName(),
                (int) config.Ogre.maxSpiritualHealth, (int) config.Ogre.minMagicule, (int) config.Ogre.maxMagicule,
                (int) config.Ogre.minAura, (int) config.Ogre.maxAura,
                List.of(CommonSkills.STRENGTH.getId()), TensuraRaces.KIJIN.getId()));
        register(context, EntityExistenceData.getDefault(
                MonsterEntityTypes.KIJIN.get().arch$registryName(),
                (int) config.Kijin.maxSpiritualHealth, (int) config.Kijin.minMagicule, (int) config.Kijin.maxMagicule,
                (int) config.Kijin.minAura, (int) config.Kijin.maxAura,
                kijinSkills(), TensuraRaces.MYSTIC_ONI.getId()));
        register(context, EntityExistenceData.getDefault(MonsterEntityTypes.MYSTIC_ONI.get().arch$registryName(),
                (int) config.MysticOni.maxSpiritualHealth, (int) config.MysticOni.minMagicule, (int) config.MysticOni.maxMagicule,
                (int) config.MysticOni.minAura, (int) config.MysticOni.maxAura, kijinSkills(), TensuraRaces.SPIRIT_ONI.getId()));
        register(context, EntityExistenceData.getDefault(MonsterEntityTypes.WICKED_ONI.get().arch$registryName(),
                (int) config.WickedOni.maxSpiritualHealth, (int) config.WickedOni.minMagicule, (int) config.WickedOni.maxMagicule,
                (int) config.WickedOni.minAura, (int) config.WickedOni.maxAura, wickedSkills(), TensuraRaces.DEATH_ONI.getId()));
        register(context, EntityExistenceData.getDefault(MonsterEntityTypes.SPIRIT_ONI.get().arch$registryName(),
                (int) config.SpiritOni.maxSpiritualHealth, (int) config.SpiritOni.minMagicule, (int) config.SpiritOni.maxMagicule,
                (int) config.SpiritOni.minAura, (int) config.SpiritOni.maxAura, kijinSkills(), TensuraRaces.DIVINE_ONI.getId()));
        register(context, EntityExistenceData.getDefault(MonsterEntityTypes.DEATH_ONI.get().arch$registryName(),
                (int) config.DeathOni.maxSpiritualHealth, (int) config.DeathOni.minMagicule, (int) config.DeathOni.maxMagicule,
                (int) config.DeathOni.minAura, (int) config.DeathOni.maxAura, wickedSkills(), TensuraRaces.DIVINE_FIGHTER.getId()));
        register(context, EntityExistenceData.getDefault(MonsterEntityTypes.DIVINE_ONI.get().arch$registryName(),
                (int) config.DivineOni.maxSpiritualHealth, (int) config.DivineOni.minMagicule, (int) config.DivineOni.maxMagicule,
                (int) config.DivineOni.minAura, (int) config.DivineOni.maxAura, divineOniSkills()));
        register(context, EntityExistenceData.getDefault(MonsterEntityTypes.DIVINE_FIGHTER.get().arch$registryName(),
                (int) config.DivineFighter.maxSpiritualHealth, (int) config.DivineFighter.minMagicule, (int) config.DivineFighter.maxMagicule,
                (int) config.DivineFighter.minAura, (int) config.DivineFighter.maxAura, divineFighterSkills()));
    }

    private static List<ResourceLocation> kijinSkills() {
        return List.of(CommonSkills.STRENGTH.getId(),
                IntrinsicSkills.DARKNESS_TRANSFORM.getId(), IntrinsicSkills.EARTH_TRANSFORM.getId(),
                IntrinsicSkills.FLAME_TRANSFORM.getId(), IntrinsicSkills.LIGHT_TRANSFORM.getId(),
                IntrinsicSkills.SPACE_TRANSFORM.getId(), IntrinsicSkills.WATER_TRANSFORM.getId(),
                IntrinsicSkills.WIND_TRANSFORM.getId());
    }

    private static List<ResourceLocation> wickedSkills() {
        return List.of(CommonSkills.STRENGTH.getId(), IntrinsicSkills.OGRE_BERSERKER.getId(), ExtraSkills.ULTRASPEED_REGENERATION.getId());
    }

    private static List<ResourceLocation> divineOniSkills() {
        return List.of(CommonSkills.STRENGTH.getId(), IntrinsicSkills.DIVINE_KI_RELEASE.getId());
    }

    private static List<ResourceLocation> divineFighterSkills() {
        return List.of(CommonSkills.STRENGTH.getId(), IntrinsicSkills.OGRE_BERSERKER.getId(),
                ExtraSkills.ULTRASPEED_REGENERATION.getId(), IntrinsicSkills.DIVINE_KI_RELEASE.getId());
    }
}
