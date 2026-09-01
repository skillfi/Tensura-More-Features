package com.github.skillfi.tensura_mf.block.entity;

import com.github.skillfi.tensura_mf.block.MagiculeIncubatorBlock;
import com.github.skillfi.tensura_mf.recipe.input.MagicIncubationRecipeInput;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import com.github.skillfi.tensura_mf.api.energy.MagicEnergyStorage;
import com.github.skillfi.tensura_mf.block.PipeBlock;
import com.github.skillfi.tensura_mf.registry.recipe.TensuraMfRecipes;
import io.github.manasmods.tensura.block.KilnBlock;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.HashSet;

public class MagiculeIncubatorBlockEntity extends AbstractEnergyBlockEntity implements WorldlyContainer, StackedContentsCompatible {
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
    public static final int CONTAINER_DATA_SIZE = 5;
    private ItemStack lastInputStack = ItemStack.EMPTY;
    public static final int INVENTORY_SIZE = 2;
    @Getter
    private int incubationProgress;
    @Getter
    private int maxIncubationProgress;
    @Getter
    private int magicEnergy;
    @Getter
    private int maxMagicEnergy;
    public final ContainerData containerData = new ContainerData() {
        private final int[] dataArray = new int[CONTAINER_DATA_SIZE];
        @Override
        public int get(int index) {
            switch (index){
                case ENERGY_SLOT_IDX -> {
                    return magicEnergy;
                }
                case MAX_ENERGY_SLOT_IDX -> {
                    return maxMagicEnergy;
                }
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
                case ENERGY_SLOT_IDX -> magicEnergy = value;
                case MAX_ENERGY_SLOT_IDX -> maxMagicEnergy = value;
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


    public MagiculeIncubatorBlockEntity(BlockPos pos, BlockState state) {
        super(TensuraMfBlocksEntities.MAGICULE_INCUBATOR.get(), pos, state,
                MAX_MAGIC_MATERIAL_AMOUNT);
        this.incubationProgress = 0;
        this.maxIncubationProgress = 100;
        maxMagicEnergy = 1000;
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction direction) {
        return new int[]{INPUT_SLOT_INDEX, OUTPUT_SLOT_INDEX};
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return i >= 0 && i < getContainerSize() && canPlaceItem(i, itemStack);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagiculeIncubatorBlockEntity pEntity) {
        if (!level.isClientSide()) {
            boolean incubationNeedsUpdate = pEntity.checkIncubationCache();
            if (incubationNeedsUpdate){
                pEntity.checkIncubationRecipe(level.registryAccess());
            }

            pEntity.updateIncubationTime();
            if (pEntity.needUpdate) {
                pEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 2);
                pEntity.needUpdate = false;
            }
        } else if (state.getValue(MagiculeIncubatorBlock.LIT)) {
            RandomSource random = level.random;
            if (random.nextFloat() < 0.11F) {
                for(int i = 0; i < random.nextInt(2) + 2; ++i) {
                    CampfireBlock.makeParticles(level, pos.above(2), state.getValue(MagiculeIncubatorBlock.LIT), false);
                }
            }
        }
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

    public double getTick(Object object) {
        return level == null ? 0 : level.getGameTime();
    }

    private boolean checkIncubationCache() {
        ItemStack input = getItem(INPUT_SLOT_INDEX);
        if (!ItemStack.isSameItemSameComponents(lastInputStack, input)) {
            lastInputStack = input.copy();
            return true;
        }
        return false;
    }

    public void clearContent() {
        this.items.clear();
        this.needUpdate = true;
    }

    public void fillStackedContents(StackedContents pHelper) {
    }

    private void checkIncubationRecipe(HolderLookup.Provider provider){
        if (((ItemStack)this.items.get(1)).isEmpty()) {
            if (this.incubationProgress > 0) {
                this.resetIncubationProgress();
            }
        } else if (this.level != null) {
            MagicIncubationRecipeInput recipeInput = new MagicIncubationRecipeInput(this.items.get(1), getMagicEnergy());
            this.level.getRecipeManager().getRecipeFor(TensuraMfRecipes.MAGIC_INCUBATION_TYPE.get(), recipeInput, this.level)
                    .ifPresentOrElse((recipe)->{
                        int incubationTick = (recipe.value()).getIncubationTick();
                        if (this.maxIncubationProgress != incubationTick) {
                            this.maxIncubationProgress = incubationTick;
                        }

                        if (this.incubationProgress >= incubationTick) {
                            (recipe.value()).assembleIncubation(this.level, this, provider);
                            this.resetIncubationProgress();
                        } else if ((Boolean)this.getBlockState().getValue(KilnBlock.BOOSTED)) {
                            this.incubationProgress += 2;
                        } else {
                            ++this.incubationProgress;
                        }
                        this.needUpdate = true;
                    }, this::resetIncubationProgress);
        }
    }

    private void resetIncubationProgress() {
        if (this.incubationProgress > 0) {
            this.incubationProgress = 0;
            this.maxIncubationProgress = 100;
            this.lastInputStack = ItemStack.EMPTY;
            this.needUpdate = true;
        }

    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MagiculeIncubatorBlockEntity incubator) {
        if (incubator.checkIncubationCache()) incubator.needUpdate = true;
        if (incubator.getMagicEnergy() < incubator.getMaxMagicEnergy()) {
            incubator.pullEnergyFromPipeNetwork(level, pos);
        }
        if (incubator.needUpdate) {
            incubator.setChanged();
            level.sendBlockUpdated(pos, state, incubator.getBlockState(), 2);
            incubator.needUpdate = false;
        }
    }

    public void performIncubation(HolderLookup.Provider provider) {
        if (this.level instanceof ServerLevel) {

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

        this.needUpdate = true;
    }

    private void updateLitState(boolean lit) {
        if (this.level != null) {
            if (lit) {
                if (!(Boolean)this.getBlockState().getValue(MagiculeIncubatorBlock.LIT)) {
                    BlockState newState = (BlockState)this.getBlockState().setValue(MagiculeIncubatorBlock.LIT, true);
                    this.level.setBlock(this.getBlockPos(), newState, 3);
                    setChanged(this.level, this.getBlockPos(), newState);
                }

                BlockPos above = this.worldPosition.above();
                BlockState aboveState = this.level.getBlockState(above);
                if (!aboveState.hasProperty(MagiculeIncubatorBlock.LIT)) {
                    return;
                }

                if (!(Boolean)aboveState.getValue(MagiculeIncubatorBlock.LIT)) {
                    BlockState newState = (BlockState)aboveState.setValue(MagiculeIncubatorBlock.LIT, true);
                    this.level.setBlock(above, newState, 3);
                    setChanged(this.level, above, newState);
                }
            } else {
                if ((Boolean)this.getBlockState().getValue(MagiculeIncubatorBlock.LIT)) {
                    BlockState newState = (BlockState)this.getBlockState().setValue(KilnBlock.LIT, false);
                    this.level.setBlock(this.getBlockPos(), newState, 3);
                    setChanged(this.level, this.getBlockPos(), newState);
                }

                BlockPos above = this.worldPosition.above();
                BlockState aboveState = this.level.getBlockState(above);
                if (!aboveState.hasProperty(MagiculeIncubatorBlock.LIT)) {
                    return;
                }

                if ((Boolean)aboveState.getValue(MagiculeIncubatorBlock.LIT)) {
                    BlockState newState = (BlockState)aboveState.setValue(MagiculeIncubatorBlock.LIT, false);
                    this.level.setBlock(above, newState, 3);
                    setChanged(this.level, above, newState);
                }
            }
        }
    }

    private void pullEnergyFromPipeNetwork(Level level, BlockPos origin) {
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        HashSet<BlockPos> visited = new HashSet<>();
        for (Direction direction : Direction.values()) {
            BlockPos adjacent = origin.relative(direction);
            if (level.getBlockState(adjacent).getBlock() instanceof PipeBlock) queue.add(adjacent);
        }
        int transferred = 0;
        while (!queue.isEmpty() && transferred < 1) {
            BlockPos pipePos = queue.removeFirst();
            if (!visited.add(pipePos) || !(level.getBlockState(pipePos).getBlock() instanceof PipeBlock)) continue;
            for (Direction direction : Direction.values()) {
                BlockPos neighbor = pipePos.relative(direction);
                if (level.getBlockState(neighbor).getBlock() instanceof PipeBlock) {
                    if (!visited.contains(neighbor)) queue.addLast(neighbor);
                    continue;
                }
                if (level.getBlockEntity(neighbor) instanceof MagicEnergyStorage source && source != this) {
                    int room = getMaxMagicEnergy() - getMagicEnergy();
                    int extracted = source.extractMagicEnergy(Math.min(1 - transferred, room));
                    if (extracted > 0) {
                        int accepted = receiveMagicEnergy(extracted);
                        transferred += accepted;
                    }
                }
            }
        }
    }

    protected void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        ContainerHelper.saveAllItems(nbt, this.items, provider);
        nbt.putInt(INCUBATION_PROGRESS, this.incubationProgress);
        nbt.putInt(MAX_INCUBATION_PROGRESS, this.maxIncubationProgress);
        nbt.putInt(MAGIC_ENERGY, this.magicEnergy);
        nbt.putInt(MAX_MAGIC_ENERGY, this.maxMagicEnergy);
    }

    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items, provider);
        incubationProgress = nbt.getInt(INCUBATION_PROGRESS);
        maxIncubationProgress = nbt.getInt(MAX_INCUBATION_PROGRESS);
        magicEnergy = nbt.getInt(MAGIC_ENERGY);
        maxMagicEnergy = nbt.getInt(MAX_MAGIC_ENERGY);
    }

    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        this.saveAdditional(tag, provider);
        return tag;
    }
}
