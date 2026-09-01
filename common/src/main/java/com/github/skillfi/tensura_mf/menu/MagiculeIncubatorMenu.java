package com.github.skillfi.tensura_mf.menu;

import com.github.skillfi.tensura_mf.block.entity.MagiculeIncubatorBlockEntity;
import com.github.skillfi.tensura_mf.registry.menu.TensuraMfMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MagiculeIncubatorMenu extends AbstractContainerMenu {
    public final MagiculeIncubatorBlockEntity blockEntity;
    private final Container container;
    private final ContainerData data;
    private final Player player;

    public MagiculeIncubatorMenu(int id, Inventory inv, MagiculeIncubatorBlockEntity blockEntity, Container container, ContainerData containerData) {
        super(TensuraMfMenus.MAGICULE_INCUBATOR.get(), id);
        this.container = container;
        this.data = containerData;
        this.player = inv.player;
        this.blockEntity = blockEntity;
        this.addSlot(new Slot(container, MagiculeIncubatorBlockEntity.INPUT_SLOT_INDEX, 139, 15));
        this.addSlot(new Slot(container, MagiculeIncubatorBlockEntity.OUTPUT_SLOT_INDEX, 139, 53));
        this.addPlayerInventory(inv);
        this.addPlayerHotbar(inv);
        checkContainerSize(container, MagiculeIncubatorBlockEntity.INVENTORY_SIZE);
    }

    public static MagiculeIncubatorMenu createMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        MagiculeIncubatorBlockEntity entity = (MagiculeIncubatorBlockEntity) inventory.player.level()
                .getBlockEntity(friendlyByteBuf.readBlockPos());
        return new MagiculeIncubatorMenu(i, inventory, entity, entity.getContainer(), entity.getContainerData());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for(int i = 0; i < 3; ++i) {
            for(int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }

    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }

    }

    public boolean isIncubating() {
        return this.blockEntity.getIncubationProgress() > 0;
    }

    public int getScaledProgress() {
        int maxProgress = this.blockEntity.getMaxIncubationProgress();
        int progress = Math.min(this.blockEntity.getIncubationProgress(), maxProgress);
        int progressArrowSize = 25;
        return progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getEnergyState() {
        int progress = this.blockEntity.getMagicEnergy();
        int progressArrowSize = 74;
        return progress != 0 ? progress * progressArrowSize / this.blockEntity.getMaxMagicEnergy() : 0;
    }

    @Override public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem(); result = stack.copy();
            if (index < 2 ? !moveItemStackTo(stack, 2, slots.size(), true) : !moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        }
        return result;
    }
    @Override public boolean stillValid(Player player) { return blockEntity.stillValid(player); }
}
