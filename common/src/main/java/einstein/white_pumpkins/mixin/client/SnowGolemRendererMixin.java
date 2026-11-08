package einstein.white_pumpkins.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import einstein.white_pumpkins.init.ModInit;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SnowGolemRenderer.class)
public class SnowGolemRendererMixin {

    @ModifyExpressionValue(method = "extractRenderState(Lnet/minecraft/world/entity/animal/golem/SnowGolem;Lnet/minecraft/client/renderer/entity/state/SnowGolemRenderState;F)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;CARVED_PUMPKIN:Lnet/minecraft/world/level/block/Block;"))
    private Block setAsWhitePumpkinSnowGolem(Block original, SnowGolem snowGolem) {
        if (snowGolem.is(ModInit.WHITE_PUMPKIN_SNOW_GOLEM.get())) {
            return ModInit.CARVED_WHITE_PUMPKIN.get();
        }
        return original;
    }
}
