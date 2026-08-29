package com.github.skillfi.tensura_mf.neoforge.data.world;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.neoforge.data.TensuraMfBiomeModification;
import lombok.NoArgsConstructor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@NoArgsConstructor
public class TensuraMfBiomeModifiers {

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        addSpawns(context);
    }

    private static void addSpawns(BootstrapContext<BiomeModifier> context) {
        TensuraMfBiomeModification.initMobSpawning();

        for(TensuraMfBiomeModification.MobSpawningTemplate template : TensuraMfBiomeModification.MOB_SPAWNING) {
            registerSpawn(context, template.biome(), template.type(), template.weight(), template.minPack(), template.maxPack(), template.budget(), template.charge());
        }

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, TensuraMf.create(name));
    }

    private static void registerSpawn(BootstrapContext<BiomeModifier> context, TagKey<Biome> biome, EntityType<?> entityType, int weight, int min, int max, double budget, double charge) {
        HolderSet<Biome> holderSet = context.lookup(Registries.BIOME).getOrThrow(biome);
        registerSpawn(context, holderSet, entityType, weight, min, max);
        registerSpawnCost(context, holderSet, entityType, budget, charge);
    }

    private static void registerSpawn(BootstrapContext<BiomeModifier> context, HolderSet<Biome> biome, EntityType<?> type, int weight, int min, int max) {
        context.register(registerKey(BuiltInRegistries.ENTITY_TYPE.getKey(type).getPath() + "_spawn"), BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(biome, new MobSpawnSettings.SpawnerData(type, weight, min, max)));
    }

    private static void registerSpawnCost(BootstrapContext<BiomeModifier> context, HolderSet<Biome> biome, EntityType<?> type, double budget, double charge) {
        Holder.Reference<EntityType<?>> holder = context.lookup(Registries.ENTITY_TYPE).getOrThrow((ResourceKey)BuiltInRegistries.ENTITY_TYPE.getResourceKey(type).get());
        context.register(registerKey(BuiltInRegistries.ENTITY_TYPE.getKey(type).getPath() + "_spawn_cost"), new BiomeModifiers.AddSpawnCostsBiomeModifier(biome, HolderSet.direct(new Holder[]{holder}), new MobSpawnSettings.MobSpawnCost(budget, charge)));
    }
}
