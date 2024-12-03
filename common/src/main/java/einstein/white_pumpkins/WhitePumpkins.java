package einstein.white_pumpkins;

import einstein.white_pumpkins.block.WhitePumpkinStemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhitePumpkins {

    public static final String MOD_ID = "white_pumpkins";
    public static final String MOD_NAME = "WhitePumpkins";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final ResourceKey<LootTable> MINESHAFT_INJECTED_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, loc("injected/abandoned_mineshaft"));
    public static final ResourceKey<LootTable> DUNGEON_MANSION_INJECTED_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, loc("injected/simple_dungeon_woodland_mansion"));
    public static final ResourceKey<PlacedFeature> PATCH_WHITE_PUMPKIN_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, loc("patch_white_pumpkin"));
    public static final TagKey<Biome> HAS_WHITE_PUMPKIN_PATCHES_TAG = TagKey.create(Registries.BIOME, loc("has_white_pumpkin_patches"));

    public static void init() {
        ModInit.init();
    }

    public static int getWhitePumpkinSteamColor(BlockState state, BlockAndTintGetter tintGetter, BlockPos pos, int tintIndex) {
        int age = state.getValue(WhitePumpkinStemBlock.AGE);
        int i = age + 1;
        int i1 = (int) (((255F - 32F) / WhitePumpkinStemBlock.MAX_AGE) * age);
        return ARGB.color(32 + i1, 247 + i, 4 * i + i1);
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
