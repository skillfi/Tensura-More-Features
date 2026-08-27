package com.github.skillfi.tensura_mf.registry;

import com.github.skillfi.tensura_mf.registry.entity.TensuraMfEntityTypes;
import com.github.skillfi.tensura_mf.registry.item.TensuraMfSpawnEggs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfRegistry {

    public static void init() {
        TensuraMfEntityTypes.init();
        TensuraMfSpawnEggs.init();
    }
}
