package fr.kayrouge.brave.agents;

import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.agents.spell.Spells;

public class DefaultAgent extends Agent {

    public DefaultAgent(String name) {
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
        return Spells.DEFAULT;
    }

    @Override
    public Spell getUltimate() {
        return Spells.DEFAULT;
    }
}
