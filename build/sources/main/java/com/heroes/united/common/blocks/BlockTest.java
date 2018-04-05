package com.heroes.united.common.blocks;

import com.heroes.united.Main;
import net.minecraft.block.material.Material;

public class BlockTest extends ModBlock {

    public BlockTest() {
        super(Material.ROCK, "block_test");
        setHardness(3f);
        setResistance(5f);
        this.setUnlocalizedName(Main.MODID + ":block_test");
    }
}