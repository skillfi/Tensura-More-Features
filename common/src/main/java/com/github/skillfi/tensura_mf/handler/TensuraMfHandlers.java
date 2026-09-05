package com.github.skillfi.tensura_mf.handler;

import com.github.skillfi.tensura_mf.api.energy.INetworkEntry;
import com.github.skillfi.tensura_mf.api.energy.IReceiver;
import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.event.TensuraMfBlockEvents;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import io.github.manasmods.tensura.event.TensuraLevelEvents;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfHandlers {

    public static CompoundEventResult<Float> receiveEnergy(ServerLevel serverLevel, BlockState state, BlockPos pos, Float amount, UUID networkId){
        BlockEntity blockEntity = serverLevel.getBlockEntity(pos);
        if (!(blockEntity instanceof IReceiver iReceiver)) return CompoundEventResult.interruptFalse(Float.NaN);
        if (networkId == null || amount <= 0 || iReceiver.getMagicEnergy() >= iReceiver.getMaxMagicEnergy()) {
            return CompoundEventResult.interruptFalse(Float.NaN);
        }
        INetwork iNetwork = TensuraMfStorages.getNetworkFrom(serverLevel);
        float currentEnergy = iNetwork.getMagicEnergy(serverLevel, state, pos, networkId);
        if (currentEnergy <= 0) return CompoundEventResult.interruptFalse(Float.NaN);

        float received = Math.min(amount, Math.min(currentEnergy,
                iReceiver.getMaxMagicEnergy() - iReceiver.getMagicEnergy()));
        iNetwork.consumptionMagicEnergy(serverLevel, state, pos, networkId, received);
        return CompoundEventResult.interruptTrue(received);
    }

    public static void init() {
        TensuraMfBlockEvents.ENERGY_CHECK.register((serverLevel, state, pos, networkId) -> {
            if (networkId == null) return CompoundEventResult.interruptFalse(0.0F);
            INetwork iNetwork = TensuraMfStorages.getNetworkFrom(serverLevel);
            Network network = iNetwork.getNetwork(pos);
            if (network == null) return CompoundEventResult.interruptFalse(0.0F);
            return CompoundEventResult.interruptTrue(network.getMagicAmount());
        });
        TensuraMfBlockEvents.ENERGY_TICK_PRE.register(serverLevel -> {
            INetwork iNetwork = TensuraMfStorages.getNetworkFrom(serverLevel);
            iNetwork.getNetworks().forEach(network -> {
                if (!network.getConnections().isEmpty()) {
                    network.getConnections().forEach(networkEntry -> {
                        if (serverLevel.getBlockEntity(networkEntry.blockPos) instanceof INetworkEntry iNetworkEntry) iNetworkEntry.setNetworkId(network.networkId);
                    });
                }
            });
        });
        TensuraMfBlockEvents.ENERGY_CONSUMPTION.register(((serverLevel, state, pos, networkId, amount) -> {
            if (networkId == null || amount == null || amount <= 0) return CompoundEventResult.interruptFalse(Float.NaN);
            INetwork iNetwork = TensuraMfStorages.getNetworkFrom(serverLevel);
            float currentEnergy = iNetwork.getNetworks().stream().filter(network -> network.getNetworkId().equals(networkId)).findFirst().get().getMagicAmount();
            if (currentEnergy <= 0) return CompoundEventResult.interruptFalse(Float.NaN);
            if (currentEnergy > amount) return CompoundEventResult.interruptTrue(amount);
            return CompoundEventResult.interruptFalse(Float.NaN);
        }));
        TensuraMfBlockEvents.ENERGY_RECEIVE.register(TensuraMfHandlers::receiveEnergy);
        TensuraMfBlockEvents.ENERGY_GENERATE.register(((serverLevel, state, pos, amount, networkId) -> {
            if (networkId != null) {
                INetwork iNetwork = TensuraMfStorages.getNetworkFrom(serverLevel);
                if (iNetwork.getNetworks().stream().anyMatch(network -> network.getNetworkId().equals(networkId))) {
                    float currentEnergy = iNetwork.getMagicEnergy(networkId);
                    float maxMagicEnergyEnergy = iNetwork.getMaxMagicEnergy(networkId);
                    if (currentEnergy < maxMagicEnergyEnergy) iNetwork.setMagicEnergy(networkId, currentEnergy + amount);
                }
            }
        }));
        TensuraLevelEvents.CHUNK_TICK_PRE.register(((serverLevel, levelChunk) -> {
            TensuraMfBlockEvents.ENERGY_TICK_PRE.invoker().tick(serverLevel);
        }));
    }
}
