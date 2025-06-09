package fr.kayrouge.brave.client;

import fr.kayrouge.brave.util.configs.BRAVEClientConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BRAVEClient implements ClientModInitializer {

    private static boolean connectedToBraveServer = false;

    public static final ConfigHolder<BRAVEClientConfig> CONFIG = AutoConfig.register(BRAVEClientConfig.class, JanksonConfigSerializer::new);

    @Override
    public void onInitializeClient() {

    }

    public static boolean isConnectedToBraveServer() {
        return connectedToBraveServer;
    }
    public static void setConnectedToBraveServer(boolean connectedToBraveServer) {
        BRAVEClient.connectedToBraveServer = connectedToBraveServer;
    }
}
