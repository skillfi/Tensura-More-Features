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
    @Language.English("Kijin Spawn Egg") @Language.Ukrainian("Яйце виклику Кіджина")
    public static final Supplier<SpawnEggItem> KIJIN;
    @Language.English("Mystic Oni Spawn Egg") @Language.Ukrainian("Яйце виклику Містичного Оні")
    public static final Supplier<SpawnEggItem> MYSTIC_ONI;
    @Language.English("Wicked Oni Spawn Egg") @Language.Ukrainian("Яйце виклику Злого Оні")
    public static final Supplier<SpawnEggItem> WICKED_ONI;
    @Language.English("Spirit Oni Spawn Egg") @Language.Ukrainian("Яйце виклику Духовного Оні")
    public static final Supplier<SpawnEggItem> SPIRIT_ONI;
    @Language.English("Death Oni Spawn Egg") @Language.Ukrainian("Яйце виклику Оні Смерті")
    public static final Supplier<SpawnEggItem> DEATH_ONI;
    @Language.English("Divine Oni Spawn Egg") @Language.Ukrainian("Яйце виклику Божественного Оні")
    public static final Supplier<SpawnEggItem> DIVINE_ONI;
    @Language.English("Divine Fighter Spawn Egg") @Language.Ukrainian("Яйце виклику Божественного Бійця")
    public static final Supplier<SpawnEggItem> DIVINE_FIGHTER;

    public static void init() {

   }

   static {
      OGRE = registerItem("ogre_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.OGRE, (new Color(182, 147, 106)).getRGB(), (new Color(127, 81, 56)).getRGB(), (new Item.Properties()).arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      KIJIN = registerItem("kijin_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.KIJIN, 0x8A6B57, 0x5D4037, new Item.Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      MYSTIC_ONI = registerItem("mystic_oni_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.MYSTIC_ONI, 0x8A6B57, 0x5D4037, new Item.Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      WICKED_ONI = registerItem("wicked_oni_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.WICKED_ONI, 0x8A6B57, 0x5D4037, new Item.Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      SPIRIT_ONI = registerItem("spirit_oni_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.SPIRIT_ONI, 0x8A6B57, 0x5D4037, new Item.Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      DEATH_ONI = registerItem("death_oni_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.DEATH_ONI, 0x8A6B57, 0x5D4037, new Item.Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      DIVINE_ONI = registerItem("divine_oni_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.DIVINE_ONI, 0x8A6B57, 0x5D4037, new Item.Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
      DIVINE_FIGHTER = registerItem("divine_fighter_spawn_egg", TensuraMfPlatform.makeSpawnEggFor(MonsterEntityTypes.DIVINE_FIGHTER, 0x8A6B57, 0x5D4037, new Item.Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)));
    }

    private static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return TensuraMfPlatform.registerItem(id, item);
    }
}
