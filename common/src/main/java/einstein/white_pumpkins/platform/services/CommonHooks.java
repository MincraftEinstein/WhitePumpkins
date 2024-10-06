package einstein.white_pumpkins.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface CommonHooks {

    float getCropGrowthSpeed(BlockState state, BlockPos pos, Level level);
}
