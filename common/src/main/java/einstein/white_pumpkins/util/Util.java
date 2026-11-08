package einstein.white_pumpkins.util;

import com.google.common.base.Suppliers;
import einstein.white_pumpkins.WhitePumpkins;
import einstein.white_pumpkins.block.WhitePumpkinStemBlock;
import einstein.white_pumpkins.platform.Services;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Set;
import java.util.function.Supplier;

import static einstein.white_pumpkins.WhitePumpkins.loc;

public class Util {

    public static final ResourceKey<LootTable> MINESHAFT_INJECTED_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, loc("injected/abandoned_mineshaft"));
    public static final ResourceKey<LootTable> DUNGEON_MANSION_INJECTED_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, loc("injected/simple_dungeon_woodland_mansion"));
    public static final ResourceKey<PlacedFeature> PATCH_WHITE_PUMPKIN_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, loc("patch_white_pumpkin"));
    public static final TagKey<Biome> HAS_WHITE_PUMPKIN_PATCHES_TAG = TagKey.create(Registries.BIOME, loc("has_white_pumpkin_patches"));
    public static final TagKey<Item> WHITE_PUMPKIN_EXTRA_DAMAGE = TagKey.create(Registries.ITEM, loc("white_pumpkin_extra_damage"));
    public static final TagKey<Item> CARVED_WHITE_PUMPKINS = TagKey.create(Registries.ITEM, loc("carved_white_pumpkins"));
    public static final Supplier<Identifier> PACK_LOCATION = Suppliers.memoize(() -> loc("animated_white_pumpkin_eyes").withPrefix(Services.HOOKS.isForgeLike() ? "resourcepacks/" : ""));
    public static final Component PACK_NAME = Component.translatable("resourcePack.white_pumpkins.animated_white_pumpkin_eyes.name");

    public static BlockTintSource createWhitePumpkinSteamTintSource() {
        return new BlockTintSource() {

            @Override
            public int color(BlockState state) {
                int age = state.getValue(WhitePumpkinStemBlock.AGE);
                int i = age + 1;
                int i1 = (int) (((255F - 32F) / WhitePumpkinStemBlock.MAX_AGE) * age);
                return ARGB.color(32 + i1, 247 + i, 4 * i + i1);
            }

            @Override
            public Set<Property<?>> relevantProperties() {
                return Set.of(WhitePumpkinStemBlock.AGE);
            }
        };
    }

    public static boolean shouldObfuscateNames(Entity entity) {
        return WhitePumpkins.CONFIGS.whitePumpkinsObfuscateNames && entity instanceof LivingEntity livingEntity && isWearingWhitePumpkin(livingEntity);
    }

    public static boolean isWearingWhitePumpkin(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(CARVED_WHITE_PUMPKINS);
    }

    public static float applyExtraWhitePumpkinDamage(ItemStack weaponStack, LivingEntity attackerEntity, float damage) {
        if (weaponStack.is(WHITE_PUMPKIN_EXTRA_DAMAGE) && isWearingWhitePumpkin(attackerEntity)) {
            damage += WhitePumpkins.CONFIGS.whitePumpkinEquippedExtraDamage.get();
        }
        return damage;
    }
}
