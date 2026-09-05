package com.github.skillfi.tensura_mf.compat;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.client.screen.MagiculeIncubatorScreen;
import com.github.skillfi.tensura_mf.recipe.MagicIncubationRecipe;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocks;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.manasmods.tensura.registry.block.TensuraBlocks;
import lombok.Getter;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Arrays;

public class MagicIncubationRecipeCategory implements IRecipeCategory<MagicIncubationRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(TensuraMf.MOD_ID, "magic_incubator/incubation");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(TensuraMf.MOD_ID, "textures/gui/magic_incubator/jei_incubation.png");
    public static final RecipeType<MagicIncubationRecipe> MAGIC_INCUBATION_RECIPE_TYPE;
    @Getter
    private final IDrawable background;
    private final IDrawable icon;

    public MagicIncubationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 81);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TensuraMfBlocks.Items.MAGICAL_INCUBATOR.get()));
    }

    public @NotNull RecipeType<MagicIncubationRecipe> getRecipeType() {
        return MAGIC_INCUBATION_RECIPE_TYPE;
    }

    public @NotNull Component getTitle() {
        return Component.translatable("tensura_mf.jei.incubating.title");
    }

    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(IRecipeLayoutBuilder builder, MagicIncubationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 139, 15).addItemStacks(Arrays.stream(recipe.getInput().getItems())
                .map(stack -> {
                    ItemStack countedStack = stack.copy();
                    countedStack.setCount(recipe.getInputCount());
                    return countedStack;
                })
                .toList());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 139, 53).addItemStack(recipe.getOutput());
    }

    public void draw(MagicIncubationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        Level level = Minecraft.getInstance().level;
        if (level != null) {

            int height = this.getMoltenProgress(recipe.getMagicAmount());
            graphics.blit(MagiculeIncubatorScreen.background, 18, 80 - height, 0, 182, 13, height);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        }
    }

    public int getMoltenProgress(float amount) {
        if (amount > 500000) {
            amount = 500000;
        }

        return amount != 0 ? (int) (amount * 74 / 500000) : 0;
    }

    public void getTooltip(ITooltipBuilder tooltip, MagicIncubationRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            if (this.isHovering(18, 6, 13, 74, mouseX, mouseY)) {
                tooltip.add(toolTipFromMoltenMaterial((float)recipe.getMagicAmount()));
            }

        }
    }

    public static MutableComponent toolTipFromMoltenMaterial(float amount) {
        int textColor = Color.BLUE.getRGB();
        return Component.translatable("tooltip.tensura_mf.magic_incubator.magic_energy", amount).withStyle(Style.EMPTY.withColor(textColor));
    }

    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        return pMouseX >= (double)(pX - 1) && pMouseX < (double)(pX + pWidth + 1) && pMouseY >= (double)(pY - 1) && pMouseY < (double)(pY + pHeight + 1);
    }

    static {
        MAGIC_INCUBATION_RECIPE_TYPE = new RecipeType(UID, MagicIncubationRecipe.class);
    }
}
