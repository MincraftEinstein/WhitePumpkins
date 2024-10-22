package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import einstein.white_pumpkins.ModInit;
import net.minecraft.client.renderer.entity.layers.SnowGolemHeadLayer;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SnowGolemHeadLayer.class)
public class SnowGolemHeadLayerMixin {

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/SnowGolem;FFFFFF)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;CARVED_PUMPKIN:Lnet/minecraft/world/level/block/Block;")
    )
    private Block render(Operation<Block> original, @Local(argsOnly = true) SnowGolem golem) {
        if (golem.getType().equals(ModInit.WHITE_PUMPKIN_SNOW_GOLEM.get())) {
            return ModInit.CARVED_WHITE_PUMPKIN.get();
        }
        return original.call();
    }
}
