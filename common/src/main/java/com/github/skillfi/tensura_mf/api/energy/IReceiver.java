package com.github.skillfi.tensura_mf.api.energy;

import net.minecraft.core.BlockPos;

import java.util.UUID;

public interface IReceiver extends IMagic{
    BlockPos getBlockPos();
    void setNetworkId(UUID networkId);
}
