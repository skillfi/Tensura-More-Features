package com.github.skillfi.tensura_mf.api.data;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public interface IResourceEntity<T extends LivingEntity> extends IResource {
    int getId();

    String getClassName();

    ResourceLocation getResource();

    default Component getName() {
        ResourceLocation resourceLocation = this.getResource();
        MutableComponent mutableComponent;
        if (resourceLocation == null) {
            mutableComponent = null;
        } else {
            String resourceLocationNamespace = resourceLocation.getNamespace();
            mutableComponent = Component.translatable(resourceLocationNamespace + "." + this.getClassName().toLowerCase() + "." + resourceLocation.getPath().replace('/', '.'));
        }

        return mutableComponent;
    }

    default String getNameTranslationKey() {
        return ((TranslatableContents) Objects.requireNonNull(this.getName()).getContents()).getKey();
    }
}
