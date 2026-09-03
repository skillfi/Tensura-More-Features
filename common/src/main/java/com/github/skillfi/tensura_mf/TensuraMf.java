package com.github.skillfi.tensura_mf;

import com.github.skillfi.tensura_mf.config.TensuraMfConfigs;
import com.github.skillfi.tensura_mf.handler.BlockHandlers;
import com.github.skillfi.tensura_mf.handler.TensuraMfHandlers;
import com.github.skillfi.tensura_mf.registry.TensuraMfRegistry;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TensuraMf {
    public static final String MOD_ID = "tensura_mf";
    public static final Logger LOG = LoggerFactory.getLogger("TensuraMF");

    public static void init() {
        // Write common init code here.
        TensuraMfConfigs.init();
        TensuraMfRegistry.init();
        TensuraMfStorages.init();
        BlockHandlers.init();
        TensuraMfHandlers.init();

    }

    public static ResourceLocation create(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
