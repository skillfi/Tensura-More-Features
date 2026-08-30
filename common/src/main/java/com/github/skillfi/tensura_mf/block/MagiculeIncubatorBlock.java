package com.github.skillfi.tensura_mf.block;

import com.mojang.serialization.MapCodec;
import com.github.skillfi.tensura_mf.block.entity.MagiculeIncubatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.Containers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagiculeIncubatorBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<MagiculeIncubatorBlock> CODEC = simpleCodec( (properties1)-> new MagiculeIncubatorBlock());
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty ACTIVE;
    public static final BooleanProperty WATERLOGGED;

    public MagiculeIncubatorBlock(){
        super(Properties.of().strength(5).noOcclusion());
        registerDefaultState(stateDefinition.any().setValue(FACING, net.minecraft.core.Direction.NORTH)
                .setValue(ACTIVE, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MagiculeIncubatorBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                   BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type,
                com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities.MAGICULE_INCUBATOR_BLOCK.get(),
                MagiculeIncubatorBlockEntity::serverTick);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING,
                context.getHorizontalDirection().getClockWise().getClockWise())
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE, WATERLOGGED);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, net.minecraft.core.Direction direction,
                                           BlockState neighborState, net.minecraft.world.level.LevelAccessor level,
                                           BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
                                               BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof MagiculeIncubatorBlockEntity incubator) {
            player.openMenu(incubator);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof MagiculeIncubatorBlockEntity incubator) {
            Containers.dropContents(level, pos, incubator);
            level.removeBlockEntity(pos);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    static {
        ACTIVE = BlockStateProperties.LIT;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
    }
}
