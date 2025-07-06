package io.yukkuric.ccb.forge.rs;

import com.refinedmods.refinedstorage.RS;
import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.util.*;
import io.yukkuric.ccb.forge.ExtInvPoolForge;
import io.yukkuric.ccb.forge.rs.mixin_interface.CachedItemStackList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.WeakHashMap;

public class ExposedRSNetwork implements IItemHandler {
    static final boolean ENABLE_INSERT = false;
    static final int BUFFER_FOR_IMPORT = 0; // not now

    private static final Map<INetwork, ExposedRSNetwork> CACHED = new WeakHashMap<>();
    public static ExposedRSNetwork get(@NotNull INetwork network) {
        return CACHED.computeIfAbsent(network, ExposedRSNetwork::new);
    }

    private final INetwork network;
    private final IStackList<ItemStack> itemList;

    private ExposedRSNetwork(@NotNull INetwork network) {
        this.network = network;
        itemList = network.getItemStorageCache().getList();
    }

    public StackListEntry<ItemStack>[] getList() {
        return CachedItemStackList.class.cast(itemList).getCachedList();
    }

    public int getSlots() {
        return getList().length + BUFFER_FOR_IMPORT;
    }

    @NotNull
    public ItemStack getStackInSlot(int slot) {
        if (slot < BUFFER_FOR_IMPORT || slot >= getSlots()) return ItemStack.EMPTY;
        return getList()[slot - BUFFER_FOR_IMPORT].getStack();
    }

    @NotNull
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (!ENABLE_INSERT) return stack;
        if (simulate) return network.insertItem(stack, stack.getCount(), Action.SIMULATE);
        return network.insertItemTracked(stack, stack.getCount());
    }

    @NotNull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        var stack = getStackInSlot(slot);
        if (stack.isEmpty()) return ItemStack.EMPTY;
        return network.extractItem(stack, amount, simulate ? Action.SIMULATE : Action.PERFORM);
    }

    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

    public static void InitSelf() {
        ExtInvPoolForge.get().register(ResourceLocation.tryBuild(RS.ID, "controller"), ExtNetworkInv.fromController);
        ExtInvPoolForge.get().register(ResourceLocation.tryBuild(RS.ID, "creative_controller"), ExtNetworkInv.fromController);
        ExtInvPoolForge.get().register(ResourceLocation.tryBuild(RS.ID, "network_transmitter"), ExtNetworkInv.fromNode);
        ExtInvPoolForge.get().register(ResourceLocation.tryBuild(RS.ID, "network_receiver"), ExtNetworkInv.fromNode);
    }
}
