package io.yukkuric.ccb.forge.contents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class CarvePumpkinHandler implements IItemHandler {
    private boolean carved = false;
    private final Level level;
    private final BlockPos pos;
    private final Direction dir;

    public CarvePumpkinHandler(Level level, BlockPos pos, Direction dir) {
        this.level = level;
        this.pos = pos;
        this.dir = dir;
    }

    private Direction pickRandomDirXZ() {
        var ret = Direction.UP;
        while (ret.getAxis() == Direction.Axis.Y) ret = Direction.getRandom(level.random);
        return ret;
    }
    private void carvePumpkin() {
        level.setBlockAndUpdate(pos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, dir.getAxis() == Direction.Axis.Y ? pickRandomDirXZ() : dir.getOpposite()));
        carved = true;
    }

    @Override
    public int getSlots() {
        return 1;
    }
    @Override
    public @NotNull ItemStack getStackInSlot(int i) {
        if (i == 0 && !carved) return new ItemStack(Items.PUMPKIN_SEEDS, 1);
        return ItemStack.EMPTY;
    }
    @Override
    public @NotNull ItemStack insertItem(int i, @NotNull ItemStack stack, boolean bl) {
        return stack;
    }
    @Override
    public @NotNull ItemStack extractItem(int slot, int count, boolean simulate) {
        var ret = getStackInSlot(slot);
        if (!simulate) carvePumpkin();
        return ret;
    }
    @Override
    public int getSlotLimit(int i) {
        return 1;
    }
    @Override
    public boolean isItemValid(int i, @NotNull ItemStack arg) {
        return false;
    }
}
