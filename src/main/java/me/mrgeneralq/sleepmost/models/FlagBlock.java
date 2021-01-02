package me.mrgeneralq.sleepmost.models;

import me.mrgeneralq.sleepmost.enums.SleepmostFlag;
import org.bukkit.World;

public class FlagBlock {

    private SleepmostFlag flag;
    private World world;
    private Object value;

    public FlagBlock(SleepmostFlag flag, World world, Object value) {
        this.flag = flag;
        this.world = world;
        this.value = value;
    }

    public SleepmostFlag getFlag() {
        return flag;
    }

    public void setFlag(SleepmostFlag flag) {
        this.flag = flag;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
