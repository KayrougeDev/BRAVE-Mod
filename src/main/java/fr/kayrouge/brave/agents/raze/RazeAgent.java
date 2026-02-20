package fr.kayrouge.brave.agents.raze;

import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.agents.spell.Spells;

import java.util.Locale;

public class RazeAgent extends Agent {

    public RazeAgent(String name) {
        super(name);
    }

    @Override
    public Spell getFirstSpell() {
        return Spells.RAZE_BOOMBOT;
    }

    @Override
    public Spell getSecondSpell() {
        return Spells.RAZE_SATCHEL;
    }

    @Override
    public Spell getThirdSpell() {
        return Spells.RAZE_GRENADE;
    }

    @Override
    public Spell getUltimate() {
        return Spells.RAZE_ULTIMATE;
    }

    @Override
    public boolean isRadiant() {
        return false;
    }
}
