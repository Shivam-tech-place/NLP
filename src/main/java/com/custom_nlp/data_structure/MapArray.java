package com.custom_nlp.data_structure;

import java.util.HashMap;
import java.util.Map;

public class MapArray<T> {
    private final Map<String, T>[] buckets;
    private final int capacity;

    @SuppressWarnings("unchecked")
    public MapArray(int capacity) {
        this.capacity = capacity;
        this.buckets = new HashMap[capacity];
        // Do not initialize the maps here (lazy init on put)
    }

    private int index(String key) {
        return (key.hashCode() & 0x7FFFFFFF) % capacity;
    }

    public void put(String key, T value) {
        int idx = index(key);
        if (buckets[idx] == null) {
            buckets[idx] = new HashMap<>();
        }
        buckets[idx].put(key, value);
    }

    public T get(String key) {
        int idx = index(key);
        if (buckets[idx] == null) return null;
        return buckets[idx].get(key);
    }

    public boolean contains(String key) {
        int idx = index(key);
        return buckets[idx] != null && buckets[idx].containsKey(key);
    }

    public boolean remove(String key) {
        int idx = index(key);
        if (buckets[idx] == null) return false;
        return buckets[idx].remove(key) != null;
    }

    public int size() {
        int total = 0;
        for (Map<String, T> bucket : buckets) {
            if (bucket != null) {
                total += bucket.size();
            }
        }
        return total;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            buckets[i] = null;  // Free memory
        }
    }
}
