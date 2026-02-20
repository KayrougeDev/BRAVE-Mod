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

public class BRAVEFrenchLangProvider extends FabricLanguageProvider {
    public BRAVEFrenchLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "fr_fr", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        // Spell
        translationBuilder.add(Agents.TEST.getDescriptionTranslationKey(), "description descriptante");
        translationBuilder.add(Spells.DEFAULT.getTranslationKey(), "N/A");

        translationBuilder.add(Agents.OMEN.getDescriptionTranslationKey(), "Véritable fantôme d'un souvenir, Omen chasse dans les ténèbres. Il aveugle les ennemis, se téléporte d'un bout à l'autre du champ de bataille et laisse la peur se répandre parmi ses adversaires qui se demandent qui sera sa prochaine victime.");
        translationBuilder.add(Spells.OMEN_TP.getTranslationKey(), "Voie Des Ombres");

        translationBuilder.add(Agents.RAZE.getDescriptionTranslationKey(), "Armée de sa personnalité et de sa grosse artillerie, Raze fait une entrée explosive depuis le Brésil. Grâce à sa force brute, elle excelle à débusquer les ennemis retranchés et à faire le ménage dans les espaces étroits, le tout avec une bonne dose de « boum ».");
        translationBuilder.add(Spells.RAZE_BOOMBOT.getTranslationKey(), "Boum Bot");
        translationBuilder.add(Spells.RAZE_SATCHEL.getTranslationKey(), "Pack Explosif");
        translationBuilder.add(Spells.RAZE_GRENADE.getTranslationKey(), "Grenade Gigogne");
        translationBuilder.add(Spells.RAZE_ULTIMATE.getTranslationKey(), "Bouquet Final");

        translationBuilder.add(Agents.WAYLAY.getDescriptionTranslationKey(), "Waylay, la prismatique radiante de Thaïlande, se transforme en particules de lumière pour filer sur le champ de bataille. Elle frappe ses cibles à l'aide d'éclats de lumière avant de se remettre à l'abri, le tout en un clin d'œil.");
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
        translationBuilder.add(BecomeAgentItem.TOOLTIP_SHOW_DESCRIPTION_TRANSLATION_KEY, "Appuyez sur Control pour voir la description de l'agent");
        translationBuilder.add(BecomeAgentItem.TOOLTIP_SPELLS_TRANSLATION_KEY, "Compétences:");
        translationBuilder.add(BecomeAgentItem.TOOLTIP_AGENT_TRANSLATION_KEY, "Agent: ");

        registryLookup.getOrThrow(BRegistries.AGENTS.getKey()).streamEntries().forEach(agentReference -> {
            translationBuilder.add(agentReference.value().getItem(), "Devenir "+agentReference.value().getDisplayName());
        });

        // Advancements
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("brave/desc"), "Protégez votre monde");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("agent/title"), "Bienvenue dans le protocole, agent");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("agent/desc"), "Devenir un agent");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_omen/title"), "L'ombre");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_omen/desc"), "Devenir Omen");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_raze/desc"), "Devenir Raze");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_waylay/title"), "Vitesse-lumiere");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("become_waylay/desc"), "Devenir Waylay");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("superpowers_are_useless/title"), "Les supers pouvoirs sont inutiles");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("superpowers_are_useless/desc"), "Essayez tout les agents non-radiants");

        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("agent_of_the_month/title"), "On a besoin que de vous");
        translationBuilder.add(BRAVEAdvancementProvider.getTranslation("agent_of_the_month/desc"), "Essayez tout les agents");

        translationBuilder.add(BKeyBindings.FIRST_SPELL_USE.getId(), "Utiliser Premier Sort");
        translationBuilder.add(BKeyBindings.SECOND_SPELL_USE.getId(), "Utiliser Deuxième Sort");
        translationBuilder.add(BKeyBindings.THIRD_SPELL_USE.getId(), "Utiliser Troisième Sort");
        translationBuilder.add(BKeyBindings.ULTIMATE_USE.getId(), "Utiliser Ultime");
        translationBuilder.add(BKeyBindings.DISPLAY_AGENT_INFO.getId(), "Afficher les infos de l'agent");

        translationBuilder.add(BKeyBindings.TEST_KEY.getId(), "Info de DEBUG");
    }
}
