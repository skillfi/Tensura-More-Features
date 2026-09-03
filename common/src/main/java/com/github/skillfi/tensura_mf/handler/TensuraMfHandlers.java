package com.github.skillfi.tensura_mf.handler;

import com.github.skillfi.tensura_mf.api.energy.IReceiver;
import com.github.skillfi.tensura_mf.event.TensuraMfBlockEvents;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfHandlers {

    public static EventResult receiveEnergy(ServerLevel serverLevel, BlockState state, BlockPos pos, Float amount, UUID networkId){
        BlockEntity blockEntity = serverLevel.getBlockEntity(pos);
        if (!(blockEntity instanceof IReceiver iReceiver)) return EventResult.interruptFalse();
        if (networkId == null || amount <= 0 || iReceiver.getMagicEnergy() >= iReceiver.getMaxMagicEnergy()) {
            return EventResult.interruptFalse();
        }
        INetwork iNetwork = TensuraMfStorages.getNetworkFrom(serverLevel);
        float currentEnergy = iNetwork.getMagicEnergy(serverLevel, state, pos, networkId);
        if (currentEnergy <= 0) return EventResult.interruptFalse();

        float received = Math.min(amount, Math.min(currentEnergy,
                iReceiver.getMaxMagicEnergy() - iReceiver.getMagicEnergy()));
        iNetwork.consumptionMagicEnergy(serverLevel, state, pos, networkId, received);
        iReceiver.setMagicEnergy(iReceiver.getMagicEnergy() + received);
        return EventResult.interruptTrue();
    }

    public static void init() {
        TensuraMfBlockEvents.ENERGY_CHECK.register((serverLevel, state, pos, networkId) -> {
            if (networkId == null) return CompoundEventResult.interruptFalse(0.0F);
            INetwork iNetwork = TensuraMfStorages.getNetworkFrom(serverLevel);
            if (iNetwork.getNetworks().stream().noneMatch(network -> network.getNetworkId().equals(networkId))) return CompoundEventResult.interruptFalse(0.0F);
            return CompoundEventResult.interruptTrue(iNetwork.getNetworks().stream().filter(network -> network.getNetworkId().equals(networkId)).findFirst().get().magicAmount);
        });
        TensuraMfBlockEvents.ENERGY_CONSUMPTION.register(((serverLevel, state, pos, networkId, amount) -> {
            if (networkId == null || amount == null || amount <= 0) return;
            INetwork iNetwork = TensuraMfStorages.getNetworkFrom(serverLevel);
            float currentEnergy = iNetwork.getNetworks().stream().filter(network -> network.getNetworkId().equals(networkId)).findFirst().get().getMagicAmount();
            if (currentEnergy <= 0) return;
            iNetwork.setMagicEnergy(networkId, Math.max(0.0F, currentEnergy - amount));
        }));
        TensuraMfBlockEvents.ENERGY_RECEIVE.register(TensuraMfHandlers::receiveEnergy);
        TensuraMfBlockEvents.ENERGY_GENERATE.register(((serverLevel, state, pos, amount, networkId) -> {
            if (networkId != null) {
                INetwork iNetwork = TensuraMfStorages.getNetworkFrom(serverLevel);
                if (iNetwork.getNetworks().stream().anyMatch(network -> network.getNetworkId().equals(networkId))) {
                    float currentEnergy = iNetwork.getMagicEnergy(networkId);
                    iNetwork.setMagicEnergy(networkId, currentEnergy + amount);
                }
            }
        }));

    }
}
