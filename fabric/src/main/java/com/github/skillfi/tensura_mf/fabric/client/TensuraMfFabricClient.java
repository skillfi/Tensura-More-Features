package com.github.skillfi.tensura_mf.fabric.client;

import com.github.skillfi.tensura_mf.client.TensuraMfClient;
import com.github.skillfi.tensura_mf.client.screen.MagiculeIncubatorScreen;
import com.github.skillfi.tensura_mf.registry.menu.TensuraMfMenus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public final class TensuraMfFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        TensuraMfClient.init();
        MenuScreens.register(TensuraMfMenus.MAGICULE_INCUBATOR.get(), MagiculeIncubatorScreen::new);
    }
}
