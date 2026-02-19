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
