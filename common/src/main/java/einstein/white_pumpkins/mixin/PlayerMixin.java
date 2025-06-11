package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import einstein.white_pumpkins.WhitePumpkins;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

    @Unique
    private final Player whitePumpkins$player = (Player) (Object) this;

    @ModifyReturnValue(method = "getName", at = @At("RETURN"))
    private Component obfuscatePlayerName(Component original) {
        if (WhitePumpkins.isWearingWhitePumpkin(whitePumpkins$player)) {
            return Component.literal("Unknown").setStyle(original.getStyle().withObfuscated(true));
        }
        return original;
    }

    @ModifyReturnValue(method = "getDisplayName", at = @At(value = "RETURN"))
    private Component skipDisplayNameEvents(Component original) {
        if (WhitePumpkins.isWearingWhitePumpkin(whitePumpkins$player)) {
            return PlayerTeam.formatNameForTeam(whitePumpkins$player.getTeam(), whitePumpkins$player.getName());
        }
        return original;
    }
}
