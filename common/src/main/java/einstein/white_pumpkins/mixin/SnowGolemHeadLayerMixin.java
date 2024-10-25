package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import einstein.white_pumpkins.ModInit;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SnowGolemRenderer.class)
public class SnowGolemHeadLayerMixin {

    @WrapOperation(method = "extractRenderState(Lnet/minecraft/world/entity/animal/SnowGolem;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;CARVED_PUMPKIN:Lnet/minecraft/world/item/Item;")
    )
    private Item render(Operation<Item> original, @Local(argsOnly = true) SnowGolem golem) {
        if (golem.getType().equals(ModInit.WHITE_PUMPKIN_SNOW_GOLEM.get())) {
            return ModInit.CARVED_WHITE_PUMPKIN.get().asItem();
        }
        return original.call();
    }
}
