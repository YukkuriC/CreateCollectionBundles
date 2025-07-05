package io.yukkuric.ccb.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.function.TriFunction;

public interface ExtInv<RET> extends TriFunction<Level, BlockPos, Direction, RET> {
}
