package me.mrgeneralq.sleepmost.core.flags.serialization;

public interface IValueSerialization<V>
{
    default Object serialize(V value)
    {
        return value;
    }
    V parseValueFrom(Object object);
}
