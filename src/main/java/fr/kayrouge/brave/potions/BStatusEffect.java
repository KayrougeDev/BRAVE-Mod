package fr.kayrouge.brave.potions;

import fr.kayrouge.brave.BRAVE;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

import java.awt.*;

public class BStatusEffect {

    public static final RegistryEntry<StatusEffect> RADIANITE_POISONING = register("radianite_poisoning", new RadianitePoisoningStatusEffect(StatusEffectCategory.NEUTRAL, new Color(0xFFCC4A).getRGB()));

    public static RegistryEntry<StatusEffect> register(String id, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, BRAVE.id(id), effect);
    }

    public static void init() {}
}
