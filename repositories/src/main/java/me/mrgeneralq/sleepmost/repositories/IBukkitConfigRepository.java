package me.mrgeneralq.sleepmost.repositories;

public interface IBukkitConfigRepository<T> {

    T get(String key);
    void set(T value);
    void reload();
    void save();
    void save(T value);
    void saveDefault();
    void reloadAll();

}
