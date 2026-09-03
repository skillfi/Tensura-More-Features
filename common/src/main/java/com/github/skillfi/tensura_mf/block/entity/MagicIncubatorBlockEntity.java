package com.github.skillfi.tensura_mf.block.entity;

import com.github.skillfi.tensura_mf.api.energy.IMagic;
import com.github.skillfi.tensura_mf.api.energy.IPipe;
import com.github.skillfi.tensura_mf.api.energy.IReceiver;
import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.block.MagicIncubatorBlock;
import com.github.skillfi.tensura_mf.event.TensuraMfBlockEvents;
import com.github.skillfi.tensura_mf.recipe.input.MagicIncubationRecipeInput;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import com.github.skillfi.tensura_mf.registry.recipe.TensuraMfRecipes;
import dev.architectury.event.EventResult;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import com.github.skillfi.tensura_mf.menu.MagiculeIncubatorMenu;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MagicIncubatorBlockEntity extends AbstractEnergyBlockEntity implements WorldlyContainer, StackedContentsCompatible, IReceiver {
    public static final String INCUBATION_PROGRESS = "magic_incubator.incubation_progress";
    public static final String MAX_INCUBATION_PROGRESS = "kiln.magic";
    public static final String MAGIC_ENERGY = "kiln.fuel";
    public static final String MAX_MAGIC_ENERGY = "kiln.maxFuel";
    private NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public static final int INPUT_SLOT_INDEX = 0;
    public static final int ENERGY_SLOT_IDX = 0;
    public static final int MAX_ENERGY_SLOT_IDX = 1;
    public static final int OUTPUT_SLOT_INDEX = 1;
    public static final int INCUBATION_SLOT_IDX = 3;
    public static final int MAX_INCUBATION_SLOT_IDX = 4;
    public static final int MAX_MAGIC_MATERIAL_AMOUNT = 1000;
    public static final int CONTAINER_DATA_SIZE = 3;
    private ItemStack lastInputStack = ItemStack.EMPTY;
    public static final int INVENTORY_SIZE = 2;
    @Getter
    private int incubationProgress;
    @Getter
    private int maxIncubationProgress;
    @Getter
    private Float magicEnergy;
    @Getter
    private Float maxMagicEnergy;
    @Getter
    public UUID id;
    @Getter
    public UUID ownerId;
    public final ContainerData containerData = new ContainerData() {
        private final int[] dataArray = new int[CONTAINER_DATA_SIZE];
        @Override
        public int get(int index) {
            switch (index){
                case INCUBATION_SLOT_IDX -> {
                    return incubationProgress;
                }
                case MAX_INCUBATION_SLOT_IDX -> {
                    return maxIncubationProgress;
                }
                default -> {
                    return 0;
                }
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index){
                case INCUBATION_SLOT_IDX -> incubationProgress = value;
                case MAX_INCUBATION_SLOT_IDX -> maxIncubationProgress = value;
            }
            dataArray[index] = value;
        }

        @Override
        public int getCount() {
            return CONTAINER_DATA_SIZE;
        }
    };


    public MagicIncubatorBlockEntity(BlockPos pos, BlockState state) {
        super(TensuraMfBlocksEntities.MAGICULE_INCUBATOR.get(), pos, state);
        this.incubationProgress = 0;
        this.maxIncubationProgress = 100;
        maxMagicEnergy = 1000.0F;
        magicEnergy = 0.0F;
        id = UUID.randomUUID();
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction direction) {
        return new int[]{INPUT_SLOT_INDEX, OUTPUT_SLOT_INDEX};
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return i >= 0 && i < getContainerSize() && canPlaceItem(i, itemStack);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagicIncubatorBlockEntity pEntity) {
        if (!level.isClientSide()) {
            if (pEntity.networkId != null && pEntity.getOwner((ServerLevel) level)!=null) pEntity.receive(level, state, pos);

            boolean incubationNeedsUpdate = !pEntity.getItem(0).isEmpty();
            if (incubationNeedsUpdate){
                pEntity.checkIncubationRecipe(level.registryAccess());
            }

            pEntity.updateIncubationTime();
            if (pEntity.needUpdate) {
               pEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 2);
                pEntity.resetDirty();
            }
        } else if (state.getValue(MagicIncubatorBlock.LIT)) {
            RandomSource random = level.random;
            if (random.nextFloat() < 0.11F) {
                for(int i = 0; i < random.nextInt(2) + 2; ++i) {
                    CampfireBlock.makeParticles(level, pos.above(2), state.getValue(MagicIncubatorBlock.LIT), false);
                }
            }
        }
    }

    @Override
    public boolean receive(Level level, BlockState state, BlockPos pos) {
        EventResult result = TensuraMfBlockEvents.ENERGY_RECEIVE.invoker().receive((ServerLevel) level, state, pos, 1, getNetworkId(), this);
        return result.isTrue();
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return i >= 0 && i < getContainerSize() && !getItem(i).isEmpty();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.tensura_mf.magicule_incubator");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        items = nonNullList;
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return this.createMenu(i, inventory, inventory.player);
    }

    public @NotNull AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MagiculeIncubatorMenu(id, inventory, this, container, containerData);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    public void clearContent() {
        this.items.clear();
        this.markDirty();
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        markDirty();
    }

    public void fillStackedContents(StackedContents pHelper) {
        // TODO document why this method is empty
    }

    private boolean checkFuel(Float neededAmount){
        return getMagicEnergy() >= neededAmount;
    }

    private void checkIncubationRecipe(HolderLookup.Provider provider){
        if ((this.items.get(0)).isEmpty()) {
            if (this.incubationProgress > 0) {
                this.resetIncubationProgress();
            }
        } else if (this.level != null) {
            MagicIncubationRecipeInput recipeInput = new MagicIncubationRecipeInput(this.items.get(0), getMagicEnergy());
            this.level.getRecipeManager().getRecipeFor(TensuraMfRecipes.MAGIC_INCUBATION_TYPE.get(), recipeInput, this.level)
                    .ifPresentOrElse((recipe)->{
                        if (checkFuel(recipe.value().getMagicAmount())){
                            int incubationTick = (recipe.value()).getIncubationTick();
                            if (this.maxIncubationProgress != incubationTick) {
                                this.maxIncubationProgress = incubationTick;
                            }

                            if (this.incubationProgress >= incubationTick) {
                                (recipe.value()).assembleIncubation(this.level, this, provider);
                                this.resetIncubationProgress();
                            } else {
                                ++this.incubationProgress;
                            }

                        } else {
                            this.resetIncubationProgress();
                        }

                        this.markDirty();

                    }, this::resetIncubationProgress);
        }
    }

    private void resetIncubationProgress() {
        if (this.incubationProgress > 0) {
            this.incubationProgress = 0;
            this.maxIncubationProgress = 100;
            this.lastInputStack = ItemStack.EMPTY;
            this.markDirty();
        }

    }

    private void updateIncubationTime() {
        if (this.magicEnergy > 0) {
            --this.magicEnergy;
            this.needUpdate = true;
            this.updateLitState(true);
        } else {
            this.updateLitState(false);
        }
    }

    public void setItem(int pIndex, ItemStack pStack) {
        this.items.set(pIndex, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }

        this.markDirty();
    }

    private void updateLitState(boolean lit) {
        if (this.level != null) {
            if (lit) {
                if (!this.getBlockState().getValue(MagicIncubatorBlock.LIT)) {
                    BlockState newState = this.getBlockState().setValue(MagicIncubatorBlock.LIT, true);
                    this.level.setBlock(this.getBlockPos(), newState, 3);
                    setChanged(this.level, this.getBlockPos(), newState);
                }

                BlockPos above = this.worldPosition.above();
                BlockState aboveState = this.level.getBlockState(above);
                if (!aboveState.hasProperty(MagicIncubatorBlock.LIT)) {
                    return;
                }

                if (!aboveState.getValue(MagicIncubatorBlock.LIT)) {
                    BlockState newState = aboveState.setValue(MagicIncubatorBlock.LIT, true);
                    this.level.setBlock(above, newState, 3);
                    setChanged(this.level, above, newState);
                }
            } else {
                if (this.getBlockState().getValue(MagicIncubatorBlock.LIT)) {
                    BlockState newState = this.getBlockState().setValue(MagicIncubatorBlock.LIT, false);
                    this.level.setBlock(this.getBlockPos(), newState, 3);
                    setChanged(this.level, this.getBlockPos(), newState);
                }

                BlockPos above = this.worldPosition.above();
                BlockState aboveState = this.level.getBlockState(above);
                if (!aboveState.hasProperty(MagicIncubatorBlock.LIT)) {
                    return;
                }

                if (aboveState.getValue(MagicIncubatorBlock.LIT)) {
                    BlockState newState = aboveState.setValue(MagicIncubatorBlock.LIT, false);
                    this.level.setBlock(above, newState, 3);
                    setChanged(this.level, above, newState);
                }
            }
        }
    }

    @Override
    public void setMagicEnergy(Float magicEnergy) {
        this.magicEnergy = magicEnergy;
        markDirty();
    }

    public LivingEntity getOwner(ServerLevel level){
        if (ownerId!=null) level.getPlayerByUUID(ownerId);
        return null;
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
        maxIncubationProgress = nbt.getInt(MAX_INCUBATION_PROGRESS);
        magicEnergy = nbt.getFloat(MAGIC_ENERGY);
        maxMagicEnergy = nbt.getFloat(MAX_MAGIC_ENERGY);
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

    @Override
    public void setNetworkId(UUID networkId) {
        this.networkId = networkId;
        markDirty();
    }
    // endregion
}
