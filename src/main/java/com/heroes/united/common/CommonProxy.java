package com.heroes.united.common;

import com.heroes.united.Main;
import com.heroes.united.client.gui.ModGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void onPreInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.INSTANCE, new ModGuiHandler());
    }

    public void onInit() {

    }

    public void onPostInit() {

    }
}
