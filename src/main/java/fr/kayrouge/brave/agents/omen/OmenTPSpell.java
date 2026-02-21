package fr.kayrouge.brave.agents.omen;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.spell.EquippableSpell;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class OmenTPSpell extends EquippableSpell {

    @Override
    public boolean use(ServerPlayerEntity player, NbtCompound nbt) {
        double x = nbt.getDouble("tpX", 0);
        double y = nbt.getDouble("tpY", 0);
        double z = nbt.getDouble("tpZ", 0);

        player.teleport(x, y, z, true);

        return false;
    }

    @Override
    public void equip(ServerPlayerEntity player) {}

    @Override
    public void cancel(ServerPlayerEntity player) {

    }

    @Override
    public int getTickCooldown() {
        return 0;
    }


    @Environment(EnvType.CLIENT)
    public void renderTarget() {

    }
}
