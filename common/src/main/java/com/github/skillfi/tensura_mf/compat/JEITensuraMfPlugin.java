package com.github.skillfi.tensura_mf.compat;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.client.screen.MagiculeIncubatorScreen;
import com.github.skillfi.tensura_mf.recipe.MagicIncubationRecipe;
import com.github.skillfi.tensura_mf.registry.recipe.TensuraMfRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class JEITensuraMfPlugin implements IModPlugin {

    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(TensuraMf.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MagicIncubationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<MagicIncubationRecipe> meltingRecipes = recipeManager.getAllRecipesFor(TensuraMfRecipes.MAGIC_INCUBATION_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(MagicIncubationRecipeCategory.MAGIC_INCUBATION_RECIPE_TYPE, meltingRecipes);
    }

    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MagiculeIncubatorScreen.class, 87, 9, 30, 14, MagicIncubationRecipeCategory.MAGIC_INCUBATION_RECIPE_TYPE);
    }
}
