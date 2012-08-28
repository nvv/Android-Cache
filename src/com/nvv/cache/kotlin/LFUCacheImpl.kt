package com.nvv.cache.kotlin

import java.util.LinkedList
import java.util.List

public class LFUCacheImpl <K, V> : AbstractCache<K, V>()  {

    protected val mCache : List<CacheElement<K, V>> = LinkedList<CacheElement<K, V>>()

    public override fun put(key: K, value: V) {
        val element = mTable.get(key)

        if (updateCacheElement(element, key, value)) {
            return
        }

        val cacheElement = createCacheElement(key, value)

        if (isFull()) {
            val elem = removeLfuElement()
            mTable.remove(elem?.key)
        }

        mCache.add(cacheElement);
        mTable.put(key, cacheElement);
    }

    protected override fun itemsCount(): Int {
        return mCache.size()
    }

    public override fun get(key: K): V? {
        val element = mTable.get(key)
        if (element != null) {
            element.hitCount++
            return element.value
        }
        return null;
    }
    public override fun print() {
        System.out.print("{ ")
        mCache.forEach() {
            System.out.print("[ " + it.key + " - " + it.value + " : " + it.hitCount + " ] ")
        }
        System.out.println("}")
    }

    public fun removeLfuElement() : CacheElement<K, V>? {
        val leastElement = leastHit()
        mCache.remove(leastElement)
        return leastElement
    }

    public fun leastHit() : CacheElement<K, V>? {
        var lowestElement : CacheElement<K, V> = mCache.get(0)
        for (element : CacheElement<K, V> in mCache) {
            if (element.hitCount < lowestElement.hitCount) {
                lowestElement = element
            }
        }
        return lowestElement;
    }
}