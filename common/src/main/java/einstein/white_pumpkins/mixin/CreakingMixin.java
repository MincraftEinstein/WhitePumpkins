package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import einstein.white_pumpkins.ModInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Creaking.class)
public class CreakingMixin {

    @ModifyExpressionValue(method = "checkCanMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/creaking/Creaking;canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z"))
    private boolean ignorePlayersWearingWhitePumpkins(boolean original, @Local Player player) {
        return original && !player.getItemBySlot(EquipmentSlot.HEAD).is(ModInit.CARVED_WHITE_PUMPKIN.get().asItem());
    }
}
