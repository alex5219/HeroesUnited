package com.heroes.united.client;

import com.heroes.united.Main;
import com.heroes.united.common.CommonProxy;
import com.heroes.united.common.blocks.ModBlockRegistry;
import com.heroes.united.common.items.ModItemRegistry;
import com.heroes.united.common.items.RegisterItemModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Main.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
        Set<Item> registeredItems = ModItemRegistry.getRegisteredItems();

        for (Item item : registeredItems) {
            if (item instanceof RegisterItemModel) {
                String path = ((RegisterItemModel) item).getResource(item.getRegistryName());
                ClientProxy.registerModel(item, path, "inventory");
            }
        }

        Set<Block> registeredBlocks = ModBlockRegistry.getRegisteredBlocks();

        for (Block block : registeredBlocks) {
            if (block instanceof RegisterItemModel) {
                String path = ((RegisterItemModel) block).getResource(block.getRegistryName());
                ClientProxy.registerModel(block, path, "inventory");
            }
        }
    }

    private static void registerModel(Item item, String path, String type) {
        ClientProxy.registerModel(item, 0, path, type);
    }

    private static void registerModel(Item item, int meta, String path, String type) {
        ModelResourceLocation resource = new ModelResourceLocation(Main.MODID + ":" + path, type);
        ModelLoader.setCustomModelResourceLocation(item, meta, resource);
    }

    private static void registerModel(Block block, int meta, String path, String type) {
        ClientProxy.registerModel(Item.getItemFromBlock(block), meta, path, type);
    }

    private static void registerModel(Block block, final String path, final String type) {
        ClientProxy.registerModel(block, 0, path, type);
    }

    @Override
    public void onPreInit() {
        super.onPreInit();
    }

    @Override
    public void onInit() {
        super.onInit();
    }

    @Override
    public void onPostInit() {
        super.onPostInit();
    }
}