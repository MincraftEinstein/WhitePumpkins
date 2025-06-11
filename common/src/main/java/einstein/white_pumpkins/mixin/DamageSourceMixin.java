package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import einstein.white_pumpkins.WhitePumpkins;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DamageSource.class)
public class DamageSourceMixin {

    @Unique
    private final DamageSource whitePumpkins$damageSource = (DamageSource) (Object) this;

    @ModifyExpressionValue(method = "getLocalizedDeathMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getDisplayName()Lnet/minecraft/network/chat/Component;"))
    private Component obfuscateItemName(Component original) {
        Entity entity = whitePumpkins$damageSource.getEntity() == null ? whitePumpkins$damageSource.getDirectEntity() : whitePumpkins$damageSource.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (WhitePumpkins.isWearingWhitePumpkin(livingEntity)) {
                return Component.literal("Unknown").setStyle(original.getStyle().withHoverEvent(null).withClickEvent(null).withObfuscated(true));
            }
        }
        return original;
    }
}
