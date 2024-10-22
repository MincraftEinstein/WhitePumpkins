package einstein.white_pumpkins;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class WhitePumpkinsFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        WhitePumpkins.init();

        CompostingChanceRegistry.INSTANCE.add(ModInit.WHITE_PUMPKIN.get(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModInit.CARVED_WHITE_PUMPKIN.get(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModInit.WHITE_PUMPKIN_SEEDS.get(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModInit.WHITE_PUMPKIN_PIE.get(), 1F);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addAfter(Blocks.PUMPKIN, new ItemStack(ModInit.WHITE_PUMPKIN.get()));
            entries.addAfter(Blocks.CARVED_PUMPKIN, new ItemStack(ModInit.CARVED_WHITE_PUMPKIN.get()));
            entries.addAfter(Blocks.JACK_O_LANTERN, new ItemStack(ModInit.WHITE_JACK_O_LANTERN.get()));
            entries.addAfter(Items.PUMPKIN_SEEDS, new ItemStack(ModInit.WHITE_PUMPKIN_SEEDS.get()));
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.addAfter(Items.PUMPKIN_PIE, new ItemStack(ModInit.WHITE_PUMPKIN_PIE.get()));
        });
        TradeOfferHelper.registerWanderingTraderOffers(1, listings -> {
            listings.add(new VillagerTrades.ItemsForEmeralds(ModInit.WHITE_PUMPKIN_SEEDS.get(), 2, 1, 1));
        });
    }

    @Override
    public void onInitializeClient() {
        WhitePumpkins.clientSetup();
        BlockRenderLayerMap.INSTANCE.putBlock(ModInit.WHITE_PUMPKIN_STEM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModInit.ATTACHED_WHITE_PUMPKIN_STEM.get(), RenderType.cutout());
    }
}
