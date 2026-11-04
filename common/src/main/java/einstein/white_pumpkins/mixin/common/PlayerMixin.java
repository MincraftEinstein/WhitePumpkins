package einstein.white_pumpkins.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import einstein.white_pumpkins.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.PlayerTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

    @Unique
    private final Player whitePumpkins$player = (Player) (Object) this;

    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getEnchantedDamage(Lnet/minecraft/world/entity/Entity;FLnet/minecraft/world/damagesource/DamageSource;)F"))
    private float modifyDamage(Player player, Entity targetEntity, float damage, DamageSource damageSource, Operation<Float> original, @Local ItemStack weaponStack) {
        return Util.applyExtraWhitePumpkinDamage(weaponStack, player, original.call(player, targetEntity, damage, damageSource));
    }

    @ModifyReturnValue(method = "getName", at = @At("RETURN"))
    private Component obfuscatePlayerName(Component original) {
        if (Util.shouldObfuscateNames(whitePumpkins$player)) {
            return Component.literal("Unknown").setStyle(original.getStyle().withObfuscated(true));
        }
        return original;
    }

    @ModifyReturnValue(method = "getDisplayName", at = @At(value = "RETURN"))
    private Component skipDisplayNameEvents(Component original) {
        if (Util.shouldObfuscateNames(whitePumpkins$player)) {
            return PlayerTeam.formatNameForTeam(whitePumpkins$player.getTeam(), whitePumpkins$player.getName());
        }
        return original;
    }
}
