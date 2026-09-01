package com.github.skillfi.tensura_mf.recipe;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.block.entity.MagiculeIncubatorBlockEntity;
import com.github.skillfi.tensura_mf.recipe.input.MagicIncubationRecipeInput;
import com.github.skillfi.tensura_mf.registry.recipe.TensuraMfRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@RequiredArgsConstructor
@ToString
@Getter
public class MagicIncubationRecipe implements Recipe<MagicIncubationRecipeInput> {
    private final Ingredient input;
    private final int incubationTick;
    private final int magicAmount;
    private final ItemStack output;

    @Override
    public boolean matches(MagicIncubationRecipeInput recipeInput, Level level) {
        ItemStack inputStack = recipeInput.getItem(0).copy();
        if (!this.input.test(inputStack)) {
            return false;
        }
        return recipeInput.magicAmount() >= magicAmount;
    }

    @Override
    public @NotNull ItemStack assemble(MagicIncubationRecipeInput recipeInput, HolderLookup.Provider provider) {
        return this.getResultItem(provider);
    }

    public void assembleIncubation(Level level, MagiculeIncubatorBlockEntity container, HolderLookup.Provider provider){
        incubation(level, container, magicAmount);
    }

    private void incubation(Level level, MagiculeIncubatorBlockEntity container, int amount ){
        container.setItem(1, getOutput());
        container.setMagicEnergy(-amount);
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.getOutput().copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return TensuraMfRecipes.MAGIC_INCUBATION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return TensuraMfRecipes.MAGIC_INCUBATION_TYPE.get();
    }

    @NoArgsConstructor
    public static class Serializer implements RecipeSerializer<MagicIncubationRecipe> {
        private static final MapCodec<MagicIncubationRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(MagicIncubationRecipe::getInput),
                Codec.INT.optionalFieldOf("incubationTick", 100).forGetter(MagicIncubationRecipe::getIncubationTick),
                Codec.INT.optionalFieldOf("magicAmount", 0).forGetter(MagicIncubationRecipe::getMagicAmount),
                ItemStack.CODEC.fieldOf("output").forGetter(MagicIncubationRecipe::getOutput))
                .apply(instance, MagicIncubationRecipe::new));

        public @NotNull MapCodec<MagicIncubationRecipe> codec() {
            return CODEC;
        }

        public @NotNull StreamCodec<RegistryFriendlyByteBuf, MagicIncubationRecipe> streamCodec() {
            return StreamCodec.composite(Ingredient.CONTENTS_STREAM_CODEC, MagicIncubationRecipe::getInput,
                    ByteBufCodecs.INT, MagicIncubationRecipe::getIncubationTick,
                    ByteBufCodecs.INT, MagicIncubationRecipe::getMagicAmount,
                    ItemStack.STREAM_CODEC, MagicIncubationRecipe::getOutput,
                    MagicIncubationRecipe::new);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Type implements RecipeType<MagicIncubationRecipe> {

    }


    @RequiredArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
    public static class Builder {
        private final ItemStack output;
        private Ingredient input;
        private int incubationTick;
        private int magicAmount;

        public Builder requires(Ingredient ingredient) {
            this.input = ingredient;
            return this;
        }

        public Builder incubationTick(int incubationTick) {
            this.incubationTick = incubationTick;
            return this;
        }

        public Builder magicInput(int amount) {
            this.magicAmount = amount;
            return this;
        }

        public static Builder of(Item item) {
            return of(item.getDefaultInstance());
        }

        public static Builder of(Supplier<? extends Item> item) {
            return of(item.get());
        }

        public void build(RecipeOutput output, ResourceLocation id) {
            SpecialRecipeBuilder.special((category) -> new MagicIncubationRecipe(this.input == null ? Ingredient.EMPTY : this.input, this.incubationTick,this.magicAmount, this.output)).save(output, id);
        }

        public void build(RecipeOutput output) {
            ResourceLocation location = BuiltInRegistries.ITEM.getKey(this.output.getItem());
            this.build(output, ResourceLocation.fromNamespaceAndPath(TensuraMf.MOD_ID, "incubation/" + location.getPath()));
        }
    }
}
