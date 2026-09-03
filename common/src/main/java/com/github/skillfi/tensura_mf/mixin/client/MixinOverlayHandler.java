package com.github.skillfi.tensura_mf.mixin.client;

import com.github.skillfi.tensura_mf.api.energy.INetworkEntry;
import com.github.skillfi.tensura_mf.block.entity.MagicEngineBlockEntity;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.manasmods.tensura.handler.client.OverlayHandler;

@Mixin(OverlayHandler.class)
public abstract class MixinOverlayHandler {

    @Shadow
    private static ClientLevel level;

    @Shadow
    private static Font font;

    @Shadow
    private static GuiGraphics graphics;

    @Inject(method = "renderBlockAnalysis", at = @At("TAIL"))
    private static void tensuraMf$renderMagicEnergy(BlockState blockState, BlockPos blockPos, CallbackInfo callbackInfo) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof INetworkEntry iNetworkEntry)) {
            return;
        }
        INetwork iNetwork = TensuraMfStorages.getNetworkFrom(level);
        Float magicEnergy = iNetwork.getMagicEnergy(iNetworkEntry.getNetworkId());

        graphics.drawString(
                font,
                Component.translatable("analysis.tensura_mf.magic_energy", magicEnergy),
                7,
                120,
                0xFFFFFF
        );
    }
}
