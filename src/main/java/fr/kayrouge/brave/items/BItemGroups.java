package fr.kayrouge.brave.items;

import fr.kayrouge.brave.BRAVE;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BItemGroups {

    public static final ResourceKey<CreativeModeTab> OTHER;

    private static ResourceKey<CreativeModeTab> createRegistryKey(String name, CreativeModeTab group) {
        ResourceKey<CreativeModeTab> k = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(BRAVE.MOD_ID, name));
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, k, group);
        return k;
    }

    static {
        OTHER = createRegistryKey("other", FabricCreativeModeTab.builder()
                    .title(Component.translatable("brave.itemGroup.other"))
                    .icon(() -> new ItemStack(BItems.ICON))
                    .build()
                );
    }

}
