package einstein.white_pumpkins.mixin;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockColors.class)
public interface BlockColorsAccessor {

    @Invoker("addColoringState")
    void whitePumpkins$addColoringState(Property<?> property, Block... blocks);
}
