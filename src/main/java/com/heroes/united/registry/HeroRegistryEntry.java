package com.heroes.united.registry;

import com.google.common.reflect.TypeToken;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class HeroRegistryEntry<T> implements IForgeRegistryEntry {
    public final HeroDelegate<T> delegate = new HeroDelegate<T>(this, getClass());
    private TypeToken<T> token = new TypeToken<T>(getClass()) {
    };

    private ResourceLocation registryName = null;

    public final T setRegistryName(ResourceLocation name) {
        return setRegistryName(new ResourceLocation(name.toString()));
    }

    public final T setRegistryName(String domain, String name) {
        return setRegistryName(new ResourceLocation(domain, name));
    }

    public final ResourceLocation getRegistryName() {
        return registryName;
    }

    public final String getDomain() {
        return registryName.getResourceDomain();
    }

    public final Class<T> getRegistryType() {
        return (Class<T>) token.getRawType();
    }

    public final String toString() {
        return delegate.name().toString();
    }
}
