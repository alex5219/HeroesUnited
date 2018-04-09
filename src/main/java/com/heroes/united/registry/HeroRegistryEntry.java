package com.heroes.united.registry;

import com.google.common.reflect.TypeToken;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class HeroRegistryEntry<T extends IForgeRegistryEntry<T>> implements IForgeRegistryEntry<T> {
        private TypeToken<T> token = new TypeToken<T>(getClass()){};
        public final HeroDelegate<T> delegate = new HeroDelegate<T>((T) this, (Class<T>) token.getRawType());
        private ResourceLocation registryName = null;

        public final T setRegistryName(String name) {
            if (getRegistryName() != null)
                throw new IllegalStateException("Attempted to set registry name with existing registry name! New: " + name + " Old: " + getRegistryName());

            int index = name.lastIndexOf(':');
            String oldPrefix = index == -1 ? "" : name.substring(0, index).toLowerCase();
            name = index == -1 ? name : name.substring(index + 1);
            ModContainer mc = Loader.instance().activeModContainer();
            String prefix = mc == null || (mc instanceof InjectedModContainer && ((InjectedModContainer)mc).wrappedContainer instanceof FMLContainer) ? "minecraft" : mc.getModId().toLowerCase();
            if (!oldPrefix.equals(prefix) && oldPrefix.length() > 0)
            {
                FMLLog.log.info("Potentially Dangerous alternative prefix `{}` for name `{}`, expected `{}`. This could be a intended override, but in most cases indicates a broken mod.", oldPrefix, name, prefix);
                prefix = oldPrefix;
            }
            this.registryName = new ResourceLocation(prefix, name);
            return (T)this;
        }

        public final String toString() {
            return delegate.name().toString();
        }

        @Override
        public final T setRegistryName(ResourceLocation name){ return setRegistryName(name.toString()); }
        public final T setRegistryName(String modID, String name){ return setRegistryName(modID + ":" + name); }
        @Override
        @Nullable
        public final ResourceLocation getRegistryName()
        {
            if (delegate.name() != null) return delegate.name();
            return registryName != null ? registryName : null;
        }

        @Override
        public final Class<T> getRegistryType() { return (Class<T>) token.getRawType(); };
    }
