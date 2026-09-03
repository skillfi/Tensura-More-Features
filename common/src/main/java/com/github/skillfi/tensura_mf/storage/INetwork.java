package com.github.skillfi.tensura_mf.storage;

import com.github.skillfi.tensura_mf.api.energy.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface INetwork {

    Map<UUID, Set<Network>> getNetwork();
    void addToNetwork(UUID networkId, Network connection);
    void removeFromNetwork(UUID networkId, Network connection);
    Map<UUID, Float> getMagicEnergy();
    float getMaxMagicEnergy();
    void setMagicEnergy(UUID networkId, Float magicEnergy);
    float getMagicEnergy(ServerLevel level, BlockState state, BlockPos pos, UUID networkId);
    void consumptionMagicEnergy(ServerLevel level, BlockState state, BlockPos pos, UUID networkId, float amount);
    void markDirty();
}
