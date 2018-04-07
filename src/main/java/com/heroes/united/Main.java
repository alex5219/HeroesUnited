package com.heroes.united;

import com.heroes.united.common.CommonProxy;
import com.heroes.united.common.items.ModItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MODID, name = "Heroes United", version = Main.VERSION, acceptedMinecraftVersions = "[1.12]")
public class Main {
    public static final String MODID = "heroesunited";
    public static final String VERSION = "1.0.0-INDEV";
    public static final Logger LOGGER = LogManager.getLogger("heroesunited");

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs("suits") {
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItemRegistry.TEST_ITEM);
        }
    };

    @Mod.Instance(Main.MODID)
    public static Main INSTANCE;

    @SidedProxy(clientSide = "com.suits.united.client.ClientProxy", serverSide = "com.suits.united.common.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        PROXY.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit();
    }
}