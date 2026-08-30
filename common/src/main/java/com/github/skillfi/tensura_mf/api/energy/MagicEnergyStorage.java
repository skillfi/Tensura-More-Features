package com.github.skillfi.tensura_mf.api.energy;

/** A small loader-independent API for blocks that can store magic energy. */
public interface MagicEnergyStorage {
    int getMagicEnergy();
    int getMaxMagicEnergy();
    int receiveMagicEnergy(int amount);
    int extractMagicEnergy(int amount);
}
