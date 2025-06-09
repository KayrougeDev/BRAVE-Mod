package fr.kayrouge.brave;

import fr.kayrouge.brave.items.BItems;
import fr.kayrouge.brave.util.configs.BRAVEServerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BRAVE implements ModInitializer {

	public static final String MOD_ID = "brave";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ConfigHolder<BRAVEServerConfig> CONFIG = AutoConfig.register(BRAVEServerConfig.class, JanksonConfigSerializer::new);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world! {} {}", CONFIG.get().testBool, CONFIG.get().testInt);

		BItems.init();
	}
}