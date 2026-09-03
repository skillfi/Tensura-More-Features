package com.github.skillfi.tensura_mf.event;

import com.github.skillfi.tensura_mf.block.entity.MagicEngineBlockEntity;
import com.github.skillfi.tensura_mf.block.entity.MagicIncubatorBlockEntity;
import com.github.skillfi.tensura_mf.block.entity.PipeBlockEntity;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class TensuraMfBlockEvents {
    public static Event<EnergyTickEvent> ENERGY_TICK_PRE = EventFactory.createLoop(new EnergyTickEvent[0]);
    public static Event<EnergyTickEvent> ENERGY_TICK_POST = EventFactory.createLoop(new EnergyTickEvent[0]);
    public static Event<EnergyReceiveEvent> ENERGY_RECEIVE = EventFactory.createEventResult(new EnergyReceiveEvent[0]);
    public static Event<EnergyTransferEvent> ENERGY_TRANSFER = EventFactory.createEventResult(new EnergyTransferEvent[0]);
    public static Event<EnergyGenerateEvent> ENERGY_GENERATE = EventFactory.createLoop(new EnergyGenerateEvent[0]);
    public static Event<EnergyCheckEvent> ENERGY_CHECK = EventFactory.createCompoundEventResult(new EnergyCheckEvent[0]);
    public static Event<EnergyConsumptionEvent> ENERGY_CONSUMPTION = EventFactory.createLoop(new EnergyConsumptionEvent[0]);


    @FunctionalInterface
    public interface EnergyTickEvent {
        void tick(ServerLevel serverLevel, BlockState state, BlockPos pos, UUID networkId);
    }

    @FunctionalInterface
    public interface EnergyReceiveEvent {
        EventResult receive(ServerLevel serverLevel, BlockState state, BlockPos pos, float amount, UUID networkId, MagicIncubatorBlockEntity blockEntity);
    }

    @FunctionalInterface
    public interface EnergyTransferEvent {
        EventResult transfer(ServerLevel serverLevel, BlockState state, BlockPos pos, float amount, UUID networkId, PipeBlockEntity pipeBlockEntity);
    }

    @FunctionalInterface
    public interface EnergyGenerateEvent {
        void generate(ServerLevel serverLevel, BlockState state, BlockPos pos, float amount, UUID networkId, MagicEngineBlockEntity blockEntity);
    }

    @FunctionalInterface
    public interface EnergyCheckEvent {
        CompoundEventResult<Float> get(ServerLevel serverLevel, BlockState state, BlockPos pos, UUID networkId, LivingEntity livingEntity);
    }

    @FunctionalInterface
    public interface EnergyConsumptionEvent {
        void get(ServerLevel serverLevel, BlockState state, BlockPos pos, UUID networkId, Float amount, LivingEntity livingEntity);
    }
}
