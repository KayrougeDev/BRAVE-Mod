package fr.kayrouge.brave.agents;

import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.client.render.BRenderers;
import fr.kayrouge.brave.items.BItemGroups;
import fr.kayrouge.brave.items.BItems;
import fr.kayrouge.brave.items.BecomeAgentItem;
import lombok.Getter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.util.Util;

public abstract class Agent {

    @Getter
    private final String displayName;
    @Getter
    private final String universalName;
    @Getter
    private final Item item;

    public Agent(String universalName, String displayName) {
        this.displayName = displayName;
        this.universalName = universalName;
        this.item = BItems.register("become_"+universalName, settings -> new BecomeAgentItem(this, settings.maxCount(1)));

        ItemGroupEvents.modifyEntriesEvent(BItemGroups.OTHER).register(entries -> entries.add(this.item));
    }

    public Agent(String name) {
        this(name.toLowerCase(), name);
    }

    public abstract Spell getFirstSpell();
    public abstract Spell getSecondSpell();
    public abstract Spell getThirdSpell();
    public abstract Spell getUltimate();

    public boolean isReallyAnUltimate() {
        return getUltimate() instanceof Spell.IUltimate;
    }

    public int getUltimateCost() {
        return isReallyAnUltimate() ? ((Spell.IUltimate)getUltimate()).getCost() : 0;
    }

    public boolean isRadiant() {
        return true;
    }

    public String getDescriptionTranslationKey() {
        return Util.createTranslationKey("agent", BRegistries.AGENTS.getId(this).withSuffixedPath(".description"));
    }

    @Environment(EnvType.CLIENT)
    public Runnable transformAnimation(BRenderers.RenderContext renderContext, long l) {
        return () -> {};
    }

}
