package fr.kayrouge.brave.agents.spell;

import fr.kayrouge.brave.BRegistries;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Util;

public abstract class Spell {

    public Spell() {
    }

    public abstract boolean use(ServerPlayerEntity player, NbtCompound data);

    public String getTranslationKey() {
        return Util.createTranslationKey("spell", BRegistries.SPELLS.getId(this));
    }

    public String getDescriptionTranslationKey() {
        return Util.createTranslationKey("spell", BRegistries.SPELLS.getId(this).withSuffixedPath(".description"));
    }

    public abstract int getTickCooldown();
    public int getSecondCooldown() {
        return getTickCooldown()/20;
    }

    public interface IUltimate {
        int getCost();
    }



}
