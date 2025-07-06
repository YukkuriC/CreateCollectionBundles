package io.yukkuric.ccb.forge.ae.mixin;

import appeng.api.stacks.KeyCounter;
import appeng.me.service.StorageService;
import io.yukkuric.ccb.forge.ae.mixin_interface.CachedKeyCounterList;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StorageService.class)
public abstract class MixinStorageService implements CachedKeyCounterList {
    @Shadow(remap = false)
    @Final
    private KeyCounter cachedAvailableStacks;

    @Inject(method = "updateCachedStacks", at = @At("HEAD"), remap = false)
    void hookInvalidate(CallbackInfo ci) {
        CachedKeyCounterList.class.cast(cachedAvailableStacks).manualInvalidate();
    }
}
