package einstein.white_pumpkins.platform;

import einstein.white_pumpkins.WhitePumpkins;
import einstein.white_pumpkins.platform.services.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FabricRegistryHelper implements RegistryHelper {

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        T instance = Registry.register(BuiltInRegistries.BLOCK, WhitePumpkins.loc(name), block.get());
        return () -> instance;
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        T instance = Registry.register(BuiltInRegistries.ITEM, WhitePumpkins.loc(name), item.get());
        return () -> instance;
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, Supplier<EntityType.Builder<T>> entity) {
        ResourceLocation id = WhitePumpkins.loc(name);
        EntityType<T> instance = Registry.register(BuiltInRegistries.ENTITY_TYPE, id, entity.get().build(ResourceKey.create(Registries.ENTITY_TYPE, id)));
        return () -> instance;
    }
}
