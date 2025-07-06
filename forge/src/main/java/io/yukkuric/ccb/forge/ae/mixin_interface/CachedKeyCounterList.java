package io.yukkuric.ccb.forge.ae.mixin_interface;

import appeng.api.stacks.AEItemKey;
import it.unimi.dsi.fastutil.objects.Object2LongMap;

import java.util.List;

public interface CachedKeyCounterList {
    void manualInvalidate();
    List<Object2LongMap.Entry<AEItemKey>> getItemList();
}
