package fr.kayrouge.brave.client;

import fr.kayrouge.brave.client.event.BClientTickEvents;
import fr.kayrouge.brave.client.event.BWorldRenderEvents;
import fr.kayrouge.brave.client.render.screen.BInGameHud;
import fr.kayrouge.brave.network.BNetworkConstants;
import fr.kayrouge.brave.util.configs.BRAVEClientConfig;
import lombok.Getter;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class BRAVEClient implements ClientModInitializer {

    @Getter
    private static boolean connectedToBraveServer = false;

    public static final KeyBinding TEST_KEY = new KeyBinding("de", GLFW.GLFW_KEY_C, KeyBinding.Category.MOVEMENT);
    public static final KeyBinding FIRST_SPELL_USE = new KeyBinding("first_spell", GLFW.GLFW_KEY_Q, KeyBinding.Category.GAMEPLAY);

    public static final ConfigHolder<BRAVEClientConfig> CONFIG = AutoConfig.register(BRAVEClientConfig.class, JanksonConfigSerializer::new);

    @Override
    public void onInitializeClient() {

        KeyBindingHelper.registerKeyBinding(TEST_KEY);
        KeyBindingHelper.registerKeyBinding(FIRST_SPELL_USE);

        BNetworkConstants.registerS2CGlobalReceiver();

        BClientTickEvents.register();
        BWorldRenderEvents.register();

        BInGameHud.register();
    }


    public static void setConnectedToBraveServer(boolean connectedToBraveServer) {
        BRAVEClient.connectedToBraveServer = connectedToBraveServer;
    }
}
