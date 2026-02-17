package fr.kayrouge.brave.agents;

import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.client.render.BRenderers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public abstract class Agent {

    private final String name;

    public Agent(String name) {
        this.name = name;
    }

    public abstract Spell getFirstSpell();
    public abstract Spell getSecondSpell();
    public abstract Spell getThirdSpell();
    public abstract Spell getUltimate();

    public String getName() {
        return name;
    }

    @Environment(EnvType.CLIENT)
    public Runnable transformAnimation(BRenderers.RenderContext renderContext, long l) {
        return () -> {};
    }
}
