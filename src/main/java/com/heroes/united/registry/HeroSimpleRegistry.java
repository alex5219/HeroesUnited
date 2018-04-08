package com.heroes.united.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

import java.util.Map;
import java.util.Set;

public class HeroSimpleRegistry<T extends HeroRegistryEntry<T>> extends RegistrySimple{
    protected final Map nameLookup;
    private final String defaultDomain;
    private final String defaultKey;
    private T defaultValue;

    public HeroSimpleRegistry(String domain, String key) {
        nameLookup = ((BiMap) registryObjects).inverse();
        defaultDomain = domain;
        defaultKey = namespace(key);
    }

    public T getDefaultValue()
    {
        return defaultValue;
    }

    protected String namespace(String key) {
        return key != null && key.indexOf(':') == -1 ? defaultDomain + ":" + key : key;
    }

    public void putObject(String key, T value) {
        key = namespace(key);

        if (defaultKey != null && key == defaultKey)
        {
            defaultValue = value;
        }

        value.setRegistryName(new ResourceLocation(key));
        super.putObject(key, value);
    }

    public void putObject(Object key, Object value)
    {
        putObject((String) key, (T) value);
    }

    protected Map createUnderlyingMap()
    {
        return HashBiMap.create();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return containsKey((String) key);
    }

    public boolean containsValue(T value)
    {
        return registryObjects.values().contains(value);
    }

    @Override
    public T getObject(Object key)
    {
        return getObject((String) key);
    }

    @Override
    public Set<String> getKeys() {
        return super.getKeys();
    }

    public T castDefault(T value) {
        if (value == null)
        {
            return getDefaultValue();
        }

        return value;
    }
}
