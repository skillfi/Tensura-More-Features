package com.github.skillfi.tensura_mf.api.energy;

import lombok.Data;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;
import java.util.UUID;

@Data
public class NetworkEntry {

    public static final String TYPE = "type";
    public static final String BLOCK_POS_TAG = "BlockPos";
    public final UUID blockId;
    public final BlockPos blockPos;
    public final NetworkType networkType;
    private static final String BLOCK_ID = "BlockId";
    public static NetworkEntry EMPTY = new NetworkEntry(UUID.randomUUID(), BlockPos.ZERO, NetworkType.PIPE);

    public CompoundTag toNBT(CompoundTag compoundTag){
        blockPosToNbt(compoundTag);
        compoundTag.putUUID(BLOCK_ID, getBlockId());
        compoundTag.putString(TYPE, getNetworkType().name());
        return compoundTag;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NetworkEntry that)) return false;
        return Objects.equals(blockId, that.blockId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(blockId);
    }

    public void blockPosToNbt(CompoundTag compoundTag){
        CompoundTag blockPosTag = new CompoundTag();
        blockPosTag.putInt("x", getBlockPos().getX());
        blockPosTag.putInt("y", getBlockPos().getY());
        blockPosTag.putInt("z", getBlockPos().getZ());
        compoundTag.put(BLOCK_POS_TAG, blockPosTag);
    }

    public static BlockPos blockPos(CompoundTag compoundTag){
        if (compoundTag.contains(BLOCK_POS_TAG)) {
            CompoundTag blockPosTag = compoundTag.getCompound(BLOCK_POS_TAG);
            int x = blockPosTag.getInt("x");
            int y = blockPosTag.getInt("y");
            int z = blockPosTag.getInt("z");
            return new BlockPos(x, y, z);
        }
        return BlockPos.ZERO;
    }


    public static NetworkEntry fromNBT(CompoundTag tag){
        BlockPos pos = blockPos(tag);
        UUID blockId = UUID.randomUUID();
        if (tag.hasUUID(BLOCK_ID)) blockId = tag.getUUID(BLOCK_ID);
        NetworkType type = NetworkType.EMPTY;
        if (tag.contains(TYPE)) type = NetworkType.valueOf(tag.getString(TYPE));
        return new NetworkEntry(blockId, pos, type);
    }
}
