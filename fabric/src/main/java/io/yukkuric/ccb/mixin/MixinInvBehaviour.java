package io.yukkuric.ccb.mixin;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.CapManipulationBehaviourBase;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.InvManipulationBehaviour;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InvManipulationBehaviour.class)
public abstract class MixinInvBehaviour extends CapManipulationBehaviourBase<ItemVariant, InvManipulationBehaviour> {
    public MixinInvBehaviour(SmartBlockEntity be, InterfaceProvider target) {
        super(be, target);
    }

    @Override
    public void findNewCapability() {
        /*var world = getWorld();
        var target = this.target.getTarget(world, this.blockEntity.getBlockPos(), this.blockEntity.getBlockState());
        var targetPos = target.getConnectedPos();
        if (!world.isLoaded(targetPos)) {
            setProvider(null, null); // TODO why this private?
            return;
        }
        var targetSide = target.getOppositeFace();
        var cap = ExtInvPoolFabric.get().getInventory(world, targetPos, targetSide);
        if (cap == null) super.findNewCapability();
        else setProvider(cap, targetSide);*/
        // TODO WDYM missing io.github.fabricators_of_create.porting_lib.core.util.INBTSerializable???
    }
}
