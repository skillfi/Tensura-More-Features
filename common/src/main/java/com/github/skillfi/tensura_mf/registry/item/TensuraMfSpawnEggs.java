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
    @Language.English("Primordial White Spawn Egg")
    @Language.Ukrainian("Яйце призову первісного білого демона")
    public static final Supplier<SpawnEggItem> PRIMORDIAL_WHITE;
    @Language.English("Primordial Black Spawn Egg")
    @Language.Ukrainian("Яйце призову первісного чорного демона")
    public static final Supplier<SpawnEggItem> PRIMORDIAL_BLACK;
    @Language.English("Primordial Rouge Spawn Egg")
    @Language.Ukrainian("Яйце призову первісного червоного демона")
    public static final Supplier<SpawnEggItem> PRIMORDIAL_ROUGE;
    @Language.English("Primordial Vert Spawn Egg")
    @Language.Ukrainian("Яйце призову первісного зеленого демона")
    public static final Supplier<SpawnEggItem> PRIMORDIAL_VERT;
    @Language.English("Primordial Jaune Spawn Egg")
    @Language.Ukrainian("Яйце призову первісного жовтого демона")
    public static final Supplier<SpawnEggItem> PRIMORDIAL_JAUNE;
    @Language.English("Primordial Violet Spawn Egg")
    @Language.Ukrainian("Яйце призову первісного фіолетового демона")
    public static final Supplier<SpawnEggItem> PRIMORDIAL_VIOLET;
    @Language.English("Primordial Bleu Spawn Egg")
    @Language.Ukrainian("Яйце призову первісного синього демона")
    public static final Supplier<SpawnEggItem> PRIMORDIAL_BLEU;

    public static void init() {

   }

   static {
      OGRE = registerItem("ogre_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.OGRE, (new Color(182, 147, 106)).getRGB(), (new Color(127, 81, 56)).getRGB(), (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      PRIMORDIAL_WHITE = registerItem("primordial_white_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.PRIMORDIAL_WHITE, 0xF4F4F4, 0xB7B7B7, (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      PRIMORDIAL_BLACK = registerItem("primordial_black_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.PRIMORDIAL_BLACK, 0x242424, 0x090909, (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      PRIMORDIAL_ROUGE = registerItem("primordial_rouge_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.PRIMORDIAL_ROUGE, 0x8F1D2C, 0x4A0D16, (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      PRIMORDIAL_VERT = registerItem("primordial_vert_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.PRIMORDIAL_VERT, 0x3D8B55, 0x1E4D2F, (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      PRIMORDIAL_JAUNE = registerItem("primordial_jaune_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.PRIMORDIAL_JAUNE, 0xD9B52E, 0x756117, (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      PRIMORDIAL_VIOLET = registerItem("primordial_violet_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.PRIMORDIAL_VIOLET, 0x7B4DA3, 0x3D2754, (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      PRIMORDIAL_BLEU = registerItem("primordial_bleu_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.PRIMORDIAL_BLEU, 0x3C6FB6, 0x1D385D, (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
    }

    private static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return TensuraMfPlatform.registerItem(id, item);
    }
}
