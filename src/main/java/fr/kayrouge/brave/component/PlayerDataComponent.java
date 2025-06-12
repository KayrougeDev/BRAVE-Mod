package fr.kayrouge.brave.component;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.Agents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class PlayerDataComponent implements AutoSyncedComponent {

    private Agents agent = Agents.NONE;
    private int exposedTime = 0;

    private final PlayerEntity player;
    public PlayerDataComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void applySyncPacket(RegistryByteBuf buf) {
        this.exposedTime = buf.readInt();
        this.agent = Agents.getByID(buf.readInt());


        // if should update agent
        if(buf.readBoolean()) {
            BRAVE.LOGGER.info("SYNCED");
            this.agent.getAgent().transformAnimation();
            // TODO Copy PopKorn render queue https://github.com/KayrougeDev/PopKorn/blob/master/src/main/java/fr/kayrouge/popkorn/client/renderer/PKRenderers.java
        }
    }

    @Override
    public void writeSyncPacket(RegistryByteBuf buf, ServerPlayerEntity recipient) {
        writeSyncPacket(buf, recipient, false);
    }

    public void writeSyncPacket(RegistryByteBuf buf, ServerPlayerEntity recipient, boolean updateAgent) {
        buf.writeInt(this.exposedTime);
        buf.writeInt(agent.getId());
        buf.writeBoolean(updateAgent);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.agent = Agents.getByID(nbtCompound.getInt("agentID"));
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("agentID", this.agent.getId());
    }


    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return player == this.player;
    }

    public int getExposedTime() {
        return exposedTime;
    }

    public void setExposedTime(int exposedTime) {
        this.exposedTime = exposedTime;
    }

    public void incrementExposedTime() {
        this.exposedTime++;
    }

    public void setAgent(Agents agent) {
        this.agent = agent;
    }

    public Agents getAgent() {
        return agent;
    }

}
