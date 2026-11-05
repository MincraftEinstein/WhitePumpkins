package einstein.white_pumpkins.init;

import einstein.white_pumpkins.block.AttachedWhitePumpkinStemBlock;
import einstein.white_pumpkins.block.CarvedWhitePumpkinBlock;
import einstein.white_pumpkins.block.WhitePumpkinBlock;
import einstein.white_pumpkins.block.WhitePumpkinStemBlock;
import einstein.white_pumpkins.entity.WhitePumpkinSnowGolem;
import me.fzzyhmstrs.fzzy_config.util.platform.Registrar;
import me.fzzyhmstrs.fzzy_config.util.platform.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;
import java.util.function.Supplier;

import static einstein.white_pumpkins.WhitePumpkins.MOD_ID;
import static einstein.white_pumpkins.WhitePumpkins.loc;
import static me.fzzyhmstrs.fzzy_config.api.ConfigApiJava.platform;

public class ModInit {

    private static final Registrar<Block> BLOCKS = platform().createRegistrar(MOD_ID, BuiltInRegistries.BLOCK);
    private static final Registrar<Item> ITEMS = platform().createRegistrar(MOD_ID, BuiltInRegistries.ITEM);
    private static final Registrar<EntityType<?>> ENTITY_TYPES = platform().createRegistrar(MOD_ID, BuiltInRegistries.ENTITY_TYPE);

    private static final Identifier PUMPKIN_OVERLAY = Identifier.withDefaultNamespace("misc/pumpkinblur");
    private static final Supplier<Item.Properties> CARVED_WHITE_PUMPKIN_ITEM_PROPERTIES = () -> new Item.Properties().component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD)
            .setSwappable(false)
            .setCameraOverlay(PUMPKIN_OVERLAY)
            .build()
    );

    public static final RegistrySupplier<Block> WHITE_PUMPKIN = registerBlock("white_pumpkin", true, WhitePumpkinBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN).mapColor(DyeColor.WHITE));
    public static final RegistrySupplier<Block> CARVED_WHITE_PUMPKIN = registerBlock("carved_white_pumpkin", true, CarvedWhitePumpkinBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CARVED_PUMPKIN).mapColor(DyeColor.WHITE), CARVED_WHITE_PUMPKIN_ITEM_PROPERTIES);
    public static final RegistrySupplier<Block> DAMAGED_CARVED_WHITE_PUMPKIN = registerBlock("damaged_carved_white_pumpkin", true, CarvedWhitePumpkinBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CARVED_PUMPKIN).mapColor(DyeColor.WHITE), CARVED_WHITE_PUMPKIN_ITEM_PROPERTIES);
    public static final RegistrySupplier<Block> WHITE_JACK_O_LANTERN = registerBlock("white_jack_o_lantern", true, CarvedWhitePumpkinBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN).mapColor(DyeColor.WHITE));
    public static final RegistrySupplier<Block> WHITE_SOUL_JACK_O_LANTERN = registerBlock("white_soul_jack_o_lantern", true, CarvedWhitePumpkinBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN).mapColor(DyeColor.WHITE));
    public static final RegistrySupplier<Block> WHITE_PUMPKIN_STEM = registerBlock("white_pumpkin_stem", false, WhitePumpkinStemBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PUMPKIN_STEM).mapColor(state -> state.getValue(WhitePumpkinStemBlock.AGE) > 5 ? DyeColor.WHITE.getMapColor() : MapColor.PLANT));
    public static final RegistrySupplier<Block> ATTACHED_WHITE_PUMPKIN_STEM = registerBlock("attached_white_pumpkin_stem", false, AttachedWhitePumpkinStemBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.ATTACHED_PUMPKIN_STEM).mapColor(DyeColor.WHITE));
    public static final RegistrySupplier<Item> WHITE_PUMPKIN_SEEDS = registerItem("white_pumpkin_seeds", properties -> new BlockItem(WHITE_PUMPKIN_STEM.get(), properties), () -> new Item.Properties().useItemDescriptionPrefix());
    public static final RegistrySupplier<Item> WHITE_PUMPKIN_PIE = registerItem("white_pumpkin_pie", Item::new, () -> new Item.Properties().food(Foods.PUMPKIN_PIE));
    public static final RegistrySupplier<EntityType<WhitePumpkinSnowGolem>> WHITE_PUMPKIN_SNOW_GOLEM = registerEntity("white_pumpkin_snow_golem", () -> EntityType.Builder.of(WhitePumpkinSnowGolem::new, MobCategory.MISC)
            .immuneTo(Blocks.POWDER_SNOW)
            .sized(0.7F, 1.9F)
            .eyeHeight(1.7F)
            .clientTrackingRange(8)
    );
    public static final RegistrySupplier<Item> WHITE_PUMPKIN_SNOW_GOLEM_SPAWN_EGG = registerItem("white_pumpkin_snow_golem_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(WHITE_PUMPKIN_SNOW_GOLEM.get()));

    public static void init() {
        BLOCKS.init();
        ITEMS.init();
        ENTITY_TYPES.init();
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, boolean hasItem, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
        return registerBlock(name, hasItem, block, properties, Item.Properties::new);
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, boolean hasItem, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties blockProperties, Supplier<Item.Properties> itemProperties) {
        blockProperties.setId(ResourceKey.create(Registries.BLOCK, loc(name)));
        RegistrySupplier<T> instance = BLOCKS.register(name, () -> block.apply(blockProperties));

        if (hasItem) {
            registerItem(name, properties -> new BlockItem(instance.get(), properties), () -> itemProperties.get().useBlockDescriptionPrefix());
        }
        return instance;
    }

    private static <T extends Item> RegistrySupplier<T> registerItem(String name, Function<Item.Properties, T> item, Supplier<Item.Properties> properties) {
        return ITEMS.register(name, () -> item.apply(properties.get().setId(ResourceKey.create(Registries.ITEM, loc(name)))));
    }

    private static <T extends Entity> RegistrySupplier<EntityType<T>> registerEntity(String name, Supplier<EntityType.Builder<T>> builder) {
        return ENTITY_TYPES.register(name, () -> builder.get().build(ResourceKey.create(Registries.ENTITY_TYPE, loc(name))));
    }
}
