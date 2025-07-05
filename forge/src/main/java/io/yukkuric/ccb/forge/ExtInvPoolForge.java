package io.yukkuric.ccb.forge;

import io.yukkuric.ccb.content.ExtInvPool;
import net.minecraftforge.items.IItemHandler;

public class ExtInvPoolForge extends ExtInvPool<IItemHandler> {
    public static ExtInvPoolForge INSTANCE;
    private ExtInvPoolForge() {
        INSTANCE = this;
    }
    public static ExtInvPoolForge get() {
        if (INSTANCE == null) INSTANCE = new ExtInvPoolForge();
        return INSTANCE;
    }
}
