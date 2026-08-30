package com.github.skillfi.tensura_mf.client.screen;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.menu.MagiculeIncubatorMenu;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MagiculeIncubatorScreen extends AbstractContainerScreen<MagiculeIncubatorMenu> {
    @Getter
    public static final ResourceLocation BACKGROUND = TensuraMf.create("textures/gui/kiln/kiln_gui.png");
    public MagiculeIncubatorScreen(MagiculeIncubatorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 155;
    }

    protected void renderBg(GuiGraphics graphics, float pPartialTick, int mX, int mY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(BACKGROUND, x, y, 0, 0, 256, 155);
//        this.renderProgress(graphics, x, y);
//        this.renderFire(graphics, x, y);
//        this.renderMolten(graphics, x, y);
//        if (((KilnMenu)this.menu).kiln.hasPrevMixingRecipe()) {
//            boolean hovering = mX >= x + 69 && mX <= x + 73 && mY >= y + 40 && mY <= y + 48;
//            graphics.blit(this.getBackground(), x + 69, y + 40, 14, this.imageHeight + (hovering ? 8 : 0), 4, 8);
//        }
//
//        if (((KilnMenu)this.menu).kiln.hasNextMixingRecipe()) {
//            boolean hovering = mX >= x + 103 && mX <= x + 107 && mY >= y + 40 && mY <= y + 48;
//            graphics.blit(this.getBackground(), x + 103, y + 40, 19, this.imageHeight + (hovering ? 8 : 0), 4, 8);
//        }

    }

    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
