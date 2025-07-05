package io.yukkuric.ccb.forge.rs.mixin;

import com.refinedmods.refinedstorage.api.util.StackListEntry;
import com.refinedmods.refinedstorage.api.util.StackListResult;
import com.refinedmods.refinedstorage.apiimpl.util.ItemStackList;
import io.yukkuric.ccb.forge.rs.mixin_interface.CachedItemStackList;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import java.util.Collection;

@Mixin(ItemStackList.class)
public abstract class CachedItemStackListImp implements CachedItemStackList {
    @Shadow(remap = false)
    @Nonnull
    public abstract Collection<StackListEntry<ItemStack>> getStacks();
    private StackListEntry<ItemStack>[] cached;

    @Inject(method = {"add(Lnet/minecraft/world/item/ItemStack;I)Lcom/refinedmods/refinedstorage/api/util/StackListResult;", "remove(Lnet/minecraft/world/item/ItemStack;I)Lcom/refinedmods/refinedstorage/api/util/StackListResult;"}, at = @At("HEAD"), remap = false)
    void hookedInvalidate(ItemStack stack, int size, CallbackInfoReturnable<StackListResult<ItemStack>> cir) {
        cached = null;
    }

    public StackListEntry<ItemStack>[] getCachedList() {
        if (cached == null) cached = getStacks().toArray(StackListEntry[]::new);
        return cached;
    }
}
