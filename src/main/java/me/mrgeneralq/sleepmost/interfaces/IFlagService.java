package me.mrgeneralq.sleepmost.interfaces;

import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import org.bukkit.World;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface IFlagService
{
    void reportIllegalValues();
    Map<World, Map<ISleepFlag<?>, Object>> getWorldsWithIllegalValues();
    <V> void setStringValueAt(ISleepFlag<V> flag, World world, String stringValue);
    List<String> getValuesSuggestions(ISleepFlag<?> flag);



    <V> String getValueDisplayName(ISleepFlag<V> flag, Object value);
    <V, R> R flagAction(ISleepFlag<V> flag, Function<ISleepFlag<V>, R> function);
}
