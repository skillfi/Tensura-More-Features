package com.github.skillfi.tensura_mf.handler;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.api.energy.*;
import com.github.skillfi.tensura_mf.block.MagicEngineGeneratorBlock;
import com.github.skillfi.tensura_mf.block.MagicIncubatorBlock;
import com.github.skillfi.tensura_mf.block.PipeBlock;
import com.github.skillfi.tensura_mf.block.entity.MagicEngineBlockEntity;
import com.github.skillfi.tensura_mf.block.entity.MagicIncubatorBlockEntity;
import com.github.skillfi.tensura_mf.block.entity.PipeBlockEntity;
import com.github.skillfi.tensura_mf.event.TensuraMfBlockEvents;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import io.github.manasmods.tensura.block.MagicEngineBlock;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TensuraMfHandlers {

    public static EventResult receiveEnergy(ServerLevel serverLevel, BlockState state, BlockPos pos, Float amount, UUID networkId, MagicIncubatorBlockEntity blockEntity){
        INetwork iNetwork = TensuraMfStorages.getNetworkFrom(blockEntity.getOwner(serverLevel));
        if (iNetwork.getMagicEnergy(serverLevel, state, pos, networkId) > 0) return EventResult.pass();
        if (blockEntity.getMagicEnergy() < blockEntity.getMaxMagicEnergy()) {
            iNetwork.consumptionMagicEnergy(serverLevel, state, pos, networkId, amount);
            blockEntity.setMagicEnergy(blockEntity.getMagicEnergy()+amount);
            return EventResult.pass();
        } else return EventResult.interruptTrue();
    }

    public static void init() {
        TensuraMfBlockEvents.ENERGY_CHECK.register((serverLevel, state, pos, networkId, livingEntity) -> {
            INetwork iNetwork = TensuraMfStorages.getNetworkFrom(livingEntity);
            if (networkId == null) return CompoundEventResult.interruptFalse(0.0F);
            if (!iNetwork.getNetwork().containsKey(networkId)) return CompoundEventResult.interruptFalse(0.0F);
            return CompoundEventResult.interruptTrue(iNetwork.getMagicEnergy().getOrDefault(networkId, 0.0F));
        });
        TensuraMfBlockEvents.ENERGY_CONSUMPTION.register(((serverLevel, state, pos, networkId, amount, livingEntity) -> {
            INetwork iNetwork = TensuraMfStorages.getNetworkFrom(livingEntity);
            if (networkId == null) return;
            Set<Network> connections = iNetwork.getNetwork().get(networkId);
            if (connections == null || connections.isEmpty()) return;
            Optional<MagicEngineBlockEntity> generator = connections.stream()
                    .map(network -> serverLevel.getBlockEntity(network.pos()))
                    .filter(MagicEngineBlockEntity.class::isInstance)
                    .map(MagicEngineBlockEntity.class::cast)
                    .findFirst();
            generator.ifPresentOrElse(engineBlockEntity -> {
                if (engineBlockEntity.magicEnergy > 0) engineBlockEntity.setMagicEnergy(engineBlockEntity.getMagicEnergy()-amount);
            }, null);

        }));
        TensuraMfBlockEvents.ENERGY_TRANSFER.register(((serverLevel, state, pos, amount, networkId, pipeBlockEntity) -> {
            INetwork iNetwork = TensuraMfStorages.getNetworkFrom(pipeBlockEntity.getOwner(serverLevel));
            Map<UUID, Set<Network>> networks = iNetwork.getNetwork();
            if (networkId == null || !networks.containsKey(networkId)) return EventResult.interruptFalse();
            if (serverLevel.getBlockEntity(pos) instanceof IMagic iMagic){
                if (iMagic.getMagicEnergy() > 0){
                    return EventResult.pass();
                }
            }
            if (serverLevel.getBlockEntity(pos) instanceof IPipe iPipe){
                boolean hasGenerator = false;
                boolean hasIncubator = false;
                for (Map.Entry<UUID, Set<Network>> entry: networks.entrySet()){
                    hasIncubator = entry.getValue().parallelStream().anyMatch(network1 -> {
                       if (serverLevel.getBlockEntity(network1.pos()) instanceof MagicIncubatorBlockEntity) return true;
                       return false;
                    });
                    hasGenerator= entry.getValue().parallelStream().anyMatch(network1 -> {
                        if (serverLevel.getBlockEntity(network1.pos()) instanceof MagicEngineBlockEntity) return true;
                        return false;
                    });
                }
                if (hasGenerator && hasIncubator){
                    return EventResult.interruptTrue();
                }
                return EventResult.interruptFalse();
            }

            return EventResult.interruptFalse();
        }));
        TensuraMfBlockEvents.ENERGY_RECEIVE.register(TensuraMfHandlers::receiveEnergy);
        TensuraMfBlockEvents.ENERGY_GENERATE.register(((serverLevel, state, pos, amount, networkId, engineBlockEntity) -> {
            if (networkId != null) {
                INetwork iNetwork = TensuraMfStorages.getNetworkFrom(engineBlockEntity.getOwner(serverLevel));
                if (iNetwork.getNetwork().containsKey(networkId)) {
                    float currentEnergy = iNetwork.getMagicEnergy().getOrDefault(networkId, 0.0F);
                    iNetwork.setMagicEnergy(networkId, currentEnergy + amount);
                }
            }
        }));
    }
}
