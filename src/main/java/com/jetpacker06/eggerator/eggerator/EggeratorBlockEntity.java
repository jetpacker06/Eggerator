package com.jetpacker06.eggerator.eggerator;

import com.jetpacker06.eggerator.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

@SuppressWarnings("unused")
public class EggeratorBlockEntity extends BlockEntity {

    public EggeratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModRegistry.EGGERATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }
    public void drops(Level pLevel) {
        System.out.println("dropping drops");
        ItemStack blockStack = new ItemStack(ModRegistry.EGGERATOR_BLOCK_ITEM.get());
        CompoundTag tag = new CompoundTag();
        tag.putInt("chickens", this.getChickens());
        blockStack.setTag(tag);
        NonNullList<ItemStack> stacksToDrop = NonNullList.create();
        stacksToDrop.add(blockStack);
        stacksToDrop.add(this.grabEggs());
        Containers.dropContents(pLevel, this.getBlockPos(), stacksToDrop);
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Random random = new Random();
    private int chickens = 0;
    private int eggTime = -1;


    public static void onTick(Level pLevel, BlockPos pPos, BlockState pState, EggeratorBlockEntity be) {
        be.onTick(pLevel, pPos, pState);
    }
    @SuppressWarnings("unused")
    private void onTick(Level pLevel, BlockPos pPos, BlockState pState) {
        ItemStack stackInSlot = this.itemHandler.getStackInSlot(0);
        int eggs = stackInSlot.getCount();
        if (this.hasChickens()) {
            if (this.eggTime == -1) {
                this.resetEggTimer();
            }
            this.eggTime--;

            if (this.eggTime == 0 & eggs < 16) {
                this.setEggCount(this.getEggCount() + 1);
            }
        }
        setChanged(pLevel, pPos, pState);
    }
    public boolean hasChickens() {
        return this.chickens != 0;
    }
    public void resetEggTimer() {
        int time = 6000;
        this.eggTime = (this.random.nextInt(time) + time) / this.chickens;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putInt("eggs", itemHandler.getStackInSlot(0).getCount());
        tag.putInt("chickens", this.chickens);
        tag.putInt("eggTime", eggTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        int eggs = nbt.getInt("eggs");
        this.chickens = nbt.getInt("chickens");
        this.eggTime = nbt.getInt("eggTime");
        this.itemHandler.setStackInSlot(0, new ItemStack(Items.EGG, eggs));
    }

    /**
     * Sets the itemHandler to be empty and returns the itemStack that was in it before, effectively "grabbing" the eggs.
     * @return The ItemStack formerly held by the BlockEntity's itemHandler.
     */
    public ItemStack grabEggs() {
        ItemStack eggs = this.itemHandler.getStackInSlot(0);
        this.itemHandler.setStackInSlot(0, new ItemStack(Items.EGG, 0));
        return eggs;
    }

    public int getChickens() {
        return this.chickens;
    }

    public int getEggTime() {
        return this.eggTime;
    }

    public int getEggCount() {
        return this.itemHandler.getStackInSlot(0).getCount();
    }

    public void setEggCount(int count) {
        this.itemHandler.setStackInSlot(0, new ItemStack(Items.EGG, count));
    }
    public void setChickens(int count) {
        this.chickens = count;
    }

}
