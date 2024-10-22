package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import einstein.white_pumpkins.ModInit;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(BlockColors.class)
public class BlockColorsMixin {

    @WrapOperation(method = "createDefault", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/block/BlockColors;addColoringState(Lnet/minecraft/world/level/block/state/properties/Property;[Lnet/minecraft/world/level/block/Block;)V"))
    private static void create(BlockColors instance, Property<?> property, Block[] blocks, Operation<Void> original) {
        original.call(instance, property, whitePumpkins$addWhitePumpkinStem(blocks));
    }

    @WrapOperation(method = "createDefault", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/block/BlockColors;register(Lnet/minecraft/client/color/block/BlockColor;[Lnet/minecraft/world/level/block/Block;)V"))
    private static void create(BlockColors instance, BlockColor blockColor, Block[] blocks, Operation<Void> original) {
        original.call(instance, blockColor, whitePumpkins$addWhitePumpkinStem(blocks));
    }

    @Unique
    private static Block[] whitePumpkins$addWhitePumpkinStem(Block[] blocks) {
        List<Block> list = new ArrayList<>(List.of(blocks));
        if (list.contains(Blocks.PUMPKIN_STEM)) {
            list.add(ModInit.WHITE_PUMPKIN_STEM.get());
        }

        if (list.contains(Blocks.ATTACHED_PUMPKIN_STEM)) {
            list.add(ModInit.ATTACHED_WHITE_PUMPKIN_STEM.get());
        }
        return list.toArray(Block[]::new);
    }
}
