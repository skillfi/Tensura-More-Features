package com.github.skillfi.tensura_mf.data;

import com.github.skillfi.tensura_mf.TensuraMf;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TensuraMfItemTags {

    public static TagKey<Item> LOW_MAGISTEEL_ITEMS = modTag("low_magisteel_items");
    public static TagKey<Item> HIGH_MAGISTEEL_ITEMS = modTag("high_magisteel_items");
    public static TagKey<Item> PURE_MAGISTEEL_ITEMS = modTag("pure_magisteel_items");
    public static TagKey<Item> MITHRIL_ITEMS = modTag("mithril_items");
    public static TagKey<Item> ORICHALCUM_ITEMS = modTag("orichalcum_items");
    public static TagKey<Item> IRON_ITEMS = modTag("iron_items");

    static TagKey<Item> modTag(String name) {
        return create(TensuraMf.create(name));
    }

    static TagKey<Item> neoforgeTag(String name) {
        return create(ResourceLocation.fromNamespaceAndPath("neoforge", name));
    }

    static TagKey<Item> vanillaTag(String name) {
        return create(ResourceLocation.withDefaultNamespace(name));
    }

    static TagKey<Item> create(ResourceLocation name) {
        return TagKey.create(Registries.ITEM, name);
    }
}
