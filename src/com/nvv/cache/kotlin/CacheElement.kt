package com.nvv.cache.kotlin

public class CacheElement<K, V> {

    /**
    * Cache element value.
    */
    var value: V? = null

    /**
    * Cache element key.
    */
    var key: K? = null

    /**
    * Counter for LFU algorithm.
    */
    var hitCount : Int = 0

}