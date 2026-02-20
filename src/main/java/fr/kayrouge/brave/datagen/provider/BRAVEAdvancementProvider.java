package fr.kayrouge.brave.datagen.provider;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.criterion.BecomeAgentCriterion;
import fr.kayrouge.brave.items.BItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BRAVEAdvancementProvider extends FabricAdvancementProvider {

    public BRAVEAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry brave = Advancement.Builder.create()
                .display(
                        BItems.ICON,
                        Text.literal("BRAVE"),
                        Text.translatable(getTranslation("brave/desc")),
                        Identifier.of("gui/advancements/backgrounds/adventure"),
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("livin", TickCriterion.Conditions.createTick())
                .build(consumer, BRAVE.MOD_ID+":brave");

        RegistryEntryLookup<Agent> agentRegistry = registryLookup.getOrThrow(BRegistries.AGENTS.getKey());

        AdvancementEntry agent = Advancement.Builder.create()
                .parent(brave)
                .display(
                        BItems.ICON_AGENT,
                        Text.translatable(getTranslation("agent/title")),
                        Text.translatable(getTranslation("agent/desc")),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("be_agent", BecomeAgentCriterion.Conditions.anyExpect(agentRegistry, Agents.DEFAULT))
                .build(consumer, BRAVE.MOD_ID+":agent");

        AdvancementEntry omenAgent = Advancement.Builder.create()
                .parent(agent)
                .display(
                        BItems.ICON_AGENT,
                        Text.translatable(getTranslation("become_omen/title")),
                        Text.translatable(getTranslation("become_omen/desc")),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        false,
                        false
                )
                .criterion("be_omen", BecomeAgentCriterion.Conditions.agent(agentRegistry,
                        Agents.OMEN))
                .build(consumer, BRAVE.MOD_ID+":become_omen");

        AdvancementEntry testAgent = Advancement.Builder.create()
                .parent(agent)
                .display(
                        BItems.ICON_AGENT,
                        Text.literal("The useful useless"),
                        Text.literal("Become a... test ?"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        false,
                        true
                )
                .rewards(AdvancementRewards.Builder.experience(5))
                .criterion("be_test", BecomeAgentCriterion.Conditions.agent(agentRegistry,
                        Agents.TEST))
                .build(consumer, BRAVE.MOD_ID+":become_test");

        AdvancementEntry razeAgent = Advancement.Builder.create()
                .parent(agent)
                .display(
                        BItems.ICON_AGENT,
                        Text.literal("BOOOM!"),
                        Text.translatable(getTranslation("become_raze/desc")),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        false,
                        false
                )
                .criterion("be_raze", BecomeAgentCriterion.Conditions.agent(agentRegistry,
                        Agents.RAZE))
                .build(consumer, BRAVE.MOD_ID+":become_raze");

        AdvancementEntry waylayAgent = Advancement.Builder.create()
                .parent(agent)
                .display(
                        BItems.ICON_AGENT,
                        Text.translatable(getTranslation("become_waylay/title")),
                        Text.translatable(getTranslation("become_waylay/desc")),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        false,
                        false
                )
                .criterion("be_waylay", BecomeAgentCriterion.Conditions.agent(agentRegistry,
                        Agents.WAYLAY))
                .build(consumer, BRAVE.MOD_ID+":become_waylay");


        AdvancementEntry superpowersAreUseless = createConditionedAllAgentsAdvancement(
                Advancement.Builder.create().parent(agent).display(
                        BItems.ICON_AGENT,
                        Text.translatable(getTranslation("superpowers_are_useless/title")),
                        Text.translatable(getTranslation("superpowers_are_useless/desc")),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                ), consumer, registryLookup.getOrThrow(BRegistries.AGENTS.getKey()), BRAVE.MOD_ID+":superpowers_are_useless", agent1 -> !agent1.isRadiant());


        AdvancementEntry agentofthemonth = createConditionedAllAgentsAdvancement(
                Advancement.Builder.create().parent(agent).display(
                        BItems.ICON_AGENT,
                        Text.translatable(BRAVEAdvancementProvider.getTranslation("agent_of_the_month/title")),
                        Text.translatable(BRAVEAdvancementProvider.getTranslation("agent_of_the_month/desc")),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        false,
                        false
                ), consumer, registryLookup.getOrThrow(BRegistries.AGENTS.getKey()), BRAVE.MOD_ID+":agent_of_the_month", agent1 -> true);

    }

    private AdvancementEntry createConditionedAllAgentsAdvancement(Advancement.Builder builder,
                                                                   Consumer<AdvancementEntry> consumer,
                                                                   RegistryWrapper.Impl<Agent> wrapper,
                                                                   String id, Predicate<Agent> agentPredicate) {

        List<String> criteriaNames = new ArrayList<>();

        wrapper.streamEntries().forEach(agentReference -> {

            Agent agent = agentReference.value();
            if(agent == Agents.TEST || agent == Agents.DEFAULT) return;
            if(!agentPredicate.test(agent)) return;
            String criterionName = "be_"+agent.getUniversalName();
            criteriaNames.add(criterionName);

            builder.criterion(criterionName, BecomeAgentCriterion.Conditions.agent(wrapper, agent));

        });
        builder.requirements(AdvancementRequirements.allOf(criteriaNames));

        return builder.build(consumer, id);
    }

    public static String getTranslation(String name) {
        return Util.createTranslationKey("advancement", BRAVE.id(name));
    }
}
