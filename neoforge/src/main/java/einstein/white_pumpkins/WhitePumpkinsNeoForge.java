package einstein.white_pumpkins;

import einstein.white_pumpkins.entity.WhitePumpkinSnowGolem;
import einstein.white_pumpkins.platform.NeoForgeRegistryHelper;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@Mod(WhitePumpkins.MOD_ID)
public class WhitePumpkinsNeoForge {

    public WhitePumpkinsNeoForge(IEventBus modEventBus) {
        WhitePumpkins.init();
        NeoForgeRegistryHelper.BLOCKS.register(modEventBus);
        NeoForgeRegistryHelper.ITEMS.register(modEventBus);
        NeoForgeRegistryHelper.ENTITY_TYPES.register(modEventBus);
        modEventBus.addListener((FMLClientSetupEvent event) -> WhitePumpkins.clientSetup());
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
            else if (tabKey.equals(CreativeModeTabs.SPAWN_EGGS)) {
                event.insertAfter(new ItemStack(Items.SNOW_GOLEM_SPAWN_EGG), new ItemStack(ModInit.WHITE_PUMPKIN_SNOW_GOLEM_SPAWN_EGG.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        });
        modEventBus.addListener((EntityRenderersEvent.RegisterRenderers event) ->
                event.registerEntityRenderer(ModInit.WHITE_PUMPKIN_SNOW_GOLEM.get(), SnowGolemRenderer::new));
        modEventBus.addListener((EntityAttributeCreationEvent event) ->
                event.put(ModInit.WHITE_PUMPKIN_SNOW_GOLEM.get(), WhitePumpkinSnowGolem.createAttributes().build()));
        modEventBus.addListener((RegisterColorHandlersEvent.Block event) ->
                event.register(WhitePumpkins::getWhitePumpkinSteamColor, ModInit.WHITE_PUMPKIN_STEM.get()));
        NeoForge.EVENT_BUS.addListener((WandererTradesEvent event) -> {
            event.getGenericTrades().add(new BasicItemListing(2, new ItemStack(ModInit.WHITE_PUMPKIN_SEEDS.get()), 12, 1));
        });
    }
}
