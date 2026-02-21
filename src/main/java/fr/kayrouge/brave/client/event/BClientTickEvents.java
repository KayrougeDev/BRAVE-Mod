package fr.kayrouge.brave.client.event;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.spell.SpellDataManager;
import fr.kayrouge.brave.agents.spell.Spells;
import fr.kayrouge.brave.client.BKeyBindings;
import fr.kayrouge.brave.component.BComponents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BClientTickEvents {

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            ClientPlayerEntity player = minecraftClient.player;
            if (player == null || BComponents.PLAYER_DATA.get(player).getEquippedSpell() != Spells.OMEN_TP) return;

            BlockHitResult hitResult = (BlockHitResult)player.raycast(12, 0, false);
            if(hitResult.getType() == HitResult.Type.BLOCK || hitResult.getType() == HitResult.Type.MISS) {
                Vec3d hitPos = hitResult.getPos();
                Vec3d rotationVec = player.getRotationVec(0.0f);
                Vec3d tpPos = hitPos.subtract(rotationVec);
                BlockPos blockPos = BlockPos.ofFloored(tpPos.x, tpPos.y, tpPos.z);


                int offset = 0;
                while (player.getEntityWorld().isInBuildLimit(blockPos.add(0, -offset, 0))) {
                    BlockPos down = blockPos.add(0, -offset, 0).down();
                    if(player.getEntityWorld().getBlockState(down).isAir()) {
                        offset++;
                        continue;
                    }

                    tpPos = new Vec3d(tpPos.x, MathHelper.floor(tpPos.y)-offset, tpPos.z);
                    break;
                }

                SpellDataManager.getInstance().setTpVec(tpPos);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(BKeyBindings::handleInputs);
    }

}
