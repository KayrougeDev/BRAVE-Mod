package fr.kayrouge.brave.component;

import fr.kayrouge.brave.agents.Agents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class PlayerDataComponent implements AutoSyncedComponent {

    private Agents agent = Agents.NONE;

    private final PlayerEntity player;
    public PlayerDataComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.agent = Agents.getByID(nbtCompound.getInt("agentID"));
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("agentID", agent.getId());
    }
}
