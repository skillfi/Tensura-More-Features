package com.github.skillfi.tensura_mf.block.entity;

import com.github.skillfi.tensura_mf.api.energy.IGenerator;
import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.api.energy.NetworkType;
import com.github.skillfi.tensura_mf.block.MagicEngineGeneratorBlock;
import com.github.skillfi.tensura_mf.event.TensuraMfBlockEvents;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import io.github.manasmods.tensura.util.MagicEngineHelper;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/** MagicEngine block entity replacement that exposes its Magicule buffer to Pipe networks. */
public class MagicEngineBlockEntity extends AbstractEnergyBlockEntity implements IGenerator {
    public static final int MAX_MAGICULE = 1000;
    private static final int GENERATION_PER_TICK = 1;
    public static final String MAGIC_ENERGY = "MagicEnergy";
    public static final String MAX_MAGIC_ENERGY = "MaxMagicEnergy";
    public static final String NETWORK_ID = "NetworkId";
    public static final String OWNER_ID = "Owner";
    public static final String BLOCK_ID = "BlockId";
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
    @Getter
    public NetworkType networkType;
    public MagicEngineBlockEntity(BlockPos pos, BlockState state) {
        super(TensuraMfBlocksEntities.MAGIC_ENGINE.get(), pos, state);
        this.tracked = false;
        this.magicEnergy = Float.NaN;
        this.maxMagicEnergy = 1000.0F;
        this.id = UUID.randomUUID();
        this.networkType = NetworkType.GENERATOR;
    }

    public void setLevel(Level level) {
        super.setLevel(level);
        if (!level.isClientSide() && this.getBlockState().getValue(MagicEngineGeneratorBlock.ENABLED)) {
            this.setTracked(true);
        }
    }

    @Override
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
        markDirty();
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
        if (!(level instanceof ServerLevel serverLevel)) return;

        INetwork iNetwork = TensuraMfStorages.getNetworkFrom(level);


        if (iNetwork.isInNetwork(pos)
                && state.hasProperty(MagicEngineGeneratorBlock.ENABLED)
                && state.getValue(MagicEngineGeneratorBlock.ENABLED)) {
            Network network1 = iNetwork.getNetwork(pos);
            engine.setNetworkId(network1.networkId);
            engine.generate(level, state, pos, network1.networkId);
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
    public void generate(Level level, BlockState state, BlockPos blockPos, UUID networkId) {
        TensuraMfBlockEvents.ENERGY_GENERATE.invoker().generate((ServerLevel) level, state, blockPos, GENERATION_PER_TICK, networkId);
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
        if (networkId!=null) nbt.putUUID(NETWORK_ID, networkId);
        if (id!=null) nbt.putUUID(BLOCK_ID, id);
        if (ownerId!=null) nbt.putUUID(OWNER_ID, ownerId);
    }

    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        if (nbt.hasUUID(MAGIC_ENERGY)) magicEnergy = nbt.getFloat(MAGIC_ENERGY);
        maxMagicEnergy = nbt.getFloat(MAX_MAGIC_ENERGY);
        if (nbt.hasUUID(NETWORK_ID)) networkId = nbt.getUUID(NETWORK_ID);
        if (nbt.hasUUID(BLOCK_ID)) id = nbt.getUUID(BLOCK_ID);
        if (nbt.hasUUID(OWNER_ID)) ownerId = nbt.getUUID(OWNER_ID);
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
    public void receive(Level level, BlockState state, BlockPos blockPos, UUID networkId) {
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
