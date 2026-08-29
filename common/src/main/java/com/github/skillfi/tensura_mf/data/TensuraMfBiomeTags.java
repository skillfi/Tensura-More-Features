package com.github.skillfi.tensura_mf.data;

import com.github.skillfi.tensura_mf.TensuraMf;
import io.github.manasmods.tensura.data.TensuraBiomeTags;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfBiomeTags {
    public static TagKey<Biome> IS_HELL = modTag("is_hell");

    static TagKey<Biome> modTag(String name) {
        return create(TensuraMf.create(name));
    }

    static TagKey<Biome> create(ResourceLocation name) {
        return TagKey.create(Registries.BIOME, name);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EntitySpawn {
        public static TagKey<Biome> OGRE = TensuraMfBiomeTags.modTag("ogre_spawn");
        public static TagKey<Biome> PRIMORDIAL_DAEMON = TensuraMfBiomeTags.modTag("primordial_daemon_spawn");
    }
}
