package com.nvv.cache.kotlin

import java.util.HashMap
import java.util.Map

public abstract class AbstractCache<K, V> {

    private val MAX_ITEMS_IN_CACHE : Int = 3

    protected val mTable : Map<K, CacheElement<K, V>> = HashMap<K, CacheElement<K, V>>()

    protected fun isFull() : Boolean {
        return itemsCount() >= MAX_ITEMS_IN_CACHE;
    }

    protected fun createCacheElement(key : K, value : V) : CacheElement<K, V> {
        val cacheElement = CacheElement<K, V>()
        cacheElement.key = key;
        cacheElement.value = value;
        return cacheElement;
    }

    protected fun updateCacheElement(element : CacheElement<K, V>? , key : K , value : V) : Boolean {
        if (element != null) {
            element.key = key;
            element.value = value;
            return true;
        }

        return false;
    }

    public abstract fun put(key : K, value :V)

    public abstract fun get(key : K) : V?

    public abstract fun print()

    protected abstract fun itemsCount() : Int
}