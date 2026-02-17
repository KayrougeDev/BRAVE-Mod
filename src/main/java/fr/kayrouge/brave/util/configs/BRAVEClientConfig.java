package fr.kayrouge.brave.util.configs;

import fr.kayrouge.brave.BRAVE;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@Config(name = BRAVE.MOD_ID+".client")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class BRAVEClientConfig implements ConfigData {

    public boolean testBool = true;
    public int testInt = 5;

}
