package com.github.skillfi.tensura_mf.neoforge.data;

import com.github.skillfi.tensura_mf.data.annotations.Language;
import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import com.github.skillfi.tensura_mf.registry.item.TensuraMfSpawnEggs;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class EnglishProviderImpl extends TensuraMfLanguageProvider{

    public EnglishProviderImpl(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    public void addTranslations() {
        addEntities();
        addItems();
        add(TensuraMfBlocks.MAGICULE_INCUBATOR_BLOCK.get(), "Magicule Incubator");
        add("item.tensura_mf.magicule_incubator", "Magicule Incubator");
        add(TensuraMfBlocks.PIPE.get(), "Magic Energy Pipe");
        add("item.tensura_mf.pipe", "Magic Energy Pipe");
        add("container.tensura_mf.magicule_incubator", "Magicule Incubator");
    }

    private void addEntities(){
        Class<?> modMonsterEntityTypes = MonsterEntityTypes.class;

        for (Field item : modMonsterEntityTypes.getFields()) {
            Language.English annotation = item.getAnnotation(Language.English.class);
            if (annotation == null) continue;

            try {
                Object fieldValue = item.get(null);
                if (!(fieldValue instanceof Supplier<?>)) continue;

                Supplier<? extends EntityType<?>> supplier = (Supplier<? extends EntityType<?>>) fieldValue;
                addEntityType(supplier, annotation.value());
            } catch (IllegalAccessException e) {
                // Ігнорувати недоступні поля
            }
        }
    }

    private void addItems() {
        Class<?> modItems = TensuraMfSpawnEggs.class;

        for (Field item : modItems.getFields()) {
            Language.English annotation = item.getAnnotation(Language.English.class);
            if (annotation == null) continue;

            try {
                Object fieldValue = item.get(null);
                if (!(fieldValue instanceof Holder<?>)) continue;

                Holder<?> holder = (Holder<?>) fieldValue;
                if (holder.value() instanceof Item) {
                    add((Item) holder.value(), annotation.value());
                }
            } catch (IllegalAccessException e) {
                // Ігнорувати недоступні поля
            }
        }
    }
}
