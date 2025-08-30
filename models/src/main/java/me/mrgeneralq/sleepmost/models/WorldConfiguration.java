package me.mrgeneralq.sleepmost.models;

import java.util.ArrayList;
import java.util.List;

public class WorldConfiguration {

    private final String worldName;
    private final List<SleepMostFlag<?>> flags;

    public WorldConfiguration(String worldName) {
        this.worldName = worldName;
        this.flags = new ArrayList<>();
    }

    public String getWorldName() {
        return worldName;
    }

    public List<SleepMostFlag<?>> getFlags() {
        return flags;
    }
}
