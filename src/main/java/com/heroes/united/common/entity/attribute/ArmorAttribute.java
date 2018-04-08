package com.heroes.united.common.entity.attribute;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static scala.xml.Null.next;

public class ArmorAttribute extends BaseAttribute {
    private final Map<UUID, Map<AttributePair, UUID>> globalUUIDs;
    private final boolean isAdditive;
    private String unlocalizedName;

    public ArmorAttribute(String unlocalizedName, double defaultValue, boolean additive) {
        super(null, "armor." + unlocalizedName, defaultValue);
        this.globalUUIDs = Maps.newHashMap();
        this.isAdditive = additive;
        this.unlocalizedName = unlocalizedName;
    }

    public ArmorAttribute(String unlocalizedName, boolean additive) {
        this(unlocalizedName, 0.0D, additive);
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public UUID getUUID() {
        Map.Entry<AttributePair, UUID> e = (Map.Entry) next();
        UUID uuid = e.getValue();
        return uuid;
    }

    public void clean(EntityPlayer player, IAttributeInstance instance, List<UUID> validated) {
        Map<AttributePair, UUID> map = this.getGlobalUUIDs(player);
        Map<AttributePair, UUID> map1 = Maps.newHashMap(map);
        ImmutableList<Map.Entry<AttributePair, UUID>> list = ImmutableList.copyOf(map.entrySet());
        UnmodifiableIterator var7 = list.iterator();

        while (var7.hasNext()) {
            Map.Entry<AttributePair, UUID> e = (Map.Entry) var7.next();
            UUID uuid = e.getValue();
            if (!validated.contains(uuid) && instance.getModifier(uuid) != null) {
                this.reset(player, instance, uuid);
                map1.remove(e.getKey());
            }
        }

        if (map1.size() < map.size()) {
            map.clear();
            map.putAll(map1);
        }

    }

    public void reset(EntityPlayer player, IAttributeInstance instance, UUID uuid) {
        HeroAttributes.removeModifier(instance, uuid);
    }

    public UUID createUUID(EntityPlayer player, AttributePair pair) {
        Map<AttributePair, UUID> map = this.getGlobalUUIDs(player);
        if (map.containsKey(pair)) {
            return map.get(pair);
        } else {
            UUID uuid = UUID.randomUUID();
            map.put(pair, uuid);
            return uuid;
        }
    }

    public UUID createUUID(EntityPlayer player, double amount, int operation) {
        return this.createUUID(player, new AttributePair(amount, operation));
    }

    private Map<AttributePair, UUID> getGlobalUUIDs(EntityPlayer player) {
        UUID uuid = player.getUniqueID();
        if (this.globalUUIDs.containsKey(uuid)) {
            return this.globalUUIDs.get(uuid);
        } else {
            Map<AttributePair, UUID> map = Maps.newHashMap();
            this.globalUUIDs.put(uuid, map);
            return map;
        }
    }

    public boolean isAdditive() {
        return this.isAdditive;
    }

    public double get(EntityLivingBase entity, double baseValue) {
        return HeroAttributes.getModifier(entity, this, baseValue);
    }

    public float get(EntityLivingBase entity, float baseValue) {
        return HeroAttributes.getModifier(entity, this, baseValue);
    }

    public double get(ItemStack[] itemstacks, double baseValue) {
        return HeroAttributes.getModifier(itemstacks, this, baseValue);
    }

    public float get(ItemStack[] itemstacks, float baseValue) {
        return HeroAttributes.getModifier(itemstacks, this, baseValue);
    }

    public double clampValue(double value) {
        return value;
    }
}
