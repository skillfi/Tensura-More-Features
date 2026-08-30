package com.github.skillfi.tensura_mf.data.existence;

import com.github.skillfi.tensura_mf.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.data.existence.EntityExistenceData;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.data.worldgen.BootstrapContext;

import java.util.List;

import static io.github.manasmods.tensura.data.existence.TensuraEntityExistenceData.register;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfEntityExistenceData {
    public static void bootstrap(BootstrapContext<EntityExistenceData> context) {
        register(context, EntityExistenceData.getDefault(
                MonsterEntityTypes.OGRE.get().arch$registryName(),
                40, 500, 1500, 500, 1500,
                List.of(), TensuraRaces.KIJIN.get().getRegistryName()));
    }
}
