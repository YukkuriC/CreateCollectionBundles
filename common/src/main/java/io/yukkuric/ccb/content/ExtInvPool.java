package io.yukkuric.ccb.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class ExtInvPool<RET> {
    private final Map<ResourceLocation, ExtInv<RET>> ENTRIES = new HashMap<>();

    public RET getInventory(Level level, BlockPos pos, Direction dir) {
        var getter = ENTRIES.get(BuiltInRegistries.BLOCK.getKey(level.getBlockState(pos).getBlock()));
        if (getter == null) return null;
        return getter.apply(level, pos, dir);
    }

    public void register(ResourceLocation key, ExtInv<RET> entry) {
        ENTRIES.put(key, entry);
    }
}
