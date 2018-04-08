package com.heroes.united.registry;

import net.minecraft.util.ObjectIntIdentityMap;

import java.util.Iterator;

public class HeroRegistryNamespaced<T extends HeroRegistryEntry<T>> extends HeroSimpleRegistry<T> {
    protected ObjectIntIdentityMap underlyingIntegerMap = new ObjectIntIdentityMap();
    private int nextId = -1;

    public HeroRegistryNamespaced(String domain, String key) {
        super(domain, key);
    }

    public void putObject(String key, T value) {
        addObject(++nextId, key, value);
    }

    public void addObject(int id, String key, T value) {
        underlyingIntegerMap.put(value, id);
        super.putObject(key, value);
    }

    public int getIDForObject(T value) {
        return underlyingIntegerMap.get(value);
    }

    public T getObjectById(int id) {
        return (T) underlyingIntegerMap.getByValue(id);
    }

    public boolean containsId(int id) {
        return underlyingIntegerMap.getByValue(id) != null;
    }

    public Iterator iterator() {
        return underlyingIntegerMap.iterator();
    }

    public T lookup(String key) {
        if (containsKey(key)) {
            return getObject(key);
        }

        try {
            int id = Integer.parseInt(key);

            if (containsId(id)) {
                return getObjectById(id);
            }
        } catch (NumberFormatException e) {
        }

        return null;
    }
}
