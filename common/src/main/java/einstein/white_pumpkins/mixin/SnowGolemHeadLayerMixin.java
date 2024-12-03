package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import einstein.white_pumpkins.ModInit;
import einstein.white_pumpkins.SnowGolemRenderStateAccessor;
import net.minecraft.client.renderer.entity.layers.SnowGolemHeadLayer;
import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SnowGolemHeadLayer.class)
public class SnowGolemHeadLayerMixin {

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/SnowGolemRenderState;FF)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;CARVED_PUMPKIN:Lnet/minecraft/world/level/block/Block;")
    )
    private Block replacePumpkin(Operation<Block> original, @Local(argsOnly = true) SnowGolemRenderState renderState) {
        if (((SnowGolemRenderStateAccessor) renderState).whitePumpkins$hasWhitePumpkin()) {
            return ModInit.CARVED_WHITE_PUMPKIN.get();
        }
        return original.call();
    }
}
