package com.github.skillfi.tensura_mf.block.entity;

import com.github.skillfi.tensura_mf.api.energy.INetworkEntry;
import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.api.energy.NetworkType;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/** A pipe has no storage of its own; it moves one magic energy unit per network tick. */
public class PipeBlockEntity extends BlockEntity implements INetworkEntry {
    private static final int TRANSFER_PER_TICK = 1;
    @Getter
    public UUID networkId = null;
    @Getter
    public UUID id = null;
    /** Set when a client-visible energy or inventory update must be sent. */
    public boolean needUpdate;
    @Getter
    public Map<UUID, List<Network>> network = new HashMap<>();
    @Getter
    public UUID ownerId;
    @Getter
    public NetworkType networkType;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(TensuraMfBlocksEntities.PIPE.get(), pos, state);
        id = UUID.randomUUID();
        networkType = NetworkType.PIPE;
    }

    public static BlockEntityTicker<PipeBlockEntity> createTickerHelper() {
        return PipeBlockEntity::serverTick;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, PipeBlockEntity blockEntity) {
        if (level.isClientSide) {
            return;
        }

        INetwork iNetwork = TensuraMfStorages.getNetworkFrom(level);
        if (iNetwork.isInNetwork(pos)) {
            Network network1 = iNetwork.getNetwork(pos);
            blockEntity.setNetworkId(network1.networkId);
        }

        if (blockEntity.needUpdate) {
            blockEntity.setChanged();
            level.sendBlockUpdated(pos, state, state, 2);
            blockEntity.resetDirty();
        }
    }

    public LivingEntity getOwner(ServerLevel level){
        return ownerId == null ? null : level.getPlayerByUUID(ownerId);
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        markDirty();
    }

    @Override
    public void setNetworkType(NetworkType type) {
        this.networkType = type;
        markDirty();
    }

    // region Dirty State Management
    public void markDirty() {
        needUpdate = true;
    }

    @ApiStatus.Experimental
    public void resetDirty() {
        needUpdate = false;
    }
    // endregion

    @Override
    public void setNetworkId(UUID id) {
        this.networkId = id;
        markDirty();
    }

    // region NBT Serialization
    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        if (networkId!=null) nbt.putUUID("NetworkId", networkId);
        if (id!=null) nbt.putUUID("BlockId", id);
        if (ownerId!=null) nbt.putUUID("OwnerId", ownerId);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        if (nbt.hasUUID("NetworkId")) networkId = nbt.getUUID("NetworkId");
        if (nbt.hasUUID("BlockId")) id = nbt.getUUID("BlockId");
        if (nbt.hasUUID("OwnerId")) ownerId = nbt.getUUID("OwnerId");
    }
    // endregion

    // region Network Synchronization
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    // endregion
}
