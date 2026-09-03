package com.github.skillfi.tensura_mf.client.screen;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.menu.MagiculeIncubatorMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.manasmods.tensura.util.client.RenderHelper;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;
import java.util.HashMap;

public class MagiculeIncubatorScreen extends AbstractContainerScreen<MagiculeIncubatorMenu> {
    @Getter
    public static final ResourceLocation background = TensuraMf.create("textures/gui/magic_incubator/magic_incubator_gui.png");
    private final HashMap<String, Object> guiState = MagiculeIncubatorMenu.guistate;
    public MagiculeIncubatorScreen(MagiculeIncubatorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 168;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int mX, int mY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(background, x, y, 0, 0, 176, 168);
        this.renderProgress(graphics, x, y);
        renderEnergy(graphics, x, y);

    }

    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    private void renderProgress(GuiGraphics graphics, int x, int y) {
        if (this.menu.isIncubating()) {
            int height = this.menu.getScaledProgress();
            graphics.blit(this.getBackground(), x + 135, y + 73 - height, 24, this.imageHeight + 24 - height, 24, height);
        }
    }

    protected void renderTooltip(GuiGraphics graphics, int mX, int mY) {
        super.renderTooltip(graphics, mX, mY);

        if (this.isHovering(18, 5, 15, 76, mX, mY)) {
            if ((this.menu).blockEntity.getMagicEnergy() > 0) {
                float magicEnergy = (this.menu).blockEntity.getMagicEnergy();
                String valueText = magicEnergy + "/" + (this.menu).blockEntity.getMaxMagicEnergy();
                this.renderMaterialTooltip(graphics, mX, mY, valueText);
            } else {
                graphics.renderTooltip(this.font, Component.translatable("tooltip.tensura.kiln.empty"), mX, mY);
            }
        }

    }

    private void renderMaterialTooltip(GuiGraphics graphics, int mouseX, int mouseY, String valueText) {
        MutableComponent component = Component.translatable("tooltip.tensura_mf.magic_incubator.magic_energy", valueText).withStyle(ChatFormatting.BLUE);
        graphics.renderTooltip(this.font, component, mouseX, mouseY);
    }

    private void renderEnergy(GuiGraphics graphics, int x, int y) {
        int height = (this.menu).getEnergyState();
        graphics.blit(this.getBackground(), x + 18, y + 80 - height, 0, this.imageHeight + 14, 13, height);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int pMouseX, int pMouseY) {
        RenderHelper.drawCenteredText(graphics, this.font, this.title, 88, this.titleLabelY + 1, Color.WHITE.getRGB(), false);
        RenderHelper.drawCenteredText(graphics, this.font, Component.translatable("tensura.kiln.smeltery_label"), 210, this.titleLabelY + 9, Color.WHITE.getRGB(), false);
        RenderHelper.drawCenteredText(graphics, this.font, this.playerInventoryTitle, 89, this.inventoryLabelY + 2, 4210752, false);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return this.minecraft != null && this.minecraft.options.keySwapOffhand.matches(pKeyCode, pScanCode) ? true : super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}
