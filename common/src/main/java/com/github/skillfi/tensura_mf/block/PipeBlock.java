package com.github.skillfi.tensura_mf.block;

import com.github.skillfi.tensura_mf.api.energy.IMagic;
import com.github.skillfi.tensura_mf.block.entity.PipeBlockEntity;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PipeBlock extends BaseEntityBlock {
    public static final MapCodec<PipeBlock> CODEC = simpleCodec(PipeBlock::new);
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape UP_SHAPE;
    private static final VoxelShape DOWN_SHAPE;

    public PipeBlock() {
        this(Properties.of().noOcclusion());
    }

    public PipeBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(NORTH, false).setValue(SOUTH, false)
                .setValue(EAST, false).setValue(WEST, false)
                .setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected MapCodec<? extends PipeBlock> codec() { return CODEC; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PipeBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {
        if (!level.isClientSide && type ==TensuraMfBlocksEntities.PIPE.get()){
            return (BlockEntityTicker<T>) PipeBlockEntity.createTickerHelper();
        }
        return null;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateConnections(defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return updateConnections(state, level, pos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.EMPTY.defaultFluidState();
    }

    private BlockState updateConnections(BlockState state, BlockGetter level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            state = state.setValue(property(direction), connectsTo(level, pos.relative(direction)));
        }
        return state;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    private static boolean connectsTo(BlockGetter level, BlockPos pos) {
        if (level.getBlockState(pos).getBlock() instanceof PipeBlock) return true;
        return level.getBlockEntity(pos) instanceof IMagic;
    }

    @Override
    protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);
        if (!level.isClientSide) level.removeBlockEntity(blockPos);
    }

    public static BooleanProperty property(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            case UP -> UP;
            case DOWN -> DOWN;
        };
    }

    public static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case UP -> UP_SHAPE;
            case DOWN -> DOWN_SHAPE;
        };
    }

    static {
        NORTH_SHAPE = Shapes.or(
                box(7F, 1.0F, 0.0F, 7F, 2.0F, 9.0F));
        SOUTH_SHAPE = Shapes.or(
                box(7F, 3.0F, 7.0F, 7F, 3.0F, 9.0F));
        EAST_SHAPE = Shapes.or(
                box(0F, 3.0F, 7.0F, 9.0F, 3.0F, 7.0F));
        WEST_SHAPE = Shapes.or(
                box(0F, 2.0F, 2.0F, 9.0F, 3.0F, 7.0F));
        UP_SHAPE = Shapes.or(
                box(7F, 7.5F, 4.5F, 7F, 7.5F, 9.0F));
        DOWN_SHAPE = Shapes.or(
                box(7F, -1.5F, 2.5F, 7F, 2.0F, 9.0F));
    }
}
