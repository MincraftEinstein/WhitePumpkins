package einstein.white_pumpkins.block;

import com.mojang.serialization.MapCodec;
import einstein.white_pumpkins.ModInit;
import einstein.white_pumpkins.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WhitePumpkinStemBlock extends BushBlock implements BonemealableBlock {

    public static final MapCodec<WhitePumpkinStemBlock> CODEC = simpleCodec(WhitePumpkinStemBlock::new);
    public static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    protected static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
            Block.box(7, 0, 7, 9, 2, 9),
            Block.box(7, 0, 7, 9, 4, 9),
            Block.box(7, 0, 7, 9, 6, 9),
            Block.box(7, 0, 7, 9, 8, 9),
            Block.box(7, 0, 7, 9, 10, 9),
            Block.box(7, 0, 7, 9, 12, 9),
            Block.box(7, 0, 7, 9, 14, 9),
            Block.box(7, 0, 7, 9, 16, 9)
    };

    public WhitePumpkinStemBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(AGE)];
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getRawBrightness(pos, 0) >= 9) {
            float growthSpeed = Services.HOOKS.getCropGrowthSpeed(state, pos, level);

            if (random.nextInt((int) (25 / growthSpeed) + 1) == 0) {
                int age = state.getValue(AGE);
                if (age < MAX_AGE) {
                    state = state.setValue(AGE, age + 1);
                    level.setBlock(pos, state, Block.UPDATE_CLIENTS);
                    return;
                }

                Direction direction = Plane.HORIZONTAL.getRandomDirection(random);
                BlockPos pumpkinPos = pos.relative(direction);
                BlockState belowPumpkinState = level.getBlockState(pumpkinPos.below());

                if (level.getBlockState(pumpkinPos).isAir() && (belowPumpkinState.is(Blocks.FARMLAND) || belowPumpkinState.is(BlockTags.DIRT))) {
                    level.setBlockAndUpdate(pumpkinPos, ModInit.WHITE_PUMPKIN.get().defaultBlockState());
                    level.setBlockAndUpdate(pos, ModInit.ATTACHED_WHITE_PUMPKIN_STEM.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction));
                }
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader reader, BlockPos pos, BlockState state) {
        return new ItemStack(ModInit.WHITE_PUMPKIN_SEEDS.get());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader reader, BlockPos pos, BlockState state) {
        return state.getValue(AGE) != MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int age = Math.min(MAX_AGE, state.getValue(AGE) + Mth.nextInt(level.random, 2, 5));
        BlockState newState = state.setValue(AGE, age);
        level.setBlock(pos, newState, Block.UPDATE_CLIENTS);

        if (age == MAX_AGE) {
            newState.randomTick(level, pos, level.random);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
