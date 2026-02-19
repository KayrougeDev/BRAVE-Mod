package fr.kayrouge.brave;

import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.agents.spell.Spells;
import fr.kayrouge.brave.criterion.BCriteria;
import fr.kayrouge.brave.items.BItems;
import fr.kayrouge.brave.network.BNetworkConstants;
import fr.kayrouge.brave.potions.BPotions;
import fr.kayrouge.brave.potions.BStatusEffect;
import fr.kayrouge.brave.util.configs.BRAVEServerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BRAVE implements ModInitializer {

	public static final String MOD_ID = "brave";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ConfigHolder<BRAVEServerConfig> CONFIG = AutoConfig.register(BRAVEServerConfig.class, JanksonConfigSerializer::new);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world! {} {}", CONFIG.get().testBool, CONFIG.get().testInt);

		BRegistries.init();
		Spells.init();
		Agents.init();
		BItems.init();
		BStatusEffect.init();
		BPotions.init();
		BCriteria.init();

		BNetworkConstants.registerC2S();
		BNetworkConstants.registerS2C();
		BNetworkConstants.registerC2SGlobalReceiver();
	}


	public static Identifier id(String id) {
		return Identifier.of(MOD_ID, id);
	}
}