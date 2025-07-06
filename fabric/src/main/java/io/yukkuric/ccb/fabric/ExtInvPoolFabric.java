package io.yukkuric.ccb.fabric;

import io.github.fabricators_of_create.porting_lib.util.StorageProvider;
import io.yukkuric.ccb.content.ExtInvPool;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;

public class ExtInvPoolFabric extends ExtInvPool<StorageProvider<ItemVariant>> {
    public static ExtInvPoolFabric INSTANCE;
    private ExtInvPoolFabric() {
        INSTANCE = this;
    }
    public static ExtInvPoolFabric get() {
        if (INSTANCE == null) INSTANCE = new ExtInvPoolFabric();
        return INSTANCE;
    }
}
