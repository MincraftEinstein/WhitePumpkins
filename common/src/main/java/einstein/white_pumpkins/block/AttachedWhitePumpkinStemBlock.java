package einstein.white_pumpkins.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import einstein.white_pumpkins.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class AttachedWhitePumpkinStemBlock extends BushBlock {

    public static final MapCodec<AttachedWhitePumpkinStemBlock> CODEC = simpleCodec(AttachedWhitePumpkinStemBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(
            Direction.SOUTH, Block.box(6, 0, 6, 10, 10, 16),
            Direction.WEST, Block.box(0, 0, 6, 10, 10, 10),
            Direction.NORTH, Block.box(6, 0, 0, 10, 10, 10),
            Direction.EAST, Block.box(6, 0, 6, 16, 10, 10))
    );

    public AttachedWhitePumpkinStemBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return AABBS.get(state.getValue(FACING));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!neighborState.is(ModInit.WHITE_PUMPKIN.get()) && direction == state.getValue(FACING)) {
            return ModInit.WHITE_PUMPKIN_STEM.get().defaultBlockState().trySetValue(WhitePumpkinStemBlock.AGE, WhitePumpkinStemBlock.MAX_AGE);
        }

        return super.updateShape(state, levelReader, tickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader reader, BlockPos pos, BlockState state, boolean b) {
        return new ItemStack(ModInit.WHITE_PUMPKIN_SEEDS.get());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
