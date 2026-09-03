package com.github.skillfi.tensura_mf.storage;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.api.energy.NetworkEntry;
import com.github.skillfi.tensura_mf.api.energy.NetworkType;
import com.github.skillfi.tensura_mf.event.TensuraMfBlockEvents;
import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageEvents;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.tensura.Tensura;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class NetworkStorage extends Storage implements INetwork {
    public static final String NETWORKS = "Networks";
    public static final String LOADED = "Loaded";
    @Getter
    private static StorageKey<NetworkStorage> key = null;
    @Getter
    private Set<Network> networks = new HashSet<>();
    @Getter
    private boolean loaded = false;
    private static final Logger LOG = Tensura.createLogger(NetworkStorage.class);
    protected NetworkStorage(Level  holder) {
        super(holder);
    }

    protected Level getOwner() {
        return (Level) this.holder;
    }

    public static void init() {
        StorageEvents.REGISTER_WORLD_STORAGE.register((registry) -> {
            ResourceLocation resourceLocation = TensuraMf.create("network_storage");
            key = registry.register(resourceLocation, NetworkStorage.class, level -> true, NetworkStorage::new);
        });
    }

    @Override
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
        markDirty();
    }

    @Override
    public void addToNetwork(UUID networkId, NetworkEntry connection) {
        if (!networks.isEmpty() && connection.getNetworkType() != NetworkType.GENERATOR){
            networks.forEach(network -> {
                if (network.getNetworkId().equals(networkId)) {
                    network.getConnections().add(connection);
                    markDirty();
                }
            });
        }
        markDirty();
    }

    @Override
    public void createNetwork(UUID ownerId, UUID networkId, Float magicEnergy, Float maxMagicEnergy, NetworkEntry networkEntry) {
        if (networks.isEmpty()) {
            Set<NetworkEntry> connections = new HashSet<>();
            connections.add(networkEntry);
            networks.add(new Network(ownerId, networkId, magicEnergy, maxMagicEnergy, connections));
            markDirty();
        }
        if (networkEntry.getNetworkType() == NetworkType.GENERATOR) {
            Set<NetworkEntry> connections = new HashSet<>();
            connections.add(networkEntry);
            networks.add(new Network(ownerId, networkId, magicEnergy, maxMagicEnergy, connections));
            markDirty();
        }
    }

    @Override
    public boolean isInNetwork(BlockPos pos){
        AtomicBoolean InNetwork = new AtomicBoolean(false);
        getNetworks().forEach(network -> network.getConnections().forEach(networkEntry -> {
            if (networkEntry.getBlockPos().equals(pos)) InNetwork.getAndSet(true);
        }));
        return InNetwork.get();
    }

    @Override
    public Network getNetwork(BlockPos pos) {
        AtomicReference<Network> atomicReference = new AtomicReference<>();
        getNetworks().forEach(network -> network.getConnections().forEach(networkEntry -> {
            if (networkEntry.getBlockPos().equals(pos)) atomicReference.getAndSet(network);
        }));
        return atomicReference.get();
    }

    @Override
    public void removeFromNetwork(UUID networkId, NetworkEntry connection) {
        if (!networks.isEmpty()) {
            networks.forEach(network -> {
                if (network.getNetworkId().equals(networkId)) {
                    network.getConnections().remove(connection);
                }
            });
        }
        markDirty();
    }

    @Override
    public void setMagicEnergy(UUID networkID, Float magicEnergy) {
        networks.forEach(network -> {
            if (network.getNetworkId().equals(networkID)) network.setMagicAmount(magicEnergy);
        });
        markDirty();
    }

    @Override
    public float getMagicEnergy(ServerLevel level, BlockState state, BlockPos pos, UUID networkId) {
        return TensuraMfBlockEvents.ENERGY_CHECK.invoker().get(level, state, pos, networkId).object();
    }

    @Override
    public float getMagicEnergy(UUID networkId) {
        if (networks.stream().anyMatch(network -> network.getNetworkId().equals(networkId))) return networks.stream().filter(network -> network.getNetworkId().equals(networkId)).findFirst().get().getMagicAmount();
        return 0.0F;
    }

    @Override
    public void consumptionMagicEnergy(ServerLevel level, BlockState state, BlockPos pos, UUID networkId, float amount) {
        TensuraMfBlockEvents.ENERGY_CONSUMPTION.invoker().get(level, state, pos, networkId, amount);
    }


    @Override
    public void save(CompoundTag data) {
        ListTag networkTag = new ListTag();
        networks.forEach(network -> networkTag.add(network.toNBT(new CompoundTag())));
        data.put(NETWORKS, networkTag);
        data.putBoolean(LOADED, loaded);
    }

    @Override
    public void load(CompoundTag data) {
        if (data.contains(NETWORKS)) {
            networks.clear();
            ListTag networkTag = data.getList(NETWORKS, 10);
            if (!networkTag.isEmpty()) {
                networkTag.forEach(tag -> networks.add(Network.fromNBT((CompoundTag) tag)));
            }
        }
        if (data.contains(LOADED)) loaded = data.getBoolean(LOADED);
    }
}
