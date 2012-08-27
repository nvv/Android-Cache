package com.nvv.cache;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FIFOCacheImpl<K, V> extends AbstractCache<K, V> {

    protected Queue<CacheElement<K, V>> mCache = new ConcurrentLinkedQueue<CacheElement<K, V>>();

    public final synchronized void put(K key, V value) {

        CacheElement<K, V> element = mTable.get(key);

        if (updateCacheElement(element, key, value)) {
            return;
        }

        CacheElement<K, V> cacheElement = createCacheElement(key, value);

        if (isFull()) {
            CacheElement<K, V> elem = mCache.poll();
            mTable.remove(elem.getKey());
        }

        mCache.offer(cacheElement);
        mTable.put(key, cacheElement);
    }

    public final synchronized V get(K key) {
        CacheElement<K, V> element = mTable.get(key);
        return element != null ? element.getValue() : null;
    }

    @Override
    public void print() {
        System.out.print("{ ");
        for (CacheElement<K, V> cacheElement : mCache) {
            System.out.print("[ " + cacheElement.getKey() + " - " + cacheElement.getValue() + " ] ");
        }
        System.out.println("}");
    }

    @Override
    protected int itemsCount() {
        return mCache.size();
    }
}
