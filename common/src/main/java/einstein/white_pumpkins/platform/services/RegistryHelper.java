package einstein.white_pumpkins.platform.services;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface RegistryHelper {

    <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block);

    <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item);

    <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, Supplier<EntityType<T>> entity);
}
