package einstein.white_pumpkins.mixin;

import einstein.white_pumpkins.ModInit;
import einstein.white_pumpkins.SnowGolemRenderStateAccessor;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import net.minecraft.world.entity.animal.SnowGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolemRenderer.class)
public class SnowGolemRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/SnowGolem;Lnet/minecraft/client/renderer/entity/state/SnowGolemRenderState;F)V", at = @At("TAIL"))
    private void setAsWhitePumpkinSnowGolem(SnowGolem snowGolem, SnowGolemRenderState renderState, float partialTick, CallbackInfo ci) {
        if (snowGolem.getType().equals(ModInit.WHITE_PUMPKIN_SNOW_GOLEM.get())) {
            ((SnowGolemRenderStateAccessor) renderState).whitePumpkins$setHasWhitePumpkin();
        }
    }
}
