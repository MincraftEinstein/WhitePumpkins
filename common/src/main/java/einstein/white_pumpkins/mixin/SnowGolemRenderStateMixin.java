package einstein.white_pumpkins.mixin;

import einstein.white_pumpkins.SnowGolemRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SnowGolemRenderState.class)
public class SnowGolemRenderStateMixin implements SnowGolemRenderStateAccessor {

    @Unique
    private boolean whitePumpkins$hasWhitePumpkin;

    @Override
    public void whitePumpkins$setHasWhitePumpkin() {
        whitePumpkins$hasWhitePumpkin = true;
    }

    @Override
    public boolean whitePumpkins$hasWhitePumpkin() {
        return whitePumpkins$hasWhitePumpkin;
    }
}
