package com.nvv.cache.kotlin

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.Queue

public class FIFOCacheImpl<K, V> : AbstractCache<K, V>() {

    protected val mCache : Queue<CacheElement<K, V>> = ConcurrentLinkedQueue<CacheElement<K, V>>()

    public override fun put(key: K, value: V) {
        val element = mTable.get(key)

        if (updateCacheElement(element, key, value)) {
            return;
        }

        val cacheElement = createCacheElement(key, value)

        if (isFull()) {
            val elem = mCache.poll();
            mTable.remove(elem?.key);
        }

        mCache.offer(cacheElement);
        mTable.put(key, cacheElement);
    }
    public override fun print() {
        System.out.print("{ ");
        mCache.forEach() {
            System.out.print("[ " + it.key + " - " + it.value + " ] ")
        }
        System.out.println("}");
    }

    protected override fun itemsCount(): Int {
        return mCache.size();
    }
    public override fun get(key: K): V? {
        return mTable.get(key)?.value;
    }
}