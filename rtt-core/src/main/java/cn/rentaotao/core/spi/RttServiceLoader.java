package cn.rentaotao.core.spi;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rtt
 * @date 2024/7/11 09:36
 */
public class RttServiceLoader {

    private static final Map<Class<?>, Collection<Class<?>>> SERVICES = new ConcurrentHashMap<>();

    // 每次获取都是独立的实例，也可以根据实际情况改造为每次获取都是同一实例
    public static <T> Collection<T> load(Class<T> service) {
        if (SERVICES.containsKey(service)) {
            // 从缓存中获取
            return createServiceInstances(service);
        }
        Collection<T> set = new LinkedHashSet<>();
        Collection<Class<?>> cache = new LinkedHashSet<>();
        for (T t : ServiceLoader.load(service)) {
            set.add(t);
            cache.add(t.getClass());
        }
        if (!cache.isEmpty()) {
            SERVICES.put(service, cache);
        }
        return set;
    }

    @SuppressWarnings("unchecked")
    private static <T> Collection<T> createServiceInstances(Class<T> service) {
        Collection<Class<?>> services = SERVICES.get(service);
        Collection<T> set = new LinkedHashSet<>();
        if (services == null || services.isEmpty()) {
            return set;
        }
        for (Class<?> s : services) {
            try {
                T t = (T) s.getDeclaredConstructor().newInstance();
                set.add(t);
            } catch (Exception e) {
                // no-op
            }
        }
        return set;
    }
}
