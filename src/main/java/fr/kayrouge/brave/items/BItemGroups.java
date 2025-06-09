package fr.kayrouge.brave.items;

import fr.kayrouge.brave.BRAVE;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BItemGroups {

    public static final RegistryKey<ItemGroup> OTHER;

    private static RegistryKey<ItemGroup> createRegistryKey(String name, ItemGroup group) {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(BRAVE.MOD_ID, name), group);
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(BRAVE.MOD_ID, name));
    }

    static {
        OTHER = createRegistryKey("other", new ItemGroup.Builder(ItemGroup.Row.BOTTOM, 0)
                .displayName(Text.translatable("brave.itemGroup.other"))
                .icon(() -> new ItemStack(BItems.ICON))
                .build());

    }

}
