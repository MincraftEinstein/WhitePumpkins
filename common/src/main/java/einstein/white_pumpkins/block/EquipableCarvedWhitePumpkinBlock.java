package einstein.white_pumpkins.block;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EquipableCarvedWhitePumpkinBlock extends CarvedWhitePumpkinBlock implements Equipable {

    public EquipableCarvedWhitePumpkinBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
