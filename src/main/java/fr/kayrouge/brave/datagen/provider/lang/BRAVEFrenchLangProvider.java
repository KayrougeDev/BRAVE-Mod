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

        translationBuilder.add(Spells.RAZE_BOOMBOT.getTranslationKey(), "Boum Bot");
        translationBuilder.add(Spells.RAZE_SATCHEL.getTranslationKey(), "Pack Explosif");
        translationBuilder.add(Spells.RAZE_GRENADE.getTranslationKey(), "Grenade Gigogne");
        translationBuilder.add(Spells.RAZE_ULTIMATE.getTranslationKey(), "Bouquet Final");

        translationBuilder.add(Spells.WAYLAY_TP.getTranslationKey(), "Réfraction");

        // Spell description
        translationBuilder.add(Spells.DEFAULT.getDescriptionTranslationKey(), "Rien");
        translationBuilder.add(Spells.OMEN_TP.getDescriptionTranslationKey(), "ÉQUIPEZ-vous d'une compétence de marche des ombres accompagnée d'un indicateur de portée. TIREZ pour commencer une courte canalisation avant de vous téléporter vers l'endroit marqué.");

        translationBuilder.add(Spells.RAZE_BOOMBOT.getDescriptionTranslationKey(), "ÉQUIPEZ-vous d'un Boum Bot. TIREZ pour déployer le bot, ce qui le propulse en ligne droite sur le sol. Il rebondit contre les murs. Les Boum Bots se verrouillent sur les ennemis situés dans un cône face à eux et les chassent, explosant quand ils les atteignent en infligeant de lourds dégâts.");
        translationBuilder.add(Spells.RAZE_SATCHEL.getDescriptionTranslationKey(), "Lancez INSTANTANÉMENT un pack explosif qui se colle aux surfaces. RÉUTILISEZ la compétence pour déclencher l'explosion, ce qui blesse et déplace tous ceux pris dans le souffle. Raze n'est pas blessée par cette compétence, mais elle subit des dégâts de chute si elle tombe d'assez haut.");
        translationBuilder.add(Spells.RAZE_GRENADE.getDescriptionTranslationKey(), "ÉQUIPEZ-vous d'une grenade à sous-munitions. TIREZ pour lancer la grenade. Elle inflige des dégâts et crée des sous-munitions qui blessent tous ceux qui sont à leur portée. Utilisez le TIR SECONDAIRE pour la lancer en cloche. Vous regagnez une charge de Grenade gigogne toutes les deux éliminations.");
        translationBuilder.add(Spells.RAZE_ULTIMATE.getDescriptionTranslationKey(), "ÉQUIPEZ-vous d'un lance-roquettes. TIREZ pour lancer une roquette qui inflige de lourds dégâts de zone au premier contact.");

        translationBuilder.add(Spells.WAYLAY_TP.getDescriptionTranslationKey(), "Produisez INSTANTANÉMENT une balise lumineuse au sol. RÉACTIVEZ pour revenir rapidement à votre balise sous forme d'une particule de lumière pure. Vous êtes invulnérable pendant ce déplacement.");


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
