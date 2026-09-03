package com.github.skillfi.tensura_mf.api.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public interface IGenerator extends IMagic{
    void generate(Level level, BlockState state, BlockPos blockPos, UUID networkId);
    BlockPos getBlockPos();
    void setNetworkId(UUID networkId);
    LivingEntity getOwner(ServerLevel level);
}
