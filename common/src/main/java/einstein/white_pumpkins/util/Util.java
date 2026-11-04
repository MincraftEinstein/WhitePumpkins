package einstein.white_pumpkins.util;

import einstein.white_pumpkins.init.ModInit;
import einstein.white_pumpkins.WhitePumpkins;
import einstein.white_pumpkins.block.WhitePumpkinStemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootTable;

public class Util {

    public static final ResourceKey<LootTable> MINESHAFT_INJECTED_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, WhitePumpkins.loc("injected/abandoned_mineshaft"));
    public static final ResourceKey<LootTable> DUNGEON_MANSION_INJECTED_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, WhitePumpkins.loc("injected/simple_dungeon_woodland_mansion"));
    public static final ResourceKey<PlacedFeature> PATCH_WHITE_PUMPKIN_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, WhitePumpkins.loc("patch_white_pumpkin"));
    public static final TagKey<Biome> HAS_WHITE_PUMPKIN_PATCHES_TAG = TagKey.create(Registries.BIOME, WhitePumpkins.loc("has_white_pumpkin_patches"));
    public static final TagKey<Item> WHITE_PUMPKIN_EXTRA_DAMAGE = TagKey.create(Registries.ITEM, WhitePumpkins.loc("white_pumpkin_extra_damage"));

    public static int getWhitePumpkinSteamColor(BlockState state, BlockAndTintGetter tintGetter, BlockPos pos, int tintIndex) {
        int age = state.getValue(WhitePumpkinStemBlock.AGE);
        int i = age + 1;
        int i1 = (int) (((255F - 32F) / WhitePumpkinStemBlock.MAX_AGE) * age);
        return ARGB.color(32 + i1, 247 + i, 4 * i + i1);
    }

    public static boolean shouldObfuscateNames(Entity entity) {
        return WhitePumpkins.CONFIGS.whitePumpkinsObfuscateNames && entity instanceof LivingEntity livingEntity && isWearingWhitePumpkin(livingEntity);
    }

    public static boolean isWearingWhitePumpkin(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(ModInit.CARVED_WHITE_PUMPKIN.get().asItem());
    }

    public static float applyExtraWhitePumpkinDamage(ItemStack weaponStack, LivingEntity attackerEntity, float damage) {
        if (weaponStack.is(WHITE_PUMPKIN_EXTRA_DAMAGE) && isWearingWhitePumpkin(attackerEntity)) {
            damage += WhitePumpkins.CONFIGS.whitePumpkinEquippedExtraDamage.get();
        }
        return damage;
    }
}
