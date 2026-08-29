package com.github.skillfi.tensura_mf.data.existence;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.race.DaemonConfig;
import io.github.manasmods.tensura.data.existence.EntityExistenceData;

import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import lombok.NoArgsConstructor;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.List;

import static io.github.manasmods.tensura.data.existence.TensuraEntityExistenceData.register;

@NoArgsConstructor
public class TensuraMfEntityExistenceData {
    private static final List<ResourceLocation> BLANC_SKILLS = List.of(
            CommonSkills.THOUGHT_COMMUNICATION.getId(),
            IntrinsicSkills.POSSESSION.getId(),
            ExtraSkills.MANA_MANIPULATION.getId(),
            ExtraSkills.THOUGHT_ACCELERATION.getId(),
            ExtraSkills.UNIVERSAL_PERCEPTION.getId(),
            ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.getId(),
            ResistanceSkills.HOLY_ATTACK_RESISTANCE.getId(),
            ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.getId(),
            ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.getId()
    );
    private static final List<ResourceLocation> NOIR_SKILLS = List.of(
            CommonSkills.THOUGHT_COMMUNICATION.getId(),
            IntrinsicSkills.POSSESSION.getId(),
            ExtraSkills.DEMON_LORD_HAKI.getId(),
            ExtraSkills.THOUGHT_ACCELERATION.getId(),
            ExtraSkills.UNIVERSAL_PERCEPTION.getId(),
            ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.getId(),
            ResistanceSkills.HOLY_ATTACK_RESISTANCE.getId(),
            ResistanceSkills.PAIN_NULLIFICATION.getId(),
            ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.getId(),
            ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.getId()
    );
    private static final List<ResourceLocation> ROUGE_SKILLS = List.of(
            CommonSkills.THOUGHT_COMMUNICATION.getId(),
            IntrinsicSkills.POSSESSION.getId(),
            ExtraSkills.DEMON_LORD_HAKI.getId(),
            ExtraSkills.THOUGHT_ACCELERATION.getId(),
            ExtraSkills.UNIVERSAL_PERCEPTION.getId()
    );
    private static final List<ResourceLocation> VIOLET_SKILLS = List.of(
            IntrinsicSkills.POSSESSION.getId(),
            ExtraSkills.THOUGHT_ACCELERATION.getId(),
            ExtraSkills.UNIVERSAL_PERCEPTION.getId(),
            ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.getId()
    );
    private static final List<ResourceLocation> JAUNE_SKILLS = List.of(
            IntrinsicSkills.POSSESSION.getId(),
            ExtraSkills.MANA_MANIPULATION.getId(),
            ExtraSkills.THOUGHT_ACCELERATION.getId(),
            ExtraSkills.UNIVERSAL_PERCEPTION.getId(),
            ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.getId(),
            ResistanceSkills.HOLY_ATTACK_RESISTANCE.getId(),
            ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.getId(),
            ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.getId()
    );
    private static final List<ResourceLocation> BLEU_SKILLS = List.of(
            CommonSkills.THOUGHT_COMMUNICATION.getId(),
            IntrinsicSkills.POSSESSION.getId(),
            ExtraSkills.DEMON_LORD_HAKI.getId(),
            ResistanceSkills.MAGIC_RESISTANCE.getId()
    );

    public static DaemonConfig getDefaultConfig() {
        return ((DaemonConfig) ConfigRegistry.getConfig(DaemonConfig.class));
    }

    public static void bootstrap(BootstrapContext<EntityExistenceData> context) {
        applyTensura(context);
    }

    private static void applyTensura(BootstrapContext<EntityExistenceData> context) {
        DaemonConfig.ArchDaemon config = getDefaultConfig().ArchDaemon;
        registerOgre(context);
        ResourceLocation daemonLord = TensuraRaces.DAEMON_LORD.get().getRegistryName();
        registerPrimordial(context, MonsterEntityTypes.PRIMORDIAL_WHITE.get(), BLANC_SKILLS, config, daemonLord);
        registerPrimordial(context, MonsterEntityTypes.PRIMORDIAL_BLACK.get(), NOIR_SKILLS, config, daemonLord);
        registerPrimordial(context, MonsterEntityTypes.PRIMORDIAL_ROUGE.get(), ROUGE_SKILLS, config, daemonLord);
        registerPrimordial(context, MonsterEntityTypes.PRIMORDIAL_VIOLET.get(), VIOLET_SKILLS, config, daemonLord);
        registerPrimordial(context, MonsterEntityTypes.PRIMORDIAL_JAUNE.get(), JAUNE_SKILLS, config, daemonLord);
        registerPrimordial(context, MonsterEntityTypes.PRIMORDIAL_BLEU.get(), BLEU_SKILLS, config, daemonLord);
        registerPrimordial(context, MonsterEntityTypes.PRIMORDIAL_VERT.get(), BLEU_SKILLS, config, daemonLord);
    }

    private static void registerOgre(BootstrapContext<EntityExistenceData> context) {
        register(context, EntityExistenceData.getDefault(
                MonsterEntityTypes.OGRE.get().arch$registryName(),
                40, 500, 1500, 500, 1500,
                List.of(), TensuraRaces.KIJIN.get().getRegistryName()));
    }

    private static void registerPrimordial(BootstrapContext<EntityExistenceData> context,
                                           EntityType<?> entityType,
                                           List<ResourceLocation> skills,
                                           DaemonConfig.ArchDaemon config,
                                           ResourceLocation evolution) {
        register(context, EntityExistenceData.getDefault(
                entityType.arch$registryName(),
                (int) config.maxSpiritualHealth,
                (int) config.minMagicule,
                (int) config.maxMagicule,
                (int) config.minAura,
                (int) config.maxAura,
                skills,
                evolution));
    }
}
