package com.github.skillfi.tensura_mf.registry.recipe;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.recipe.MagicIncubationRecipe;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class TensuraMfRecipes {
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES;
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;
    public static final RegistrySupplier<RecipeType<MagicIncubationRecipe>> MAGIC_INCUBATION_TYPE;
    public static final RegistrySupplier<RecipeSerializer<MagicIncubationRecipe>> MAGIC_INCUBATION_SERIALIZER;

    public static void init() {
        RECIPE_TYPES.register();
        RECIPE_SERIALIZERS.register();
    }

    static {
        RECIPE_TYPES = DeferredRegister.create(TensuraMf.MOD_ID, Registries.RECIPE_TYPE);
        MAGIC_INCUBATION_TYPE = RECIPE_TYPES.register("magic_incubation", MagicIncubationRecipe.Type::new);
        RECIPE_SERIALIZERS = DeferredRegister.create(TensuraMf.MOD_ID, Registries.RECIPE_SERIALIZER);
        MAGIC_INCUBATION_SERIALIZER = RECIPE_SERIALIZERS.register("magic_incubation", MagicIncubationRecipe.Serializer::new);
    }
}
