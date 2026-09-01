package com.github.skillfi.tensura_mf.registry.menu;

import com.github.skillfi.tensura_mf.TensuraMfPlatform;
import com.github.skillfi.tensura_mf.client.screen.MagiculeIncubatorScreen;
import com.github.skillfi.tensura_mf.menu.MagiculeIncubatorMenu;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.menu.MenuRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.inventory.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TensuraMfMenus {
    public static final Logger LOG = LoggerFactory.getLogger("TensuraMF - Menu");
    public static final Supplier<MenuType<MagiculeIncubatorMenu>> MAGICULE_INCUBATOR =
            TensuraMfPlatform.registerMenu("magicule_incubator", () -> MenuRegistry.ofExtended(MagiculeIncubatorMenu::createMenu));

    public static void init() { /* TODO document why this method is empty */ }

    public static void client() {
        ClientLifecycleEvent.CLIENT_SETUP.register(state->{
           MenuRegistry.registerScreenFactory(MAGICULE_INCUBATOR.get(), MagiculeIncubatorScreen::new);
        });
    }
}
