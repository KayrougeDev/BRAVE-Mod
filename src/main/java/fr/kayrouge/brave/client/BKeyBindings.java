package fr.kayrouge.brave.client;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.agents.spell.Spells;
import fr.kayrouge.brave.component.BComponents;
import fr.kayrouge.brave.network.c2s.SpellUseC2SPayload;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class BKeyBindings {

    public static final KeyBinding TEST_KEY = new KeyBinding("de", GLFW.GLFW_KEY_C, KeyBinding.Category.MOVEMENT);
    public static final KeyBinding FIRST_SPELL_USE = new KeyBinding("first_spell", GLFW.GLFW_KEY_K, KeyBinding.Category.GAMEPLAY);
    public static final KeyBinding DISPLAY_AGENT_INFO = new KeyBinding("display_agent_info", GLFW.GLFW_KEY_F7, KeyBinding.Category.MISC);

    public static void register() {
        KeyBindingHelper.registerKeyBinding(TEST_KEY);
        KeyBindingHelper.registerKeyBinding(FIRST_SPELL_USE);
        KeyBindingHelper.registerKeyBinding(DISPLAY_AGENT_INFO);
    }

    public static void handleInputs(MinecraftClient client) {
        while(TEST_KEY.wasPressed()) {
            if(client.player == null) {
                BRAVE.LOGGER.info("PLAYER NULL");
                break;
            }
            client.player.sendMessage(Text.literal(BComponents.PLAYER_DATA.get(client.player).getAgent().toString()), false);
            client.player.sendMessage(Text.literal(String.valueOf(BComponents.PLAYER_DATA.get(client.player).getExposedTime()/20)), false);
            client.player.sendMessage(Text.literal(String.valueOf(BComponents.PLAYER_DATA.get(client.player).getEquippedSpell().toString())), false);
        }

        while (FIRST_SPELL_USE.wasPressed()) {
            useSpell(BComponents.PLAYER_DATA.get(client.player).getAgent().getFirstSpell());
        }
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
