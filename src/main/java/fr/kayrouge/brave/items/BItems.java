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
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class BItems {

    public static final Item ICON = register("icon", new Item(new Item.Settings().maxCount(1).fireproof().rarity(Rarity.EPIC).jukeboxPlayable(JukeboxSongs.PIGSTEP)), true);
    public static final Item RADIANITE = register("radianite", new Item(new Item.Settings().maxCount(24)), ItemGroups.INGREDIENTS);


    private static Item register(String name, Item item) {
        return register(name, item, false);
    }

    private static Item register(String name, Item item, boolean autoGroup) {
        Registry.register(Registries.ITEM, Identifier.of(BRAVE.MOD_ID, name), item);
        if(autoGroup) registerInItemGroup(item, BItemGroups.OTHER);
        return item;
    }

    private static Item register(String name, Item item, RegistryKey<ItemGroup> group) {
        register(name, item);
        registerInItemGroup(item, group);
        return item;
    }

    private static void registerInItemGroup(Item item, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void init() {}

}
