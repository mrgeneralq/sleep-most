package me.mrgeneralq.sleepmost.core.flags.values;

public abstract class AbstractFlagValue<V>
{
    protected final V value;

    public AbstractFlagValue(V value)
    {
        this.value = value;
    }
    public V get()
    {
        return this.value;
    }
    public abstract String serialize();
}
