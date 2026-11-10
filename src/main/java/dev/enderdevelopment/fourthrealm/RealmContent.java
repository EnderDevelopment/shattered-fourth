package dev.enderdevelopment.fourthrealm;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class RealmContent {
    public static final ToolMaterial STARFORGED = ToolMaterials.NETHERITE;
    public static final Item OATH_SIGIL = register("oath_sigil", new Item(new Item.Settings().maxCount(1)));
    public static final Item STARFORGED_BLADE = register("starforged_blade", new SwordItem(STARFORGED, 8, -2.2f, new Item.Settings().fireproof()));
    public static final Item STARFORGED_HELMET = register("starforged_helmet", new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.HELMET, new Item.Settings().fireproof()));
    public static final Item STARFORGED_CHESTPLATE = register("starforged_chestplate", new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof()));
    public static final Item STARFORGED_LEGGINGS = register("starforged_leggings", new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof()));
    public static final Item STARFORGED_BOOTS = register("starforged_boots", new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.BOOTS, new Item.Settings().fireproof()));

    private RealmContent() {}

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(FourthRealm.MOD_ID, name), item);
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(STARFORGED_BLADE);
            entries.add(STARFORGED_HELMET);
            entries.add(STARFORGED_CHESTPLATE);
            entries.add(STARFORGED_LEGGINGS);
            entries.add(STARFORGED_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(OATH_SIGIL));
    }
}
