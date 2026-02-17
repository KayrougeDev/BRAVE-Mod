package fr.kayrouge.brave.agents.spell;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.omen.OmenTPSpell;
import net.minecraft.registry.Registry;

public class Spells {

    public static final EquippableSpell DEFAULT = register("default", new DefaultSpell());
    public static final Spell OMEN_TP = register("omen_tp", new OmenTPSpell());

    public static <T extends Spell> T register(String id, T spell) {
        return Registry.register(BRegistries.SPELLS, BRAVE.id(id), spell);
    }

    public static void init() {}

}
