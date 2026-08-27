package com.github.skillfi.tensura_mf.config.entity;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

import java.util.List;

public class SpawnRateConfig extends ManasConfig {
    @Comment("Spawn probability for mobs (1 in X chance). First value is Day, Second is Night.\nExamples: 1=100%, 5=20%, 10=10%. 0=disabled.")
    public SpawnChance SpawnChance = new SpawnChance();

    public String getFileName() {
        return "tensura_mf/entity/spawn_rate_config";
    }

    public static class SpawnChance extends ManasSubConfig {
        public List<Integer> ogre = List.of(4, 3);
    }
}
