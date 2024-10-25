package einstein.white_pumpkins.entity;

import einstein.white_pumpkins.WhitePumpkins;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public class WhitePumpkinSnowGolem extends SnowGolem {

    public static final ResourceKey<LootTable> SHEAR_WHITE_PUMPKIN_SNOW_GOLEM = ResourceKey.create(Registries.LOOT_TABLE, WhitePumpkins.loc("shearing/white_pumpkin_snow_golem"));

    public WhitePumpkinSnowGolem(EntityType<? extends SnowGolem> type, Level level) {
        super(type, level);
    }

    @Override
    public void shear(ServerLevel level, SoundSource source, ItemStack stack) {
        level.playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, source, 1, 1);
        setPumpkin(false);
        dropFromShearingLootTable(level, SHEAR_WHITE_PUMPKIN_SNOW_GOLEM, stack, (level1, stack1) -> spawnAtLocation(level1, stack1, getEyeHeight()));
    }
}
