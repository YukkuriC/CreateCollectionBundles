package io.yukkuric.ccb.forge.ae.mixin;

import appeng.api.stacks.*;
import io.yukkuric.ccb.forge.ae.mixin_interface.CachedKeyCounterList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.List;

@Mixin(KeyCounter.class)
public class CachedKeyCounterListImpl implements CachedKeyCounterList {
    @Shadow(remap = false)
    @Final
//    private Reference2ObjectMap<Object, VariantCounter> lists;
    private Reference2ObjectMap<Object, Iterable<Object2LongMap.Entry<AEKey>>> lists;
    private List<Object2LongMap.Entry<AEItemKey>> cached;

    public void manualInvalidate() {
        cached = null;
    }
    public List<Object2LongMap.Entry<AEItemKey>> getItemList() {
        if (cached == null) {// build new
            List<Object2LongMap.Entry<AEKey>> lst = new ArrayList<>();
            for (var grp : lists.entrySet()) {
                if (!(grp.getKey() instanceof Item)) continue;
                for (var pair : grp.getValue()) {
                    if (!(pair.getKey() instanceof AEItemKey)) continue;
                    lst.add(pair);
                }
            }
            cached = List.class.cast(lst);
        }
        return cached;
    }
}
