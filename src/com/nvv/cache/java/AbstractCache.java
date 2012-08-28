package com.nvv.cache.java;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    private static final int MAX_ITEMS_IN_CACHE = 3;

    protected Map<K, CacheElement<K, V>> mTable = new HashMap<K, CacheElement<K, V>>();

    protected boolean isFull() {
        return itemsCount() >= MAX_ITEMS_IN_CACHE;
    }

    protected CacheElement<K, V> createCacheElement(K key, V value) {
        CacheElement<K, V> cacheElement = new CacheElement<K, V>();
        cacheElement.setKey(key);
        cacheElement.setValue(value);
        return cacheElement;
    }

    protected boolean updateCacheElement(CacheElement<K, V> element, K key, V value) {
        if (element != null) {
            element.setValue(value);
            element.setKey(key);

            return true;
        }

        return false;
    }

    public abstract void put(K key, V value);

    public abstract V get(K key);

    public abstract void print();

    protected abstract int itemsCount();
}
