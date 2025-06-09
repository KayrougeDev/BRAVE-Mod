package fr.kayrouge.brave.util.configs;

import fr.kayrouge.brave.BRAVE;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = BRAVE.MOD_ID+".server")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class BRAVEServerConfig implements ConfigData {

    public boolean testBool = true;
    public int testInt = 5;

}
