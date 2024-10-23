package einstein.white_pumpkins;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhitePumpkins {

    public static final String MOD_ID = "white_pumpkins";
    public static final String MOD_NAME = "WhitePumpkins";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final ResourceKey<LootTable> MINESHAFT_INJECTED_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, loc("injected/abandoned_mineshaft"));
    public static final ResourceKey<LootTable> DUNGEON_MANSION_INJECTED_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, loc("injected/simple_dungeon_woodland_mansion"));

    public static void init() {
        ModInit.init();
    }

    public static void clientSetup() {
        ItemProperties.register(ModInit.CARVED_WHITE_PUMPKIN.get().asItem(), WhitePumpkins.loc("on_head"),
                (stack, level, entity, seed) -> {
                    if (entity != null) {
                        if (stack.equals(entity.getItemBySlot(EquipmentSlot.HEAD))) {
                            return 1;
                        }
                    }
                    return 0;
                }
        );
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
