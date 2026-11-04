package einstein.white_pumpkins.mixin;

import einstein.white_pumpkins.util.Util;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(method = "shouldShowName*", at = @At("HEAD"), cancellable = true)
    private void shouldShowName(@Coerce Entity entity, double v, CallbackInfoReturnable<Boolean> cir) {
        if (Util.shouldObfuscateNames(entity)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
