package com.github.skillfi.tensura_mf.block;

import com.github.skillfi.tensura_mf.api.energy.Network;
import com.github.skillfi.tensura_mf.block.part.IncubatorPart;
import com.github.skillfi.tensura_mf.block.part.TensuraMfBlockParts;
import com.github.skillfi.tensura_mf.registry.block.TensuraMfBlocksEntities;
import com.github.skillfi.tensura_mf.storage.INetwork;
import com.github.skillfi.tensura_mf.storage.TensuraMfStorages;
import com.mojang.serialization.MapCodec;
import com.github.skillfi.tensura_mf.block.entity.MagicIncubatorBlockEntity;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.Containers;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

import static net.minecraft.core.Direction.NORTH;

public class MagicIncubatorBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<MagicIncubatorBlock> CODEC = simpleCodec( MagicIncubatorBlock::new);
    public static final DirectionProperty FACING;
    public static final BooleanProperty LIT;
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<IncubatorPart> PART;
    private static final VoxelShape TOP_SHAPE;
    private static final VoxelShape BASE_SHAPE;
    private static final VoxelShape GLASS_SHAPE;
    public static final VoxelShape GLASS;
    public static final VoxelShape GLASS_TOP;

    public MagicIncubatorBlock(BlockBehaviour.Properties properties){
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, NORTH)
                .setValue(LIT, false).setValue(WATERLOGGED, false));
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide()) {
            BlockPos basePos = this.getBasePosition(pPos, pState.getValue(PART));

            BlockPos glassPos = this.getPartPosition(basePos, IncubatorPart.GLASS);
            BlockPos topPos = this.getPartPosition(basePos, IncubatorPart.TOP);

            pLevel.setBlock(glassPos, pState.setValue(PART, IncubatorPart.GLASS).setValue(WATERLOGGED, this.isWaterAtPosition(pLevel, glassPos)), 3);
            pLevel.setBlock(topPos, pState.setValue(PART, IncubatorPart.TOP).setValue(WATERLOGGED, this.isWaterAtPosition(pLevel, topPos)), 3);

            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);

        }
    }

    @Override
    public BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
        if (!pLevel.isClientSide()) {
            BlockPos basePos = this.getBasePosition(pPos, pState.getValue(PART));

            for (IncubatorPart part : IncubatorPart.values()) {
                BlockPos partPos = this.getPartPosition(basePos, part);
                if (!partPos.equals(pPos)) {
                    pLevel.setBlockAndUpdate(partPos, Blocks.AIR.defaultBlockState());
                }
            }
        }

        return pState;
    }

    private BlockPos getBasePosition(BlockPos sourcePos, IncubatorPart part) {
        return switch (part) {
            case BASE -> sourcePos;
            case GLASS -> sourcePos.below();
            case TOP -> sourcePos.below(2);
        };
    }

    private BlockPos getPartPosition(BlockPos basePos, IncubatorPart targetPart) {
        return switch (targetPart) {
            case BASE -> basePos;
            case GLASS -> basePos.above();
            case TOP -> basePos.above(2);
        };
    }

    private boolean isWaterAtPosition(Level level, BlockPos blockPos) {
        return level.getFluidState(blockPos).is(Fluids.WATER);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        IncubatorPart part = pState.getValue(PART);
        switch (part) {
            case TOP -> {
                return TOP_SHAPE;
            }
            case GLASS -> {
                return GLASS_SHAPE;
            }
            default -> {
                return BASE_SHAPE;
            }
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        return blockpos.getY() <= level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext) ? (this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite())).setValue(WATERLOGGED, this.isWaterAtPosition(level, blockpos)) : null;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if ((Boolean)pState.getValue(LIT) && (pState.getValue(PART)).equals(IncubatorPart.BASE)) {
            double d0 = (double)pPos.getX() + (double)0.5F;
            double d1 = (double)pPos.getY() + 0.2;
            double d2 = (double)pPos.getZ() + (double)0.5F;
            if (pRandom.nextDouble() < 0.1) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = (Direction)pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = pRandom.nextDouble() * 0.6 - 0.3;
            double d4 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : d3;
            double d5 = pRandom.nextDouble() * (double)6.0F / (double)16.0F;
            double d6 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : d3;
            pLevel.addParticle(ParticleTypes.SMOKE, d0 + d4, d1 + d5, d2 + d6, (double)0.0F, (double)0.0F, (double)0.0F);
            pLevel.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, d0 + d4, d1 + d5, d2 + d6, (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    public static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (state) -> (Boolean)state.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState pState) {
        return Boolean.TRUE.equals(pState.getValue(WATERLOGGED)) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT, PART, WATERLOGGED);
    }

    @Override
    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(PART) == IncubatorPart.BASE ? new MagicIncubatorBlockEntity(blockPos, blockState) : null;
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return state.getValue(PART) == IncubatorPart.BASE ? createTickerHelper(type, TensuraMfBlocksEntities.MAGICULE_INCUBATOR.get(), MagicIncubatorBlockEntity::tick) : null;
    }

    @Override
    public ExtendedMenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        var entity = level.getBlockEntity(blockPos);
        if (entity instanceof BaseContainerBlockEntity) {
            return new BlockPosMenuProvider(blockPos, (BaseContainerBlockEntity) entity);
        }
        return null;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide) {
            var blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof BaseContainerBlockEntity) {
                MenuRegistry.openExtendedMenu((ServerPlayer) player, getMenuProvider(blockState, level, blockPos));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof MagicIncubatorBlockEntity incubator) {
            Containers.dropContents(level, pos, incubator);
            level.removeBlockEntity(pos);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        LIT = BlockStateProperties.LIT;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        PART = TensuraMfBlockParts.INCUBATOR_PART;
        // Blockbench model coordinates are in pixels; VoxelShape uses the same
        // 0..16 coordinate system for one block. The model's rear connector
        // extends beyond the south edge to z=20.5.
        GLASS = box(1.0F, 8.0F, 1.0F, 14.0F, 8.0F, 14.0F);
        GLASS_TOP = box(1.0F, 0.0F, 1.0F, 15.0F, 8.0F, 13.0F);

        BASE_SHAPE = Shapes.or(
                box(0.0F, 0.0F, 0.0F, 16.0F, 2.0F, 16.0F),
                box(1.0F, 2.0F, 1.0F, 14.0F, 6.0F, 14.0F),
                GLASS);
        GLASS_SHAPE = Shapes.or(box(1.0F, 0.0F, 1.0F, 14.0F, 16.0F, 14.0F));

        TOP_SHAPE = Shapes.or(
                GLASS_TOP,
                box(1.0F, 8.0F, 1.0F, 14.0F, 34.0F, 14.0F),
                box(0.0F, 14.0F, 0.0F, 16.0F, 32.0F, 16.0F));
//        TOP_SHAPE = Shapes.or(
//                box((double)3.0F, (double)0.0F, (double)3.0F, (double)13.0F, (double)9.0F, (double)13.0F),
//                new VoxelShape[]{box((double)2.0F, (double)9.0F, (double)2.0F, (double)14.0F, (double)14.0F, (double)14.0F),
//                        box((double)1.0F, (double)14.0F, (double)1.0F, (double)15.0F, (double)16.0F, (double)15.0F)});
    }

    public static class BlockPosMenuProvider implements ExtendedMenuProvider {
        private final BlockPos pos;
        private final BaseContainerBlockEntity entity;

        public BlockPosMenuProvider(BlockPos pos, BaseContainerBlockEntity entity) {
            this.pos = pos;
            this.entity = entity;
        }

        @Override
        public void saveExtraData(FriendlyByteBuf buf) {
            buf.writeBlockPos(pos);
        }

        @Override
        public Component getDisplayName() {
            return entity.getDisplayName();
        }

        @Override
        public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
            return entity.createMenu(i, inventory, player);
        }
    }
}
