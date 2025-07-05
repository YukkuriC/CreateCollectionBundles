package io.yukkuric.ccb.forge.contents;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

public class InfiniteBookHandler implements IItemHandler {
    @Override
    public int getSlots() {
        return 2;
    }
    @Override
    public @NotNull ItemStack getStackInSlot(int i) {
        if (i == 0) return new ItemStack(Items.BOOK, 64);
        if (i == 1) return new ItemStack(Items.LAPIS_LAZULI, 64);
        return ItemStack.EMPTY;
    }
    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return stack;
    }
    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemHandlerHelper.copyStackWithSize(getStackInSlot(slot), amount);
    }
    @Override
    public int getSlotLimit(int i) {
        return 64;
    }
    @Override
    public boolean isItemValid(int i, @NotNull ItemStack arg) {
        return false;
    }
}
