package einstein.white_pumpkins;

import einstein.white_pumpkins.init.ModConfigs;
import einstein.white_pumpkins.init.ModInit;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhitePumpkins {

    public static final String MOD_ID = "white_pumpkins";
    public static final String MOD_NAME = "WhitePumpkins";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final ModConfigs CONFIGS = ConfigApiJava.registerAndLoadConfig(ModConfigs::new, RegisterType.BOTH);

    public static void init() {
        ModInit.init();
    }

    public static Identifier loc(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
