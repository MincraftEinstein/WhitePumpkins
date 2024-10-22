package einstein.white_pumpkins.entity;

import einstein.white_pumpkins.ModInit;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WhitePumpkinSnowGolem extends SnowGolem {

    public WhitePumpkinSnowGolem(EntityType<? extends SnowGolem> type, Level level) {
        super(type, level);
    }

    @Override
    public void shear(SoundSource source) {
        level().playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, source, 1, 1);

        if (!level().isClientSide()) {
            setPumpkin(false);
            spawnAtLocation(new ItemStack(ModInit.CARVED_WHITE_PUMPKIN.get()), getEyeHeight());
        }
    }
}
