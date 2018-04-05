package com.heroes.united.common.blocks;

import com.heroes.united.Main;
import com.heroes.united.common.items.RegisterItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class ModBlock extends Block implements RegisterItemBlock {
    protected String name;

    public ModBlock(Material material, String name) {
        super(material);
        this.name = name;
        this.setUnlocalizedName(name);
        this.setCreativeTab(Main.CREATIVE_TAB);
    }

    public ItemBlock createItemBlock() {
        return new ItemBlock(this);
    }
}