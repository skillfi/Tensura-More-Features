package com.github.skillfi.tensura_mf.entity.ai.behaviour;

import com.github.skillfi.tensura_mf.config.EntityConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfBehaviourHelper {
    public static final EntityConfig CONFIG = ConfigRegistry.getConfig(EntityConfig.class);
}
