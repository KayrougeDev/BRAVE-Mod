package fr.kayrouge.brave.agents.raze;

import fr.kayrouge.brave.agents.spell.EquippableSpell;
import fr.kayrouge.brave.agents.spell.Spell;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class RazeUltimateSpell extends EquippableSpell implements Spell.IUltimate {
    @Override
    public void equip(ServerPlayerEntity player) {

    }

    @Override
    public void cancel(ServerPlayerEntity player) {

    }

    @Override
    public boolean use(ServerPlayerEntity player, NbtCompound data) {
        return false;
    }

    @Override
    public int getTickCooldown() {
        return 0;
    }

    @Override
    public int getCost() {
        return 0;
    }
}
