package com.github.skillfi.tensura_mf.storage;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.event.TensuraMfBlockEvents;
import dev.architectury.event.events.common.PlayerEvent;
import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageEvents;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.tensura.Tensura;
import io.github.manasmods.tensura.storage.ability.AbilityStorage;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.*;

public class NetworkStorage extends Storage implements INetwork {
    @Getter
    private static StorageKey<NetworkStorage> key = null;
    @Getter
    private Map<UUID, Set<Network>> network = new HashMap<>();
    @Getter
    private Map<UUID, Float> magicEnergy = new HashMap<>();
    @Getter
    private float maxMagicEnergy = 1000;
    private static final Logger LOG = Tensura.createLogger(NetworkStorage.class);
    protected NetworkStorage(LivingEntity  holder) {
        super(holder);
    }

    protected LivingEntity getOwner() {
        return (LivingEntity) this.holder;
    }

    public static void init() {
        StorageEvents.REGISTER_ENTITY_STORAGE.register((registry) -> {
            ResourceLocation resourceLocation = TensuraMf.create("network_storage");
            Objects.requireNonNull(Player.class);
            key = registry.register(resourceLocation, NetworkStorage.class, Player.class::isInstance, target -> new NetworkStorage((LivingEntity)target));
        });
        PlayerEvent.PLAYER_JOIN.register((player) -> {

        });
    }

    @Override
    public void addToNetwork(UUID networkId, Network connection) {
        network.computeIfAbsent(networkId, id -> new HashSet<>()).add(connection);
        markDirty();
    }

    @Override
    public void removeFromNetwork(UUID networkId, Network connection) {
        Set<Network> entries = network.get(networkId);
        if (entries != null) {
            network.remove(networkId, connection);
        }
        markDirty();
    }

    @Override
    public void setMagicEnergy(UUID networkID, Float magicEnergy) {
        this.magicEnergy.put(networkID, magicEnergy);
        markDirty();
    }

    @Override
    public float getMagicEnergy(ServerLevel level, BlockState state, BlockPos pos, UUID networkId) {
        return TensuraMfBlockEvents.ENERGY_CHECK.invoker().get(level, state, pos, networkId, getOwner()).object();
    }

    @Override
    public void consumptionMagicEnergy(ServerLevel level, BlockState state, BlockPos pos, UUID networkId, float amount) {
        TensuraMfBlockEvents.ENERGY_CONSUMPTION.invoker().get(level, state, pos, networkId, amount, getOwner());
    }


    @Override
    public void save(CompoundTag data) {
        ListTag networkTag = new ListTag();
        for (Map.Entry<UUID, Set<Network>> entry : getNetwork().entrySet()) {
            CompoundTag networkCompound = new CompoundTag();
            networkCompound.putUUID("NetworkId", entry.getKey());

            ListTag connections = new ListTag();
            for (Network network1 : entry.getValue()) {
                CompoundTag networkT = new CompoundTag();
                connections.add(network1.toNBT(networkT));
            }

            networkCompound.put("Connections", connections);
            networkTag.add(networkCompound);
        }
        data.put("Network", networkTag);
        ListTag energyTag = new ListTag();
        for (Map.Entry<UUID, Float> entry: getMagicEnergy().entrySet()){
            CompoundTag energyValue = new CompoundTag();
            energyValue.putFloat("MagicEnergy", entry.getValue());
            energyValue.putUUID("NetworkId", entry.getKey());
            energyTag.add(energyValue);
        }
        data.put("magicEnergy", energyTag);
    }

    @Override
    public void load(CompoundTag data) {
        if (data.contains("Network")) {
            network.clear();
            ListTag networkTag = data.getList("Network", 10);
            if (!networkTag.isEmpty()) {
                for (Tag connectionTag : networkTag) {
                    CompoundTag connection = (CompoundTag) connectionTag;
                    UUID networkId = connection.getUUID("NetworkId");
                    ListTag values = connection.getList("Connections", 10);

                    Set<Network> entries = network.computeIfAbsent(networkId, id -> new HashSet<>());

                    if (!values.isEmpty()) {
                        for (Tag valueTag : values) {
                            entries.add(Network.fromNBT((CompoundTag) valueTag));
                        }
                    }
                }
            }
        }
        if (data.contains("magicEnergy")) {
            ListTag energyTag = data.getList("magicEnergy", 10);
            if (energyTag != null && !energyTag.isEmpty()){
                magicEnergy.clear();
                energyTag.forEach(tag -> {
                    UUID networkId = ((CompoundTag)tag).getUUID("NetworkId");
                    Float amount = ((CompoundTag)tag).getFloat("MagicEnergy");
                    magicEnergy.putIfAbsent(networkId, amount);
                });
            }
        }
    }
}
