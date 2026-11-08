package einstein.white_pumpkins;

import einstein.white_pumpkins.entity.WhitePumpkinSnowGolem;
import einstein.white_pumpkins.init.ModInit;
import einstein.white_pumpkins.util.Util;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.List;

@Mod(WhitePumpkins.MOD_ID)
public class WhitePumpkinsNeoForge {

    public WhitePumpkinsNeoForge(IEventBus modEventBus) {
        WhitePumpkins.init();
        modEventBus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
            if (tabKey.equals(CreativeModeTabs.NATURAL_BLOCKS)) {
                event.insertAfter(new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(ModInit.WHITE_PUMPKIN_SEEDS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(new ItemStack(Blocks.JACK_O_LANTERN), new ItemStack(ModInit.WHITE_PUMPKIN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(new ItemStack(ModInit.WHITE_PUMPKIN.get()), new ItemStack(ModInit.CARVED_WHITE_PUMPKIN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(new ItemStack(ModInit.CARVED_WHITE_PUMPKIN.get()), new ItemStack(ModInit.DAMAGED_CARVED_WHITE_PUMPKIN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(new ItemStack(ModInit.DAMAGED_CARVED_WHITE_PUMPKIN.get()), new ItemStack(ModInit.WHITE_JACK_O_LANTERN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(new ItemStack(ModInit.WHITE_JACK_O_LANTERN.get()), new ItemStack(ModInit.WHITE_SOUL_JACK_O_LANTERN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
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
        modEventBus.addListener((RegisterColorHandlersEvent.BlockTintSources event) ->
                event.register(List.of(Util.createWhitePumpkinSteamTintSource()), ModInit.WHITE_PUMPKIN_STEM.get()));
        modEventBus.addListener((AddPackFindersEvent event) -> {
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                event.addPackFinders(Util.PACK_LOCATION.get(), PackType.CLIENT_RESOURCES,
                        Util.PACK_NAME, PackSource.BUILT_IN, false, Pack.Position.TOP);
            }
        });
    }
}
