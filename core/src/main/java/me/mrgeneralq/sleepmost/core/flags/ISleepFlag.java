package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.serialization.IValueSerialization;
import org.bukkit.World;

public interface ISleepFlag<V> {
    String getName();
    String getValueDescription();
    boolean isValidValue(Object value);
    V getValueAt(World world);
    void setValueAt(World world, V value);
    IValueSerialization<V> getSerialization();
    V getDefaultValue();

    default String getDisplayName(V value) {
        return value.toString();
    }
}
