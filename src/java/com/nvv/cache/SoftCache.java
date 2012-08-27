package com.nvv.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class SoftCache<K, V> {
    private Map<K, SoftReference<V>> mCacheMap = new HashMap<K, SoftReference<V>>();

    public V get(K key) {
        SoftReference<V> softRef = mCacheMap.get(key);

        return softRef == null ? null : softRef.get();
    }

    public V put(K key, V value) {
        SoftReference<V> softRef = mCacheMap.put(key, new SoftReference<V>(value));

        if (softRef == null) {
            return null;
        }

        V oldValue = softRef.get();
        softRef.clear();

        return oldValue;
    }

    public V remove(K key) {
        SoftReference<V> softRef = mCacheMap.remove(key);

        if (softRef == null) {
            return null;
        }

        V oldValue = softRef.get();
        softRef.clear();

        return oldValue;
    }
}
