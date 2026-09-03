package com.github.skillfi.tensura_mf.block.entity;

import com.github.skillfi.tensura_mf.api.energy.IMagic;
import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.event.TensuraMfBlockEvents;
import dev.architectury.event.EventResult;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** Base implementation for container block entities that store magic energy. */
public abstract class AbstractEnergyBlockEntity extends BaseContainerBlockEntity implements IMagic {

    public static final int ENERGY_SLOT_IDX = 0;
    public static final int MAX_ENERGY_SLOT_IDX = 1;
    public static final int CONTAINER_DATA_SIZE = 2;
    public static final int INVENTORY_SIZE = 2;
    public static final String ENERGY_TAG = "MagicEnergy";

    /** The block entity's own inventory, exposed for menus and automation. */
    @Getter
    protected final Container container = this;
    @Getter
    public UUID networkId = null;
    @Getter
    public UUID id = null;
    @Getter
    public Map<UUID, List<Network>> network = new HashMap<>();

    /** One synchronized menu value containing the current magic energy. */
    private NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);


    /** Set when a client-visible energy or inventory update must be sent. */
    public boolean needUpdate;

    protected AbstractEnergyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
