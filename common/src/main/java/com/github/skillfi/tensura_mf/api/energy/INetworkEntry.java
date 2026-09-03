package com.github.skillfi.tensura_mf.api.energy;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

public interface INetworkEntry {
    UUID getNetworkId();
    UUID getId();
    UUID getOwnerId();
    void setNetworkId(UUID id);
    void setOwnerId(UUID id);
    BlockPos getBlockPos();
    NetworkType getNetworkType();
    void setNetworkType(NetworkType type);
    // region Dirty State Management
    void markDirty();
    @ApiStatus.Experimental
    void resetDirty();
    // endregion
}
