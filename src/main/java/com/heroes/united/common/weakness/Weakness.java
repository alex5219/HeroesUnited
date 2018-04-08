package com.heroes.united.common.weakness;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.heroes.united.common.abilities.Ability;
import com.heroes.united.common.suits.Hero;
import com.heroes.united.helper.ModHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;

public class Weakness implements Comparable<Weakness>, Predicate<EntityLivingBase> {
    private final ImmutableList<Object> requirements;
    public String name;
    public Hero hero;

    public Weakness(String name, Hero hero, Ability... abilities) {
        this.requirements = ImmutableList.builder().addAll(Arrays.asList(abilities)).build();
        this.name = name;
        this.hero = hero;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Object> getRequirements() {
        return this.requirements;
    }

    public String getUnlocalizedName() {
        return "weakness." + this.name.replace(':', '.');
    }

    public String getLocalizedName() {
        return I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    public void onUpdate(EntityLivingBase entity, Hero hero, TickEvent.Phase phase, boolean hasWeakness) {
    }

    public float damageTaken(EntityLivingBase entity, EntityLivingBase attacker, Hero hero, DamageSource source, float amount, float originalAmount) {
        return amount;
    }

    public float damageReduction(EntityLivingBase entity, Hero hero, DamageSource source, float reduction) {
        return reduction;
    }

    public int compareTo(Weakness o) {
        return this.getLocalizedName().compareTo(o.getLocalizedName());
    }

    public boolean apply(EntityLivingBase input) {
        return ModHelper.hasWeakness(input, this);
    }

}
