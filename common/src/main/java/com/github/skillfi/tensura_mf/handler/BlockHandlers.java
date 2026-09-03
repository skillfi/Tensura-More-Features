package com.github.skillfi.tensura_mf.handler;

import com.github.skillfi.tensura_mf.api.energy.INetworkEntry;
import com.github.skillfi.tensura_mf.api.energy.NetworkEntry;
import com.github.skillfi.tensura_mf.api.energy.NetworkType;
import com.github.skillfi.tensura_mf.block.entity.MagicEngineBlockEntity;
import com.github.skillfi.tensura_mf.block.entity.MagicIncubatorBlockEntity;
import com.github.skillfi.tensura_mf.block.entity.PipeBlockEntity;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
public class BlockHandlers {

    public static void init() {
        BlockEvent.PLACE.register((level, pos, state, placer) -> {
            if (!level.isClientSide) {
                BlockEntity self = level.getBlockEntity(pos);
                NetworkType type = typeOf(self);
                if (type == null) return EventResult.interruptTrue();
                if (placer == null) return EventResult.interruptTrue();

                INetwork storage = TensuraMfStorages.getNetworkFrom(level);
                if (self instanceof INetworkEntry engineBlock) {
                    if (engineBlock.getNetworkType() == NetworkType.GENERATOR){
                        UUID newNetwork = UUID.randomUUID();
                        engineBlock.setNetworkId(newNetwork);
                        engineBlock.setOwnerId(placer.getUUID());
                        NetworkEntry networkEntry = new NetworkEntry(engineBlock.getId(), engineBlock.getBlockPos(), engineBlock.getNetworkType());
                        storage.createNetwork(placer.getUUID(), newNetwork, 0.0F, 1000.0F, networkEntry);
                        return EventResult.interruptTrue();
                    }

                    setOwnerId(self, placer.getUUID());
                    UUID networkId = findNeighborNetworkId(level, pos, storage);
                    if (networkId == null) return EventResult.interruptTrue();
                    INetworkEntry iNetworkEntry = (INetworkEntry) self;
                    setNetworkId(self, networkId);
                    NetworkEntry networkEntry = new NetworkEntry(iNetworkEntry.getId(), iNetworkEntry.getBlockPos(), iNetworkEntry.getNetworkType());
                    storage.addToNetwork(networkId, networkEntry);
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.interruptTrue();
        });

        BlockEvent.BREAK.register((level, pos, state, player, xp) -> {
            BlockEntity entity = level.getBlockEntity(pos);
            NetworkType type = typeOf(entity);
            UUID networkId = networkIdOf(entity);
            if (type != null && networkId != null) {
                INetwork storage = TensuraMfStorages.getNetworkFrom(level);
                if (entity instanceof INetworkEntry iNetworkEntry) {
                    storage.removeFromNetwork(iNetworkEntry.getNetworkId(), new NetworkEntry(iNetworkEntry.getId(), iNetworkEntry.getBlockPos(), iNetworkEntry.getNetworkType()));
                }
            }
            return EventResult.interruptTrue();
        });
    }

    private static UUID findNeighborNetworkId(Level level, BlockPos pos, INetwork storage) {
        AtomicReference<UUID> networkId = new AtomicReference<UUID>();
        for (Direction direction : Direction.values()) {
            BlockPos neighbourPos = pos.relative(direction);
            storage.getNetworks().forEach(network -> {
                Optional<NetworkEntry> neightborEntry = network.getConnections().stream().filter(networkEntry -> networkEntry.getBlockPos().equals(neighbourPos)).findFirst();
                neightborEntry.ifPresent(networkEntry -> networkId.getAndSet(network.networkId));
            });
        }
        return networkId.get();
    }

    private static NetworkType typeOf(BlockEntity entity) {
        if (entity instanceof MagicEngineBlockEntity) return NetworkType.GENERATOR;
        if (entity instanceof PipeBlockEntity) return NetworkType.PIPE;
        if (entity instanceof MagicIncubatorBlockEntity) return NetworkType.RECEIVER;
        return null;
    }

    private static UUID networkIdOf(BlockEntity entity) {
        if (entity instanceof INetworkEntry block) return block.getNetworkId();
        return null;
    }

    private static void setNetworkId(BlockEntity entity, UUID networkId) {
        if (entity instanceof PipeBlockEntity block) block.setNetworkId(networkId);
        else if (entity instanceof MagicIncubatorBlockEntity block) block.setNetworkId(networkId);
    }

    private static void setOwnerId(BlockEntity entity, UUID ownerId) {
        if (entity instanceof MagicEngineBlockEntity block) block.setOwnerId(ownerId);
        else if (entity instanceof PipeBlockEntity block) block.setOwnerId(ownerId);
        else if (entity instanceof MagicIncubatorBlockEntity block) block.setOwnerId(ownerId);
    }
}
