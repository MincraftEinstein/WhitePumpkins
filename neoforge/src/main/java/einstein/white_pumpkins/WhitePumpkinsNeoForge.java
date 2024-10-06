package einstein.white_pumpkins;

import einstein.white_pumpkins.platform.NeoForgeRegistryHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

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
        modEventBus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
            if (tabKey.equals(CreativeModeTabs.NATURAL_BLOCKS)) {
                event.insertAfter(new ItemStack(Blocks.PUMPKIN), new ItemStack(ModInit.WHITE_PUMPKIN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(new ItemStack(Blocks.CARVED_PUMPKIN), new ItemStack(ModInit.CARVED_WHITE_PUMPKIN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(new ItemStack(Blocks.JACK_O_LANTERN), new ItemStack(ModInit.WHITE_JACK_O_LANTERN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(ModInit.WHITE_PUMPKIN_SEEDS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
            else if (tabKey.equals(CreativeModeTabs.FOOD_AND_DRINKS)) {
                event.insertAfter(new ItemStack(Items.PUMPKIN_PIE), new ItemStack(ModInit.WHITE_PUMPKIN_PIE.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        });
    }
}
