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

    public static final KeyBinding FIRST_SPELL_USE = new KeyBinding(BRAVE.MOD_ID+":first_spell", GLFW.GLFW_KEY_U, KeyBinding.Category.GAMEPLAY);
    public static final KeyBinding SECOND_SPELL_USE = new KeyBinding(BRAVE.MOD_ID+":second_spell", GLFW.GLFW_KEY_I, KeyBinding.Category.GAMEPLAY);
    public static final KeyBinding THIRD_SPELL_USE = new KeyBinding(BRAVE.MOD_ID+":third_spell", GLFW.GLFW_KEY_O, KeyBinding.Category.GAMEPLAY);
    public static final KeyBinding ULTIMATE_USE = new KeyBinding(BRAVE.MOD_ID+":ultimate", GLFW.GLFW_KEY_P, KeyBinding.Category.GAMEPLAY);



    public static final KeyBinding DISPLAY_AGENT_INFO = new KeyBinding("display_agent_info", GLFW.GLFW_KEY_F7, KeyBinding.Category.MISC);

    public static void register() {
        KeyBindingHelper.registerKeyBinding(TEST_KEY);
        KeyBindingHelper.registerKeyBinding(FIRST_SPELL_USE);
        KeyBindingHelper.registerKeyBinding(SECOND_SPELL_USE);
        KeyBindingHelper.registerKeyBinding(THIRD_SPELL_USE);
        KeyBindingHelper.registerKeyBinding(ULTIMATE_USE);
        KeyBindingHelper.registerKeyBinding(DISPLAY_AGENT_INFO);
    }

    public static void handleInputs(MinecraftClient client) {
        while(TEST_KEY.wasPressed()) {
            client.player.sendMessage(Text.literal(BComponents.PLAYER_DATA.get(client.player).getAgent().toString()), false);
            client.player.sendMessage(Text.literal(String.valueOf(BComponents.PLAYER_DATA.get(client.player).getExposedTime()/20)), false);
            client.player.sendMessage(Text.literal(String.valueOf(BComponents.PLAYER_DATA.get(client.player).getEquippedSpell().toString())), false);
        }

        while (FIRST_SPELL_USE.wasPressed()) {
            useSpell(BComponents.PLAYER_DATA.get(client.player).getAgent().getFirstSpell());
        }
        while (SECOND_SPELL_USE.wasPressed()) {
            useSpell(BComponents.PLAYER_DATA.get(client.player).getAgent().getSecondSpell());
        }
        while (THIRD_SPELL_USE.wasPressed()) {
            useSpell(BComponents.PLAYER_DATA.get(client.player).getAgent().getThirdSpell());
        }
        while (ULTIMATE_USE.wasPressed()) {
            useSpell(BComponents.PLAYER_DATA.get(client.player).getAgent().getUltimate());
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
