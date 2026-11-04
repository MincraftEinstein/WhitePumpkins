package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import einstein.white_pumpkins.ModInit;
import einstein.white_pumpkins.util.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({AbstractSkeleton.class, Zombie.class})
public class ZombieAndAbstractSkeletonMixin {

    @Unique
    private final Entity whitePumpkins$me = (Entity) (Object) this;

    @ModifyExpressionValue(method = "finalizeSpawn", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;CARVED_PUMPKIN:Lnet/minecraft/world/level/block/Block;"))
    private Block replacePumpkin(Block carvedPumpkinBlock, ServerLevelAccessor accessor) {
        return accessor.getBiome(whitePumpkins$me.blockPosition()).is(Util.HAS_WHITE_PUMPKIN_PATCHES_TAG)
                ? ModInit.CARVED_WHITE_PUMPKIN.get() : carvedPumpkinBlock;
    }

    @ModifyExpressionValue(method = "finalizeSpawn", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;JACK_O_LANTERN:Lnet/minecraft/world/level/block/Block;"))
    private Block replaceJackOLantern(Block jackOLanternBlock, ServerLevelAccessor accessor, @Local RandomSource random) {
        return accessor.getBiome(whitePumpkins$me.blockPosition()).is(Util.HAS_WHITE_PUMPKIN_PATCHES_TAG)
                ? (random.nextBoolean() ? ModInit.WHITE_JACK_O_LANTERN.get() : ModInit.WHITE_SOUL_JACK_O_LANTERN.get())
                : jackOLanternBlock;
    }
}
