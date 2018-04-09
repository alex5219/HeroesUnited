package com.heroes.united.common.suits;

import com.heroes.united.common.items.ItemHero;

public class HeroTest extends Hero{
    public void init() {
        helmet = new ItemHero(this, ItemHero.ArmorType.HELMET);
        chestplate = new ItemHero(this, ItemHero.ArmorType.CHESTPIECE);
        leggings = new ItemHero(this, ItemHero.ArmorType.PANTS);
        boots = new ItemHero(this, ItemHero.ArmorType.BOOTS);
    }

    public int getTier() {
        return 4;
    }

    public ItemHero.ArmorVersion getVersion() {
        return ItemHero.ArmorVersion.NONE;
    }
}
