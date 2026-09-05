package com.github.skillfi.tensura_mf.neoforge.data;

import com.github.skillfi.tensura_mf.recipe.MagicIncubationRecipe;
import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocks;
import io.github.manasmods.tensura.registry.block.TensuraBlocks;
import io.github.manasmods.tensura.registry.item.TensuraArmorItems;
import io.github.manasmods.tensura.registry.item.TensuraMaterialItems;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import io.github.manasmods.tensura.registry.item.TensuraToolItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TensuraMfRecipeProvider extends RecipeProvider {
    public TensuraMfRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        magicIncubation(recipeOutput);
        blocksAndItems(recipeOutput);
    }

    private static final float[] TIER_MAGIC = {100.0F / 81.0F, 100.0F / 9.0F, 100.0F, 900.0F, 8100.0F, 72900.0F};

    private void magicIncubation(RecipeOutput output) {
        magicIncubation(output, TensuraBlocks.Items.MAGIC_ORE.get(), Items.IRON_ORE, 1, 250.0F, "from_iron_ore");

        ItemLike[] nuggets = {TensuraMaterialItems.LOW_MAGISTEEL_NUGGET.get(), TensuraMaterialItems.HIGH_MAGISTEEL_NUGGET.get(), TensuraMaterialItems.PURE_MAGISTEEL_NUGGET.get(), TensuraMaterialItems.MITHRIL_NUGGET.get(), TensuraMaterialItems.ORICHALCUM_NUGGET.get(), TensuraMaterialItems.HIHIIROKANE_NUGGET.get()};
        ItemLike[] ingots = {TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get(), TensuraMaterialItems.HIGH_MAGISTEEL_INGOT.get(), TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get(), TensuraMaterialItems.MITHRIL_INGOT.get(), TensuraMaterialItems.ORICHALCUM_INGOT.get(), TensuraMaterialItems.HIHIIROKANE_INGOT.get()};
        registerNuggets(output, nuggets, ingots);

        ItemLike[][] groups = {
                {TensuraBlocks.Items.LOW_MAGISTEEL_BLOCK.get(), TensuraBlocks.Items.HIGH_MAGISTEEL_BLOCK.get(), TensuraBlocks.Items.PURE_MAGISTEEL_BLOCK.get(), TensuraBlocks.Items.MITHRIL_BLOCK.get(), TensuraBlocks.Items.ORICHALCUM_BLOCK.get(), TensuraBlocks.Items.HIHIIROKANE_BLOCK.get()},
                {TensuraMaterialItems.LOW_MAGISTEEL_BONE_GOLEM.get(), TensuraMaterialItems.HIGH_MAGISTEEL_BONE_GOLEM.get(), TensuraMaterialItems.PURE_MAGISTEEL_BONE_GOLEM.get(), TensuraMaterialItems.MITHRIL_BONE_GOLEM.get(), TensuraMaterialItems.ORICHALCUM_BONE_GOLEM.get(), TensuraMaterialItems.HIHIIROKANE_BONE_GOLEM.get()},
                {TensuraToolItems.LOW_MAGISTEEL_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_SWORD.get(), TensuraToolItems.PURE_MAGISTEEL_SWORD.get(), TensuraToolItems.MITHRIL_SWORD.get(), TensuraToolItems.ORICHALCUM_SWORD.get(), TensuraToolItems.HIHIIROKANE_SWORD.get()},
                {TensuraToolItems.LOW_MAGISTEEL_SHORT_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_SHORT_SWORD.get(), TensuraToolItems.PURE_MAGISTEEL_SHORT_SWORD.get(), TensuraToolItems.MITHRIL_SHORT_SWORD.get(), TensuraToolItems.ORICHALCUM_SHORT_SWORD.get(), TensuraToolItems.HIHIIROKANE_SHORT_SWORD.get()},
                {TensuraToolItems.LOW_MAGISTEEL_LONG_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_LONG_SWORD.get(), TensuraToolItems.PURE_MAGISTEEL_LONG_SWORD.get(), TensuraToolItems.MITHRIL_LONG_SWORD.get(), TensuraToolItems.ORICHALCUM_LONG_SWORD.get(), TensuraToolItems.HIHIIROKANE_LONG_SWORD.get()},
                {TensuraToolItems.LOW_MAGISTEEL_GREAT_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_GREAT_SWORD.get(), TensuraToolItems.PURE_MAGISTEEL_GREAT_SWORD.get(), TensuraToolItems.MITHRIL_GREAT_SWORD.get(), TensuraToolItems.ORICHALCUM_GREAT_SWORD.get(), TensuraToolItems.HIHIIROKANE_GREAT_SWORD.get()},
                {TensuraToolItems.LOW_MAGISTEEL_KATANA.get(), TensuraToolItems.HIGH_MAGISTEEL_KATANA.get(), TensuraToolItems.PURE_MAGISTEEL_KATANA.get(), TensuraToolItems.MITHRIL_KATANA.get(), TensuraToolItems.ORICHALCUM_KATANA.get(), TensuraToolItems.HIHIIROKANE_KATANA.get()},
                {TensuraToolItems.LOW_MAGISTEEL_KODACHI.get(), TensuraToolItems.HIGH_MAGISTEEL_KODACHI.get(), TensuraToolItems.PURE_MAGISTEEL_KODACHI.get(), TensuraToolItems.MITHRIL_KODACHI.get(), TensuraToolItems.ORICHALCUM_KODACHI.get(), TensuraToolItems.HIHIIROKANE_KODACHI.get()},
                {TensuraToolItems.LOW_MAGISTEEL_TACHI.get(), TensuraToolItems.HIGH_MAGISTEEL_TACHI.get(), TensuraToolItems.PURE_MAGISTEEL_TACHI.get(), TensuraToolItems.MITHRIL_TACHI.get(), TensuraToolItems.ORICHALCUM_TACHI.get(), TensuraToolItems.HIHIIROKANE_TACHI.get()},
                {TensuraToolItems.LOW_MAGISTEEL_ODACHI.get(), TensuraToolItems.HIGH_MAGISTEEL_ODACHI.get(), TensuraToolItems.PURE_MAGISTEEL_ODACHI.get(), TensuraToolItems.MITHRIL_ODACHI.get(), TensuraToolItems.ORICHALCUM_ODACHI.get(), TensuraToolItems.HIHIIROKANE_ODACHI.get()},
                {TensuraToolItems.LOW_MAGISTEEL_SPEAR.get(), TensuraToolItems.HIGH_MAGISTEEL_SPEAR.get(), TensuraToolItems.PURE_MAGISTEEL_SPEAR.get(), TensuraToolItems.MITHRIL_SPEAR.get(), TensuraToolItems.ORICHALCUM_SPEAR.get(), TensuraToolItems.HIHIIROKANE_SPEAR.get()},
                {TensuraToolItems.LOW_MAGISTEEL_SCYTHE.get(), TensuraToolItems.HIGH_MAGISTEEL_SCYTHE.get(), TensuraToolItems.PURE_MAGISTEEL_SCYTHE.get(), TensuraToolItems.MITHRIL_SCYTHE.get(), TensuraToolItems.ORICHALCUM_SCYTHE.get(), TensuraToolItems.HIHIIROKANE_SCYTHE.get()},
                {TensuraToolItems.LOW_MAGISTEEL_PICKAXE.get(), TensuraToolItems.HIGH_MAGISTEEL_PICKAXE.get(), TensuraToolItems.PURE_MAGISTEEL_PICKAXE.get(), TensuraToolItems.MITHRIL_PICKAXE.get(), TensuraToolItems.ORICHALCUM_PICKAXE.get(), TensuraToolItems.HIHIIROKANE_PICKAXE.get()},
                {TensuraToolItems.LOW_MAGISTEEL_AXE.get(), TensuraToolItems.HIGH_MAGISTEEL_AXE.get(), TensuraToolItems.PURE_MAGISTEEL_AXE.get(), TensuraToolItems.MITHRIL_AXE.get(), TensuraToolItems.ORICHALCUM_AXE.get(), TensuraToolItems.HIHIIROKANE_AXE.get()},
                {TensuraToolItems.LOW_MAGISTEEL_SHOVEL.get(), TensuraToolItems.HIGH_MAGISTEEL_SHOVEL.get(), TensuraToolItems.PURE_MAGISTEEL_SHOVEL.get(), TensuraToolItems.MITHRIL_SHOVEL.get(), TensuraToolItems.ORICHALCUM_SHOVEL.get(), TensuraToolItems.HIHIIROKANE_SHOVEL.get()},
                {TensuraToolItems.LOW_MAGISTEEL_HOE.get(), TensuraToolItems.HIGH_MAGISTEEL_HOE.get(), TensuraToolItems.PURE_MAGISTEEL_HOE.get(), TensuraToolItems.MITHRIL_HOE.get(), TensuraToolItems.ORICHALCUM_HOE.get(), TensuraToolItems.HIHIIROKANE_HOE.get()},
                {TensuraToolItems.LOW_MAGISTEEL_SICKLE.get(), TensuraToolItems.HIGH_MAGISTEEL_SICKLE.get(), TensuraToolItems.PURE_MAGISTEEL_SICKLE.get(), TensuraToolItems.MITHRIL_SICKLE.get(), TensuraToolItems.ORICHALCUM_SICKLE.get(), TensuraToolItems.HIHIIROKANE_SICKLE.get()},
                {TensuraArmorItems.LOW_MAGISTEEL_HELMET.get(), TensuraArmorItems.HIGH_MAGISTEEL_HELMET.get(), TensuraArmorItems.PURE_MAGISTEEL_HELMET.get(), TensuraArmorItems.MITHRIL_HELMET.get(), TensuraArmorItems.ORICHALCUM_HELMET.get(), TensuraArmorItems.HIHIIROKANE_HELMET.get()},
                {TensuraArmorItems.LOW_MAGISTEEL_CHESTPLATE.get(), TensuraArmorItems.HIGH_MAGISTEEL_CHESTPLATE.get(), TensuraArmorItems.PURE_MAGISTEEL_CHESTPLATE.get(), TensuraArmorItems.MITHRIL_CHESTPLATE.get(), TensuraArmorItems.ORICHALCUM_CHESTPLATE.get(), TensuraArmorItems.HIHIIROKANE_CHESTPLATE.get()},
                {TensuraArmorItems.LOW_MAGISTEEL_LEGGINGS.get(), TensuraArmorItems.HIGH_MAGISTEEL_LEGGINGS.get(), TensuraArmorItems.PURE_MAGISTEEL_LEGGINGS.get(), TensuraArmorItems.MITHRIL_LEGGINGS.get(), TensuraArmorItems.ORICHALCUM_LEGGINGS.get(), TensuraArmorItems.HIHIIROKANE_LEGGINGS.get()},
                {TensuraArmorItems.LOW_MAGISTEEL_BOOTS.get(), TensuraArmorItems.HIGH_MAGISTEEL_BOOTS.get(), TensuraArmorItems.PURE_MAGISTEEL_BOOTS.get(), TensuraArmorItems.MITHRIL_BOOTS.get(), TensuraArmorItems.ORICHALCUM_BOOTS.get(), TensuraArmorItems.HIHIIROKANE_BOOTS.get()}
        };
        int[] ingotCounts = {9, 4, 2, 1, 1, 1, 2, 1, 3, 1, 4, 3, 3, 1, 1, 4, 5, 8, 7, 4, 4};
        for (int i = 0; i < groups.length; i++) {
            registerMaterialGroup(output, groups[i], ingots, ingotCounts[i]);
        }
    }

    private void registerNuggets(RecipeOutput output, ItemLike[] nuggets, ItemLike[] ingots) {
        for (int tier = 0; tier < nuggets.length; tier++) {
            registerRecipe(output, ingots[tier], nuggets[tier], 9, TIER_MAGIC[tier], "from_nuggets");
        }
        for (int tier = 1; tier < nuggets.length; tier++) {
            registerRecipe(output, nuggets[tier], nuggets[tier - 1], 1, TIER_MAGIC[tier] / 9.0F, "upgrade");
        }
    }

    private void registerMaterialGroup(RecipeOutput output, ItemLike[] items, ItemLike[] ingots, int ingotCount) {
        for (int tier = 0; tier < items.length; tier++) {
            registerRecipe(output, items[tier], ingots[tier], ingotCount, TIER_MAGIC[tier] * ingotCount, "from_ingots");
            if (tier > 0) {
                registerRecipe(output, items[tier], items[tier - 1], 1, TIER_MAGIC[tier] * ingotCount, "upgrade");
            }
        }
    }

    private void registerRecipe(RecipeOutput output, ItemLike result, ItemLike ingredient, int inputCount, float magicAmount, String suffix) {
        ResourceLocation resultId = BuiltInRegistries.ITEM.getKey(result.asItem());
        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(TensuraMf.MOD_ID, "incubation/" + resultId.getPath() + "_" + suffix);
        MagicIncubationRecipe.Builder.of(result.asItem()).requires(Ingredient.of(ingredient)).inputCount(inputCount).magicInput(magicAmount).incubationTick(Math.max(1, Math.round(magicAmount))).build(output, recipeId);
    }

    private void magicIncubation(RecipeOutput output, ItemLike result, ItemLike ingredient, int inputCount, float magicAmount, String suffix) {
        registerRecipe(output, result, ingredient, inputCount, magicAmount, suffix);
    }

    private void legacyMagicIncubation(RecipeOutput recipeOutput) {
        magicIncubation(recipeOutput, TensuraBlocks.Items.MAGIC_ORE.get().getDefaultInstance(), Items.IRON_ORE.asItem(), 250.0F, 250);

        tieredMagicIncubation(recipeOutput,
                TensuraBlocks.Items.LOW_MAGISTEEL_BLOCK.get(),
                TensuraBlocks.Items.HIGH_MAGISTEEL_BLOCK.get(),
                TensuraBlocks.Items.PURE_MAGISTEEL_BLOCK.get(),
                TensuraBlocks.Items.MITHRIL_BLOCK.get(),
                TensuraBlocks.Items.ORICHALCUM_BLOCK.get(),
                TensuraBlocks.Items.HIHIIROKANE_BLOCK.get());

        magicIncubation(recipeOutput, TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get().getDefaultInstance(), Items.IRON_INGOT, 250.0F, 250);
        tieredMagicIncubation(recipeOutput,
                TensuraMaterialItems.LOW_MAGISTEEL_INGOT.get(),
                TensuraMaterialItems.HIGH_MAGISTEEL_INGOT.get(),
                TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get(),
                TensuraMaterialItems.MITHRIL_INGOT.get(),
                TensuraMaterialItems.ORICHALCUM_INGOT.get(),
                TensuraMaterialItems.HIHIIROKANE_INGOT.get());

        magicIncubation(recipeOutput, TensuraMaterialItems.LOW_MAGISTEEL_NUGGET.get().getDefaultInstance(), Items.IRON_NUGGET, 250.0F, 250);
        tieredMagicIncubation(recipeOutput,
                TensuraMaterialItems.LOW_MAGISTEEL_NUGGET.get(),
                TensuraMaterialItems.HIGH_MAGISTEEL_NUGGET.get(),
                TensuraMaterialItems.PURE_MAGISTEEL_NUGGET.get(),
                TensuraMaterialItems.MITHRIL_NUGGET.get(),
                TensuraMaterialItems.ORICHALCUM_NUGGET.get(),
                TensuraMaterialItems.HIHIIROKANE_NUGGET.get());

        tieredMagicIncubation(recipeOutput,
                TensuraMaterialItems.LOW_MAGISTEEL_BONE_GOLEM.get(),
                TensuraMaterialItems.HIGH_MAGISTEEL_BONE_GOLEM.get(),
                TensuraMaterialItems.PURE_MAGISTEEL_BONE_GOLEM.get(),
                TensuraMaterialItems.MITHRIL_BONE_GOLEM.get(),
                TensuraMaterialItems.ORICHALCUM_BONE_GOLEM.get(),
                TensuraMaterialItems.HIHIIROKANE_BONE_GOLEM.get());

        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_SWORD.get(),
                TensuraToolItems.HIGH_MAGISTEEL_SWORD.get(),
                TensuraToolItems.PURE_MAGISTEEL_SWORD.get(),
                TensuraToolItems.MITHRIL_SWORD.get(),
                TensuraToolItems.ORICHALCUM_SWORD.get(),
                TensuraToolItems.HIHIIROKANE_SWORD.get());
        magicIncubation(recipeOutput, TensuraToolItems.LOW_MAGISTEEL_SWORD.get().getDefaultInstance(), Items.IRON_SWORD, 250.0F, 250);

        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_SHORT_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_SHORT_SWORD.get(),
                TensuraToolItems.PURE_MAGISTEEL_SHORT_SWORD.get(), TensuraToolItems.MITHRIL_SHORT_SWORD.get(),
                TensuraToolItems.ORICHALCUM_SHORT_SWORD.get(), TensuraToolItems.HIHIIROKANE_SHORT_SWORD.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_LONG_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_LONG_SWORD.get(),
                TensuraToolItems.PURE_MAGISTEEL_LONG_SWORD.get(), TensuraToolItems.MITHRIL_LONG_SWORD.get(),
                TensuraToolItems.ORICHALCUM_LONG_SWORD.get(), TensuraToolItems.HIHIIROKANE_LONG_SWORD.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_GREAT_SWORD.get(), TensuraToolItems.HIGH_MAGISTEEL_GREAT_SWORD.get(),
                TensuraToolItems.PURE_MAGISTEEL_GREAT_SWORD.get(), TensuraToolItems.MITHRIL_GREAT_SWORD.get(),
                TensuraToolItems.ORICHALCUM_GREAT_SWORD.get(), TensuraToolItems.HIHIIROKANE_GREAT_SWORD.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_KATANA.get(), TensuraToolItems.HIGH_MAGISTEEL_KATANA.get(),
                TensuraToolItems.PURE_MAGISTEEL_KATANA.get(), TensuraToolItems.MITHRIL_KATANA.get(),
                TensuraToolItems.ORICHALCUM_KATANA.get(), TensuraToolItems.HIHIIROKANE_KATANA.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_KODACHI.get(), TensuraToolItems.HIGH_MAGISTEEL_KODACHI.get(),
                TensuraToolItems.PURE_MAGISTEEL_KODACHI.get(), TensuraToolItems.MITHRIL_KODACHI.get(),
                TensuraToolItems.ORICHALCUM_KODACHI.get(), TensuraToolItems.HIHIIROKANE_KODACHI.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_TACHI.get(), TensuraToolItems.HIGH_MAGISTEEL_TACHI.get(),
                TensuraToolItems.PURE_MAGISTEEL_TACHI.get(), TensuraToolItems.MITHRIL_TACHI.get(),
                TensuraToolItems.ORICHALCUM_TACHI.get(), TensuraToolItems.HIHIIROKANE_TACHI.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_ODACHI.get(), TensuraToolItems.HIGH_MAGISTEEL_ODACHI.get(),
                TensuraToolItems.PURE_MAGISTEEL_ODACHI.get(), TensuraToolItems.MITHRIL_ODACHI.get(),
                TensuraToolItems.ORICHALCUM_ODACHI.get(), TensuraToolItems.HIHIIROKANE_ODACHI.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_SPEAR.get(), TensuraToolItems.HIGH_MAGISTEEL_SPEAR.get(),
                TensuraToolItems.PURE_MAGISTEEL_SPEAR.get(), TensuraToolItems.MITHRIL_SPEAR.get(),
                TensuraToolItems.ORICHALCUM_SPEAR.get(), TensuraToolItems.HIHIIROKANE_SPEAR.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_SCYTHE.get(), TensuraToolItems.HIGH_MAGISTEEL_SCYTHE.get(),
                TensuraToolItems.PURE_MAGISTEEL_SCYTHE.get(), TensuraToolItems.MITHRIL_SCYTHE.get(),
                TensuraToolItems.ORICHALCUM_SCYTHE.get(), TensuraToolItems.HIHIIROKANE_SCYTHE.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_PICKAXE.get(), TensuraToolItems.HIGH_MAGISTEEL_PICKAXE.get(),
                TensuraToolItems.PURE_MAGISTEEL_PICKAXE.get(), TensuraToolItems.MITHRIL_PICKAXE.get(),
                TensuraToolItems.ORICHALCUM_PICKAXE.get(), TensuraToolItems.HIHIIROKANE_PICKAXE.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_AXE.get(), TensuraToolItems.HIGH_MAGISTEEL_AXE.get(),
                TensuraToolItems.PURE_MAGISTEEL_AXE.get(), TensuraToolItems.MITHRIL_AXE.get(),
                TensuraToolItems.ORICHALCUM_AXE.get(), TensuraToolItems.HIHIIROKANE_AXE.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_SHOVEL.get(), TensuraToolItems.HIGH_MAGISTEEL_SHOVEL.get(),
                TensuraToolItems.PURE_MAGISTEEL_SHOVEL.get(), TensuraToolItems.MITHRIL_SHOVEL.get(),
                TensuraToolItems.ORICHALCUM_SHOVEL.get(), TensuraToolItems.HIHIIROKANE_SHOVEL.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_HOE.get(), TensuraToolItems.HIGH_MAGISTEEL_HOE.get(),
                TensuraToolItems.PURE_MAGISTEEL_HOE.get(), TensuraToolItems.MITHRIL_HOE.get(),
                TensuraToolItems.ORICHALCUM_HOE.get(), TensuraToolItems.HIHIIROKANE_HOE.get());
        tieredMagicIncubation(recipeOutput,
                TensuraToolItems.LOW_MAGISTEEL_SICKLE.get(), TensuraToolItems.HIGH_MAGISTEEL_SICKLE.get(),
                TensuraToolItems.PURE_MAGISTEEL_SICKLE.get(), TensuraToolItems.MITHRIL_SICKLE.get(),
                TensuraToolItems.ORICHALCUM_SICKLE.get(), TensuraToolItems.HIHIIROKANE_SICKLE.get());

        magicIncubation(recipeOutput, TensuraToolItems.LOW_MAGISTEEL_PICKAXE.get().getDefaultInstance(), Items.IRON_PICKAXE, 250.0F, 250);
        magicIncubation(recipeOutput, TensuraToolItems.LOW_MAGISTEEL_AXE.get().getDefaultInstance(), Items.IRON_AXE, 250.0F, 250);
        magicIncubation(recipeOutput, TensuraToolItems.LOW_MAGISTEEL_SHOVEL.get().getDefaultInstance(), Items.IRON_SHOVEL, 250.0F, 250);
        magicIncubation(recipeOutput, TensuraToolItems.LOW_MAGISTEEL_HOE.get().getDefaultInstance(), Items.IRON_HOE, 250.0F, 250);

        tieredMagicIncubation(recipeOutput,
                TensuraArmorItems.LOW_MAGISTEEL_HELMET.get(), TensuraArmorItems.HIGH_MAGISTEEL_HELMET.get(),
                TensuraArmorItems.PURE_MAGISTEEL_HELMET.get(), TensuraArmorItems.MITHRIL_HELMET.get(),
                TensuraArmorItems.ORICHALCUM_HELMET.get(), TensuraArmorItems.HIHIIROKANE_HELMET.get());
        tieredMagicIncubation(recipeOutput,
                TensuraArmorItems.LOW_MAGISTEEL_CHESTPLATE.get(), TensuraArmorItems.HIGH_MAGISTEEL_CHESTPLATE.get(),
                TensuraArmorItems.PURE_MAGISTEEL_CHESTPLATE.get(), TensuraArmorItems.MITHRIL_CHESTPLATE.get(),
                TensuraArmorItems.ORICHALCUM_CHESTPLATE.get(), TensuraArmorItems.HIHIIROKANE_CHESTPLATE.get());
        tieredMagicIncubation(recipeOutput,
                TensuraArmorItems.LOW_MAGISTEEL_LEGGINGS.get(), TensuraArmorItems.HIGH_MAGISTEEL_LEGGINGS.get(),
                TensuraArmorItems.PURE_MAGISTEEL_LEGGINGS.get(), TensuraArmorItems.MITHRIL_LEGGINGS.get(),
                TensuraArmorItems.ORICHALCUM_LEGGINGS.get(), TensuraArmorItems.HIHIIROKANE_LEGGINGS.get());
        tieredMagicIncubation(recipeOutput,
                TensuraArmorItems.LOW_MAGISTEEL_BOOTS.get(), TensuraArmorItems.HIGH_MAGISTEEL_BOOTS.get(),
                TensuraArmorItems.PURE_MAGISTEEL_BOOTS.get(), TensuraArmorItems.MITHRIL_BOOTS.get(),
                TensuraArmorItems.ORICHALCUM_BOOTS.get(), TensuraArmorItems.HIHIIROKANE_BOOTS.get());

        magicIncubation(recipeOutput, TensuraArmorItems.LOW_MAGISTEEL_HELMET.get().getDefaultInstance(), Items.IRON_HELMET, 250.0F, 250);
        magicIncubation(recipeOutput, TensuraArmorItems.LOW_MAGISTEEL_CHESTPLATE.get().getDefaultInstance(), Items.IRON_CHESTPLATE, 250.0F, 250);
        magicIncubation(recipeOutput, TensuraArmorItems.LOW_MAGISTEEL_LEGGINGS.get().getDefaultInstance(), Items.IRON_LEGGINGS, 250.0F, 250);
        magicIncubation(recipeOutput, TensuraArmorItems.LOW_MAGISTEEL_BOOTS.get().getDefaultInstance(), Items.IRON_BOOTS, 250.0F, 250);
    }

    private void tieredMagicIncubation(RecipeOutput recipeOutput, ItemLike... tiers) {
        for (int i = 1; i < tiers.length; i++) {
            int value = i * 250;
            magicIncubation(recipeOutput, tiers[i].asItem().getDefaultInstance(), tiers[i - 1].asItem(), (float) value, value);
        }
    }

    protected static void magicIncubation(RecipeOutput recipeOutput, ItemStack output, Item ingredient, Float amount, int incubationTick) {
        MagicIncubationRecipe.Builder.of(output).requires(Ingredient.of(ingredient)).magicInput(amount).incubationTick(incubationTick).build(recipeOutput);
    }

    public static ResourceLocation getRecipeName(ItemLike itemLike, String postFix) {
        ResourceLocation location = RecipeBuilder.getDefaultRecipeId(itemLike.asItem());
        String namespace = location.getNamespace();
        String locationPath = location.getPath();
        return ResourceLocation.fromNamespaceAndPath(namespace, locationPath + postFix);
    }

    private void incubatorRecipe(RecipeOutput output, ItemLike magicBlock, ItemLike pipe, ItemLike magicCrystal, ItemLike glass_pane, ItemLike block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block).
                define('#', magicBlock).
                define('$', pipe).
                define('&', magicCrystal).
                define('*', glass_pane).
                pattern("#$#").
                pattern("*&*").
                pattern("#$#").
                unlockedBy(getHasName(magicBlock), has(magicBlock)).
                save(output, getRecipeName(block, "from_" + getItemName(magicBlock)));
    }

    private void pipeRecipe(RecipeOutput output, ItemLike glass, ItemLike magisteel, ItemLike block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block).
                define('#', glass).
                define('$', magisteel).
                pattern("###").
                pattern("#$#").
                pattern("###").
                unlockedBy(getHasName(glass), has(glass)).
                save(output, getRecipeName(block, "from_" + getItemName(glass)));
    }

    private void glassRecipe(RecipeOutput output, ItemLike glass, ItemLike ingot, ItemLike block) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block).
                define('#', glass).define('$', ingot).
                pattern("###").pattern("#$#").pattern("###").
                unlockedBy(getHasName(glass), has(glass)).
                save(output, getRecipeName(block, "from_" + getItemName(ingot)));
    }

    private void blocksAndItems(RecipeOutput recipeOutput) {
        glassRecipe(recipeOutput, Blocks.GLASS, TensuraMaterialItems.HIGH_MAGISTEEL_INGOT.get(), TensuraMfBlocks.MAGISTEEL_GLASS.get());
        incubatorRecipe(recipeOutput,
                TensuraBlocks.HIGH_MAGISTEEL_BLOCK.get(),
                TensuraMfBlocks.PIPE.get(),
                TensuraMaterialItems.ELEMENT_CORE_WATER.get(),
                TensuraMfBlocks.MAGISTEEL_GLASS.get(),
                TensuraMfBlocks.MAGICAL_INCUBATOR.get());
        incubatorRecipe(recipeOutput,
                TensuraBlocks.ORICHALCUM_BLOCK.get(),
                TensuraMfBlocks.PIPE.get(),
                TensuraMaterialItems.ELEMENT_CORE_WATER.get(),
                TensuraMfBlocks.MAGISTEEL_GLASS.get(),
                TensuraMfBlocks.MAGICAL_INCUBATOR_ORICHALCUM.get());
        incubatorRecipe(recipeOutput,
                TensuraBlocks.HIHIIROKANE_BLOCK.get(),
                TensuraMfBlocks.PIPE.get(),
                TensuraMaterialItems.ELEMENT_CORE_WATER.get(),
                TensuraMfBlocks.MAGISTEEL_GLASS.get(),
                TensuraMfBlocks.MAGICAL_INCUBATOR_HIHIIROKANE.get());
        incubatorRecipe(recipeOutput,
                TensuraBlocks.MITHRIL_BLOCK.get(),
                TensuraMfBlocks.PIPE.get(),
                TensuraMaterialItems.ELEMENT_CORE_WATER.get(),
                TensuraMfBlocks.MAGISTEEL_GLASS.get(),
                TensuraMfBlocks.MAGICAL_INCUBATOR_MITHRIL.get());
        pipeRecipe(recipeOutput, TensuraMfBlocks.MAGISTEEL_GLASS.get(), TensuraMaterialItems.HIGH_MAGISTEEL_INGOT.get(), TensuraMfBlocks.PIPE.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.BRICKS_MAGIC_ENGINE.get(), 1).group("bricks_magic_engine").define('#', Items.BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S',  TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.STONE_BRICKS_MAGIC_ENGINE.get(), 1).group("stone_bricks_magic_engine").define('#', Items.STONE_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_stone_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.STONE_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.TUFF_BRICKS_MAGIC_ENGINE.get(), 1).group("tuff_bricks_magic_engine").define('#', Items.TUFF_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_tuff_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.TUFF_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.DEEPSLATE_BRICKS_MAGIC_ENGINE.get(), 1).group("deepslate_bricks_magic_engine").define('#', Items.DEEPSLATE_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_deepslate_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.DEEPSLATE_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.MUD_BRICKS_MAGIC_ENGINE.get(), 1).group("mud_bricks_magic_engine").define('#', Items.MUD_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_mud_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.MUD_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.PRISMARINE_BRICKS_MAGIC_ENGINE.get(), 1).group("prismarine_bricks_magic_engine").define('#', Items.PRISMARINE_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_prismarine_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.PRISMARINE_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE.get(), 1).group("nether_bricks_magic_engine").define('#', Items.NETHER_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_nether_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.NETHER_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.RED_NETHER_BRICKS_STONE_BRICKS_MAGIC_ENGINE.get(), 1).group("red_nether_bricks_magic_engine").define('#', Items.RED_NETHER_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_red_nether_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.RED_NETHER_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.POLISHED_BLACKSTONE_BRICKS_MAGIC_ENGINE.get(), 1).group("polished_blackstone_bricks_magic_engine").define('#', Items.POLISHED_BLACKSTONE_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_polished_blackstone_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.POLISHED_BLACKSTONE_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.QUARTZ_BRICKS_MAGIC_ENGINE.get(), 1).group("quartz_bricks_magic_engine").define('#', Items.QUARTZ_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_quartz_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.QUARTZ_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.END_STONE_BRICKS_MAGIC_ENGINE.get(), 1).group("end_stone_bricks_magic_engine").define('#', Items.END_STONE_BRICKS).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_end_stone_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.END_STONE_BRICKS}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.PURPUR_MAGIC_ENGINE.get(), 1).group("purpur_bricks_magic_engine").define('#', Items.PURPUR_BLOCK).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_purpur_block", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{Items.PURPUR_BLOCK}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.LOW_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get(), 1).group("low_quality_magic_crystal_bricks_magic_engine").define('#', TensuraBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BRICKS.get()).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_low_quality_magic_crystal_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraBlocks.LOW_QUALITY_MAGIC_CRYSTAL_BRICKS.get()}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get(), 1).group("medium_quality_magic_crystal_bricks_magic_engine").define('#', TensuraBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS.get()).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_medium_quality_magic_crystal_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraBlocks.MEDIUM_QUALITY_MAGIC_CRYSTAL_BRICKS.get()}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TensuraMfBlocks.Items.HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS_MAGIC_ENGINE.get(), 1).group("high_quality_magic_crystal_bricks_magic_engine").define('#', TensuraBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS.get()).define('I', TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()).define('S', TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()).pattern("#S#").pattern("#I#").pattern("###").unlockedBy("has_high_quality_magic_crystal_bricks", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraBlocks.HIGH_QUALITY_MAGIC_CRYSTAL_BRICKS.get()}).build()})).unlockedBy("has_magisteel_ingot", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMaterialItems.PURE_MAGISTEEL_INGOT.get()}).build()})).unlockedBy("has_magic_crystal", inventoryTrigger(new ItemPredicate[]{net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{TensuraMobDropItems.HIGH_QUALITY_MAGIC_CRYSTAL.get()}).build()})).save(recipeOutput);
    }
}
