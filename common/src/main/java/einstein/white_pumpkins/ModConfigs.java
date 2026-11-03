package einstein.white_pumpkins;

import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;

import static einstein.white_pumpkins.WhitePumpkins.MOD_ID;

@Translation(prefix = "config." + MOD_ID)
public class ModConfigs extends Config {

    public ValidatedFloat whitePumpkinEquippedExtraDamage = new ValidatedFloat(5, 128, 0);
    public boolean whitePumpkinsObfuscateNames = true;

    public ModConfigs() {
        super(WhitePumpkins.loc("main"), "", "", MOD_ID);
    }
}
