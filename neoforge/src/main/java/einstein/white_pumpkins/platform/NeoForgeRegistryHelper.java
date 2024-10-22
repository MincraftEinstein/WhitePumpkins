package einstein.white_pumpkins.platform;

import einstein.white_pumpkins.WhitePumpkins;
import einstein.white_pumpkins.platform.services.RegistryHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements RegistryHelper {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(WhitePumpkins.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(WhitePumpkins.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, WhitePumpkins.MOD_ID);

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, Supplier<EntityType<T>> entity) {
        return ENTITY_TYPES.register(name, entity);
    }
}
