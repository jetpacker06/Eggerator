package com.jetpacker06.eggerator.eggerator;

import com.jetpacker06.eggerator.ModRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class EggeratorBlock extends BaseEntityBlock implements EntityBlock {

    public static final MapCodec<EggeratorBlock> CODEC = simpleCodec(EggeratorBlock::new);

    public EggeratorBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos,
                         @NotNull BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() == pNewState.getBlock()) {
            return;
        }
        BlockEntity bE = pLevel.getBlockEntity(pPos);
        if (bE instanceof EggeratorBlockEntity eBE) {
            eBE.drops(pLevel);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        EggeratorBlockEntity be = (EggeratorBlockEntity) pLevel.getBlockEntity(pPos);
        assert be != null;
        ItemStack eggs = be.grabEggs();
        pPlayer.getInventory().add(eggs);

        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new EggeratorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState,
                         @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModRegistry.EGGERATOR_BLOCK_ENTITY.get(), EggeratorBlockEntity::onTick);
    }
}
