package com.github.skillfi.tensura_mf.api.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record Network(UUID uuid, BlockPos pos, String blockName) {

    public static final String NETWORK_ID = "NetworkId";
    private static final String LEGACY_NETWORK_ID = "BlockId";
    public static Network EMPTY = new Network(UUID.randomUUID(), BlockPos.ZERO, "");

    Block getBlock(ServerLevel level){
        return level.getBlockState(pos()).getBlock();
    }

    @Override
    public @NotNull String toString() {
        return "Network(UUID: "+uuid() + ", BlockPos: " + pos() + ")";
    }

    public CompoundTag toNBT(CompoundTag compoundTag){
        compoundTag.putInt("x", pos().getX());
        compoundTag.putInt("y", pos().getY());
        compoundTag.putInt("z", pos().getZ());
        compoundTag.putUUID(NETWORK_ID, uuid());
        compoundTag.putString("blockName", blockName);
        return compoundTag;
    }

    public static Network fromNBT(CompoundTag tag){
        int x = tag.getInt("x");
        int y = tag.getInt("y");
        int z = tag.getInt("z");
        UUID uuid = tag.hasUUID(NETWORK_ID)
                ? tag.getUUID(NETWORK_ID)
                : tag.getUUID(LEGACY_NETWORK_ID);
        String blockName = tag.getString("blockName");
        return new Network(uuid, new BlockPos(x, y, z), blockName);
    }


}
