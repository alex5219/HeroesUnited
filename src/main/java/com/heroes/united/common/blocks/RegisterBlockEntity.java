package com.heroes.united.common.blocks;

import net.minecraft.tileentity.TileEntity;

public interface RegisterBlockEntity {
    Class<? extends TileEntity> getEntityClass();
}