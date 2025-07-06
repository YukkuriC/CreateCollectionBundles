package io.yukkuric.ccb.mixin;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.CapManipulationBehaviourBase;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.InvManipulationBehaviour;
import io.yukkuric.ccb.forge.ExtInvPoolForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InvManipulationBehaviour.class)
public abstract class MixinInvBehaviour extends CapManipulationBehaviourBase<IItemHandler, InvManipulationBehaviour> {
    public MixinInvBehaviour(SmartBlockEntity be, InterfaceProvider target) {
        super(be, target);
    }

    @Override
    public void findNewCapability() {
        var world = this.getWorld();
        var target = getTarget();
        var targetPos = target.getConnectedPos();
        if (!world.isLoaded(targetPos)) {
            targetCapability = LazyOptional.empty();
            return;
        }
        var cap = ExtInvPoolForge.get().getInventory(world, targetPos, target.getOppositeFace());
        if (cap == null) super.findNewCapability();
        else targetCapability = LazyOptional.of(() -> cap);
    }
}
