package einstein.white_pumpkins.mixin;

import einstein.white_pumpkins.WhitePumpkins;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {

    @Inject(method = "shouldShowName*", at = @At("HEAD"), cancellable = true)
    private void shouldShowName(T entity, double d, CallbackInfoReturnable<Boolean> cir) {
        if (WhitePumpkins.isWearingWhitePumpkin(entity)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
