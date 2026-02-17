package fr.kayrouge.brave.network;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.network.c2s.EquippedSpellUseC2SPayload;
import fr.kayrouge.brave.network.c2s.SpellUseC2SPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class BNetworkConstants {

    // C2S
    public static final Identifier SPELL_USE_PACKET_ID = BRAVE.id("spell_use");
    public static final Identifier EQUIPPED_SPELL_USE_PACKET_ID = BRAVE.id("equipped_spell_use");

    public static void registerC2S() {
        PayloadTypeRegistry.playC2S().register(SpellUseC2SPayload.ID, SpellUseC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(EquippedSpellUseC2SPayload.ID, EquippedSpellUseC2SPayload.CODEC);
    }

    public static void registerS2C() {
    }

    public static void registerC2SGlobalReceiver() {
        ServerPlayNetworking.registerGlobalReceiver(SpellUseC2SPayload.ID, SpellUseC2SPayload::receive);
        ServerPlayNetworking.registerGlobalReceiver(EquippedSpellUseC2SPayload.ID, EquippedSpellUseC2SPayload::receive);
    }

    public static void registerS2CGlobalReceiver() {
    }

}
