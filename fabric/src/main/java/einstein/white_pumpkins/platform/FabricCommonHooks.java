package einstein.white_pumpkins.platform;

import einstein.white_pumpkins.platform.services.CommonHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FabricCommonHooks implements CommonHooks {

    @Override
    public float getCropGrowthSpeed(BlockState state, BlockPos pos, Level level) {
        return CropBlock.getGrowthSpeed(state.getBlock(), level, pos);
    }
}
