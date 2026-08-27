package com.github.skillfi.tensura_mf.registry.item;

import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.data.annotations.Language;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.awt.*;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfSpawnEggs {

    @Language.English("Ogre Spawn Egg")
    public static final Supplier<SpawnEggItem> OGRE;

    public static void init() {

   }

   static {
      OGRE = registerItem("ogre_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.OGRE, (new Color(182, 147, 106)).getRGB(), (new Color(127, 81, 56)).getRGB(), (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
    }

    private static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return TensuraMfPlatform.registerItem(id, item);
    }
}
