package com.heroes.united.helper;

import com.heroes.united.common.ServerUtils;
import com.heroes.united.common.abilities.Ability;
import com.heroes.united.common.items.ItemHero;
import com.heroes.united.common.suits.Hero;
import com.heroes.united.common.weakness.Weakness;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModHelper {

    public ModHelper() {
    }

    public static ItemStack[] getEquipment(EntityLivingBase entity) {
        return new ItemStack[]{entity.getItemStackFromSlot(EntityEquipmentSlot.FEET), entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS), entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST), entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD)};
    }

    public static boolean isHero(EntityLivingBase entity) {
        return isHero(getEquipment(entity));
    }

    public static boolean isHero(ItemStack[] itemstacks) {
        Hero hero = getHeroFromArmor(ServerUtils.nonNull(itemstacks));
        if (hero != null) {
            ItemStack[] suit = hero.getArmorStacks();

            for (int i = 0; i < suit.length; ++i) {
                if (getHeroFromArmor(suit[i]) != getHeroFromArmor(itemstacks[i])) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static Hero getHero(EntityLivingBase entity) {
        return entity != null && isHero(entity) ? getHeroFromArmor(ServerUtils.nonNull(getEquipment(entity))) : null;
    }

    public static Hero getHero(ItemStack[] itemstacks) {
        return isHero(itemstacks) ? getHeroFromArmor(ServerUtils.nonNull(itemstacks)) : null;
    }

    public static Hero getHeroFromArmor(ItemStack itemstack) {
        if (itemstack != null) {
            Item item = itemstack.getItem();
            if (item instanceof ItemHero) {
                return ((ItemHero) item).getHero(itemstack);
            }
        }

        return null;
    }

    public static boolean hasAbility(EntityLivingBase entity, Ability ability) {
        Hero hero = getHero(entity);
        return hero != null && hero.hasAbility(ability);
    }

    public static boolean hasWeakness(EntityLivingBase entity, Weakness weakness) {
        Hero hero = getHero(entity);
        return hero != null && hero.hasWeakness(weakness);
    }
}