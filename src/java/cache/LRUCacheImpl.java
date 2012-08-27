package cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LRUCacheImpl<K, V> extends AbstractCache<K, V> {

    protected List<CacheElement<K, V>> mCache = new ArrayList<CacheElement<K, V>>();

    @Override
    public void put(K key, V value) {
        CacheElement<K, V> element = mTable.get(key);

        if (updateCacheElement(element, key, value)) {
            Collections.swap(mCache, mCache.indexOf(element), 0);
            return;
        }

        CacheElement<K, V> cacheElement = createCacheElement(key, value);

        if (isFull()) {
            CacheElement<K, V> elem = mCache.remove(mCache.size() - 1);
            mTable.remove(elem.getKey());
        }

        mCache.add(0, cacheElement);
        mTable.put(key, cacheElement);
    }

    @Override
    public final synchronized V get(K key) {
        CacheElement<K, V> element = mTable.get(key);
        if (element != null) {
            Collections.swap(mCache, mCache.indexOf(element), 0);
            return element.getValue();
        }
        return null;
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
