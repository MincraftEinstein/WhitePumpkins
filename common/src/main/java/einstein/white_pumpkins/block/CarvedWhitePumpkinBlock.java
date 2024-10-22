package einstein.white_pumpkins.block;

import com.mojang.serialization.MapCodec;
import einstein.white_pumpkins.ModInit;
import einstein.white_pumpkins.entity.WhitePumpkinSnowGolem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class CarvedWhitePumpkinBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<CarvedWhitePumpkinBlock> CODEC = simpleCodec(CarvedWhitePumpkinBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final Predicate<BlockState> PUMPKINS_PREDICATE = (state) ->
            state != null && (state.is(ModInit.CARVED_WHITE_PUMPKIN.get()) || state.is(ModInit.WHITE_JACK_O_LANTERN.get()));

    @Nullable
    private BlockPattern snowGolemFull;
    @Nullable
    private BlockPattern ironGolemFull;

    public CarvedWhitePumpkinBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends CarvedWhitePumpkinBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(state.getBlock())) {
            trySpawnGolem(level, pos);
        }
    }

    private void trySpawnGolem(Level level, BlockPos pos) {
        BlockPattern.BlockPatternMatch snowGolemPatternMatch = getOrCreateSnowGolemFull().find(level, pos);
        if (snowGolemPatternMatch != null) {
            WhitePumpkinSnowGolem snowGolem = ModInit.WHITE_PUMPKIN_SNOW_GOLEM.get().create(level);
            if (snowGolem != null) {
                spawnGolemInWorld(level, snowGolemPatternMatch, snowGolem, snowGolemPatternMatch.getBlock(0, 2, 0).getPos());
            }
            return;
        }

        BlockPattern.BlockPatternMatch ironGolemPatternMatch = getOrCreateIronGolemFull().find(level, pos);
        if (ironGolemPatternMatch != null) {
            IronGolem ironGolem = EntityType.IRON_GOLEM.create(level);
            if (ironGolem != null) {
                ironGolem.setPlayerCreated(true);
                spawnGolemInWorld(level, ironGolemPatternMatch, ironGolem, ironGolemPatternMatch.getBlock(1, 2, 0).getPos());
            }
        }
    }

    private static void spawnGolemInWorld(Level level, BlockPattern.BlockPatternMatch patternMatch, Entity golem, BlockPos pos) {
        CarvedPumpkinBlock.clearPatternBlocks(level, patternMatch);
        golem.moveTo(pos.getX() + 0.5, pos.getY() + 0.05, pos.getZ() + 0.5, 0, 0);
        level.addFreshEntity(golem);

        for (ServerPlayer serverplayer : level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, golem);
        }

        CarvedPumpkinBlock.updatePatternBlocks(level, patternMatch);
    }

    private BlockPattern getOrCreateSnowGolemFull() {
        if (snowGolemFull == null) {
            snowGolemFull = BlockPatternBuilder.start()
                    .aisle("#", "$", "$")
                    .where('#', BlockInWorld.hasState(PUMPKINS_PREDICATE))
                    .where('$', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
                    .build();
        }

        return snowGolemFull;
    }

    private BlockPattern getOrCreateIronGolemFull() {
        if (ironGolemFull == null) {
            ironGolemFull = BlockPatternBuilder.start()
                    .aisle("%#%", "$$$", "%$%")
                    .where('#', BlockInWorld.hasState(PUMPKINS_PREDICATE))
                    .where('$', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
                    .where('%', (blockInWorld) -> blockInWorld.getState().isAir())
                    .build();
        }

        return ironGolemFull;
    }
}
