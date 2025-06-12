package fr.kayrouge.brave.agents;

import fr.kayrouge.brave.agents.spell.Spell;
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


    @Environment(EnvType.CLIENT)
    public void transformAnimation() {
    }

}
