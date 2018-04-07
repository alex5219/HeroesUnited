package com.heroes.united.common.items;

import com.heroes.united.Main;
import net.minecraft.item.Item;

public class ItemTest extends Item implements RegisterItemModel {
    public ItemTest() {
        super();
        this.setUnlocalizedName(Main.MODID + ":test_item");
        this.setCreativeTab(Main.CREATIVE_TAB);
    }
}