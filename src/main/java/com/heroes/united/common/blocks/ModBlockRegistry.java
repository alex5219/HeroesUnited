package com.heroes.united.common.blocks;

import com.heroes.united.Main;
import com.heroes.united.common.items.RegisterItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModBlockRegistry {
    private static final Set<Block> REGISTERED_BLOCKS = new LinkedHashSet<>();
    private static final Set<ItemBlock> REGISTERED_ITEM_BLOCKS = new LinkedHashSet<>();

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        ModBlockRegistry.register(event, new ResourceLocation(Main.MODID, "test_block"), new BlockTest());
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        for (Block block : REGISTERED_BLOCKS) {
            if (block instanceof RegisterItemBlock) {
                if (block.getRegistryName() == null) {
                    Main.LOGGER.warn("Tried to register ItemBlock without a registry name.");
                    continue;
                }
                ItemBlock itemBlock = ((RegisterItemBlock) block).createItemBlock();
                event.getRegistry().register(itemBlock.setRegistryName(block.getRegistryName()));
            }
        }
    }

    private static void register(RegistryEvent.Register<Block> event, ResourceLocation identifier, Block block) {
        event.getRegistry().register(block.setRegistryName(identifier));
        REGISTERED_BLOCKS.add(block);

        if (block instanceof RegisterBlockEntity) {
            String blockEntityKey = identifier.getResourcePath() + ":" + identifier.getResourceDomain();
            GameRegistry.registerTileEntity(((RegisterBlockEntity) block).getEntityClass(), blockEntityKey);
        }
    }

    public static Set<Block> getRegisteredBlocks() {
        return Collections.unmodifiableSet(REGISTERED_BLOCKS);
    }

    public static Set<ItemBlock> getRegisteredItemBlocks() {
        return Collections.unmodifiableSet(REGISTERED_ITEM_BLOCKS);
    }
}