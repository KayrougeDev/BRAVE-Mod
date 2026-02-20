package fr.kayrouge.brave.agents.raze;

import fr.kayrouge.brave.agents.spell.Spell;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class RazeSatchelSpell extends Spell {
    @Override
    public boolean use(ServerPlayerEntity player, NbtCompound data) {
        player.sendMessage(Text.literal("Satchel out"));
        return false;
    }

    @Override
    public int getTickCooldown() {
        return 0;
    }
}
