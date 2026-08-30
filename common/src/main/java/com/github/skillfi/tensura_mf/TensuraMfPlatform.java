package com.github.skillfi.tensura_mf;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class TensuraMfPlatform {
    
    @ExpectPlatform
    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String id, Supplier<BlockEntityType<T>> blockEntityType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends net.minecraft.world.inventory.AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String id, Supplier<MenuType<T>> menuType) {
        throw new AssertionError();
    }

    
    @ExpectPlatform
    public static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        throw new AssertionError();
    }

    
    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entity) {
        throw new AssertionError();
    }

    
    @ExpectPlatform
    public static <T extends ArmorMaterial> Supplier<T> registerArmorMaterial(String id, Supplier<T> armorMaterial) {
        throw new AssertionError();
    }

    
    @ExpectPlatform
    public static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        throw new AssertionError();
    }

    
    @ExpectPlatform
    public static <T extends SoundEvent> Supplier<T> registerSound(String id, Supplier<T> sound) {
        throw new AssertionError();
    }

    
    @ExpectPlatform
    public static <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(String id, Supplier<T> tab) {
        throw new AssertionError();
    }

    
    @ExpectPlatform
    public static <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(Supplier<EntityType<E>> entityType, int primaryEggColour, int secondaryEggColour, Item.Properties itemProperties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static CreativeModeTab.Builder newCreativeTabBuilder() {
        throw new AssertionError();
    }
}
