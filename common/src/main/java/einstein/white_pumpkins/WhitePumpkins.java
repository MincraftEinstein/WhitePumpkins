package einstein.white_pumpkins;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WhitePumpkins {

    public static final String MOD_ID = "white_pumpkins";
    public static final String MOD_NAME = "WhitePumpkins";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        ModInit.init();
    }

    public static void clientSetup() {
        ItemProperties.register(ModInit.CARVED_WHITE_PUMPKIN.get().asItem(), WhitePumpkins.loc("on_head"),
                (stack, level, entity, seed) -> {
                    if (entity != null) {
                        if (stack.equals(entity.getItemBySlot(EquipmentSlot.HEAD))) {
                            return 1;
                        }
                    }
                    return 0;
                }
        );
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
