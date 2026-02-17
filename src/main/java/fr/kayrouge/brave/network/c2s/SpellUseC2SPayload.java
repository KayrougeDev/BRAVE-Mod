package fr.kayrouge.brave.network.c2s;

import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.spell.EquippableSpell;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.component.BComponents;
import fr.kayrouge.brave.component.PlayerDataComponent;
import fr.kayrouge.brave.network.BNetworkConstants;
import fr.kayrouge.brave.network.BPacketCodecs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record SpellUseC2SPayload(Spell spell) implements CustomPayload {

    public static final CustomPayload.Id<SpellUseC2SPayload> ID = new CustomPayload.Id<>(BNetworkConstants.SPELL_USE_PACKET_ID);

    public static final PacketCodec<RegistryByteBuf, SpellUseC2SPayload> CODEC = PacketCodec.tuple(
            BPacketCodecs.SPELL, SpellUseC2SPayload::spell,
            SpellUseC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ServerPlayNetworking.Context context) {
        context.server().execute(() -> {


            Text t = Text.literal("null");
            Identifier id = BRegistries.SPELLS.getId(spell);
            if(id != null) {
                t = Text.of(id);
            }
            context.server().sendMessage(Text.literal("Received spell ").append(t));

            PlayerDataComponent data = BComponents.PLAYER_DATA.get(context.player());

            if(spell instanceof EquippableSpell eSpell) {

                if(eSpell != data.getEquippedSpell()) {
                    eSpell.equip();
                    data.setEquippedSpell(eSpell);
                    BComponents.PLAYER_DATA.sync(context.player());
                }
                return;
            }

            data.resetEquippedSpell();
            BComponents.PLAYER_DATA.sync(context.player());

        });
    }
}
