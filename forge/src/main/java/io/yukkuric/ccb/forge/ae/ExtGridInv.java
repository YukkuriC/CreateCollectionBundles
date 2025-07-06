package io.yukkuric.ccb.forge.ae;

import appeng.api.networking.storage.IStorageService;
import appeng.blockentity.grid.AENetworkPowerBlockEntity;
import io.yukkuric.ccb.content.ExtInv;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;

public enum ExtGridInv implements ExtInv<IItemHandler> {
    INSTANCE;

    public IStorageService getGridStorage(BlockEntity raw) {
        if (!(raw instanceof AENetworkPowerBlockEntity node)) return null;
        var grid = node.getMainNode().getGrid();
        if (grid == null) return null;
        return grid.getStorageService();
    }

    public IItemHandler apply(Level level, BlockPos pos, Direction dir) {
        // TODO sub block check
        var be = level.getBlockEntity(pos);
        var storage = getGridStorage(be);
        return storage == null ? null : ExposedAENetwork.get(storage);
    }
}
