package cache;

public class CacheElement<K, V> {

    /**
     * Cache element value.
     */
    private V mValue;

    /**
     * Cache element key.
     */
    private K mKey;

    /**
     * Counter for LFU algorithm.
     */
    private int mHitCount;

    public V getValue() {
        return mValue;
    }

    public void setValue(V value) {
        mValue = value;
    }

    public K getKey() {
        return mKey;
    }

    public void setKey(K key) {
        mKey = key;
    }

    public int getHitCount() {
        return mHitCount;
    }

    public void incrementHitCount() {
        mHitCount++;
    }

    public void setHitCount(int hitCount) {
        mHitCount = hitCount;
    }
}
