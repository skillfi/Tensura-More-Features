package com.github.skillfi.tensura_mf.registry.menu;

import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.menu.MagiculeIncubatorMenu;
import dev.architectury.registry.menu.MenuRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.inventory.MenuType;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TensuraMfMenus {
    public static final Supplier<MenuType<MagiculeIncubatorMenu>> MAGICULE_INCUBATOR =
            TensuraMfPlatform.registerMenu("magicule_incubator", () -> MenuRegistry.ofExtended(MagiculeIncubatorMenu::new));

    public static void init() { /* TODO document why this method is empty */ }
}
