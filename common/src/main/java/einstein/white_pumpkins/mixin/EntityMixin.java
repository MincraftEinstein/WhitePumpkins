package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import einstein.white_pumpkins.WhitePumpkins;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {

    @Unique
    private final Entity whitePumpkins$entity = (Entity) (Object) this;

    @ModifyReturnValue(method = "getDisplayName", at = @At(value = "RETURN"))
    private Component skipDisplayNameEvents(Component original) {
        if (WhitePumpkins.shouldObfuscateNames(whitePumpkins$entity)) {
            return PlayerTeam.formatNameForTeam(whitePumpkins$entity.getTeam(), whitePumpkins$entity.getName());
        }
        return original;
    }

    @ModifyReturnValue(method = "getCustomName", at = @At("RETURN"))
    private Component obfuscateEntityName(@Nullable Component original) {
        if (original != null && WhitePumpkins.shouldObfuscateNames(whitePumpkins$entity)) {
            return Component.literal("Unknown").setStyle(original.getStyle().withObfuscated(true));
        }
        return original;
    }
}
