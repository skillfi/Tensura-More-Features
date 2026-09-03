package com.github.skillfi.tensura_mf.storage;

import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.api.energy.NetworkEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface INetwork {

    Set<Network> getNetworks();
    void addToNetwork(UUID networkId, NetworkEntry connection);
    void createNetwork(UUID ownerId, UUID networkId, Float magicEnergy, Float maxMagicEnergy, NetworkEntry networkEntry);
    void removeFromNetwork(UUID networkId, NetworkEntry connection);
    void setMagicEnergy(UUID networkId, Float magicEnergy);
    float getMagicEnergy(ServerLevel level, BlockState state, BlockPos pos, UUID networkId);
    float getMagicEnergy(UUID networkId);
    void consumptionMagicEnergy(ServerLevel level, BlockState state, BlockPos pos, UUID networkId, float amount);
    boolean isInNetwork(BlockPos pos);
    Network getNetwork(BlockPos pos);
    boolean isLoaded();
    void setLoaded(boolean loaded);
    void markDirty();
}
