package com.github.skillfi.tensura_mf.block.entity;

import com.github.skillfi.tensura_mf.block.MagiculeIncubatorBlock;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import com.github.skillfi.tensura_mf.api.energy.MagicEnergyStorage;
import com.github.skillfi.tensura_mf.block.PipeBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import com.github.skillfi.tensura_mf.menu.MagiculeIncubatorMenu;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MagiculeIncubatorBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible, GeoAnimatable, MagicEnergyStorage {
    private NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public static final int INPUT_SLOT_INDEX = 0;
    public static final int OUTPUT_SLOT_INDEX = 1;
    public static final int MAX_MAGIC_MATERIAL_AMOUNT = 1000;
    private int magicMaterialAmount;
    private ItemStack lastInputStack = ItemStack.EMPTY;
    public boolean needUpdate;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public MagiculeIncubatorBlockEntity(BlockPos pos, BlockState state) {
        super(TensuraMfBlocksEntities.MAGICULE_INCUBATOR_BLOCK.get(), pos, state);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[]{INPUT_SLOT_INDEX, OUTPUT_SLOT_INDEX};
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return i >= 0 && i < getContainerSize() && canPlaceItem(i, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return i >= 0 && i < getContainerSize() && !getItem(i).isEmpty();
    }

    @Override
    public void fillStackedContents(StackedContents stackedContents) {
        for (ItemStack item : items) stackedContents.accountStack(item);
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
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new MagiculeIncubatorMenu(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "incubator", 5, event -> {
            boolean active = event.getAnimatable().getBlockState().getValue(MagiculeIncubatorBlock.ACTIVE);
            event.getController().setAnimation(RawAnimation.begin().thenLoop(
                    active ? "animation.capsule.active" : "animation.capsule.idle"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return level == null ? 0 : level.getGameTime();
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        super.setItem(slot, stack);
        setChanged();
        if (level != null && !level.isClientSide) {
            boolean active = !getItem(INPUT_SLOT_INDEX).isEmpty() && getItem(OUTPUT_SLOT_INDEX).isEmpty();
            if (getBlockState().getValue(MagiculeIncubatorBlock.ACTIVE) != active) {
                level.setBlock(worldPosition, getBlockState().setValue(MagiculeIncubatorBlock.ACTIVE, active), 3);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("magicule_incubator.magic", magicMaterialAmount);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        magicMaterialAmount = Math.max(0, Math.min(MAX_MAGIC_MATERIAL_AMOUNT,
                tag.getInt("magicule_incubator.magic")));
        lastInputStack = ItemStack.EMPTY;
        needUpdate = true;
    }

    @Override
    public int getMagicEnergy() { return magicMaterialAmount; }

    @Override
    public int getMaxMagicEnergy() { return MAX_MAGIC_MATERIAL_AMOUNT; }

    @Override
    public int receiveMagicEnergy(int amount) {
        if (amount <= 0) return 0;
        int accepted = Math.min(amount, getMaxMagicEnergy() - magicMaterialAmount);
        if (accepted > 0) {
            magicMaterialAmount += accepted;
            needUpdate = true;
        }
        return accepted;
    }

    @Override
    public int extractMagicEnergy(int amount) {
        if (amount <= 0) return 0;
        int extracted = Math.min(amount, magicMaterialAmount);
        magicMaterialAmount -= extracted;
        if (extracted > 0) needUpdate = true;
        return extracted;
    }

    public int addMagicMaterialAmount(int amount) { return receiveMagicEnergy(amount); }

    public int getMagicMaterialAmount() { return magicMaterialAmount; }

    public void setMagicMaterialAmount(int amount) {
        int clamped = Math.max(0, Math.min(MAX_MAGIC_MATERIAL_AMOUNT, amount));
        if (magicMaterialAmount != clamped) {
            magicMaterialAmount = clamped;
            needUpdate = true;
        }
    }

    private boolean checkInputCache() {
        ItemStack input = getItem(INPUT_SLOT_INDEX);
        if (!ItemStack.isSameItemSameComponents(lastInputStack, input)) {
            lastInputStack = input.copy();
            return true;
        }
        return false;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MagiculeIncubatorBlockEntity incubator) {
        if (incubator.checkInputCache()) incubator.needUpdate = true;
        if (incubator.getMagicEnergy() < incubator.getMaxMagicEnergy()) {
            incubator.pullEnergyFromPipeNetwork(level, pos);
        }
        if (incubator.needUpdate) {
            incubator.setChanged();
            level.sendBlockUpdated(pos, state, incubator.getBlockState(), 2);
            incubator.needUpdate = false;
        }
    }

    private void pullEnergyFromPipeNetwork(Level level, BlockPos origin) {
        java.util.ArrayDeque<BlockPos> queue = new java.util.ArrayDeque<>();
        java.util.HashSet<BlockPos> visited = new java.util.HashSet<>();
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
}
