package com.github.skillfi.tensura_mf.neoforge.data.loot;


import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class TensuraMfEntityLootProvider extends EntityLootSubProvider {
    public TensuraMfEntityLootProvider(HolderLookup.Provider lookup) {
        super(FeatureFlags.REGISTRY.allFlags(), lookup);
    }

    public void generate() {
        LootTable.Builder table = LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.LEATHER)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 3.0F)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BONE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))));
        this.add(MonsterEntityTypes.OGRE.get(), table);
        this.add(MonsterEntityTypes.KIJIN.get(), table);
        this.add(MonsterEntityTypes.MYSTIC_ONI.get(), table);
        this.add(MonsterEntityTypes.WICKED_ONI.get(), table);
        this.add(MonsterEntityTypes.SPIRIT_ONI.get(), table);
        this.add(MonsterEntityTypes.DEATH_ONI.get(), table);
        this.add(MonsterEntityTypes.DIVINE_ONI.get(), table);
        this.add(MonsterEntityTypes.DIVINE_FIGHTER.get(), table);
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return BuiltInRegistries.ENTITY_TYPE.stream().filter((type) -> BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace().equals(TensuraMf.MOD_ID));
    }
}
