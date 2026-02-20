package fr.kayrouge.brave.agents;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.omen.OmenAgent;
import fr.kayrouge.brave.agents.raze.RazeAgent;
import fr.kayrouge.brave.agents.waylay.WaylayAgent;
import net.minecraft.registry.Registry;

public class Agents {

    public static final Agent DEFAULT = register(new DefaultAgent("Human"));
    public static final Agent TEST = register(new DefaultAgent("test", "T3ST"));
    public static final Agent OMEN = register(new OmenAgent("Omen"));
    public static final Agent WAYLAY = register(new WaylayAgent("Waylay"));
    public static final Agent RAZE = register(new RazeAgent("Raze"));

    private static Agent register(Agent agent) {
        return Registry.register(BRegistries.AGENTS, BRAVE.id(agent.getUniversalName()), agent);
    }


    public static void init() {}
}
