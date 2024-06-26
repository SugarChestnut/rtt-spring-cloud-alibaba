package cn.rentaotao.core.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Map实现，完全兼容java.util.HashMap
 *
 */
public class JsonMap<K, V> extends HashMap<K, V> {

    public JsonMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public JsonMap(int initialCapacity) {
        super(initialCapacity);
    }

    public JsonMap() {
    }

    public JsonMap(Map<? extends K, ? extends V> m) {
        super(m);
    }
}