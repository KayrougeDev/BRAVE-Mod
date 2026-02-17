package fr.kayrouge.brave;

import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.spell.Spell;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class BRegistries {

    public static final Registry<Spell> SPELLS = create("spells", Spell.class);
    public static final Registry<Agent> AGENTS = create("agents", Agent.class);


    private static <T> Registry<T> create(String id, Class<T> clazz) {
        return FabricRegistryBuilder.createSimple(
                key(id, clazz)
        ).buildAndRegister();
    }

    private static <T> RegistryKey<Registry<T>> key(String id, Class<T> clazz) {
        return RegistryKey.ofRegistry(BRAVE.id(id));
    }

    public static void init() {}

}
