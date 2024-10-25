package einstein.white_pumpkins;

import einstein.white_pumpkins.block.AttachedWhitePumpkinStemBlock;
import einstein.white_pumpkins.block.CarvedWhitePumpkinBlock;
import einstein.white_pumpkins.block.WhitePumpkinBlock;
import einstein.white_pumpkins.block.WhitePumpkinStemBlock;
import einstein.white_pumpkins.entity.WhitePumpkinSnowGolem;
import einstein.white_pumpkins.platform.Services;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

import static einstein.white_pumpkins.WhitePumpkins.loc;

public class ModInit {

    private static final ResourceLocation PUMPKIN_OVERLAY = ResourceLocation.withDefaultNamespace("misc/pumpkinblur");

    public static final Supplier<Block> WHITE_PUMPKIN = register("white_pumpkin", true, WhitePumpkinBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN).mapColor(DyeColor.WHITE));
    public static final Supplier<Block> CARVED_WHITE_PUMPKIN = register("carved_white_pumpkin", true, CarvedWhitePumpkinBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CARVED_PUMPKIN).mapColor(DyeColor.WHITE),
            new Item.Properties().component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD)
                    .setSwappable(false)
                    .setCameraOverlay(PUMPKIN_OVERLAY)
                    .build()
            ));
    public static final Supplier<Block> WHITE_JACK_O_LANTERN = register("white_jack_o_lantern", true, CarvedWhitePumpkinBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN).mapColor(DyeColor.WHITE));
    public static final Supplier<Block> WHITE_PUMPKIN_STEM = register("white_pumpkin_stem", false, WhitePumpkinStemBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN_STEM));
    public static final Supplier<Block> ATTACHED_WHITE_PUMPKIN_STEM = register("attached_white_pumpkin_stem", false, AttachedWhitePumpkinStemBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.ATTACHED_PUMPKIN_STEM));
    public static final Supplier<Item> WHITE_PUMPKIN_SEEDS = registerItem("white_pumpkin_seeds", properties -> new BlockItem(WHITE_PUMPKIN_STEM.get(), properties), new Item.Properties().useItemDescriptionPrefix());
    public static final Supplier<Item> WHITE_PUMPKIN_PIE = registerItem("white_pumpkin_pie", Item::new, new Item.Properties().food(Foods.PUMPKIN_PIE));
    public static final Supplier<EntityType<WhitePumpkinSnowGolem>> WHITE_PUMPKIN_SNOW_GOLEM = Services.REGISTRY.registerEntity("white_pumpkin_snow_golem", () -> EntityType.Builder.of(WhitePumpkinSnowGolem::new, MobCategory.MISC)
            .immuneTo(Blocks.POWDER_SNOW)
            .sized(0.7F, 1.9F)
            .eyeHeight(1.7F)
            .clientTrackingRange(8)
    );
    public static final Supplier<Item> WHITE_PUMPKIN_SNOW_GOLEM_SPAWN_EGG = registerItem("white_pumpkin_snow_golem_spawn_egg", properties -> new SpawnEggItem(WHITE_PUMPKIN_SNOW_GOLEM.get(), 0xD9F2F2, 0x81A4A4, properties), new Item.Properties());

    public static void init() {
    }

    private static <T extends Block> Supplier<T> register(String name, boolean hasItem, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
        return register(name, hasItem, block, properties, new Item.Properties());
    }

    private static <T extends Block> Supplier<T> register(String name, boolean hasItem, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        blockProperties.setId(ResourceKey.create(Registries.BLOCK, loc(name)));
        Supplier<T> instance = Services.REGISTRY.registerBlock(name, () -> block.apply(blockProperties));

        if (hasItem) {
            registerItem(name, properties -> new BlockItem(instance.get(), properties), itemProperties.useBlockDescriptionPrefix());
        }
        return instance;
    }

    private static <T extends Item> Supplier<T> registerItem(String name, Function<Item.Properties, T> item, Item.Properties properties) {
        properties.setId(ResourceKey.create(Registries.ITEM, loc(name)));
        return Services.REGISTRY.registerItem(name, () -> item.apply(properties));
    }
}
