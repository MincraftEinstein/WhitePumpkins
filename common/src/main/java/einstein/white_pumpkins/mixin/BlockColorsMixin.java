package einstein.white_pumpkins.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import einstein.white_pumpkins.ModInit;
import einstein.white_pumpkins.WhitePumpkins;
import einstein.white_pumpkins.block.WhitePumpkinStemBlock;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockColors.class)
public class BlockColorsMixin {

    @Inject(method = "createDefault", at = @At("TAIL"))
    private static void create(CallbackInfoReturnable<BlockColors> cir, @Local BlockColors blockColors) {
        blockColors.register((state, tintGetter, pos, tintIndex) -> {
            int age = state.getValue(WhitePumpkinStemBlock.AGE);
            int i = age + 1;
            int i1 = (int) (((255F - 32F) / WhitePumpkinStemBlock.MAX_AGE) * age);
            return ARGB.color(32 + i1, 247 + i, 4 * i + i1);
        }, ModInit.WHITE_PUMPKIN_STEM.get());
        ((BlockColorsAccessor) blockColors).whitePumpkins$addColoringState(WhitePumpkinStemBlock.AGE, ModInit.WHITE_PUMPKIN_STEM.get());
    }
}
