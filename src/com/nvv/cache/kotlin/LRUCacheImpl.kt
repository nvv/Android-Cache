package com.nvv.cache.kotlin

import java.util.List
import java.util.ArrayList
import java.util.Collections

public class LRUCacheImpl<K, V> : AbstractCache<K, V>() {

    protected val mCache : List<CacheElement<K, V>> = ArrayList<CacheElement<K, V>>();

    protected override fun itemsCount(): Int {
        return mCache.size()
    }

    public override fun get(key: K): V? {
        val element = mTable.get(key)
        if (element != null) {
            Collections.swap(mCache, mCache.indexOf(element), 0)
            return element.value
        }
        return null;
    }

    public override fun print() {
        System.out.print("{ ");
        mCache.forEach() {
            System.out.print("[ " + it.key + " - " + it.value + " ] ");
        }
        System.out.println("}");
    }

    public override fun put(key: K, value: V) {
        val element = mTable.get(key)

        if (updateCacheElement(element, key, value)) {
            Collections.swap(mCache, mCache.indexOf(element), 0)
            return
        }

        val cacheElement = createCacheElement(key, value);

        if (isFull()) {
            val elem = mCache.remove(mCache.size() - 1);
            mTable.remove(elem.key);
        }

        mCache.add(0, cacheElement);
        mTable.put(key, cacheElement);
    }
}