package io.yukkuric.ccb.fabric;

import io.yukkuric.ccb.CCBMain;
import net.fabricmc.api.ModInitializer;

public final class CCBFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CCBMain.Init();
    }
}
