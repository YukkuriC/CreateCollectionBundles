package io.yukkuric.ccb.forge;

import io.yukkuric.ccb.CCBMain;
import io.yukkuric.ccb.forge.ae.ExposedAENetwork;
import io.yukkuric.ccb.forge.contents.CarvePumpkinHandler;
import io.yukkuric.ccb.forge.rs.ExposedRSNetwork;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod(CCBMain.MOD_ID)
public final class CCBForge {
    public CCBForge() {
        CCBMain.Init();

        var INV = ExtInvPoolForge.get();
        // INV.register(ResourceLocation.tryParse("enchanting_table"), (lvl, pos, dir) -> new InfiniteBookHandler());
        INV.register(ResourceLocation.tryParse("pumpkin"), CarvePumpkinHandler::new);

        // RS
        if (modLoaded("refinedstorage")) {
            ExposedRSNetwork.InitSelf();
        }
        // AE
        if (modLoaded("ae2")) {
            ExposedAENetwork.InitSelf();
        }
    }

    public static boolean modLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
//    public static void debugLog(String value) {
//        var player = Minecraft.getInstance().player;
//        if (player != null) player.sendSystemMessage(Component.literal(value));
//    }
}
