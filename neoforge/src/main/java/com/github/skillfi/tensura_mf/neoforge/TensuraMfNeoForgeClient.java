package com.github.skillfi.tensura_mf.neoforge;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.client.TensuraMfClient;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import com.github.skillfi.tensura_mf.client.screen.MagiculeIncubatorScreen;
import com.github.skillfi.tensura_mf.registry.menu.TensuraMfMenus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EventBusSubscriber(modid = TensuraMf.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class TensuraMfNeoForgeClient {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        TensuraMfClient.registerRenderers(event::registerEntityRenderer, event::registerBlockEntityRenderer);
    }

    @SubscribeEvent
    public static void registerScreens(final RegisterMenuScreensEvent event) {
        event.register(TensuraMfMenus.MAGICULE_INCUBATOR.get(), MagiculeIncubatorScreen::new);
    }

}
