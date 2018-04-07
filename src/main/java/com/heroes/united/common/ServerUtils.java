package com.heroes.united.common;

import java.util.Iterator;

public class ServerUtils {
    public ServerUtils() {

    }

    public static <T> T nonNull(T[] iter) {
        Object[] var1 = iter;
        int var2 = iter.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            T t = (T) var1[var3];
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    public static <T> T nonNull(Iterable<T> iter) {
        Iterator var1 = iter.iterator();
        Object t;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            t = var1.next();
        } while(t == null);

        return (T) t;
    }

    public static <T> T nonNull(Iterable iter, Class<T> filter) {
        Iterator var2 = iter.iterator();
        Object obj;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            obj = var2.next();
        } while(obj == null || !filter.isInstance(obj));

        return (T) obj;
    }
}