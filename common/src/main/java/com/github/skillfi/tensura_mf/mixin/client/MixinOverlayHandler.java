package com.github.skillfi.tensura_mf.mixin.client;

import com.github.skillfi.tensura_mf.api.energy.INetworkEntry;
import com.github.skillfi.tensura_mf.block.entity.MagicEngineBlockEntity;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import io.github.manasmods.tensura.util.client.RenderHelper;
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
    private static final Component MAGIC_ENERGY_LABEL = Component.translatable("analysis.tensura_mf.magic_energy");

    @Inject(method = "renderBlockAnalysis", at = @At("TAIL"))
    private static void tensuraMf$renderMagicEnergy(BlockState blockState, BlockPos blockPos, CallbackInfo callbackInfo) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof INetworkEntry iNetworkEntry)) {
            return;
        }
        String text = "-> ";

        INetwork iNetwork = TensuraMfStorages.getNetworkFrom(level);
        Float magicEnergy = iNetwork.getMagicEnergy(iNetworkEntry.getNetworkId());
        Float maxMagicEnergy = iNetwork.getMaxMagicEnergy(iNetworkEntry.getNetworkId());
        String iNetworkText = text + magicEnergy + "/" + maxMagicEnergy;

        RenderHelper.drawSimpleScrollingText(graphics, font, MAGIC_ENERGY_LABEL, 7, 120, 16, 16777215);
        RenderHelper.drawSimpleScrollingText(graphics, font, Component.literal(iNetworkText), 7, 120 + 9 + 2, 16, 16777215);
    }
}
