package com.heroes.united.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.heroes.united.common.abilities.Ability;
import com.heroes.united.common.entity.attribute.ArmorAttribute;
import com.heroes.united.common.entity.attribute.IAttributeContainer;
import com.heroes.united.common.suits.Hero;
import com.heroes.united.common.weakness.Weakness;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ItemHero extends ItemArmor implements ISpecialArmor, RegisterItemModel {
    private static final ArmorMaterial MATERIAL_ARMOR = EnumHelper.addArmorMaterial("Superhero", "Superhero", 128, new int[]{2, 4, 3, 2}, 30, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 2.5F);
    private static boolean hideStats = false;
    private final Hero heroType;

    public ItemHero(Hero hero, ArmorType type) {
        super(MATERIAL_ARMOR, 4, type.armorPiece);
        this.heroType = hero;
        this.setMaxDamage(this.heroType.getDurability(type.armorPiece));
    }

    public Hero getHero(ItemStack itemstack) {
        return this.heroType;
    }

    public String getItemStackDisplayName(ItemStack itemstack) {
        Hero hero = this.getHero(itemstack);
        ArmorVersion version = hero.getVersion();
        String name = hero.getLocalizedName();
        String key = "item.superhero_armor.name";
        if (name.contains("/") && itemstack != null) {
            name = name.substring(0, name.indexOf("/"));
        }

        List<String> args = Lists.newArrayList(name, I18n.translateToLocal(String.format("item.superhero_armor.piece.%s", this.armorType.name().toLowerCase(Locale.ROOT))));
        if (!version.isDefault()) {
            key = key + ".version";
            args.add(I18n.translateToLocal(String.format("item.superhero_armor.version.%s", version.name().toLowerCase(Locale.ROOT))));
        }

        if (name.endsWith("s")) {
            key = key + ".alt";
        }

        return I18n.translateToLocalFormatted(key, args.toArray()).trim();
    }

    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return new ArmorProperties(0, 0.009999999776482582D, armor.getMaxDamage() + 1 - armor.getItemDamage());
    }

    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 0;
    }

    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        stack.damageItem((int) Math.max((float) damage / 4.0F, 1.0F), entity);
    }

    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean b) {
        Hero hero = this.getHero(itemstack);
        String s = hero.getLocalizedName();
        if (s.contains("/")) {
            String name = s.substring(s.indexOf("/") + 1);
            list.add(ChatFormatting.WHITE + name);
        }

        List<Ability> abilities = hero.getAbilities();
        List<Weakness> weaknesses = hero.getWeaknesses();
        list.add(I18n.translateToLocalFormatted("tooltip.tier", hero.getTier()));

        if (!hideStats) {
            if (abilities.size() > 0 || weaknesses.size() > 0 || !this.getAttributeModifiers(armorType, itemstack).isEmpty()) {
                list.add(I18n.translateToLocal("tooltip.fullSet"));
            }

            if (abilities.size() > 0 || weaknesses.size() > 0) {
                list.add("");
            }

            Iterator var9 = abilities.iterator();

            while (var9.hasNext()) {
                Ability ability = (Ability) var9.next();
                list.add(ChatFormatting.GREEN + "* " + ability.getLocalizedName());
            }

            var9 = weaknesses.iterator();

            while (var9.hasNext()) {
                Weakness weakness = (Weakness) var9.next();
                list.add(ChatFormatting.RED + "* " + weakness.getLocalizedName());
            }

            if (itemstack.isItemEnchanted()) {
                list.add("");
            }
        } else {
            list.add(ChatFormatting.DARK_GRAY + ChatFormatting.ITALIC.toString() + I18n.translateToLocal("tooltip.moreInfo"));
        }
    }

    public final Multimap getAttributeModifiers(ItemStack itemstack, EntityEquipmentSlot slot) {
        if (hideStats) {
            return HashMultimap.create();
        } else {
            final Multimap multimap = super.getAttributeModifiers(slot, itemstack);
            this.getHero(itemstack).getAttributeModifiers(new IAttributeContainer() {
                public void add(ArmorAttribute attribute, double amount, int operation) {
                    multimap.put(attribute.getUnlocalizedName(), new AttributeModifier(attribute.getUUID(), attribute.getUnlocalizedName(), amount, operation));
                }
            }, itemstack);
            return multimap;
        }
    }

    public enum ArmorOrientation {
        /**
         * Any better names then Orientation? Sounds off...
         */
        Hero,
        Villain,
        AntiHero;

        ArmorOrientation() {
        }

        public boolean isDefault() {
            return this == Hero;
        }
    }

    public enum ArmorVersion {
        NONE,
        MARVEL,
        DC;

        ArmorVersion() {
        }

        public boolean isDefault() {
            return this == NONE;
        }
    }

    public enum ArmorType {
        MASK(EntityEquipmentSlot.HEAD),
        HOOD(EntityEquipmentSlot.HEAD),
        COWL(EntityEquipmentSlot.HEAD),
        HELMET(EntityEquipmentSlot.HEAD),
        GOGGLES(EntityEquipmentSlot.HEAD),
        HEAD(EntityEquipmentSlot.HEAD),
        HAIR(EntityEquipmentSlot.HEAD),
        HAT(EntityEquipmentSlot.HEAD),
        CHESTPIECE(EntityEquipmentSlot.CHEST),
        CHESTPLATE(EntityEquipmentSlot.CHEST),
        JACKET(EntityEquipmentSlot.CHEST),
        TORSO(EntityEquipmentSlot.CHEST),
        ROBES(EntityEquipmentSlot.CHEST),
        TRENCHCOAT(EntityEquipmentSlot.CHEST),
        PANTS(EntityEquipmentSlot.LEGS),
        LEGGINGS(EntityEquipmentSlot.LEGS),
        LEGS(EntityEquipmentSlot.LEGS),
        BOOTS(EntityEquipmentSlot.FEET),
        SHOES(EntityEquipmentSlot.FEET);

        public final EntityEquipmentSlot armorPiece;

        ArmorType(EntityEquipmentSlot slot) {
            this.armorPiece = slot;
        }
    }
}

