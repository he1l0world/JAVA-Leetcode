package org.leetcode.jdk;

import java.util.*;

public class MyMap<K, V> implements Iterable<MyMap.Entry<K, V>> {
    private Entry<K, V>[] table;
    static final int INITIAL_CAPACITY = 16;
    static final double DEFAULT_LOAD_FACTOR = 0.75;
    private int threshold;
    private int size;
    private double loadFactor = 0.75;

    public MyMap() {
        this(INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyMap(int capacity, double loadFactor) {
        this.size = 0;
        this.threshold = (int) (capacity * loadFactor);
        this.loadFactor = loadFactor;
        table = new Entry[capacity];
    }

    public int size(){
        return size;
    }

    public V get(Object key) {
        Entry<K, V> entry = getEntry(hash(key), (K) key);
        return entry == null ? null : entry.value;
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value);
    }

    private V putVal(int hash, K key, V value){
        int i = hash & (table.length - 1);
        for(Entry<K, V> entry = table[i]; entry != null; entry = entry.next){
            if (entry.hash == hash && Objects.equals(entry.key, key)){
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        table[i] = new Entry(hash, key, value, table[i]);
        if(++size > threshold)
            resize();
        return null;
    }

    public V remove(Object key){
        return removeEntry(hash(key), (K) key);
    }

    private V removeEntry(int hash, K key){
        int i = hash & (table.length -1);
        for(Entry<K, V> entry = table[i], prev = null; entry != null; prev = entry, entry = entry.next){
            if (entry.hash == hash && Objects.equals(entry.key, key)){
                --size;
                if (prev == null){
                    table[i] = entry.next;
                    return entry.value;
                }else {
                    prev.next = entry.next;
                    return entry.value;
                }
            }
        }

        return null;
    }

    private void resize(){
        Entry<K, V>[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1;
        Entry<K, V>[] newTable = new Entry[newCapacity];

        for(Entry<K, V> head: oldTable){
            while(head != null){
                int i = head.hash & (newCapacity - 1);
                Entry<K, V> next = head.next;
                head.next = newTable[i];
                newTable[i] = head;
                head = next;
            }
        }

        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }

    private int hash(Object key){
        return key == null? 0 : (key.hashCode() ^ (key.hashCode() >>> 16));
    }

    private Entry<K, V> getEntry(int hash, K key){
        Entry<K, V> entry = table[hash & (table.length - 1)];
        while(entry != null && !Objects.equals(key, entry.key)){
            entry = entry.next;
        }
        return entry;
    }

    public final static class Entry<K, V> {
        public final K key;
        public V value;
        final int hash;
        Entry<K, V> next;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new EntryIterator();
    }

    private class EntryIterator implements Iterator<Entry<K, V>> {

        private Entry<K, V> current;
        private Entry<K, V> next;
        private int index;

        EntryIterator(){
            current = next = null;
            index = 0;
            do{}while(index < table.length && (next = table[index++]) == null);
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Entry<K, V> next() {
            if((next = (current = next).next) == null){
                do{}while(index < table.length && (next = table[index++]) == null);
            }
            return current;
        }
    }
}
