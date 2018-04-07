package com.heroes.united.common.entity.attribute;

import com.google.common.collect.Lists;
import com.heroes.united.common.ServerUtils;
import com.heroes.united.common.suits.Hero;
import com.heroes.united.helper.ModHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class HeroAttributes {
    private static final UUID UUID_HEALTH_MOD = UUID.randomUUID();
    public static final ArmorAttribute SWORD_DAMAGE = new ArmorAttribute("swordDamage", true);
    public static final ArmorAttribute PUNCH_DAMAGE = new ArmorAttribute("punchDamage", true);
    public static final ArmorAttribute BOW_DRAWBACK = new ArmorAttribute("bowDrawback", false);
    public static final ArmorAttribute ARROW_DAMAGE = new ArmorAttribute("arrowDamage", true);
    public static final ArmorAttribute ATTACK_DAMAGE = new ArmorAttribute("attackDamage", true);
    public static final ArmorAttribute FALL_RESISTANCE = new ArmorAttribute("fallResistance", false);
    public static final ArmorAttribute JUMP_HEIGHT = new ArmorAttribute("jumpHeight", true);
    public static final ArmorAttribute SPRINT_SPEED = new ArmorAttribute("sprintSpeed", true);
    public static final ArmorAttribute DAMAGE_REDUCTION = new ArmorAttribute("damageReduction", true);

    public HeroAttributes() {
    }

    public static AttributeWrapper getAttribute(EntityLivingBase entity, ArmorAttribute attribute) {
        return getAttribute(ModHelper.getEquipment(entity), attribute);
    }

    public static AttributeWrapper getAttribute(ItemStack[] itemstacks, final ArmorAttribute attribute) {
        if (ModHelper.isHero(itemstacks)) {
            Hero hero = ModHelper.getHero(itemstacks);
            ItemStack itemstack = ServerUtils.nonNull(itemstacks);
            if (itemstack != null) {
                final AttributeWrapper wrapper = new AttributeWrapper(attribute);
                hero.getAttributeModifiers(new IAttributeContainer() {
                    public void add(ArmorAttribute attr, double amount, int operation) {
                        if (attr == attribute) {
                            wrapper.apply(amount, operation);
                        }

                    }
                }, itemstack);
                return wrapper.isEmpty() ? null : wrapper;
            }
        }

        return null;
    }

    public static float getArmorProtection(EntityLivingBase entity) {
        return getArmorProtection(ModHelper.getEquipment(entity));
    }

    public static float getArmorProtection(ItemStack[] itemstacks) {
        AttributeWrapper wrapper = getAttribute(itemstacks, DAMAGE_REDUCTION);
        if (wrapper == null) {
            return 0.0F;
        } else {
            return wrapper.getValue(1.0F) - 1.0F;
        }
    }

    public static double getModifier(EntityLivingBase entity, ArmorAttribute attribute, double baseValue) {
        return getModifier(ModHelper.getEquipment(entity), attribute, baseValue);
    }

    public static double getModifier(ItemStack[] itemstacks, ArmorAttribute attribute, double baseValue) {
        AttributeWrapper wrapper = getAttribute(itemstacks, attribute);
        if (wrapper != null) {
            double result = wrapper.getValue(baseValue);
            return attribute.isAdditive() ? result : baseValue * 2.0D - result;
        } else {
            return baseValue;
        }
    }

    public static float getModifier(EntityLivingBase entity, ArmorAttribute attribute, float baseValue) {
        return getModifier(ModHelper.getEquipment(entity), attribute, baseValue);
    }

    public static float getModifier(ItemStack[] itemstacks, ArmorAttribute attribute, float baseValue) {
        return (float) getModifier(itemstacks, attribute, (double) baseValue);
    }

    public static void applyModifiers(EntityPlayer player) {
        IAttributeInstance speedAttribute = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        IAttributeInstance healthAttribute = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        IAttributeInstance[] instances = new IAttributeInstance[]{speedAttribute, speedAttribute};

        ArmorAttribute[] attributes = new ArmorAttribute[]{SPRINT_SPEED};
        boolean[] applicable = new boolean[]{true, player.isSprinting(), true};

        for (int i = 0; i < attributes.length; ++i) {
            ArmorAttribute attribute = attributes[i];
            AttributeWrapper wrapper = getAttribute(player, attribute);
            List<UUID> list = Lists.newArrayList();
            if (wrapper != null) {
                List<AttributePair> modifiers = wrapper.getModifiers();
                Iterator var14 = modifiers.iterator();

                while (var14.hasNext()) {
                    AttributePair mod = (AttributePair) var14.next();
                    UUID uuid = attribute.createUUID(player, mod);
                    if (applicable[i]) {
                        applyModifier(instances[i], uuid, mod.amount, mod.operation);
                        list.add(uuid);
                    }
                }
            }

            attribute.clean(player, instances[i], list);
        }
    }

    public static boolean applyModifier(IAttributeInstance instance, UUID uuid, double amount, int operation) {
        AttributeModifier modifier = instance.getModifier(uuid);
        if (modifier == null) {
            instance.applyModifier((new AttributeModifier(uuid, uuid.toString(), amount, operation)).setSaved(false));
            return true;
        } else {
            return ((modifier.getAmount() != amount || modifier.getOperation() != operation) && removeModifier(instance, uuid)) && applyModifier(instance, uuid, amount, operation);
        }
    }

    public static boolean removeModifier(IAttributeInstance instance, UUID uuid) {
        AttributeModifier modifier = instance.getModifier(uuid);
        if (modifier != null) {
            instance.removeModifier(modifier);
            return true;
        } else {
            return false;
        }
    }
}
