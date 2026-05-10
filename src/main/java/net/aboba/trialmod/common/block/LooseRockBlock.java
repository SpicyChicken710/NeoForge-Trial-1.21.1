package net.aboba.trialmod.common.block;

import net.aboba.trialmod.registration.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LooseRockBlock extends Block {
    // A small, flat shape so it looks like a rock on the ground (4x2x4 pixels)
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 2.0D, 12.0D);

    public LooseRockBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            // Remove the block and give the item
            level.destroyBlock(pos, false);
            player.addItem(new ItemStack(ItemRegistry.FLINT_NODULE.get()));
        }
        return InteractionResult.SUCCESS;
    }
}