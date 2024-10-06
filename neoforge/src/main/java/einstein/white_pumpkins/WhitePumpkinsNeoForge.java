package einstein.white_pumpkins;

import einstein.white_pumpkins.platform.NeoForgeRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterNamedRenderTypesEvent;

@Mod(WhitePumpkins.MOD_ID)
public class WhitePumpkinsNeoForge {

    public WhitePumpkinsNeoForge(IEventBus modEventBus) {
        WhitePumpkins.init();
        NeoForgeRegistryHelper.BLOCKS.register(modEventBus);
        NeoForgeRegistryHelper.ITEMS.register(modEventBus);
        modEventBus.addListener((FMLClientSetupEvent event) -> WhitePumpkins.clientSetup());
        modEventBus.addListener((RegisterColorHandlersEvent.Block event) ->
                event.register((state, tintGetter, pos, tintIndex) ->
                        WhitePumpkins.registerBlockColors(state, pos, tintGetter, tintIndex)
                )
        );
    }
}
