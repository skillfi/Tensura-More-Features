package com.github.skillfi.tensura_mf.neoforge.data;


import com.github.skillfi.tensura_mf.data.TensuraMfBiomeTags;
import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import lombok.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfBiomeModification {
    public static final List<MobSpawningTemplate> MOB_SPAWNING = new ArrayList();


    public static void initMobSpawning() {
        addSpawn(TensuraMfBiomeTags.EntitySpawn.OGRE, MonsterEntityTypes.OGRE.get(), 80, 1, 4, 0.8);
        addSpawn(TensuraMfBiomeTags.EntitySpawn.PRIMORDIAL_DAEMON, MonsterEntityTypes.PRIMORDIAL_WHITE.get(), 50, 1, 1, 0.8);
        addSpawn(TensuraMfBiomeTags.EntitySpawn.PRIMORDIAL_DAEMON, MonsterEntityTypes.PRIMORDIAL_BLACK.get(), 50, 1, 1, 0.8);
//        addSpawn(TensuraMfBiomeTags.EntitySpawn.PRIMORDIAL_DAEMON, MonsterEntityTypes.PRIMORDIAL_ROUGE.get(), 50, 0, 1, 0.8);
    }

    private static void addSpawn(TagKey<Biome> biome, EntityType<?> entityType, int weight, int min, int max, double charge) {
        addSpawn(biome, entityType, weight, min, max, 4.0F, charge);
    }

    private static void addSpawn(TagKey<Biome> biome, EntityType<?> entityType, int weight, int min, int max, double budget, double charge) {
        MOB_SPAWNING.add(new MobSpawningTemplate(entityType, biome, weight, min, max, budget, charge));
    }


    public record MobSpawningTemplate(EntityType<?> type, TagKey<Biome> biome, int weight, int minPack, int maxPack,
                                      double budget, double charge) {
    }
}
