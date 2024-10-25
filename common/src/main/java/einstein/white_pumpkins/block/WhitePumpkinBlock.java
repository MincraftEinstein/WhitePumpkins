package einstein.white_pumpkins.block;

import einstein.white_pumpkins.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class WhitePumpkinBlock extends Block {

    public WhitePumpkinBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!stack.is(Items.SHEARS)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        Direction direction = hitResult.getDirection();
        Direction oppositeDirection = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : direction;
        ItemEntity itemEntity = new ItemEntity(level,
                pos.getX() + 0.5 + oppositeDirection.getStepX() * 0.65,
                pos.getY() + 0.1,
                pos.getZ() + 0.5 + oppositeDirection.getStepZ() * 0.65,
                new ItemStack(ModInit.WHITE_PUMPKIN_SEEDS.get(), 4)
        );

        itemEntity.setDeltaMovement(
                0.05 * oppositeDirection.getStepX() + level.random.nextDouble() * 0.02,
                0.05,
                0.05 * oppositeDirection.getStepZ() + level.random.nextDouble() * 0.02
        );

        level.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1, 1);
        level.setBlock(pos, ModInit.CARVED_WHITE_PUMPKIN.get().defaultBlockState().setValue(CarvedWhitePumpkinBlock.FACING, oppositeDirection), Block.UPDATE_ALL_IMMEDIATE);
        level.addFreshEntity(itemEntity);
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        level.gameEvent(player, GameEvent.SHEAR, pos);
        player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
        return InteractionResult.CONSUME;
    }
}
