package fr.kayrouge.brave.client;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.component.BComponents;
import fr.kayrouge.brave.util.configs.BRAVEClientConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class BRAVEClient implements ClientModInitializer {

    private static boolean connectedToBraveServer = false;

    public static final KeyBinding TEST_KEY = new KeyBinding("de", GLFW.GLFW_KEY_C, KeyBinding.MOVEMENT_CATEGORY);

    public static final ConfigHolder<BRAVEClientConfig> CONFIG = AutoConfig.register(BRAVEClientConfig.class, JanksonConfigSerializer::new);

    @Override
    public void onInitializeClient() {

        KeyBindingHelper.registerKeyBinding(TEST_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while(TEST_KEY.wasPressed()) {
                if(minecraftClient.player == null) {
                    BRAVE.LOGGER.info("PLAYER NULL");
                    break;
                }
                minecraftClient.player.sendMessage(Text.literal(BComponents.PLAYER_DATA.get(minecraftClient.player).getAgent().toString()));
                minecraftClient.player.sendMessage(Text.literal(String.valueOf(BComponents.PLAYER_DATA.get(minecraftClient.player).getExposedTime()/20)));
            }
        });
    }

    public static boolean isConnectedToBraveServer() {
        return connectedToBraveServer;
    }
    public static void setConnectedToBraveServer(boolean connectedToBraveServer) {
        BRAVEClient.connectedToBraveServer = connectedToBraveServer;
    }
}
