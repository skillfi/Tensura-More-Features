package com.github.skillfi.tensura_mf.config;

import io.github.manasmods.manascore.config.ConfigRegistry;
import com.github.skillfi.tensura_mf.config.entity.SpawnRateConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfConfigs {

    public static void init(){
        ConfigRegistry.registerConfig(new EntityConfig());
        ConfigRegistry.registerConfig(new SpawnRateConfig());
    }
}
