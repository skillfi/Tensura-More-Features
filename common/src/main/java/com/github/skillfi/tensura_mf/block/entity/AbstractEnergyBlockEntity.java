package com.github.skillfi.tensura_mf.block.entity;

import com.github.skillfi.tensura_mf.api.energy.MagicEnergyStorage;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** Base implementation for container block entities that store magic energy. */
public abstract class AbstractEnergyBlockEntity extends BaseContainerBlockEntity implements MagicEnergyStorage {

    public static final int ENERGY_SLOT_IDX = 0;
    public static final int CONTAINER_DATA_SIZE = 1;
    public static final int INVENTORY_SIZE = 2;
    public static final String ENERGY_TAG = "MagicEnergy";

    /** The block entity's own inventory, exposed for menus and automation. */
    @Getter
    protected final Container container = this;

    /** One synchronized menu value containing the current magic energy. */
    @Getter
    public final ContainerData containerData = new ContainerData() {
        private final int[] dataArray = new int[CONTAINER_DATA_SIZE];
        @Override
        public int get(int index) {
            switch (index){
                case ENERGY_SLOT_IDX -> {
                    return magicEnergy;
                }
                default -> {
                    return 0;
                }
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index){
                case ENERGY_SLOT_IDX -> magicEnergy = value;
            }
            dataArray[index] = value;
        }

        @Override
        public int getCount() {
            return CONTAINER_DATA_SIZE;
        }
    };
    private NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);


    @Getter
    private int magicEnergy;

    @Getter
    private final int maxMagicEnergy;

    /** Set when a client-visible energy or inventory update must be sent. */
    public boolean needUpdate;

    protected AbstractEnergyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,
                                        int maxMagicEnergy) {
        super(type, pos, state);
        if (maxMagicEnergy < 0) {
            throw new IllegalArgumentException("maxMagicEnergy must not be negative");
        }
        this.maxMagicEnergy = maxMagicEnergy;
    }

    @Override
    public int receiveMagicEnergy(int amount) {
        if (amount <= 0) {
            return 0;
        }

        int accepted = Math.min(amount, maxMagicEnergy - magicEnergy);
        if (accepted > 0) {
            setMagicEnergy(getMagicEnergy() + accepted);
        }
        return accepted;
    }

    @Override
    public int extractMagicEnergy(int amount) {
        if (amount <= 0) {
            return 0;
        }

        int extracted = Math.min(amount, magicEnergy);
        if (extracted > 0) {
            setMagicEnergy(magicEnergy - extracted);
        }
        return extracted;
    }

    public int addMagicEnergy(int amount) {
        return receiveMagicEnergy(amount);
    }

    /** Backwards-compatible name for callers using the material terminology. */
    public int addMagicMaterialAmount(int amount) {
        return addMagicEnergy(amount);
    }

    public void setMagicEnergy(int amount) {
        int clamped = Math.max(0, Math.min(maxMagicEnergy, amount));
        if (magicEnergy != clamped) {
            magicEnergy = clamped;
            needUpdate = true;
            setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt(ENERGY_TAG, magicEnergy);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        magicEnergy = Math.max(0, Math.min(maxMagicEnergy, tag.getInt(ENERGY_TAG)));
        needUpdate = true;
    }
}
