package com.nvv.cache.java;

import java.util.LinkedList;
import java.util.List;

public class LFUCacheImpl<K, V> extends AbstractCache<K, V>  {

    protected List<CacheElement<K, V>> mCache = new LinkedList<CacheElement<K, V>>();

    @Override
    public void put(K key, V value) {

        CacheElement<K, V> element = mTable.get(key);

        if (updateCacheElement(element, key, value)) {
            return;
        }

        CacheElement<K, V> cacheElement = createCacheElement(key, value);

        if (isFull()) {
            CacheElement<K, V> elem = removeLfuElement();
            mTable.remove(elem.getKey());
        }

        mCache.add(cacheElement);
        mTable.put(key, cacheElement);
    }

    @Override
    public final synchronized V get(K key) {
        CacheElement<K, V> element = mTable.get(key);
        if (element != null) {
            element.incrementHitCount();
            return element.getValue();
        }
        return null;
    }

    @Override
    public void print() {
        System.out.print("{ ");
        for (CacheElement<K, V> cacheElement : mCache) {
            System.out.print("[ " + cacheElement.getKey() + " - " + cacheElement.getValue() + " : " +
                    cacheElement.getHitCount() + " ] ");
        }
        System.out.println("}");
    }

    @Override
    protected int itemsCount() {
        return mCache.size();
    }

    public CacheElement<K, V> removeLfuElement() {
        CacheElement<K, V> leastElement = leastHit();
        mCache.remove(leastElement);
        return leastElement;
    }

    public CacheElement<K, V> leastHit() {

        CacheElement<K, V> lowestElement = null;
        for (CacheElement<K, V> element : mCache) {
            if (lowestElement == null) {
                lowestElement = element;
            } else {
                if (element.getHitCount() < lowestElement.getHitCount()) {
                    lowestElement = element;
                }
            }
        }
        return lowestElement;
    }
}
