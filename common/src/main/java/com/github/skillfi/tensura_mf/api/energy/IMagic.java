package com.github.skillfi.tensura_mf.api.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/** A small loader-independent API for blocks that can store magic energy. */
public interface IMagic {
    UUID getNetworkId();
    UUID getId();
    Float getMagicEnergy();
    Float getMaxMagicEnergy();
    void setMagicEnergy(Float magicEnergy);
    boolean receive(Level level, BlockState state, BlockPos blockPos);
    // region Dirty State Management
    void markDirty();
    @ApiStatus.Experimental
    void resetDirty();
    // endregion

}
