package fr.kayrouge.brave.component;

import fr.kayrouge.brave.BRAVE;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class BComponents implements EntityComponentInitializer {

    public static final ComponentKey<PlayerDataComponent> PLAYER_DATA = ComponentRegistry.getOrCreate(
            Identifier.of(BRAVE.MOD_ID, "player_data"), PlayerDataComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYER_DATA, PlayerDataComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
