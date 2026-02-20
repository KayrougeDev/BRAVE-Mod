package fr.kayrouge.brave.datagen.provider.lang;

import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.agents.spell.Spells;
import fr.kayrouge.brave.items.BItemGroups;
import fr.kayrouge.brave.items.BItems;
import fr.kayrouge.brave.items.BecomeAgentItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BRAVEEnglishLangProvider extends FabricLanguageProvider {
    public BRAVEEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        // Spell
        translationBuilder.add(Spells.DEFAULT.getTranslationKey(), "N/A");
        translationBuilder.add(Spells.OMEN_TP.getTranslationKey(), "Shrouded Step");

        translationBuilder.add(Spells.RAZE_BOOMBOT.getTranslationKey(), "Boom Bot");
        translationBuilder.add(Spells.RAZE_SATCHEL.getTranslationKey(), "Blast Pack");
        translationBuilder.add(Spells.RAZE_GRENADE.getTranslationKey(), "Paint Shells");
        translationBuilder.add(Spells.RAZE_ULTIMATE.getTranslationKey(), "Showstopper");

        translationBuilder.add(Spells.WAYLAY_TP.getTranslationKey(), "Refract");

        // Spell description
        translationBuilder.add(Spells.DEFAULT.getDescriptionTranslationKey(), "Nothing");
        translationBuilder.add(Spells.OMEN_TP.getDescriptionTranslationKey(), "EQUIP a shadow walk ability and see its range indicator. FIRE to begin a brief channel, then teleport to the marked location.");

        translationBuilder.add(Spells.RAZE_BOOMBOT.getDescriptionTranslationKey(), "EQUIP a Boom Bot. FIRE will deploy the bot, causing it to travel in a straight line on the ground, bouncing off walls. The Boom Bot will lock on to any enemies in its frontal cone and chase them, exploding for heavy damage if it reaches them.");
        translationBuilder.add(Spells.RAZE_SATCHEL.getDescriptionTranslationKey(), "INSTANTLY throw a Blast Pack that will stick to surfaces. RE-USE the ability after deployment to detonate, damaging and moving anything hit. Raze isn't damaged by this ability, but will still take fall damage if launched up far enough.");
        translationBuilder.add(Spells.RAZE_GRENADE.getDescriptionTranslationKey(), "EQUIP a cluster grenade. FIRE to throw the grenade, which does damage and creates sub-munitions, each doing damage to anyone in their range. ALT FIRE to lob. Paint Shells charge resets every two kills.");
        translationBuilder.add(Spells.RAZE_ULTIMATE.getDescriptionTranslationKey(), "EQUIP a rocket launcher. FIRE to shoot a rocket that does massive area damage on contact with anything.");

        translationBuilder.add(Spells.WAYLAY_TP.getDescriptionTranslationKey(), "INSTANTLY create a beacon of light on the floor. REACTIVATE to speed back to your beacon as a mote of pure light. You are invulnerable as you travel.");


        // Item
        translationBuilder.add(BItems.RADIANITE, "Radianite");
        translationBuilder.add(BItems.ICON, "Icon: BRAVE");
        translationBuilder.add(BItems.ICON_AGENT, "Icon: Agent");

        translationBuilder.add(BItemGroups.OTHER, "BRAVE: Other");

        translationBuilder.add(BecomeAgentItem.TOOLTIP_SHOW_SPELL_TRANSLATION_KEY, "Press Shift to see this agent's spells");
        translationBuilder.add(BecomeAgentItem.TOOLTIP_SPELLS_TRANSLATION_KEY, "Spells:");
        translationBuilder.add(BecomeAgentItem.TOOLTIP_AGENT_TRANSLATION_KEY, "Agent: ");


        wrapperLookup.getOrThrow(BRegistries.AGENTS.getKey()).streamEntries().forEach(agentReference -> {

            translationBuilder.add(agentReference.value().getItem(), "Become "+agentReference.value().getDisplayName());

        });
    }
}
