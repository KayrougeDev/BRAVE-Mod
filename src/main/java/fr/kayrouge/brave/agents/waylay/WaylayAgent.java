package fr.kayrouge.brave.agents.waylay;

import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.agents.spell.Spells;

public class WaylayAgent extends Agent {

    public WaylayAgent(String name) {
        super(name);
    }

    @Override
    public Spell getFirstSpell() {
        return Spells.DEFAULT;
    }

    @Override
    public Spell getSecondSpell() {
        return Spells.DEFAULT;
    }

    @Override
    public Spell getThirdSpell() {
        return Spells.WAYLAY_TP;
    }

    @Override
    public Spell getUltimate() {
        return Spells.DEFAULT;
    }
}
