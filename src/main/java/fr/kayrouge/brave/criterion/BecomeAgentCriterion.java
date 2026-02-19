package fr.kayrouge.brave.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.predicate.AgentPredicate;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public class BecomeAgentCriterion extends AbstractCriterion<BecomeAgentCriterion.Conditions> {


    @Override
    public Codec<Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player, Agent agent) {
        trigger(player, conditions -> conditions.matches(agent));
    }

    public static record Conditions(Optional<LootContextPredicate> player, Optional<AgentPredicate> agent, boolean negated) implements AbstractCriterion.Conditions {
        public static Codec<BecomeAgentCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
                AgentPredicate.CODEC.optionalFieldOf("agent").forGetter(Conditions::agent),
                Codec.BOOL.optionalFieldOf("negated", false).forGetter(Conditions::negated)
                ).apply(instance, Conditions::new));

        public static AdvancementCriterion<BecomeAgentCriterion.Conditions> any() {
            return BCriteria.BECOME_AGENT.create(new Conditions(Optional.empty(), Optional.empty(), false));
        }

        public static AdvancementCriterion<BecomeAgentCriterion.Conditions> agent(RegistryEntryLookup<Agent> lookup, Agent... agents) {
            return predicate(AgentPredicate.Builder.create().agents(lookup, agents), false);
        }

        public static AdvancementCriterion<BecomeAgentCriterion.Conditions> predicate(AgentPredicate.Builder builder, boolean not) {
            return BCriteria.BECOME_AGENT.create(new Conditions(Optional.empty(), Optional.of(builder.build()), not));
        }

        public static AdvancementCriterion<BecomeAgentCriterion.Conditions> anyExpect(RegistryEntryLookup<Agent> lookup, Agent... agents) {
            return predicate(AgentPredicate.Builder.create().agents(lookup, agents), true);
        }

        public boolean matches(Agent a) {
            boolean b = this.agent.isEmpty() || this.agent.get().test(a);

            return negated != b;
        }

    }
}
