package fr.kayrouge.brave.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.Agent;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;

import java.util.Optional;
import java.util.function.Predicate;

public record AgentPredicate(Optional<RegistryEntryList<Agent>> agents) implements Predicate<Agent> {
    public static final Codec<AgentPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    RegistryCodecs.entryList(BRegistries.AGENTS.getKey()).optionalFieldOf("agents").forGetter(AgentPredicate::agents)
            ).apply(instance, AgentPredicate::new));

    @Override
    public boolean test(Agent agent) {
        if(this.agents.isEmpty()) {
            return true;
        }

        return this.agents().get().contains(BRegistries.AGENTS.getEntry(agent));
    }

    public static class Builder {
        private Optional<RegistryEntryList<Agent>> agent = Optional.empty();

        public static Builder create() {
            return new Builder();
        }

        public AgentPredicate.Builder agents(RegistryEntryLookup<Agent> agentRegistry, Agent... agents) {
            this.agent = Optional.of(RegistryEntryList.of(BRegistries.AGENTS::getEntry, agents));
            return this;
        }

        public AgentPredicate.Builder tag(RegistryEntryLookup<Agent> agentRegistry, TagKey<Agent> tag) {
            this.agent = Optional.of(agentRegistry.getOrThrow(tag));
            return this;
        }

        public AgentPredicate build() {
            return new AgentPredicate(this.agent);
        }

    }
}
