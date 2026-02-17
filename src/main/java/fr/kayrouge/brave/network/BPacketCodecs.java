package fr.kayrouge.brave.network;

import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.spell.Spell;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public interface BPacketCodecs {

    PacketCodec<RegistryByteBuf, Spell> SPELL = new PacketCodec<RegistryByteBuf, Spell>() {
        @Override
        public void encode(RegistryByteBuf buf, Spell value) {
            PacketCodecs.registryValue(BRegistries.SPELLS.getKey()).encode(buf, value);
        }

        @Override
        public Spell decode(RegistryByteBuf buf) {
            return PacketCodecs.registryValue(BRegistries.SPELLS.getKey()).decode(buf);
        }
    };

    PacketCodec<RegistryByteBuf, Agent> AGENT = new PacketCodec<RegistryByteBuf, Agent>() {
        @Override
        public void encode(RegistryByteBuf buf, Agent value) {
            PacketCodecs.registryValue(BRegistries.AGENTS.getKey()).encode(buf, value);
        }

        @Override
        public Agent decode(RegistryByteBuf buf) {
            return PacketCodecs.registryValue(BRegistries.AGENTS.getKey()).decode(buf);
        }
    };

}
