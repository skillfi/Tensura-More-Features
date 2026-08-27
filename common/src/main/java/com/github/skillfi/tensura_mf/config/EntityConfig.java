package com.github.skillfi.tensura_mf.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

import java.util.List;

public class EntityConfig extends ManasConfig {
    public Ogre Ogre = new Ogre();

    public String getFileName() {
        return "tensura_mf/entity/entity_config";
    }

    public static class Ogre extends ManasSubConfig {
        @Comment("Random colors for Ogres' hair.")
        public List ogreHairColors = List.of(-14869219, -11849440, -10855846, -12961222, -1, -4741456, -1326982);
        @Comment("Random colors for Ogres' top clothes.")
        public List ogreClothingColors = List.of(-14513374, -14540254, -1, -11184811, -2239048, -7640241, -8388480, -7650029, -16763765);
        @Comment("Random colors for Ogres' bottom clothes.")
        public List ogreBottomClothesColors = List.of(-14540254, -11184811, -2239048, -11850209, -7640241);
    }
}
