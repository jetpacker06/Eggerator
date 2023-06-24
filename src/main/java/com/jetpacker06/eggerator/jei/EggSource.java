package com.jetpacker06.eggerator.jei;

import com.jetpacker06.eggerator.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum EggSource {
    CHICKEN(Items.CHICKEN_SPAWN_EGG),
    EGGERATOR(ModRegistry.EGGERATOR_BLOCK_ITEM.get());

    EggSource(Item catalystItem) {
        this.catalystItem = catalystItem;
    }
    private final Item catalystItem;

    public ItemStack getCatalystItem() {
        return new ItemStack(catalystItem);
    }
}
