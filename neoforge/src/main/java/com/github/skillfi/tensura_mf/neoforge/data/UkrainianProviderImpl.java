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

/** Generates Ukrainian translations declared on registry fields. */
public class UkrainianProviderImpl extends TensuraMfLanguageProvider {
    public UkrainianProviderImpl(PackOutput output) {
        super(output, "uk_ua");
    }

    @Override
    public void addTranslations() {
        addEntities();
        addItems();
        add(TensuraMfBlocks.MAGICULE_INCUBATOR.get(), "Інкубатор магікул");
        add("item.tensura_mf.magicule_incubator", "Інкубатор магікул");
        add(TensuraMfBlocks.PIPE.get(), "Труба магічної енергії");
        add("item.tensura_mf.pipe", "Труба магічної енергії");
        add("container.tensura_mf.magicule_incubator", "Інкубатор магікул");
        add("analysis.tensura_mf.magic_energy", "Магічна енергія: %s");
    }

    private void addEntities() {
        for (Field field : MonsterEntityTypes.class.getFields()) {
            Language.Ukrainian annotation = field.getAnnotation(Language.Ukrainian.class);
            if (annotation == null) {
                continue;
            }

            try {
                Object value = field.get(null);
                if (!(value instanceof Supplier<?>)) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                Supplier<? extends EntityType<?>> supplier = (Supplier<? extends EntityType<?>>) value;
                addEntityType(supplier, annotation.value());
            } catch (IllegalAccessException ignored) {
                // Ignore inaccessible registry fields.
            }
        }
    }

    private void addItems() {
        for (Field field : TensuraMfSpawnEggs.class.getFields()) {
            Language.Ukrainian annotation = field.getAnnotation(Language.Ukrainian.class);
            if (annotation == null) {
                continue;
            }

            try {
                Object value = field.get(null);
                if (value instanceof Holder<?> holder && holder.value() instanceof Item item) {
                    add(item, annotation.value());
                }
            } catch (IllegalAccessException ignored) {
                // Ignore inaccessible registry fields.
            }
        }
    }
}
