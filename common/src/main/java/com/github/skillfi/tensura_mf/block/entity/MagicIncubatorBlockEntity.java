package com.github.skillfi.tensura_mf.block.entity;

import com.github.skillfi.tensura_mf.block.MagicIncubatorBlock;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import com.github.skillfi.tensura_mf.menu.MagiculeIncubatorMenu;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MagicIncubatorBlockEntity extends AbstractMagicIncubatorBlockEntity {

    public MagicIncubatorBlockEntity(BlockPos pos, BlockState state) {
        super(TensuraMfBlocksEntities.MAGICAL_INCUBATOR.get(), pos, state);
        if (getIncubatorType() == MagicIncubatorBlock.IncubatorType.NORMAL) maxMagicEnergy = 1000.0F;
        if (getIncubatorType() == MagicIncubatorBlock.IncubatorType.MITHRIL) maxMagicEnergy = 5000.0F;
        if (getIncubatorType() == MagicIncubatorBlock.IncubatorType.ORICHALCUM) maxMagicEnergy = 10000.0F;
        if (getIncubatorType() == MagicIncubatorBlock.IncubatorType.HIHIIROKANE) maxMagicEnergy = 656100.0F;
    }

    // region Menu & Naming
    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.tensura_mf.magicule_incubator");
    }

    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        this.saveAdditional(tag, provider);
        return tag;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return this.createMenu(i, inventory, inventory.player);
    }

    public @NotNull AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MagiculeIncubatorMenu(id, inventory, this, container, containerData);
    }
    // endregion

    // region NBT Serialization
    protected void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        ContainerHelper.saveAllItems(nbt, this.items, provider);
        nbt.putInt(INCUBATION_PROGRESS, this.incubationProgress);
        nbt.putInt(MAX_INCUBATION_PROGRESS, this.maxIncubationProgress);
        nbt.putFloat(MAGIC_ENERGY, this.magicEnergy);
        nbt.putFloat(MAX_MAGIC_ENERGY, this.maxMagicEnergy);
        if (networkId!=null) nbt.putUUID("NetworkId", networkId);
        if (id!=null) nbt.putUUID("BlockId", id);
        if (ownerId!=null) nbt.putUUID("OwnerId", ownerId);
    }

    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items, provider);
        incubationProgress = nbt.getInt(INCUBATION_PROGRESS);
        if (nbt.contains(MAX_INCUBATION_PROGRESS)) {
            maxIncubationProgress = nbt.getInt(MAX_INCUBATION_PROGRESS);
        }
        if (nbt.contains(MAGIC_ENERGY)) {
            magicEnergy = nbt.getFloat(MAGIC_ENERGY);
        }
        if (nbt.contains(MAX_MAGIC_ENERGY)) {
            maxMagicEnergy = nbt.getFloat(MAX_MAGIC_ENERGY);
        }
        if (nbt.hasUUID("NetworkId")) networkId = nbt.getUUID("NetworkId");
        if (nbt.hasUUID("BlockId")) id = nbt.getUUID("BlockId");
        if (nbt.hasUUID("OwnerId")) ownerId = nbt.getUUID("OwnerId");
    }
    // endregion


    // region Network Synchronization

    @Override
    public void setNetworkId(UUID networkId) {
        this.networkId = networkId;
        markDirty();
    }
    // endregion

}
