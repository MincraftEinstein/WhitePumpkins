package einstein.white_pumpkins;

import einstein.white_pumpkins.entity.WhitePumpkinSnowGolem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

import static einstein.white_pumpkins.WhitePumpkins.HAS_WHITE_PUMPKIN_PATCHES_TAG;
import static einstein.white_pumpkins.WhitePumpkins.loc;

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
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.addAfter(Items.SNOW_GOLEM_SPAWN_EGG, new ItemStack(ModInit.WHITE_PUMPKIN_SNOW_GOLEM_SPAWN_EGG.get()));
        });
        TradeOfferHelper.registerWanderingTraderOffers(builder ->
                builder.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL,
                        new VillagerTrades.ItemsForEmeralds(ModInit.WHITE_PUMPKIN_SEEDS.get(), 2, 1, 1))
        );
        FabricDefaultAttributeRegistry.register(ModInit.WHITE_PUMPKIN_SNOW_GOLEM.get(), WhitePumpkinSnowGolem.createAttributes());
        LootTableEvents.MODIFY.register((key, builder, source, registries) -> {
            if (source.isBuiltin()) {
                if (key.equals(BuiltInLootTables.SIMPLE_DUNGEON)) {
                    builder.withPool(new LootPool.Builder()
                            .add(NestedLootTable.lootTableReference(WhitePumpkins.DUNGEON_MANSION_INJECTED_LOOT_TABLE))
                    );
                }
                else if (key.equals(BuiltInLootTables.ABANDONED_MINESHAFT)) {
                    builder.withPool(new LootPool.Builder()
                            .add(NestedLootTable.lootTableReference(WhitePumpkins.MINESHAFT_INJECTED_LOOT_TABLE))
                    );
                }
                else if (key.equals(BuiltInLootTables.WOODLAND_MANSION)) {
                    builder.withPool(new LootPool.Builder()
                            .add(NestedLootTable.lootTableReference(WhitePumpkins.DUNGEON_MANSION_INJECTED_LOOT_TABLE))
                    );
                }
            }
        });

        BiomeModifications.addFeature(context -> context.hasTag(HAS_WHITE_PUMPKIN_PATCHES_TAG), GenerationStep.Decoration.VEGETAL_DECORATION, WhitePumpkins.PATCH_WHITE_PUMPKIN_FEATURE);
        BiomeModifications.create(loc("remove_pumpkin_patches")).add(ModificationPhase.REMOVALS, context -> context.hasTag(HAS_WHITE_PUMPKIN_PATCHES_TAG), (context, modificationContext) ->
                modificationContext.getGenerationSettings().removeFeature(VegetationPlacements.PATCH_PUMPKIN));
    }

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModInit.WHITE_PUMPKIN_STEM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModInit.ATTACHED_WHITE_PUMPKIN_STEM.get(), RenderType.cutout());
        EntityRendererRegistry.register(ModInit.WHITE_PUMPKIN_SNOW_GOLEM.get(), SnowGolemRenderer::new);
        ColorProviderRegistry.BLOCK.register(WhitePumpkins::getWhitePumpkinSteamColor, ModInit.WHITE_PUMPKIN_STEM.get());
    }
}
