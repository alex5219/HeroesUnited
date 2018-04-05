package com.heroes.united.common.items;

import net.minecraft.util.ResourceLocation;

public interface RegisterItemModel {
    default String getResource(ResourceLocation registryName) {
        return registryName.getResourcePath();
    }
}