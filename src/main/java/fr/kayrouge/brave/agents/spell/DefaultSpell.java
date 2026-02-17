package fr.kayrouge.brave.agents.spell;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class DefaultSpell extends EquippableSpell {
    @Override
    public boolean use(ServerPlayerEntity player, NbtCompound nbt) {
        return false;
    }

    @Override
    public int getTickCooldown() {
        return 0;
    }

    @Override
    public void equip() {
    }

    @Override
    public void cancel() {
    }
}
