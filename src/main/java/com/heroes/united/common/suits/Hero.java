package com.heroes.united.common.suits;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.heroes.united.common.abilities.Ability;
import com.heroes.united.common.abilities.IAbility;
import com.heroes.united.common.abilities.IAbilityContainer;
import com.heroes.united.common.entity.attribute.HeroAttributes;
import com.heroes.united.common.entity.attribute.IAttributeContainer;
import com.heroes.united.common.items.ItemHero;
import com.heroes.united.common.items.Tiers;
import com.heroes.united.common.weakness.Weakness;
import com.heroes.united.helper.ModHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public abstract class Hero implements Comparable<Hero>, Predicate<Entity>, IAbility {

    private final ImmutableList<Ability> abilities;
    private final ImmutableList<Weakness> weaknesses;
    protected Item helmet;
    protected Item chestplate;
    protected Item leggings;
    protected Item boots;
    private String name;
    private Hero hero;

    public Hero(String name, Hero hero) {
        this.name = name;
        this.hero = hero;
        final ImmutableList.Builder abilityBuilder = ImmutableList.builder();
        final ImmutableList.Builder weaknessBuilder = ImmutableList.builder();
        this.getAbilities(new IAbilityContainer() {
            public <T extends IAbility> void add(T hero, Ability<T> ability) {
                if (hero != null) {
                    abilityBuilder.add(ability);
                }
            }

            public void add(Hero hero, Weakness weakness) {
            }
        });

        this.abilities = abilityBuilder.build();
        this.getAbilities(new IAbilityContainer() {
            public <T extends IAbility> void add(T hero, Ability<T> ability) {
            }

            public void add(Hero hero, Weakness weakness) {
                if (hero != null && hero.getAbilities().containsAll(weakness.getRequirements())) {
                    weaknessBuilder.add(weakness);
                }

            }
        });
        this.weaknesses = weaknessBuilder.build();
    }

    public abstract void init();

    public abstract int getTier();

    public abstract ItemHero.ArmorVersion getVersion();

    public final Item getHelmet() {
        return this.helmet;
    }

    public final Item getChestplate() {
        return this.chestplate;
    }

    public final Item getLeggings() {
        return this.leggings;
    }

    public final Item getBoots() {
        return this.boots;
    }

    public final Item[] getArmor() {
        return new Item[]{this.helmet, this.chestplate, this.leggings, this.boots};
    }

    public final ItemStack[] getArmorStacks() {
        Item[] armor = this.getArmor();
        ItemStack[] itemstacks = new ItemStack[armor.length];

        for (int i = 0; i < armor.length; ++i) {
            if (armor[i] != null) {
                itemstacks[i] = new ItemStack(armor[i]);
            }
        }

        return itemstacks;
    }

    public String getHeroName() {
        return this.name;
    }

    public Hero getHero() {
        return this.hero;
    }

    public String getUnlocalizedName() {
        return "hero." + this.getHeroName();
    }

    public String getLocalizedName() {
        return I18n.translateToLocal(this.getUnlocalizedName() + ".name").trim().replace("\\u00f1", "ñ");
    }

    public void onUpdate(EntityPlayer player, TickEvent.Phase phase) {
    }

    public void getAbilities(IAbilityContainer abilities) {
    }

    public final ImmutableList<Ability> getAbilities() {
        return this.abilities;
    }

    public final ImmutableList<Weakness> getWeaknesses() {
        return this.weaknesses;
    }

    public final boolean hasAbility(Ability ability) {
        return this.getAbilities().contains(ability);
    }

    public final boolean hasWeakness(Weakness weakness) {
        return this.getWeaknesses().contains(weakness);
    }

    public void getAttributeModifiers(IAttributeContainer attributes, ItemStack itemstack) {
        attributes.add(HeroAttributes.DAMAGE_REDUCTION, Tiers.getProtection(this.getTier()), 1);
    }

    public int getDurability(EntityEquipmentSlot armorPiece) {
        return 1024 + (this.getTier() - 1) * 256;
    }

    public final int getPiecesToSet() {
        int pieces = 0;
        Item[] var2 = this.getArmor();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Item item = var2[var4];
            if (item != null) {
                ++pieces;
            }
        }

        return pieces;
    }

    public final String[] getHeaderText() {
        String s = this.getLocalizedName();
        String[] astring = new String[2];
        if (s != null && s.contains("/")) {
            astring[0] = s.substring(0, s.indexOf("/"));
            astring[1] = s.substring(s.indexOf("/") + 1);
        } else {
            astring[0] = s;
        }

        return astring;
    }

    public int compareTo(Hero o) {
        int i = this.getLocalizedName().compareTo(o.getLocalizedName());
        return i == 0 ? Double.compare((double) this.getVersion().ordinal(), (double) o.getVersion().ordinal()) : i;
    }

    public boolean apply(Entity input) {
        return input instanceof EntityLivingBase && ModHelper.getHero((EntityLivingBase) input) == this;
    }
}
