package com.everest.item;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Blighthon extends SwordItem {
    public Blighthon(Settings settings) {
        super(ToolMaterials.NETHERITE, 2, 2, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockPos userBlockPos = user.getBlockPos();
        int fallDistance = 0;

        user.resetLastAttackedTicks();
        user.setInvisible(true);

        // * is the user in the air
        if (userBlockPos.down().equals(Blocks.AIR)) {
            // * check every block below the player if is solid ground, break out of the loop is true to work out fall distance
            for (int y = userBlockPos.getY() - 1; y >= world.getBottomY(); y--) {
                BlockPos blockPos = new BlockPos(userBlockPos.getX(), y, userBlockPos.getX());

                if (world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                    break;
                }

                fallDistance++;
            }
        }

        return super.use(world, user, hand);
    }
}
