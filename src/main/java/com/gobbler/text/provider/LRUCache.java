package com.gobbler.text.provider;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Objects;

/**
 * A linked hashmap implementation of LRU cache used by the loader
 * @author vm023561
 */
public class LRUCache<K, V>
{

    private final Map<K, V> cacheMap;

    public LRUCache (final int cacheSize)
    {

        // true = use access order instead of insertion order.
        this.cacheMap = new LinkedHashMap<K, V>(cacheSize, 0.75f, true)
        {
            @Override
            protected boolean removeEldestEntry (Map.Entry<K, V> eldest)
            {
                // When to remove the eldest entry.
                return size() > cacheSize; // Size exceeded the max allowed.
            }
        };
    }

    public synchronized void put (K key, V elem)
    {
        cacheMap.put(key, elem);
    }

    public synchronized V get (K key)
    {
        return cacheMap.get(key);
    }

    public synchronized V atomicGetAndSet (K key, V elem)
    {
        V result = get(key);
        put(key, elem);
        return result;
    }

    /**
     * {@link Override}
     */
    @Override public boolean equals (Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        LRUCache<?, ?> lruCache = (LRUCache<?, ?>) o;
        return Objects.equal(cacheMap, lruCache.cacheMap);
    }

    @Override public int hashCode ()
    {
        return Objects.hashCode(cacheMap);
    }

    @Override public String toString ()
    {
        return Objects.toStringHelper(this)
                .add("cacheMap", cacheMap)
                .toString();
    }
}
