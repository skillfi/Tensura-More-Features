package com.github.skillfi.tensura_mf.neoforge;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

import static com.github.skillfi.tensura_mf.neoforge.TensuraMfNeoForge.*;

public class TensuraMfPlatformImpl {
    private static final List<Supplier<? extends MobEffect>> MOD_EFFECT_REGISTRY = new CopyOnWriteArrayList<>();
    private static final List<Supplier<? extends Block>> BLOCK_REGISTRY = new CopyOnWriteArrayList<>();
    private static final List<Supplier<? extends Item>> ITEM_REGISTRY = new CopyOnWriteArrayList<>();
    private static final List<Supplier<BlockEntityType<? extends BlockEntity>>> BLOCK_ENTITY_TYPE_REGISTRY = new CopyOnWriteArrayList<>();
    private static final List<Supplier<MenuType<?>>> MENU_REGISTRY = new CopyOnWriteArrayList<>();
    private static final List<Supplier<ParticleType<?>>> PARTICLES_REGISTRY = new CopyOnWriteArrayList<>();
    private static final List<Supplier<EntityType<? extends Entity>>> ENTITY_REGISTRY = new CopyOnWriteArrayList<>();


    
    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String id, Supplier<BlockEntityType<T>> supplier) {
        return BLOCK_ENTITIES.register(id, supplier);
    }


    
    public static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> supplier) {
        return BLOCKS.register(id, supplier);
    }


    
    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, Supplier<EntityType<T>> entity) {
        return ENTITIES.register(id, entity);
    }


    
    public static <T extends ArmorMaterial> Supplier<T> registerArmorMaterial(String id, Supplier<T> armorMaterial) {
        return ARMOR_MATERIALS.register(id, armorMaterial);
    }


    
    public static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return ITEMS.register(id, item);
    }


    
    public static <T extends SoundEvent> Supplier<T> registerSound(String id, Supplier<T> sound) {
        return SOUND_EVENTS.register(id, sound);
    }


    
    public static <T extends CreativeModeTab> Supplier<T> registerCreativeModeTab(String id, Supplier<T> tab) {
        return CREATIVE_TABS.register(id, tab);
    }


    
    public static <E extends Mob> Supplier<SpawnEggItem> makeSpawnEggFor(Supplier<EntityType<E>> entityType, int primaryEggColour, int secondaryEggColour, Item.Properties itemProperties) {
        return () -> new DeferredSpawnEggItem(entityType, primaryEggColour, secondaryEggColour, itemProperties);
    }

    
    public CreativeModeTab.Builder newCreativeTabBuilder() {
        return CreativeModeTab.builder();
    }
}
