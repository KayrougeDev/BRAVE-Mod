package fr.kayrouge.brave.agents.spell;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.omen.OmenTPSpell;
import fr.kayrouge.brave.agents.raze.RazeBoombotSpell;
import fr.kayrouge.brave.agents.raze.RazeGrenadeSpell;
import fr.kayrouge.brave.agents.raze.RazeSatchelSpell;
import fr.kayrouge.brave.agents.raze.RazeUltimateSpell;
import fr.kayrouge.brave.agents.waylay.WaylayTPSpell;
import net.minecraft.registry.Registry;

public class Spells {

    public static final EquippableSpell DEFAULT = register("default", new DefaultSpell());

    //Omen
    public static final Spell OMEN_TP = register("omen_tp", new OmenTPSpell());

    // Waylay
    public static final Spell WAYLAY_TP = register("waylay_tp", new WaylayTPSpell());

    // Raze
    public static final Spell RAZE_BOOMBOT = register("raze_boombot", new RazeBoombotSpell());
    public static final Spell RAZE_SATCHEL = register("raze_satchel", new RazeSatchelSpell());
    public static final Spell RAZE_GRENADE = register("raze_grenade", new RazeGrenadeSpell());
    public static final Spell RAZE_ULTIMATE = register("raze_ultimate", new RazeUltimateSpell());

    public static <T extends Spell> T register(String id, T spell) {
        return Registry.register(BRegistries.SPELLS, BRAVE.id(id), spell);
    }

    public static void init() {}

}
