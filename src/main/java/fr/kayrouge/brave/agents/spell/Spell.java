package fr.kayrouge.brave.agents.spell;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class Spell {

    public Spell() {
    }

    public abstract boolean use(ServerPlayerEntity player, NbtCompound data);


    public abstract int getTickCooldown();
    public int getSecondCooldown() {
        return getTickCooldown()/20;
    }

    public interface IUltimate {
        int getCost();
    }



}
