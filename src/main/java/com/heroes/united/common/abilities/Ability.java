package com.heroes.united.common.abilities;

import com.google.common.base.Predicate;
import com.google.common.collect.UnmodifiableIterator;
import com.heroes.united.common.suits.Hero;
import com.heroes.united.helper.ModHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Ability<T extends IAbility> implements Comparable<Ability>, Predicate<Entity> {
    public String name;
    public Hero hero;

    public Ability(String name, Hero hero){
        this.name = name;
        this.hero = hero;
    }

    public String getUnlocalizedName() {
        return "ability." + this.getHeroName();
    }

    public String getHeroName(){
        return this.name;
    }

    public String getLocalizedName() {
        return I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }


    public void onUpdate(EntityLivingBase entity, Hero hero, T instance, TickEvent.Phase phase) {
    }

    public static void updateAbilities(EntityLivingBase entity, Hero hero, TickEvent.Phase phase) {
        if (hero != null) {
            UnmodifiableIterator var3 = hero.getAbilities().iterator();

            while(var3.hasNext()) {
                Ability ability = (Ability)var3.next();
                ability.onUpdate(entity, hero, hero, phase);
            }
        }

    }

    public int compareTo(Ability o) {
        return this.getLocalizedName().compareTo(o.getLocalizedName());
    }

    public boolean apply(Entity input) {
        return input instanceof EntityLivingBase && ModHelper.hasAbility((EntityLivingBase)input, this);
    }
}
