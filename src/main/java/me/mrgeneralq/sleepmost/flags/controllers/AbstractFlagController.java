package me.mrgeneralq.sleepmost.flags.controllers;
import me.mrgeneralq.sleepmost.flags.ISleepFlag;
import org.bukkit.World;

public abstract class AbstractFlagController<V>
{
    private ISleepFlag<V> flag;

    public void setFlag(ISleepFlag<V> flag)
    {
        this.flag = flag;
    }
    protected ISleepFlag<V> getFlag()
    {
        return this.flag;
    }
    public abstract V getValueAt(World world);
    public abstract void setValueAt(World world, V value);

    public void setValueAt(World world, String textValue)
    {
        V value = this.flag.parseValueFrom(textValue);

        setValueAt(world, value);
    }
}
