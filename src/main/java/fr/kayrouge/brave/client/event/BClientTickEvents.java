package fr.kayrouge.brave.client.event;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.agents.spell.SpellDataManager;
import fr.kayrouge.brave.agents.spell.Spells;
import fr.kayrouge.brave.client.BRAVEClient;
import fr.kayrouge.brave.component.BComponents;
import fr.kayrouge.brave.network.c2s.SpellUseC2SPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
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

                BRAVE.LOGGER.info(SpellDataManager.getInstance().getTpVec().toString());
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while(BRAVEClient.TEST_KEY.wasPressed()) {
                if(minecraftClient.player == null) {
                    BRAVE.LOGGER.info("PLAYER NULL");
                    break;
                }
                minecraftClient.player.sendMessage(Text.literal(BComponents.PLAYER_DATA.get(minecraftClient.player).getAgent().toString()), false);
                minecraftClient.player.sendMessage(Text.literal(String.valueOf(BComponents.PLAYER_DATA.get(minecraftClient.player).getExposedTime()/20)), false);
                minecraftClient.player.sendMessage(Text.literal(String.valueOf(BComponents.PLAYER_DATA.get(minecraftClient.player).getEquippedSpell().toString())), false);
            }

            while (BRAVEClient.FIRST_SPELL_USE.wasPressed()) {
                useSpell(BComponents.PLAYER_DATA.get(minecraftClient.player).getAgent().getFirstSpell());
            }
        });
    }

    private static void useSpell(Spell spell) {
        if(spell == Spells.DEFAULT) {
            MinecraftClient.getInstance().player.sendMessage(Text.of("You are not an agent !"), true);
        }
        else {
            ClientPlayNetworking.send(new SpellUseC2SPayload(spell));
        }
    }

}
