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

public class BRAVEFrenchLangProvider extends FabricLanguageProvider {
    public BRAVEFrenchLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "fr_fr", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        // Spell
        translationBuilder.add(Spells.DEFAULT.getTranslationKey(), "N/A");
        translationBuilder.add(Spells.OMEN_TP.getTranslationKey(), "Voie Des Ombres");

        // Spell description
        translationBuilder.add(Spells.DEFAULT.getDescriptionTranslationKey(), "Rien");
        translationBuilder.add(Spells.OMEN_TP.getDescriptionTranslationKey(), "ÉQUIPEZ-vous d'une compétence de marche des ombres accompagnée d'un indicateur de portée. TIREZ pour commencer une courte canalisation avant de vous téléporter vers l'endroit marqué.");


        // Item
        translationBuilder.add(BItems.RADIANITE, "Radianite");
        translationBuilder.add(BItems.ICON, "Icône");
        translationBuilder.add(BItems.ICON_AGENT, "Icône: Agent");

        translationBuilder.add(BItemGroups.OTHER, "BRAVE: Autre");

        translationBuilder.add(BecomeAgentItem.TOOLTIP_SHOW_SPELL_TRANSLATION_KEY, "Appuyez sur Maj pour voir les compétences");
        translationBuilder.add(BecomeAgentItem.TOOLTIP_SPELLS_TRANSLATION_KEY, "Compétences:");
        translationBuilder.add(BecomeAgentItem.TOOLTIP_AGENT_TRANSLATION_KEY, "Agent: ");

        registryLookup.getOrThrow(BRegistries.AGENTS.getKey()).streamEntries().forEach(agentReference -> {
            translationBuilder.add(agentReference.value().getItem(), "Devenir "+agentReference.value().getDisplayName());
        });
    }
}
