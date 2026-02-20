package fr.kayrouge.brave.datagen.provider.lang;

import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.agents.spell.Spells;
import fr.kayrouge.brave.client.BKeyBindings;
import fr.kayrouge.brave.datagen.provider.BRAVEAdvancementProvider;
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
        translationBuilder.add(Agents.TEST.getDescriptionTranslationKey(), "description descriptante");
        translationBuilder.add(Spells.DEFAULT.getTranslationKey(), "N/A");

        translationBuilder.add(Agents.OMEN.getDescriptionTranslationKey(), "A phantom of a memory, Omen hunts in the shadows. He renders enemies blind, teleports across the field, then lets paranoia take hold as his foe scrambles to learn where he might strike next.");
        translationBuilder.add(Spells.OMEN_TP.getTranslationKey(), "Shrouded Step");

        translationBuilder.add(Agents.RAZE.getDescriptionTranslationKey(), "Raze explodes out of Brazil with her big personality and big guns. With her blunt-force-trauma playstyle, she excels at flushing entrenched enemies and clearing tight spaces with a generous dose of \"boom.\"");
        translationBuilder.add(Spells.RAZE_BOOMBOT.getTranslationKey(), "Boom Bot");
        translationBuilder.add(Spells.RAZE_SATCHEL.getTranslationKey(), "Blast Pack");
        translationBuilder.add(Spells.RAZE_GRENADE.getTranslationKey(), "Paint Shells");
        translationBuilder.add(Spells.RAZE_ULTIMATE.getTranslationKey(), "Showstopper");

        translationBuilder.add(Agents.WAYLAY.getDescriptionTranslationKey(), "Thailand's prismatic radiant Waylay transforms into light itself as she darts across the battlefield, striking down her targets through shards of light before flitting back to safety, all in the blink of an eye.");
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
        translationBuilder.add(BecomeAgentItem.TOOLTIP_SHOW_DESCRIPTION_TRANSLATION_KEY, "Press Control to see this agent's description");
        translationBuilder.add(BecomeAgentItem.TOOLTIP_SPELLS_TRANSLATION_KEY, "Spells:");
        translationBuilder.add(BecomeAgentItem.TOOLTIP_AGENT_TRANSLATION_KEY, "Agent: ");

        wrapperLookup.getOrThrow(BRegistries.AGENTS.getKey()).streamEntries().forEach(agentReference -> {
            translationBuilder.add(agentReference.value().getItem(), "Become "+agentReference.value().getDisplayName());
        });

        // Advancements
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("brave/desc"), "Protect your world");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("agent/title"), "Welcome to the protocol, agent");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("agent/desc"), "Become an agent");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_omen/title"), "The shadow");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_omen/desc"), "Become Omen");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_raze/desc"), "Become Raze");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_waylay/title"), "Lightspeed");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_waylay/desc"), "Become Waylay");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("superpowers_are_useless/title"), "Superpowers are useless");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("superpowers_are_useless/desc"), "Try every non radiant agents");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("agent_of_the_month/title"), "We only need you");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("agent_of_the_month/desc"), "Try out every agents");

        translationBuilder.add(BKeyBindings.FIRST_SPELL_USE.getId(), "Use First Spell");
        translationBuilder.add(BKeyBindings.SECOND_SPELL_USE.getId(), "Use Second Spell");
        translationBuilder.add(BKeyBindings.THIRD_SPELL_USE.getId(), "Use Third Spell");
        translationBuilder.add(BKeyBindings.ULTIMATE_USE.getId(), "Use Ultimate");
        translationBuilder.add(BKeyBindings.DISPLAY_AGENT_INFO.getId(), "Display Agent Info");

        translationBuilder.add(BKeyBindings.TEST_KEY.getId(), "DEBUG Info");
    }
}
