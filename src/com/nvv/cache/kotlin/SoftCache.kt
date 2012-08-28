package com.nvv.cache.kotlin;

import java.util.HashMap
import java.lang.ref.SoftReference
import java.util.Map

public class SoftCache<K, V> {

    private val mCacheMap : Map<K, SoftReference<V>?> = HashMap<K, SoftReference<V>?>()

    public fun get(key : K) : V? {
        return mCacheMap.get(key)?.get()
    }

    public fun put(key : K, value :V ) : V? {
        val softRef : SoftReference<V>? = mCacheMap.put(key, SoftReference<V>(value));

        val oldValue = softRef?.get();
        softRef?.clear();

        return oldValue;
    }

    public fun remove(key : K) : V? {
        val softRef = mCacheMap.remove(key);

        val oldValue = softRef?.get();
        softRef?.clear();

        return oldValue;
    }

}
