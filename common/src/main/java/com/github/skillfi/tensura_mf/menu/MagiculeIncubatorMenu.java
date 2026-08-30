package com.github.skillfi.tensura_mf.menu;

import com.github.skillfi.tensura_mf.block.entity.MagiculeIncubatorBlockEntity;
import com.github.skillfi.tensura_mf.registry.menu.TensuraMfMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MagiculeIncubatorMenu extends AbstractContainerMenu {
    public final MagiculeIncubatorBlockEntity incubatorBlock;
    public final Level level;

    public MagiculeIncubatorMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (MagiculeIncubatorBlockEntity)inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public MagiculeIncubatorMenu(int id, Inventory inv, MagiculeIncubatorBlockEntity container) {
        super(TensuraMfMenus.MAGICULE_INCUBATOR.get(), id);
        checkContainerSize(container, 2);
        this.incubatorBlock = container;
        this.level = inv.player.level();
        this.addSlot(new Slot(container, 0, 205, 44));
        this.addSlot(new Slot(container, 1, 206, 99));
        this.addPlayerInventory(inv);
        this.addPlayerHotbar(inv);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for(int i = 0; i < 3; ++i) {
            for(int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 31 + l * 18, 86 + i * 18));
            }
        }

    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 6 + i * 18, 89));
        }

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
    @Override public boolean stillValid(Player player) { return incubatorBlock.stillValid(player); }
}
