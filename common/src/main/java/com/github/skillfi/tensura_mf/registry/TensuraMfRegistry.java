package com.github.skillfi.tensura_mf.registry;

import com.github.skillfi.tensura_mf.registry.entity.TensuraMfEntityTypes;
import com.github.skillfi.tensura_mf.registry.item.TensuraMfSpawnEggs;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocks;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import com.github.skillfi.tensura_mf.registry.menu.TensuraMfMenus;

import com.github.skillfi.tensura_mf.registry.recipe.TensuraMfRecipes;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfRegistry {

    public static void init() {
        TensuraMfEntityTypes.init();
        TensuraMfSpawnEggs.init();
        TensuraMfBlocks.init();
        TensuraMfBlocksEntities.init();
        TensuraMfRecipes.init();
        TensuraMfMenus.init();
    }
}
