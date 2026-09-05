package com.github.skillfi.tensura_mf.api.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

/** A small loader-independent API for blocks that can store magic energy. */
public interface IMagic extends INetworkEntry{
    Float getMagicEnergy();
    Float getMaxMagicEnergy();
    void setMagicEnergy(Float magicEnergy);
    void receive(Level level, BlockState state, BlockPos blockPos, UUID networkId);
}
