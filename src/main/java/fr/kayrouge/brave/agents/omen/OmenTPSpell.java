package fr.kayrouge.brave.agents.omen;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.spell.EquippableSpell;
import fr.kayrouge.brave.agents.spell.Spell;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class OmenTPSpell extends EquippableSpell {

    @Override
    public boolean use(ServerPlayerEntity player, NbtCompound nbt) {
        BRAVE.LOGGER.info("Use omen tp");

        double x = nbt.getDouble("tpX", 0);
        double y = nbt.getDouble("tpY", 0);
        double z = nbt.getDouble("tpZ", 0);

        player.teleport(x, y, z, true);

        return false;
    }

    @Override
    public void equip() {
        BRAVE.LOGGER.info("Equip omen tp");
    }

    @Override
    public void cancel() {
        BRAVE.LOGGER.info("Cancel omen tp");
    }

    @Override
    public int getTickCooldown() {
        return 0;
    }
}
