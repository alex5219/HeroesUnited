package com.heroes.united.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.Objects;

public class HeroDelegate<T> implements IRegistryDelegate<T>
{
    private T referant;
    private String name;
    private final Class<T> type;

    public HeroDelegate(T referant, Class<T> type)
    {
        this.referant = referant;
        this.type = type;
    }

    public T get()
    {
        return referant;
    }

    public ResourceLocation name()
    {
        return new ResourceLocation(name);
    }

    public Class<T> type()
    {
        return type;
    }

    void changeReference(T newTarget)
    {
        this.referant = newTarget;
    }

    void setName(String name)
    {
        this.name = name;
    }


    @Override
    public int hashCode()
    {
        return Objects.hashCode(name);
    }
}
