package fr.kayrouge.brave.items;

import fr.kayrouge.brave.BRAVE;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.impl.biome.modification.BuiltInResourceKeys;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class BItems {

    public static final Item ICON = register("icon", new Item(new Item.Properties().maxCount(1).fireproof().rarity(Rarity.EPIC).jukeboxPlayable(JukeboxSongs.PIGSTEP)), BItemGroups.OTHER);
    public static final Item ICON_AGENT = register("icon_agent", new Item(settings.maxCount(1).fireproof().rarity(Rarity.EPIC).jukeboxPlayable(JukeboxSongs.LAVA_CHICKEN)), BItemGroups.OTHER);
    public static final Item RADIANITE = register("radianite", Item(settings.maxCount(24)), ItemGroups.INGREDIENTS);


    public static Item register(String name, Item item) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BRAVE.MOD_ID, name));
        Registry.register(BuiltInRegistries.ITEM, key, item);
        return item;
    }

    private static Item register(String name, Item item, ResourceKey<CreativeModeTab> group) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BRAVE.MOD_ID, name));
        Registry.register(BuiltInRegistries.ITEM, key, item);
        registerInItemGroup(item, group);
        return item;
    }

    private static void registerInItemGroup(Item item, ResourceKey<CreativeModeTab> group) {
        CreativeModeTabEvents.modifyOutputEvent(group).register(entries -> entries.accept(item));
    }

    public static void init() {}

}
