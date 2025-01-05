package com.everest.init;

import com.everest.Valantino;
import com.everest.item.Blighthon;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ValantinoItems {
    public static final Item BLIGHTHON = registerItem("blighthon", new Blighthon(new FabricItemSettings().rarity(Rarity.UNCOMMON)));

    public static final Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Valantino.MOD_ID, name), item);
    }

    public static void register() {}
}
