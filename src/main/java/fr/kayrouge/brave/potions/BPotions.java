package fr.kayrouge.brave.potions;

import fr.kayrouge.brave.BRAVE;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BPotions {

    public static final Potion LIQUID_RADIANITE = register(new Potion("liquid_radianite", new StatusEffectInstance(BStatusEffect.RADIANITE_POISONING, 20*60, 1, false, false)));

    public static Potion register(Potion potion) {

        return Registry.register(Registries.POTION, BRAVE.id(potion.getBaseName()), potion);
    }

    public static void init() {}

}
