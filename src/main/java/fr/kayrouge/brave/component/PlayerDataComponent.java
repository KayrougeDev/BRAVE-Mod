package fr.kayrouge.brave.component;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.agents.spell.EquippableSpell;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.agents.spell.Spells;
import fr.kayrouge.brave.client.render.BRenderers;
import fr.kayrouge.brave.criterion.BCriteria;
import fr.kayrouge.brave.network.BPacketCodecs;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class PlayerDataComponent implements AutoSyncedComponent {

    @Getter
    private Agent agent = Agents.DEFAULT;
    @Getter
    @Setter
    private int exposedTime = 0;
    @Getter
    private Spell equippedSpell = Spells.DEFAULT;

    private final PlayerEntity player;
    public PlayerDataComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void applySyncPacket(RegistryByteBuf buf) {
        this.agent = BPacketCodecs.AGENT.decode(buf);
        this.equippedSpell = BPacketCodecs.SPELL.decode(buf);


        // if should update agent
        if(buf.readBoolean()) {
            BRAVE.LOGGER.info("SYNCED");
            BRenderers.INSTANCE.addRenderTask(this.agent::transformAnimation,1000*5);
        }
    }

    @Override
    public void writeSyncPacket(RegistryByteBuf buf, ServerPlayerEntity recipient) {
        writeSyncPacket(buf, recipient, false);
    }

    public void writeSyncPacket(RegistryByteBuf buf, ServerPlayerEntity recipient, boolean updateAgent) {
        BPacketCodecs.AGENT.encode(buf, this.agent);
        BPacketCodecs.SPELL.encode(buf, this.equippedSpell);

        buf.writeBoolean(updateAgent);
    }


    @Override
    public void readData(ReadView readView) {
        this.agent = BRegistries.AGENTS.get(Identifier.tryParse(readView.getString(
                "agentID", BRegistries.AGENTS.getId(Agents.DEFAULT).toString()
        )));
    }

    @Override
    public void writeData(WriteView writeView) {
        writeView.putString("agentID", BRegistries.AGENTS.getId(this.agent).toString());
    }

    public void setAgent(Agent agent) {
        setAgent(agent, false);
    }

    public void setAgent(Agent agent, boolean sync) {
        if (!(this.player instanceof ServerPlayerEntity)) {
            BRAVE.LOGGER.error("Tried change agent on client side !");
            return;
        }
        this.agent = agent;
        BCriteria.BECOME_AGENT.trigger((ServerPlayerEntity) this.player, agent);
        if (sync) {
            BComponents.PLAYER_DATA.sync(this.player);
        }
    }

    public void incrementExposedTime() {
        this.exposedTime++;
    }

    public void setEquippedSpell(EquippableSpell equippedSpell) {
        this.equippedSpell = equippedSpell;
    }

    public void resetEquippedSpell() {
        this.equippedSpell = Spells.DEFAULT;
    }
}
