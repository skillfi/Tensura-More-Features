package com.github.skillfi.tensura_mf.neoforge.data;

import com.github.skillfi.tensura_mf.recipe.MagicIncubationRecipe;
import io.github.manasmods.tensura.registry.block.TensuraBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TensuraMfRecipeProvider extends RecipeProvider {
    public TensuraMfRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        magicIncubation(recipeOutput);
    }

    private void magicIncubation(RecipeOutput recipeOutput) {
        magicIncubation(recipeOutput, TensuraBlocks.Items.MAGIC_ORE.get().getDefaultInstance(), Items.IRON_ORE.asItem(), 800.0F, 500);
    }

    protected static void magicIncubation(RecipeOutput recipeOutput, ItemStack output, Item ingredient, Float amount, int incubationTick) {
        MagicIncubationRecipe.Builder.of(output).requires(Ingredient.of(ingredient)).magicInput(amount).incubationTick(incubationTick).build(recipeOutput);
    }
}
