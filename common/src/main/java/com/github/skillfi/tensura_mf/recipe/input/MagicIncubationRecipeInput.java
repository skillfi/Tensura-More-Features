package com.github.skillfi.tensura_mf.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record MagicIncubationRecipeInput(ItemStack item, Float magicAmount) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return this.item;
    }

    @Override
    public int size() {
        return 1;
    }

    public boolean isEmpty() {
        return this.item.isEmpty();
    }
}
