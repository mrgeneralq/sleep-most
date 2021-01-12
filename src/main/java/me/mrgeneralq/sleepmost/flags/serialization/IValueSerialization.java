package me.mrgeneralq.sleepmost.flags.serialization;

public interface IValueSerialization<V>
{
    default Object serialize(V value)
    {
        return value;
    }
    default String getDisplayName(V value)
    {
        return value.toString();
    }
    V parseValueFrom(Object object);
}
