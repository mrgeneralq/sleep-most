package me.mrgeneralq.sleepmost.repositories;

public interface IRepository<K, V> {

    V get(K key);
    void set(K key, V value);
    boolean exists(K key);
    void remove(K key);

}
