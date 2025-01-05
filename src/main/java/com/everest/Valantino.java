package com.everest;

import com.everest.init.ValantinoItems;
import com.everest.item.Blighthon;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Valantino implements ModInitializer {
	public static final String MOD_ID = "valantino";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ValantinoItems.register();

		ServerTickEvents.END_WORLD_TICK.register(serverWorld -> {
			List<ServerPlayerEntity> players = serverWorld.getPlayers();

			for (ServerPlayerEntity player : players) {
				System.out.println("Player detected");
				if (player.fallDistance > 0) {
					System.out.println("Player does not have air below them");

					ItemStack mainHandStack = player.getMainHandStack();
					if (mainHandStack.getItem() instanceof Blighthon) {
						System.out.println("Player is holding the item");
						if (player.isInvisible()) {
							System.out.println("Player is invis");
							BlockPos playerBlockPos = player.getBlockPos();
							int fallDistance = 0;

							for (int y = playerBlockPos.getY() - 1; y >= serverWorld.getBottomY(); y--) {
								BlockPos blockPos = new BlockPos(playerBlockPos.getX(), y, playerBlockPos.getX());
								if (!player.isInvisible()) {
									System.out.println("Player invis was removed mid for loop? resetting");
									player.setInvisible(true);
								}

								if (serverWorld.getBlockState(blockPos.down()).getBlock() != Blocks.AIR) {
									System.out.println("The block 1 block below the player is grass block");
									player.setInvisible(false);
									break;
								}

								fallDistance++;
							}

							BlockPos landingBlock = playerBlockPos.offset(Direction.Axis.Y, -fallDistance);
							System.out.println(landingBlock);

							while (serverWorld.getBlockState(playerBlockPos.down()).getBlock() == Blocks.AIR) {
								double baseDownwardsVelocity = player.getVelocity().y;
								double fallSpeed = baseDownwardsVelocity * Math.pow(fallDistance, 1.5);

								player.setVelocity(player.getVelocity().x, -fallSpeed, player.getVelocity().z);
							}
							player.setInvisible(false);
						}
					}
				}
			}
		});

		LOGGER.info("Hello Fabric world!");
	}
}