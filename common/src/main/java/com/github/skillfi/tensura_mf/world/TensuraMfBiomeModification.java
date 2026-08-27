package com.github.skillfi.tensura_mf.world;


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
        addSpawn(TensuraMfBiomeTags.EntitySpawn.OGRE, (EntityType) MonsterEntityTypes.OGRE.get(), 80, 1, 4, 0.8);
    }

    private static void addSpawn(TagKey<Biome> biome, EntityType<?> type, int weight, int min, int max, double charge) {
        addSpawn(biome, type, weight, min, max, (double)4.0F, charge);
    }

    private static void addSpawn(TagKey<Biome> biome, EntityType<?> type, int weight, int min, int max, double budget, double charge) {
        MOB_SPAWNING.add(new MobSpawningTemplate(type, biome, weight, min, max, budget, charge));
    }

    @RequiredArgsConstructor
    @Getter
    public static class MobSpawningTemplate {
        private final EntityType<?> type;
        private final TagKey<Biome> biome;
        private final int weight;
        private final int minPack;
        private final int maxPack;
        private final double budget;
        private final double charge;
    }
}
