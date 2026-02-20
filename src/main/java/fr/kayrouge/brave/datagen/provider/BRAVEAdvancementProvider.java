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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
                        Text.literal("Protect your world"),
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
                        Text.literal("Welcome to the protocol, agent"),
                        Text.literal("Become an agent"),
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
                        Text.literal("The shadow"),
                        Text.literal("Become Omen"),
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


        AdvancementEntry agentofthemonth = createAgentOfTheMonth(agent, consumer, registryLookup.getOrThrow(BRegistries.AGENTS.getKey()));

    }

    private AdvancementEntry createAgentOfTheMonth(AdvancementEntry parent, Consumer<AdvancementEntry> consumer, RegistryWrapper.Impl<Agent> wrapper) {
        Advancement.Builder agentofthemonth = Advancement.Builder.create()
                .parent(parent)
                .display(
                        BItems.ICON_AGENT,
                        Text.literal("We only need you"),
                        Text.literal("Try out every agent"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                );

        List<String> criteriaNames = new ArrayList<>();

        wrapper.streamEntries().forEach(agentReference -> {

            Agent agent = agentReference.value();
            if(agent == Agents.TEST || agent == Agents.DEFAULT) return;
            String criterionName = "be_"+agent.getUniversalName();
            criteriaNames.add(criterionName);

            agentofthemonth.criterion(criterionName, BecomeAgentCriterion.Conditions.agent(wrapper, agent));

        });
        agentofthemonth.requirements(AdvancementRequirements.allOf(criteriaNames));

        return agentofthemonth.build(consumer, BRAVE.MOD_ID+":agent_of_the_month");
    }
}
