package com.github.skillfi.tensura_mf.api.energy;

import lombok.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Network {

    public static final String MAGIC_AMOUNT_TAG = "MagicAmount";
    public static final String MAX_MAGIC_AMOUNT_TAG = "MaxMagicAmount";
    @Setter @NonNull public UUID ownerId;
    @Setter @NonNull public UUID networkId;
    @Setter @NonNull public Float magicAmount;
    @Setter @NonNull public Float maxMagicAmount;
    public Set<NetworkEntry> connections;

    public static final String OWNER_ID = "OwnerId";
    public static final String NETWORK_ID = "NetworkId";
    private static final String CONNECTIONS = "Connections";

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Network network)) return false;
        return Objects.equals(networkId, network.networkId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(networkId);
    }

    public CompoundTag toNBT(CompoundTag compoundTag){
        compoundTag.putUUID(OWNER_ID, getOwnerId());
        compoundTag.putUUID(NETWORK_ID, getNetworkId());
        compoundTag.putFloat(MAGIC_AMOUNT_TAG, getMagicAmount());
        compoundTag.putFloat(MAX_MAGIC_AMOUNT_TAG, getMaxMagicAmount());
        ListTag connections = new ListTag();
        for (NetworkEntry networkEntry : getConnections()) {
            CompoundTag networkT = new CompoundTag();
            connections.add(networkEntry.toNBT(networkT));
        }
        compoundTag.put(CONNECTIONS, connections);
        return compoundTag;
    }

    public static Network fromNBT(CompoundTag tag){
        UUID ownerId = null;
        UUID networkId = null;
        Float magicAmount = Float.NaN;
        Float maxMagicAmount = Float.NaN;
        Set<NetworkEntry> connections = new HashSet<>();
        if (tag.hasUUID(OWNER_ID))  ownerId = tag.getUUID(OWNER_ID);
        if (tag.hasUUID(NETWORK_ID)) networkId = tag.getUUID(NETWORK_ID);
        if (tag.contains(MAGIC_AMOUNT_TAG)) magicAmount = tag.getFloat(MAGIC_AMOUNT_TAG);
        if (tag.contains(MAX_MAGIC_AMOUNT_TAG)) maxMagicAmount = tag.getFloat(MAX_MAGIC_AMOUNT_TAG);
        if (tag.contains(CONNECTIONS)){
            ListTag connectionsTag = tag.getList(CONNECTIONS, 10);
            if (!connectionsTag.isEmpty()){
                for (Tag entrytag : connectionsTag) {
                    connections.add(NetworkEntry.fromNBT((CompoundTag) entrytag));
                }
            }
        }
        return new Network(ownerId, networkId, magicAmount, maxMagicAmount, connections);
    }


}
