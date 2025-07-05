package io.yukkuric.ccb.forge.rs.mixin_interface;

import com.refinedmods.refinedstorage.api.util.StackListEntry;
import net.minecraft.world.item.ItemStack;

public interface CachedItemStackList {
    StackListEntry<ItemStack>[] getCachedList();
}
