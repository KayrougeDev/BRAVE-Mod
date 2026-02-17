package fr.kayrouge.brave.items;

import fr.kayrouge.brave.BRAVE;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.jukebox.JukeboxSongs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public class BItems {

    public static final Item ICON = register("icon", settings -> new Item(settings.maxCount(1).fireproof().rarity(Rarity.EPIC).jukeboxPlayable(JukeboxSongs.PIGSTEP)), BItemGroups.OTHER);
    public static final Item RADIANITE = register("radianite", settings -> new Item(settings.maxCount(24)), ItemGroups.INGREDIENTS);
    public static final Item RESET = register("reset", settings -> new ResetItem(settings.maxCount(1)));


    private static Item register(String name, Function<Item.Settings, Item> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(BRAVE.MOD_ID, name));

        Item item = factory.apply(new Item.Settings().registryKey(key));

        Registry.register(Registries.ITEM, key, item);
        return item;
    }

    private static Item register(String name, Function<Item.Settings, Item> factory, RegistryKey<ItemGroup> group) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(BRAVE.MOD_ID, name));
        Item item = factory.apply(new Item.Settings().registryKey(key));
        Registry.register(Registries.ITEM, key, item);
        registerInItemGroup(item, group);
        return item;
    }

    private static void registerInItemGroup(Item item, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void init() {}

}
