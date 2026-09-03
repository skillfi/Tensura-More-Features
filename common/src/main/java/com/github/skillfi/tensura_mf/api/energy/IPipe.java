package com.github.skillfi.tensura_mf.api.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

public interface IPipe {
    void setNetworkId(UUID id);
    UUID getNetworkId();
    UUID getId();
    boolean transfer(Level level, BlockState state, BlockPos pos);
    BlockPos getBlockPos();
    // region Dirty State Management
    void markDirty();
    @ApiStatus.Experimental
    void resetDirty();
    // endregion
}
