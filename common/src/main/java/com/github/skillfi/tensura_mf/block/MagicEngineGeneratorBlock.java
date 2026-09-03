package com.github.skillfi.tensura_mf.block;

import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.block.entity.MagicEngineBlockEntity;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import com.mojang.serialization.MapCodec;
import dev.architectury.event.EventResult;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.chunk.ChunkStorage;
import lombok.Generated;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.ToIntFunction;

/** MagicEngine variant backed by this mod's Magicule energy storage. */
public class MagicEngineGeneratorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING;
    public static final BooleanProperty ENABLED;
    private static final VoxelShape SHAPE_DOWN;
    private static final VoxelShape SHAPE_EAST;
    private static final VoxelShape SHAPE_WEST;
    private static final VoxelShape SHAPE_SOUTH;
    private static final VoxelShape SHAPE_NORTH;
    private static final VoxelShape SHAPE_UP;
    private final double magiculeReduction;
    private final double reductionRange;
    private final boolean creativeOnly;
    public static final MapCodec<MagicEngineGeneratorBlock> CODEC;

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MagicEngineBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(
                type, TensuraMfBlocksEntities.MAGIC_ENGINE.get(), MagicEngineBlockEntity::serverTick);
    }

    public MagicEngineGeneratorBlock(double reduction, double range, boolean creativeOnly, BlockBehaviour.Properties properties) {
        super(properties);
        this.magiculeReduction = reduction;
        this.reductionRange = range;
        this.creativeOnly = creativeOnly;
        this.registerDefaultState(((this.stateDefinition.any()).setValue(FACING, Direction.UP)).setValue(ENABLED, Boolean.FALSE));
    }

    public MagicEngineGeneratorBlock(BlockBehaviour.Properties properties) {
        this(ChunkStorage.CONFIG.magicEngineReduction, ChunkStorage.CONFIG.magicEngineRange, false, properties);
    }

    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape voxelShape;
        switch ((Direction)state.getValue(FACING)) {
            case NORTH -> voxelShape = SHAPE_NORTH;
            case SOUTH -> voxelShape = SHAPE_SOUTH;
            case WEST -> voxelShape = SHAPE_WEST;
            case EAST -> voxelShape = SHAPE_EAST;
            case DOWN -> voxelShape = SHAPE_DOWN;
            case UP -> voxelShape = SHAPE_UP;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return voxelShape;
    }

    public static ToIntFunction<BlockState> getLightEmission() {
        return (state) -> state.getValue(ENABLED) ? 15 : 0;
    }

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    public int getSignal(BlockState pState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pState.getValue(ENABLED) ? 15 : 0;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{FACING}).add(new Property[]{ENABLED});
    }

    public @NotNull BlockState rotate(BlockState pState, Rotation pRot) {
        return (BlockState)pState.setValue(FACING, pRot.rotate((Direction)pState.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation((Direction)pState.getValue(FACING)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getClickedFace();
        return super.getStateForPlacement(pContext).setValue(FACING, direction);
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pLevel.isClientSide()) pLevel.removeBlockEntity(pPos);
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);

    }

    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult blockHitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else if (this.isCreativeOnly() && !player.isCreative()) {
            return super.useWithoutItem(state, level, pos, player, blockHitResult);
        } else {
            boolean current = (Boolean)state.getValue(ENABLED);
            state = (BlockState)state.setValue(ENABLED, !current);
            level.setBlock(pos, state, 3);
            this.applyMagiculeModifier(level, pos, !current);
            BlockEntity var8 = level.getBlockEntity(pos);
            if (var8 instanceof MagicEngineBlockEntity) {
                MagicEngineBlockEntity entity = (MagicEngineBlockEntity)var8;
                entity.setTracked(!current);
            }

            level.playSound((Player)null, pos, !current ? SoundEvents.BEACON_ACTIVATE : SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 1.0F, !current ? 0.6F : 0.5F);
            level.gameEvent(player, !current ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
            if (player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer)player;
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, player.getMainHandItem());
            }

            return InteractionResult.SUCCESS;
        }
    }

    protected void applyMagiculeModifier(Level level, BlockPos pPos, boolean enable) {
        double radius = this.getReductionRange();
        double reduction = this.getMagiculeReduction() * (double)-1.0F;
        int minX = (int)((double)pPos.getX() - radius);
        int maxX = (int)((double)pPos.getX() + radius);
        int minZ = (int)((double)pPos.getZ() - radius);
        int maxZ = (int)((double)pPos.getZ() + radius);
        int minChunkX = minX >> 4;
        int maxChunkX = maxX >> 4;
        int minChunkZ = minZ >> 4;
        int maxChunkZ = maxZ >> 4;

        for(int chunkX = minChunkX; chunkX <= maxChunkX; ++chunkX) {
            for(int chunkZ = minChunkZ; chunkZ <= maxChunkZ; ++chunkZ) {
                LevelChunk chunk = level.getChunk(chunkX, chunkZ);
                ChunkStorage storage = TensuraStorages.getChunkFrom(chunk);
                if (enable) {
                    storage.addBlockModifier(pPos, reduction, radius);
                } else {
                    storage.removeBlockModifier(pPos);
                }

                storage.markDirty();
            }
        }
    }

    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public @NotNull RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Generated
    public double getMagiculeReduction() {
        return this.magiculeReduction;
    }

    @Generated
    public double getReductionRange() {
        return this.reductionRange;
    }

    @Generated
    public boolean isCreativeOnly() {
        return this.creativeOnly;
    }

    static {
        FACING = BlockStateProperties.FACING;
        ENABLED = BlockStateProperties.ENABLED;
        CODEC = simpleCodec(MagicEngineGeneratorBlock::new);
        SHAPE_DOWN = Block.box((double)0.0F, (double)-1.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F);
        SHAPE_EAST = Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)17.0F, (double)16.0F, (double)16.0F);
        SHAPE_WEST = Block.box((double)-1.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F);
        SHAPE_SOUTH = Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)17.0F);
        SHAPE_NORTH = Block.box((double)0.0F, (double)0.0F, (double)-1.0F, (double)16.0F, (double)16.0F, (double)16.0F);
        SHAPE_UP = Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)17.0F, (double)16.0F);
    }
}
