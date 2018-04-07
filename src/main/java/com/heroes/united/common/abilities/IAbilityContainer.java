package com.heroes.united.common.abilities;

import com.heroes.united.common.suits.Hero;
import com.heroes.united.common.weakness.Weakness;

public interface IAbilityContainer { <T extends IAbility>
    void add(T var1, Ability<T> var2);
    void add(Hero var1, Weakness var2);
}
