package fr.kayrouge.brave.agents;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.omen.OmenAgent;
import net.minecraft.registry.Registry;

public class Agents {

    public static final Agent DEFAULT = register(new DefaultAgent("Human"));
    public static final Agent TEST = register(new DefaultAgent("TEST"));
    public static final Agent OMEN = register(new OmenAgent("Omen"));


    private static Agent register(String id, Agent agent) {
        return Registry.register(BRegistries.AGENTS, BRAVE.id(id), agent);
    }

    private static Agent register(Agent agent) {
        return register(agent.getUniversalName(), agent);
    }


    public static void init() {}
}
