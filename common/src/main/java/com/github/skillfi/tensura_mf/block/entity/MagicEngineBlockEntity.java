package com.github.skillfi.tensura_mf.block.entity;

import com.github.skillfi.tensura_mf.api.energy.IGenerator;
import com.github.skillfi.tensura_mf.api.energy.IMagic;
import com.github.skillfi.tensura_mf.api.energy.IPipe;
import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.block.MagicEngineGeneratorBlock;
import com.github.skillfi.tensura_mf.event.TensuraMfBlockEvents;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import dev.architectury.event.EventResult;
import io.github.manasmods.tensura.util.MagicEngineHelper;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** MagicEngine block entity replacement that exposes its Magicule buffer to Pipe networks. */
public class MagicEngineBlockEntity extends AbstractEnergyBlockEntity implements IGenerator {
    public static final int MAX_MAGICULE = 1000;
    private static final int GENERATION_PER_TICK = 1;
    public static final String MAGIC_ENERGY = "MagicEnergy";
    public static final String MAX_MAGIC_ENERGY = "MaxMagicEnergy";
    public int spin;
    private boolean tracked;
    private static final NonNullList<ItemStack> EMPTY_ITEMS = NonNullList.create();
    @Getter
    public Float magicEnergy;
    @Getter
    public Float maxMagicEnergy;
    @Getter
    public UUID id;
    @Getter
    public UUID ownerId;

    public MagicEngineBlockEntity(BlockPos pos, BlockState state) {
        super(TensuraMfBlocksEntities.MAGIC_ENGINE.get(), pos, state);
        this.tracked = false;
        this.magicEnergy = Float.NaN;
        this.maxMagicEnergy = 1000.0F;
        this.id = UUID.randomUUID();
    }

    public void setLevel(Level level) {
        super.setLevel(level);
        if (!level.isClientSide() && this.getBlockState().getValue(MagicEngineGeneratorBlock.ENABLED)) {
            this.setTracked(true);
        }
    }

    public LivingEntity getOwner(ServerLevel level){
        if (ownerId!=null) return level.getPlayerByUUID(ownerId);
        return null;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        markDirty();
    }

    public void setRemoved() {
        if (this.tracked) {
            this.setTracked(false);
        }

        super.setRemoved();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.tensura.magic_engine");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return EMPTY_ITEMS;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        // MagicEngine has no item inventory. Its container is intentionally empty.
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    protected @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return null;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                  MagicEngineBlockEntity engine) {
        if (state.hasProperty(MagicEngineGeneratorBlock.ENABLED)
                && state.getValue(MagicEngineGeneratorBlock.ENABLED) && engine.networkId != null) {
            engine.generate(level, state, pos);
        }

        if (engine.needUpdate) {
            engine.setChanged();
            level.sendBlockUpdated(pos, state, engine.getBlockState(), 2);
            engine.resetDirty();
        }
    }

    public void setTracked(boolean shouldTrack) {
        if (this.tracked != shouldTrack) {
            if (shouldTrack) {
                MagicEngineHelper.increment();
            } else {
                MagicEngineHelper.decrement();
            }

            this.tracked = shouldTrack;
        }
    }

    @Override
    public void generate(Level level, BlockState state, BlockPos blockPos) {
        TensuraMfBlockEvents.ENERGY_GENERATE.invoker().generate((ServerLevel) level, state, blockPos, GENERATION_PER_TICK, getNetworkId(), this);
    }

    @Override
    public void setNetworkId(UUID networkId) {
        this.networkId = networkId;
        markDirty();
    }

    // region NBT Serialization
    protected void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        if (magicEnergy > 0) nbt.putFloat(MAGIC_ENERGY, this.magicEnergy);
        nbt.putFloat(MAX_MAGIC_ENERGY, this.maxMagicEnergy);
        if (networkId!=null) nbt.putUUID("NetworkId", networkId);
        if (id!=null) nbt.putUUID("BlockId", id);
        if (ownerId!=null) nbt.putUUID("Owner", ownerId);
    }

    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        if (nbt.hasUUID(MAGIC_ENERGY)) magicEnergy = nbt.getFloat(MAGIC_ENERGY);
        maxMagicEnergy = nbt.getFloat(MAX_MAGIC_ENERGY);
        if (nbt.hasUUID("NetworkId")) networkId = nbt.getUUID("NetworkId");
        if (nbt.hasUUID("BlockId")) id = nbt.getUUID("BlockId");
        if (nbt.hasUUID("Owner")) ownerId = nbt.getUUID("Owner");
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

    @Override
    public void setMagicEnergy(Float magicEnergy) {
        this.magicEnergy  = magicEnergy;
        markDirty();
    }

    @Override
    public boolean receive(Level level, BlockState state, BlockPos blockPos) {
        return false;
    }

    @Override
    public void markDirty() {
        this.needUpdate = true;
    }

    @Override
    public void resetDirty() {
        this.needUpdate = false;
    }
    // endregion
}
