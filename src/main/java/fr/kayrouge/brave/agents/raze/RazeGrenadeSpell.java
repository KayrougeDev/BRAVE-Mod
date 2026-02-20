package fr.kayrouge.brave.agents.raze;

import fr.kayrouge.brave.agents.spell.EquippableSpell;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class RazeGrenadeSpell extends EquippableSpell {

    @Override
    public void equip(ServerPlayerEntity player) {

    }

    @Override
    public void cancel(ServerPlayerEntity player) {

    }

    @Override
    public boolean use(ServerPlayerEntity player, NbtCompound data) {
        player.sendMessage(Text.literal("GRENADEEEE"));
        return false;
    }

    @Override
    public int getTickCooldown() {
        return 0;
    }
}
