package fr.kayrouge.brave.agents.spell;

import net.minecraft.server.network.ServerPlayerEntity;

public abstract class EquippableSpell extends  Spell {

    public abstract void equip(ServerPlayerEntity player);
    public abstract void cancel(ServerPlayerEntity player);
}
