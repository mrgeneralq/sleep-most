package me.mrgeneralq.sleepmost.models;

public class SleepMostFlag<T> {

    private final String name;
    private final Class<T> type;

    public SleepMostFlag(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }
}
