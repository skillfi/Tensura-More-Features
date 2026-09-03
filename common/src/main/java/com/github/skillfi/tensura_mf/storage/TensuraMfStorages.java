package com.github.skillfi.tensura_mf.storage;

import lombok.NonNull;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class TensuraMfStorages {

    public static void init() {
        NetworkStorage.init();
    }

    public static INetwork getNetworkFrom(@NonNull Level level) {
        if (level == null) {
            throw new NullPointerException("level is marked non-null but is null");
        } else {
            StorageKeyGuard.requireInitialized();
            INetwork storage = level.manasCore$getStorage(NetworkStorage.getKey());
            return storage;
        }
    }

    private static final class StorageKeyGuard {
        private static void requireInitialized() {
            if (NetworkStorage.getKey() == null) {
                throw new IllegalStateException("NetworkStorage has not been registered yet");
            }
        }
    }
}
