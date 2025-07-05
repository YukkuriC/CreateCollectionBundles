package io.yukkuric.ccb.forge.rs;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.blockentity.ControllerBlockEntity;
import com.refinedmods.refinedstorage.blockentity.NetworkNodeBlockEntity;
import io.yukkuric.ccb.content.ExtInv;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public interface ExtNetworkInv extends ExtInv<IItemHandler> {
    @Nullable INetwork getNetwork(BlockEntity src);

    @Override
    default IItemHandler apply(Level level, BlockPos pos, Direction dir) {
        var network = getNetwork(level.getBlockEntity(pos));
        return network == null ? null : ExposedRSNetwork.get(network);
    }

    ExtNetworkInv fromController = be -> {
        if (be instanceof ControllerBlockEntity src) return src.getNetwork();
        return null;
    };
    ExtNetworkInv fromNode = be -> {
        if (be instanceof NetworkNodeBlockEntity<?> src) return src.getNode().getNetwork();
        return null;
    };
}
