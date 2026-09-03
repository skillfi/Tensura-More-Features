package com.github.skillfi.tensura_mf.handler;

import com.github.skillfi.tensura_mf.TensuraMf;
import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.block.PipeBlock;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
public class BlockHandlers {

    public static void init(){
        BlockEvent.PLACE.register(((level, pos, state, placer) -> {
            Block block = level.getBlockState(pos).getBlock();
            INetwork iNetwork = TensuraMfStorages.getNetworkFrom((LivingEntity) placer);
            if (level.getBlockEntity(pos) instanceof MagicEngineBlockEntity engineBlock && engineBlock.getNetworkId() == null) {
                UUID newNetwork = UUID.randomUUID();
                engineBlock.setNetworkId(newNetwork);
                engineBlock.setOwnerId(placer.getUUID());
                iNetwork.addToNetwork(newNetwork, new Network(engineBlock.id, engineBlock.getBlockPos(), engineBlock.getName().getString()));
                iNetwork.setMagicEnergy(newNetwork, 0.0F);
            }
            if (level.getBlockEntity(pos) instanceof MagicIncubatorBlockEntity blockEntity) {
                blockEntity.setOwnerId(placer.getUUID());
            }
            if (level.getBlockEntity(pos) instanceof PipeBlockEntity blockEntity) {
                blockEntity.setOwnerId(placer.getUUID());
            }


            UUID foundNetworkId = null;
            UUID neighborBlockId = null;
            BlockPos foundNeighborPos = null;
            String foundBlockName = null;
            Optional<UUID> currentNetwork = iNetwork.getNetwork().keySet().stream().findFirst();
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BlockEntity be = level.getBlockEntity(neighborPos);

                AtomicReference<UUID> neighborNetworkId = new AtomicReference<>();
                UUID currentBlockId = null;
                String currentBlockName = null;

                if (be instanceof MagicEngineBlockEntity iGenerator) {
                    neighborNetworkId.set(currentNetwork.orElse(iGenerator.getNetworkId()));
                    currentBlockId = iGenerator.id;
                    currentBlockName = iGenerator.getName().getString();
                } else if (be instanceof MagicIncubatorBlockEntity iReceiver) {
                    neighborNetworkId.set(currentNetwork.orElse(iReceiver.getNetworkId()));
                    currentBlockId = iReceiver.id;
                    currentBlockName = TensuraMf.MOD_ID + ".block.magic_incubator";
                } else if (be instanceof PipeBlockEntity iPipe) {
                    neighborNetworkId.set(currentNetwork.orElse(iPipe.getNetworkId()));
                    currentBlockId = iPipe.id;
                    currentBlockName = TensuraMf.MOD_ID + ".block.pipe";
                }

                if (neighborNetworkId.get() != null) {
                    foundNetworkId = neighborNetworkId.get();
                    neighborBlockId = currentBlockId;
                    foundNeighborPos = neighborPos;
                    foundBlockName = currentBlockName;
                    break;
                }
            }

            UUID networkId = foundNetworkId;

            if (neighborBlockId != null && foundNeighborPos != null) {
                iNetwork.addToNetwork(currentNetwork.orElse(UUID.randomUUID()), new Network(neighborBlockId, foundNeighborPos, foundBlockName));
            }

            BlockEntity selfBe = level.getBlockEntity(pos);
            if (selfBe instanceof MagicEngineBlockEntity iGenerator) {
                iGenerator.setNetworkId(currentNetwork.orElse(UUID.randomUUID()));
                iNetwork.addToNetwork(currentNetwork.orElse(UUID.randomUUID()), new Network(iGenerator.id, pos, iGenerator.getName().getString()));
                return EventResult.interruptTrue();
            } else if (selfBe instanceof MagicIncubatorBlockEntity iReceiver) {
                iReceiver.setNetworkId(currentNetwork.orElse(UUID.randomUUID()));
                iNetwork.addToNetwork(currentNetwork.orElse(UUID.randomUUID()), new Network(iReceiver.id, pos, TensuraMf.MOD_ID + ".block.magic_incubator"));
                return EventResult.interruptTrue();
            } else if (selfBe instanceof PipeBlockEntity iPipe) {
                iPipe.setNetworkId(currentNetwork.orElse(UUID.randomUUID()));
                iNetwork.addToNetwork(currentNetwork.orElse(UUID.randomUUID()), new Network(iPipe.id, pos, TensuraMf.MOD_ID + ".block.pipe"));
                return EventResult.interruptTrue();
            }

            return EventResult.interruptTrue();
        }));
        BlockEvent.BREAK.register(((level, pos, state, player, xp) -> {
            Block block = level.getBlockState(pos).getBlock();

            INetwork iNetwork = TensuraMfStorages.getNetworkFrom(player);
            Optional<UUID> currentNetwork = iNetwork.getNetwork().keySet().stream().findFirst();
            if (level.getBlockEntity(pos) instanceof PipeBlockEntity iPipe) {
                Network oldNetwork = new Network(iPipe.getId(), iPipe.getBlockPos(), TensuraMf.MOD_ID + ".block.pipe");
                currentNetwork.ifPresent(uuid -> {
                    if (iPipe.getNetworkId() == uuid){
                        iNetwork.removeFromNetwork(uuid, oldNetwork);
                    }
                });
                return EventResult.interruptTrue();
            }
            if (level.getBlockEntity(pos) instanceof MagicIncubatorBlockEntity incubatorBlock) {
                Network oldNetwork = new Network(incubatorBlock.getId(), incubatorBlock.getBlockPos(), TensuraMf.MOD_ID + ".block.magic_incubator");
                currentNetwork.ifPresent(uuid -> {
                    if (incubatorBlock.getNetworkId() == uuid){
                        iNetwork.removeFromNetwork(uuid, oldNetwork);
                    }
                });
                return EventResult.interruptTrue();
            }
            if (level.getBlockEntity(pos) instanceof MagicEngineBlockEntity engineBlock) {
                Network oldNetwork = new Network(engineBlock.getId(), engineBlock.getBlockPos(), engineBlock.getName().getString());
                currentNetwork.ifPresent(uuid -> {
                    if (engineBlock.getNetworkId() == uuid){
                        iNetwork.removeFromNetwork(uuid, oldNetwork);
                    }
                });
                return EventResult.interruptTrue();
            }
            if (!(block instanceof PipeBlock)) {
                return EventResult.interruptTrue();
            }
            return EventResult.interruptTrue();
        }));
    }
}
