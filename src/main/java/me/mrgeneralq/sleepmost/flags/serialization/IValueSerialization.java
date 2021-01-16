package me.mrgeneralq.sleepmost.flags.serialization;

public interface IValueSerialization<V>
{
    default Object serialize(V value)
    {
        return value;
    }
    V parseValueFrom(Object object);
}
