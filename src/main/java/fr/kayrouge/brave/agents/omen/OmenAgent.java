package fr.kayrouge.brave.agents.omen;

import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.agents.spell.Spells;
import fr.kayrouge.brave.client.render.BRenderers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class OmenAgent extends Agent {

    public OmenAgent(String name) {
        super(name);
    }

    @Override
    public Spell getFirstSpell() {
        return Spells.OMEN_TP;
    }

    @Override
    public Spell getSecondSpell() {
        return null;
    }

    @Override
    public Spell getThirdSpell() {
        return null;
    }

    @Override
    public Spell getUltimate() {
        return null;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Runnable transformAnimation(BRenderers.RenderContext renderContext, long l) {
        return () -> {};
    }
}
