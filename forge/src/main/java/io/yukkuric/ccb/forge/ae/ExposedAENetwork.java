package io.yukkuric.ccb.forge.ae;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.networking.storage.IStorageService;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.MEStorage;
import appeng.core.AppEng;
import io.yukkuric.ccb.forge.ExtInvPoolForge;
import io.yukkuric.ccb.forge.ae.mixin_interface.CachedKeyCounterList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ExposedAENetwork implements IItemHandler {
    static final boolean ENABLE_INSERT = true;
    static final int BUFFER_FOR_IMPORT = 1;

    private static final Map<IStorageService, ExposedAENetwork> CACHED = new WeakHashMap<>();
    public static ExposedAENetwork get(@NotNull IStorageService storage) {
        return CACHED.computeIfAbsent(storage, ExposedAENetwork::new);
    }

    private final IStorageService service;
    private final MEStorage storage;
    private ExposedAENetwork(@NotNull IStorageService service) {
        this.service = service;
        storage = service.getInventory();
    }

    private List<Object2LongMap.Entry<AEItemKey>> getList() {
        return CachedKeyCounterList.class.cast(service.getCachedInventory()).getItemList();
    }

    public int getSlots() {
        return BUFFER_FOR_IMPORT + getList().size();
    }

    @NotNull
    public ItemStack getStackInSlot(int slot) {
        if (slot < BUFFER_FOR_IMPORT || slot >= getSlots()) return ItemStack.EMPTY;
        var pair = getList().get(slot - BUFFER_FOR_IMPORT);
        return pair.getKey().toStack((int) Math.min(Integer.MAX_VALUE, pair.getLongValue()));
    }
    public ItemStack getReadonlyStackInSlot(int slot) {
        if (slot < BUFFER_FOR_IMPORT || slot >= getSlots()) return ItemStack.EMPTY;
        var pair = getList().get(slot - BUFFER_FOR_IMPORT);
        return pair.getKey().getReadOnlyStack();
    }

    @NotNull
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (!ENABLE_INSERT) return stack;
        var inserted = (int) storage.insert(AEItemKey.of(stack), stack.getCount(), simulate ? Actionable.SIMULATE : Actionable.MODULATE, IActionSource.empty());
        return ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - inserted);
    }

    @NotNull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        var stack = getReadonlyStackInSlot(slot);
        if (stack.isEmpty()) return ItemStack.EMPTY;
        var extracted = (int) storage.extract(AEItemKey.of(stack), amount, simulate ? Actionable.SIMULATE : Actionable.MODULATE, IActionSource.empty());
        return ItemHandlerHelper.copyStackWithSize(stack, extracted);
    }

    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

    public static void InitSelf() {
        ExtInvPoolForge.get().register(ResourceLocation.tryBuild(AppEng.MOD_ID, "controller"), ExtGridInv.INSTANCE);
    }
}
