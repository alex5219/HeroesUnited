package com.heroes.united.registry;

import com.heroes.united.common.suits.Hero;
import com.heroes.united.common.suits.HeroTest;

import java.lang.reflect.Field;
import java.util.Locale;

public class HeroRegistry {
    public static final Hero TEST = new HeroTest();

    public static void register() {
        for (Field field : HeroRegistry.class.getFields()) {
            if (field.getType() == Hero.class) {
                try {
                    Hero.register(field.getName().toLowerCase(Locale.ROOT), (Hero) field.get(null));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
