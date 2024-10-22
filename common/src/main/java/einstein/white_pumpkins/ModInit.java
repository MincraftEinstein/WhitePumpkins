package einstein.white_pumpkins;

import einstein.white_pumpkins.block.*;
import einstein.white_pumpkins.entity.WhitePumpkinSnowGolem;
import einstein.white_pumpkins.platform.Services;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class ModInit {

    public static final Supplier<Block> WHITE_PUMPKIN = register("white_pumpkin", true, () -> new WhitePumpkinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN).mapColor(DyeColor.WHITE)));
    public static final Supplier<Block> CARVED_WHITE_PUMPKIN = register("carved_white_pumpkin", true, () -> new EquipableCarvedWhitePumpkinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARVED_PUMPKIN).mapColor(DyeColor.WHITE)));
    public static final Supplier<Block> WHITE_JACK_O_LANTERN = register("white_jack_o_lantern", true, () -> new CarvedWhitePumpkinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN).mapColor(DyeColor.WHITE)));
    public static final Supplier<Block> WHITE_PUMPKIN_STEM = register("white_pumpkin_stem", false, () -> new WhitePumpkinStemBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN_STEM)));
    public static final Supplier<Block> ATTACHED_WHITE_PUMPKIN_STEM = register("attached_white_pumpkin_stem", false, () -> new AttachedWhitePumpkinStemBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ATTACHED_PUMPKIN_STEM)));
    public static final Supplier<Item> WHITE_PUMPKIN_SEEDS = Services.REGISTRY.registerItem("white_pumpkin_seeds", () -> new ItemNameBlockItem(WHITE_PUMPKIN_STEM.get(), new Item.Properties()));
    public static final Supplier<Item> WHITE_PUMPKIN_PIE = Services.REGISTRY.registerItem("white_pumpkin_pie", () -> new Item(new Item.Properties().food(Foods.PUMPKIN_PIE)));
    public static final Supplier<EntityType<WhitePumpkinSnowGolem>> WHITE_PUMPKIN_SNOW_GOLEM = Services.REGISTRY.registerEntity("white_pumpkin_snow_golem", () -> EntityType.Builder.of(WhitePumpkinSnowGolem::new, MobCategory.MISC)
            .immuneTo(Blocks.POWDER_SNOW)
            .sized(0.7F, 1.9F)
            .eyeHeight(1.7F)
            .clientTrackingRange(8)
            .build(WhitePumpkins.MOD_ID + ":white_pumpkin_snow_golem")
    );
    public static final Supplier<Item> WHITE_PUMPKIN_SNOW_GOLEM_SPAWN_EGG = Services.REGISTRY.registerItem("white_pumpkin_snow_golem_spawn_egg", () -> new SpawnEggItem(WHITE_PUMPKIN_SNOW_GOLEM.get(), 0xD9F2F2, 0x81A4A4, new Item.Properties()));

    public static void init() {
    }

    private static <T extends Block> Supplier<T> register(String name, boolean hasItem, Supplier<T> block) {
        Supplier<T> instance = Services.REGISTRY.registerBlock(name, block);
        if (hasItem) {
            Services.REGISTRY.registerItem(name, () -> new BlockItem(instance.get(), new Item.Properties()));
        }
        return instance;
    }
}
