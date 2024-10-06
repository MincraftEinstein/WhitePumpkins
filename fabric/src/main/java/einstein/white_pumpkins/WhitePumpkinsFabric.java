package einstein.white_pumpkins;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.RenderType;

public class WhitePumpkinsFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        WhitePumpkins.init();
    }

    @Override
    public void onInitializeClient() {
        WhitePumpkins.clientSetup();
        BlockRenderLayerMap.INSTANCE.putBlock(ModInit.WHITE_PUMPKIN_STEM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModInit.ATTACHED_WHITE_PUMPKIN_STEM.get(), RenderType.cutout());
        ColorProviderRegistry.BLOCK.register((state, tintGetter, pos, tintIndex) ->
                WhitePumpkins.registerBlockColors(state, pos, tintGetter, tintIndex)
        );
    }
}
