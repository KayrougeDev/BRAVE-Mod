package fr.kayrouge.brave.agents.waylay;

import fr.kayrouge.brave.agents.spell.Spell;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class WaylayTPSpell extends Spell {

    @Override
    public boolean use(ServerPlayerEntity player, NbtCompound data) {
        return false;
    }

    @Override
    public int getTickCooldown() {
        return 0;
    }
}
