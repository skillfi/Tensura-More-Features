package com.github.skillfi.tensura_mf.mixin;

import io.github.manasmods.tensura.data.existence.TensuraEntityExistenceData;
import io.github.manasmods.tensura.data.existence.EntityExistenceData;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.registry.magic.AspectualMagics;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import net.minecraft.data.worldgen.BootstrapContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin({TensuraEntityExistenceData.class})
public class MixinTensuraEntityExistenceData {
    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void tensuraMf$registerArchDaemon(BootstrapContext<EntityExistenceData> context, CallbackInfo callbackInfo) {
        TensuraEntityExistenceData.register(context, EntityExistenceData.getDefault(
                MonsterEntityTypes.ARCH_DAEMON.getId(),
                2000, 80000, 140000, 500, 1000,
                List.of(
                        ExtraSkills.MAGIC_SENSE.getId(),
                        IntrinsicSkills.POSSESSION.getId(),
                        ResistanceSkills.MAGIC_RESISTANCE.getId(),
                        AspectualMagics.MUD_SPEARS.getId(),
                        AspectualMagics.STONE_SHOT.getId(),
                        AspectualMagics.FIRE_BALL.getId(),
                        AspectualMagics.ICE_BREAKER.getId(),
                        AspectualMagics.THUNDER_ORB.getId(),
                        AspectualMagics.ACID_SHELL.getId(),
                        AspectualMagics.WATER_JAIL.getId(),
                        AspectualMagics.TORNADO_BLADE.getId(),
                        AspectualMagics.REINFORCED_BARRIER.getId()),
                TensuraRaces.DEVIL_LORD.get().getRegistryName()));
    }
}
