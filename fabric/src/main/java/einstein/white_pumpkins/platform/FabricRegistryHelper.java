package einstein.white_pumpkins.platform;

import einstein.white_pumpkins.WhitePumpkins;
import einstein.white_pumpkins.platform.services.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
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
}
