package einstein.white_pumpkins;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.client.renderer.RenderType;

public class WhitePumpkinsFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        WhitePumpkins.init();

        CompostingChanceRegistry.INSTANCE.add(ModInit.WHITE_PUMPKIN.get(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModInit.CARVED_WHITE_PUMPKIN.get(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModInit.WHITE_PUMPKIN_SEEDS.get(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModInit.WHITE_PUMPKIN_PIE.get(), 1F);
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
