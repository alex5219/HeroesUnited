package com.heroes.united.common.items;

import com.heroes.united.Main;
import com.heroes.united.common.suits.Hero;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModItemRegistry {
    @GameRegistry.ObjectHolder("heroesunited:test_item")
    public static final Item TEST_ITEM = Items.STICK;
    private static final Set<Item> REGISTERED_ITEMS = new LinkedHashSet<>();

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        ModItemRegistry.register(event, new ResourceLocation(Main.MODID, "test_item"), new ItemTest());
    }

    private static void register(RegistryEvent.Register<Item> event, ResourceLocation identifier, Item item) {
        event.getRegistry().register(item.setRegistryName(identifier));
        REGISTERED_ITEMS.add(item);
    }

    public static Set<Item> getRegisteredItems() {
        return Collections.unmodifiableSet(REGISTERED_ITEMS);
    }

    /**
     * Subscribe event cannot have multiple methods... I don't know a good way of doing this.
     *
     * @SubscribeEvent
    public static void registerArmor(RegistryEvent.Register<Item> event, ItemHero item, Hero hero) {
        if (!Hero.REGISTRY.containsValue(hero) || !Hero.REGISTRY.containsKey(Hero.getNameForHero(hero))) {
            throw new RuntimeException(String.format("Hero %s is not registered!", hero));
        }
        String unlocalizedName = hero.getRegistryName().getResourcePath();

        if (!unlocalizedName.endsWith("s")) {
            unlocalizedName += "s";
        }
        unlocalizedName += "_" + item.armorType.name().toLowerCase(Locale.ROOT);
        register(event, new ResourceLocation(Main.MODID, unlocalizedName), item);
    }*/
}